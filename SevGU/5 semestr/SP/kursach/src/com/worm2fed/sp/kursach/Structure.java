package com.worm2fed.sp.kursach;

import java.io.*;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Structure {
	public 	boolean 				PrintCards,
									PrintSymb;
	public 	String 					file;
	
	private int 					PosFragment,
	 								Schak,
									CodeLen,
									Value;
	private String 					SegmentName,
	 								Listing,
	 								Code,
	 								ListOfErrors;
	private boolean 				ErrorFlag;
	private RandomAccessFile 		ObjectCode;
	private PrintWriter 			ListingFile,
									Cardfile,
									SymbolFile;
	
	private TableOfCommand 			TabOfCom 		= new TableOfCommand(5);
	private TableOfDirective 		TabOfDir 		= new TableOfDirective(4);
	private TableOfRegisters 		TabOfReg 		= new TableOfRegisters(16);
	private ArrayList<SymbolTable> 	SymbolTab 		= new ArrayList<SymbolTable>();
	private Cards 					H;
	private ArrayList<Cards> 		T 				= new ArrayList<Cards>();
	private Cards 					E;
	private PositionInProgramm 		position 		= new PositionInProgramm();

	final 	int 					StructureError 	= 0;
	final 	int 					OperandsError 	= 1;
	final 	int 					UnknownCommand 	= 2;
	final 	int 					ReferError 		= 3;
	final 	int 					MultipleError 	= 4;
	final 	int 					AddressingError = 5;

	String[] Error 		= { "Ошибка в структуре программы", "Несоответствие типов и/или количества операндов",
			"Неизвестная команда (инструкция)", "Ссылка на неопределённое имя или метку",
			"Повторное определение метки или переменной", "Неверная адресация", "Переполнение таблиц символов" };

	String[] ComAndDir 	= { "SEGMENT", 	"ORG", 	"DB", 	"DW", 	"ENDS",	"END", "OFFSET", 	"MOV", "XCHG", 	"IMUL", "LOOP", "INT" };
	String[] Types 		= { "Dir",		"Dir", 	"Dir", 	"Dir", 	"Dir", 	"Dir", "Dir", 		"Com", "Com", 	"Com", 	"Com", 	"Com" };

	Structure() {
		PosFragment 	= 0;
		SegmentName	 	= "";
		Listing 		= "";
		ErrorFlag 		= false;
		Schak 			= 0;
		ListOfErrors 	= "";
		CodeLen 		= 0;
		Code 			= "";
	}

	public boolean Begin(String MnemonicCode) throws IOException {
		// первый просмотр
		firstPass(MnemonicCode);

		/*
		 * писать карты в объектный файл будем только в случае успешного второго
		 * просмотра
		 */
		if (secondPass(MnemonicCode)) {
			// запись листинга
			writeListing();
			// запись объектного кода
			writeObject(); 
			
			// запись карт
			if (PrintCards)
				writeCards();
			
			// запись таблицы символических имен
			if (PrintSymb)
				writeSymbol(); 

			return true;
		} else {
			// запись листинга
			writeListing(); 		
			
			return false;
		}
	}
	
	// первый просмотр
	void firstPass(String t) {
		String[] 	Letters;
		String 		Text 	= t.toString();
		// разбивание текста на массив строк
		String[] 	Lines 	= Text.split("\\n+"); 			
		
		/*
		 * основной цикл, в котором последовательно происходит проверка всех
		 * строк программы
		 */
		for (int i = 0; i < Lines.length; i++) {
			/*
			 * текущая строка:
			 * в верхний регистр и убираем пробелы слева и справа
			 */
			String CurrentLine = Lines[i].toUpperCase().trim();
			CurrentLine = DeleteComments(CurrentLine); 		// убираем комментарии
			
			// пропускаем пустые строки
			if (CurrentLine.trim().equals("")) 				
				continue;
			
			// команды в текущем месте сегмента
			String[] CurrentCommand = position.getCurrCom(PosFragment).split(",+");
			// Команды в следующем после текущего места сегмента
			String[] NextCommand 	= position.getNextCom(PosFragment).split(",+");

			// если мы находимся в области определения сегмента, то... 
			if (PosFragment == 0) {
				// разбиваем текущую линию на отдельные слова
				Letters = CurrentLine.split(" +"); 
				
				// если такая команда вообще существует...
				if (OurComAndDir(Letters, ComAndDir)) {
					// если сегмент есть вообще
					if (OurComAndDir(Letters, CurrentCommand)) {
						// если сразу идёт сегмент, то ошибка...
						if (Letters[0].equals(CurrentCommand[0]))
							Err((i + 1) + ". " + "Не указано имя сегмента\n");
						else if (Letters[1].equals(CurrentCommand[0]))
							SegmentName = Letters[0].toUpperCase();
						else
							Err((i + 1) + ". " + "Имя сегмента состоит из более чем 1 слова\n");
					} else {
						// если команда из другой области
						if (OurComAndDir(Letters, ComAndDir))
							Err((i + 1) + ". " + Error[StructureError] + ".\n");
						// если просто забыли указать СЕГМЕНТ
						else
							Err((i + 1) + ". " + "Забыли указать ключевое слово SEGMENT\n");
					}
					
					if (i == (Lines.length - 1)) {
						Err((i + 1) + ". " + "Неожиданное окончание сегмента\n");
						break;
					}
					
					// двигаемся дальше
					PosFragment = position.getNextPosSeg(PosFragment); 
					continue;
				} else {
					Err((i + 1) + ". " + Error[UnknownCommand] + "\n");
					continue;
				}
			}
			
			// если находимся в области команд, то...
			if (PosFragment == 1) {
				// разбиваем текущую строку на отдельные слова
				Letters = CurrentLine.split("[ :]+"); 
				
				// если такая команда вообще существует...
				if (OurComAndDir(Letters, ComAndDir)) {
					// если она является нормальной для данной области
					if (OurComAndDir(Letters, CurrentCommand)) {
						// подготовка к распознаванию метки, команды и операндов...
						String 	Metka = "", 
								Command = "", 
								Op1 = "", 
								Op2 = ""; 
						
						// если метка есть
						if (CurrentLine.indexOf(':') != -1) 
							// то выделить и запомнить её
							Metka = CurrentLine.substring(0, CurrentLine.indexOf(':')); 
						
						// метка уже не нужна в основной строке
						CurrentLine = CurrentLine.substring(CurrentLine.indexOf(':') + 1, CurrentLine.length()).trim(); 

						Pattern p = Pattern.compile("[a-zA-Z]{1}[a-zA-Z\\d]*");
						Matcher m = p.matcher(Metka);
						boolean b = m.matches();
						
						if (!Metka.equals("")) {
							if (!b) {
								Err((i + 1) + ". " + "Неправильное определение метки" + "\n");
								continue;
							} else if (DoubleVarOrLabel(Metka))
								Err((i + 1) + ". " + Error[MultipleError] + "\n");
							else
								SymbolTab.add(new SymbolTable(Metka, HexSchak(Schak, 4), "Label"));
						}

						int index = 0;
						char c = CurrentLine.charAt(index);
						
						// поиск команды
						while (index != CurrentLine.length() - 1 && c != ' ') {
							Command += c;
							index++; 
							c = CurrentLine.charAt(index);
						}
						
						// избавляемся от команды в основной строке
						CurrentLine = CurrentLine
								.substring(CurrentLine.indexOf(Command) + Command.length(), CurrentLine.length())
								.trim();
						// если после команды нет операндов
						if (CurrentLine.equals("")) {
							Err((i + 1) + ". " + Error[OperandsError] + "\n");
							continue;
						}
						
						// находим тип операции
						String OperationType = FindType(Command);
						// если неизвестный тип (неправильно расположили операцию и операнды)
						if (OperationType.equals("Unknown")) {
							Err((i + 1) + ". " + Error[UnknownCommand] + "\n");
							continue;
						// если обнаружена команда
						} else if (OperationType.equals("Com")) {
							// находим количество операндов для данной команды
							int OpNum = TabOfCom.getOpNum(Command);
							// если операндов должно быть 2
							if (OpNum == 2) {
								// поиск операндов
								if (CurrentLine.indexOf(',') != -1) {
									Op1 = CurrentLine.substring(0, CurrentLine.indexOf(',')).trim();
									Op2 = CurrentLine.substring(CurrentLine.indexOf(',') + 1, CurrentLine.length())
											.trim();
								// если не нашли запятую
								} else {
									Err((i + 1) + ". " + "Между операндами отсутствует запятая" + "\n");
									continue;
								}
							// если 1 операнд
							} else
								Op1 = CurrentLine.substring(0, CurrentLine.length()).trim();
						// если операция - директива
						} else if (OperationType.equals("Dir"))
							Op1 = CurrentLine.substring(0, CurrentLine.length()).trim();

						// определение типа первого и второго операнда
						String Op1Kind = OpKind(Op1, 1);
						String Op2Kind = OpKind(Op2, 1); 
						
						// в случае неверной адресации
						if (Op1Kind.equals("UnkAdr") || Op2Kind.equals("UnkAdr")) {
							Err((i + 1) + ". " + Error[AddressingError] + "\n");
							continue;
						}
						
						// если нашли несоответствие типов операндов
						if ((OperationType.equals("Com") && !TabOfCom.isRightOperands(Command, Op1Kind, Op2Kind))
								|| (OperationType.equals("Dir")
										&& !TabOfDir.isRightOperands(Command, Op1Kind, Op2Kind))) { 
							Err((i + 1) + ". " + Error[OperandsError] + "\n");
							continue;
						}
						
						if (OperationType.equals("Dir")) {
							Schak = Value;
							continue;
						}
						
						// увеличиваем СЧАК
						Schak += TabOfCom.getInstrLen(Command, Op1Kind, Op2Kind);
					// если попали в следующую область
					} else if (OurComAndDir(Letters, NextCommand)) {
						PosFragment = position.getNextPosSeg(PosFragment);
						i--;
						continue;
					// если сразу идет закрытие сегмента
					} else if (OurComAndDir(Letters, position.getCurrCom(3).split(",+"))) {
						PosFragment = 3;
						i--;
						continue;
					// если сразу идёт окончание программы
					} else if (OurComAndDir(Letters, position.getCurrCom(4).split(",+"))) {
						PosFragment = 4;
						Err((i + 1) + ". " + "Нет директивы закрытия сегмента" + "\n");
						i--;
						continue;
					// если команда не подходящая под предыдущие описания
					} else {
						Err((i + 1) + ". " + Error[StructureError] + "\n");
						continue;
					}
				// если нет ...
				} else {
					Err((i + 1) + ". " + Error[UnknownCommand] + "\n");
					continue;
				}
			}

			// если находимся в области данных, то...
			if (PosFragment == 2) {
				// разбиваем текущую строку на отдельные слова
				Letters = CurrentLine.split(" +");
				// если команда существует...
				if (OurComAndDir(Letters, ComAndDir)) {
					// если она является нормальной для данной области
					if (OurComAndDir(Letters, CurrentCommand)) {
						if (Letters[0].equals("") != true && OurComAndDir(Letters[1], CurrentCommand)) {
							// инициализация двух возможных операндов и самой директивы
							String 	Directive 	= Letters[1], 
									Op1 		= "", 
									Op2 		= ""; 
							
							// поиск операндов
							if (CurrentLine.indexOf(',') != -1) {
								Op1 = CurrentLine.substring(CurrentLine.indexOf(Letters[1]) + Letters[1].length(),
										CurrentLine.indexOf(',')).trim();
								Op2 = CurrentLine.substring(CurrentLine.indexOf(',') + 1, CurrentLine.length()).trim();
							// если 1 операнд
							} else
								Op1 = CurrentLine.substring(CurrentLine.indexOf(Letters[1]) + Letters[1].length(),
										CurrentLine.length()).trim();
							
							// определение типа операндов
							String Op1Kind = OpKind(Op1, 1);
							String Op2Kind = OpKind(Op2, 1);
							
							// если нашли несоответствие типов операндов
							if (!TabOfDir.isRightOperands(Directive, Op1Kind, Op2Kind)) { 
								Err((i + 1) + ". " + Error[OperandsError] + "\n");
								continue;
							}
							
							if (DoubleVarOrLabel(Letters[0]))
								Err((i + 1) + ". " + Error[MultipleError] + "\n");
							else
								SymbolTab.add(new SymbolTable(Letters[0], HexSchak(Schak, 4), "VAR"));

							Schak += TabOfDir.getInstrLen(Letters[1], Op1Kind, Op2Kind);
						} else {
							Err((i + 1) + ". " + "Имя переменной не определено" + "\n");
							continue;
						}
					// если попали в следующую область
					} else if (OurComAndDir(Letters, NextCommand)) {
						PosFragment = position.getNextPosSeg(PosFragment);
						i--;
						continue;
					// если сразу идёт окончание программы
					} else if (OurComAndDir(Letters, position.getCurrCom(4).split(",+"))) {
						PosFragment = 4;
						Err((i + 1) + ". " + "Нет директивы закрытия сегмента" + "\n");
						i--;
						continue;
					// если команда не подходящая под предыдущие описания
					} else {
						Err((i + 1) + ". " + Error[StructureError] + "\n");
						continue;
					}
				// если нет ...
				} else {
					Err((i + 1) + ". " + Error[UnknownCommand] + "\n");
					continue;
				}
			}

			// если находимся в области окончания сегмента, то...
			if (PosFragment == 3) {
				// разбиваем текущую линию на отдельные слова
				Letters = CurrentLine.split(" +"); 
				// если сразу идёт закрытие сегмента, то ошибка...
				if (Letters[0].equals(CurrentCommand[0])) 
					Err((i + 1) + ". " + "Не указано имя сегмента\n");
				// если окончание имеется
				else if ((Letters.length != 1) && Letters[1].equals(CurrentCommand[0])) {
					if (Letters[0].equals(SegmentName)) {

					} else
						Err((i + 1) + ". " + "Имя сегмента при его закрытии указано неправильно\n");
				} else
					Err((i + 1) + ". " + "Слишком много букв при закрытии сегмента!" + "\n");
				
				if (i == (Lines.length - 1)) {
					Err((i + 1) + ". " + "Неожиданное окончание программы\n");
					break;
				}
				
				PosFragment = position.getNextPosSeg(PosFragment);
				continue;
			}

			// если находимся в области окончания программы, то... 
			if (PosFragment == 4) {
				// если конец программы
				if (CurrentLine.trim().equals(CurrentCommand[0])) {
					// проверяем нет ли чего в конце
					if ((Lines.length - 1) != i) {
						Err((i + 1) + ". " + "Конец у программы присутствует " + "\n");
						break;
					}
				// ошибка структуры
				} else
					Err((i + 1) + ". " + "Ожидалось окончание программы!" + "\n");
				break;
			}
		}
		if (PosFragment == 1) {
			Err((Lines.length + 1) + ". "
					+ "Неожиданное окончание программы (в этой строке нехватает директив закрытия сегмента и окончания программы)\n");
		}

		H 			= new Cards('H', SegmentName, Schak); 	// формирование карты H
		Schak 		= 0; 									// сброс СЧАКа
		PosFragment = 0; 									// сброс позиции в программе

	}
	
	// второй просмотр
	boolean secondPass(String t) {
		String[] Letters;
		String 	 Text 		= t.toString();
		String[] Lines 		= Text.split("\\n+");
		
		for (int i = 0; i < Lines.length; i++) {
			if (HaveErr(i + 1)) {
				Listing += ListOfErrors.substring(0, ListOfErrors.indexOf("\n")) + ":      " + Lines[i].trim() + "\n";
				ListOfErrors = ListOfErrors.substring(ListOfErrors.indexOf("\n") + 1, ListOfErrors.length());
				continue;
			}
			
			String CurrentLine = Lines[i].toUpperCase().trim();
			CurrentLine = DeleteComments(CurrentLine); 
			
			if (CurrentLine.trim().equals("")) {
				Listing += (i + 1) + ". " + "   :             " + Lines[i].trim() + "\n";
				continue;
			}
			
			String[] CurrentCommand = position.getCurrCom(PosFragment).split(",+");
			String[] NextCommand = position.getNextCom(PosFragment).split(",+"); 

			if (PosFragment == 0) {
				Letters = CurrentLine.split(" +"); 
				if (OurComAndDir(Letters, ComAndDir)) {
					if (OurComAndDir(Letters, CurrentCommand)) {
						if (Letters[0].equals(CurrentCommand[0]))
							Listing += (i + 1) + ". " + "Не указано имя сегмента" + ":   " + Lines[i].trim() + "\n";
						else if (Letters[1].equals(CurrentCommand[0])) {
							SegmentName = Letters[0];
							Listing += (i + 1) + ". " + "   :                                        " + Lines[i].trim()
									+ "\n";
						} else
							Listing += (i + 1) + ". " + "Имя сегмента состоит из более чем 1 слова" + ":   "
									+ Lines[i].trim() + "\n";
					} else if (OurComAndDir(Letters, NextCommand)) {
						PosFragment = position.getNextPosSeg(PosFragment);
						i--;
						continue;
					} else if (i == (Lines.length - 1)) {
						Listing += (i + 1) + ". " + "Неожиданное окончание сегмента" + ":   " + Lines[i].trim() + "\n";
						break;
					} else {
						if (OurComAndDir(Letters, ComAndDir))
							Listing += (i + 1) + ". " + Error[StructureError] + ":   " + Lines[i].trim() + "\n";
						else 
							Listing += (i + 1) + ". " + "Забыли указать ключевое слово SEGMENT\n" + ":   "
									+ Lines[i].trim() + "\n";
					}

					PosFragment = position.getNextPosSeg(PosFragment);
					continue;
				} else {
					Listing += (i + 1) + ". " + Error[UnknownCommand] + ":   " + Lines[i].trim() + "\n";
					continue;
				}
			}

			if (PosFragment == 1) {
				Letters = CurrentLine.split("[ :]+");
				if (OurComAndDir(Letters, ComAndDir)) {
					if (OurComAndDir(Letters, CurrentCommand)) {
						String 	Metka 	= "", 
								Command = "", 
								Op1 	= "", 
								Op2 	= "";

						if (CurrentLine.indexOf(':') != -1)
							Metka = CurrentLine.substring(0, CurrentLine.indexOf(':')); 
						
						CurrentLine = CurrentLine.substring(CurrentLine.indexOf(':') + 1, CurrentLine.length()).trim();

						int index = 0;
						char c = CurrentLine.charAt(index);
						
						while (index != CurrentLine.length() - 1 && c != ' ') {
							Command += c;
							index++;
							c = CurrentLine.charAt(index);
						}
						
						CurrentLine = CurrentLine
								.substring(CurrentLine.indexOf(Command) + Command.length(), CurrentLine.length())
								.trim();

						if (CurrentLine.equals("")) {
							Listing += (i + 1) + ". " + Error[OperandsError] + ":   " + Lines[i].trim() + "\n";
							continue;
						}

						String OperationType = FindType(Command);
						if (OperationType.equals("Unknown")) {
							Listing += (i + 1) + ". " + Error[UnknownCommand] + ":   " + Lines[i].trim() + "\n";
							continue;
						} else if (OperationType.equals("Com")) {
							int OpNum = TabOfCom.getOpNum(Command);
							
							if (OpNum == 2) {
								if (CurrentLine.indexOf(',') != -1){
									Op1 = CurrentLine.substring(0, CurrentLine.indexOf(',')).trim();
									Op2 = CurrentLine.substring(CurrentLine.indexOf(',') + 1, CurrentLine.length())
											.trim();

								} else {
									Err((i + 1) + ". " + "Между операндами отсутствует запятая" + "\n");
									continue;
								}
							} else
								Op1 = CurrentLine.substring(0, CurrentLine.length()).trim();
						} else if (OperationType.equals("Dir"))
							Op1 = CurrentLine.substring(0, CurrentLine.length()).trim();

						String Op1Kind = OpKind(Op1, 2);
						String Op2Kind = OpKind(Op2, 2);

						if (Op1Kind.equals("UnkAdr") || Op2Kind.equals("UnkAdr")) {
							Listing += (i + 1) + ". " + Error[AddressingError] + ":   " + Lines[i].trim() + "\n";
							continue;
						}
						
						if (Op1Kind.equals("UnknVar") || Op2Kind.equals("UnknVar")) { 
							Listing += (i + 1) + ". " + Error[ReferError] + ":   " + Lines[i].trim() + "\n";
							continue;
						}
						
						if ((OperationType.equals("Com") && !TabOfCom.isRightOperands(Command, Op1Kind, Op2Kind))
								|| (OperationType.equals("Dir")
										&& !TabOfDir.isRightOperands(Command, Op1Kind, Op2Kind))) {
							Listing += (i + 1) + ". " + Error[OperandsError] + ":   " + Lines[i].trim() + "\n";
							continue;
						}
						if (OperationType.equals("Dir")) {
							Listing += (i + 1) + ". " + "   :                                        " + Lines[i].trim()
									+ "\n";
							Schak = Value;
							continue;
						}
						
						// получение длины инструкции
						CodeLen = TabOfCom.getInstrLen(Command, Op1Kind, Op2Kind); 
						// получение кода инструкции
						Code = getCode(Command, Op1, Op2, Op1Kind, Op2Kind, CodeLen); 
						// формирование строки листинга
						Listing += (i + 1) + ". " + HexSchak(Schak, 4) + ":  " + Code.toUpperCase()
								+ "                      ".substring(0, 30 - 5 * CodeLen) + Lines[i].trim() + "\n";
						// формирование карты Т
						T.add(new Cards('T', Schak, CodeLen, Code)); 
						// увеличение СЧАКа на длину инструкции
						Schak += CodeLen; 
					} else if (OurComAndDir(Letters, NextCommand)) {
						PosFragment = position.getNextPosSeg(PosFragment);
						i--;
						continue;
					} else if (OurComAndDir(Letters, position.getCurrCom(3).split(",+"))) {
						PosFragment = 3;
						i--;
						continue;
					} else if (OurComAndDir(Letters, position.getCurrCom(4).split(",+"))) {
						PosFragment = 4;
						Listing += (i + 1) + ". " + "Нет директивы закрытия сегмента" + ":   " + Lines[i].trim() + "\n";
						i--;
						continue;
					} else {
						Listing += (i + 1) + ". " + Error[StructureError] + ":   " + Lines[i].trim() + "\n";
						continue;
					}
				} else {
					Listing += (i + 1) + ". " + Error[UnknownCommand] + ":   " + Lines[i].trim() + "\n";
					continue;
				}
			}

			if (PosFragment == 2) {
				Letters = CurrentLine.split(" +");
				if (OurComAndDir(Letters, ComAndDir)) {
					if (OurComAndDir(Letters, CurrentCommand)) {
						if (Letters[0].equals("") != true && OurComAndDir(Letters[1], CurrentCommand)) {
							String 	Directive 	= Letters[1], 
									Op1 		= "", 
									Op2 		= "";

							if (CurrentLine.indexOf(',') != -1) {
								Op1 = CurrentLine.substring(CurrentLine.indexOf(Letters[1]) + Letters[1].length(),
										CurrentLine.indexOf(',')).trim();
								Op2 = CurrentLine.substring(CurrentLine.indexOf(',') + 1, CurrentLine.length()).trim();
							} else 
								Op1 = CurrentLine.substring(CurrentLine.indexOf(Letters[1]) + Letters[1].length(),
										CurrentLine.length()).trim();

							String Op1Kind = OpKind(Op1, 2);
							String Op2Kind = OpKind(Op2, 2);

							if (!TabOfDir.isRightOperands(Directive, Op1Kind, Op2Kind)) {
								Err((i + 1) + ". " + Error[OperandsError] + "\n");
								continue;
							}
							
							// получение длины инструкции
							CodeLen = TabOfDir.getInstrLen(Letters[1], Op1Kind, Op2Kind); 
							// получение кода инструкции
							Code = getCode(Letters[1], Op1, Op2, Op1Kind, Op2Kind, CodeLen); 
							// формирование строки листинга
							Listing += (i + 1) + ". " + HexSchak(Schak, 4) + ":  " + Code.toUpperCase()
									+ "                             ".substring(0, 30 - 5 * CodeLen) + Lines[i].trim()
									+ "\n"; 
							// формирование карты Т
							T.add(new Cards('T', Schak, CodeLen, Code)); 
							// инкрементирование СЧАКа на длину инструкции
							Schak += CodeLen; 
						} else {
							Listing += (i + 1) + ". " + "Имя переменной не определено" + ":   " + Lines[i].trim()
									+ "\n";
							continue;
						}
					} else if (OurComAndDir(Letters, NextCommand)) {
						PosFragment = position.getNextPosSeg(PosFragment);
						i--;
						continue;
					} else if (OurComAndDir(Letters, position.getCurrCom(4).split(",+"))) {
						PosFragment = 4;
						Listing += (i + 1) + ". " + "Нет директивы закрытия сегмента" + ":   " + Lines[i].trim() + "\n";
						i--;
						continue;
					} else {
						Listing += (i + 1) + ". " + Error[StructureError] + ":   " + Lines[i].trim() + "\n";
						continue;
					}
				} else {
					Listing += (i + 1) + ". " + Error[UnknownCommand] + ":   " + Lines[i].trim() + "\n";
					continue;
				}
			}

			if (PosFragment == 3) {
				Letters = CurrentLine.split(" +");
				if (Letters[0].equals(CurrentCommand[0]))
					Listing += (i + 1) + ". " + "Не указано имя сегмента" + ":   " + Lines[i].trim() + "\n";
				else if ((Letters.length != 1) && Letters[1].equals(CurrentCommand[0])) {
					if (Letters[0].equals(SegmentName))
						Listing += (i + 1) + ". " + "   :                                        " + Lines[i].trim()
								+ "\n";
					else
						Listing += (i + 1) + ". " + "Имя сегмента указано неправильно" + ":   " + Lines[i].trim()
								+ "\n";
				} else
					Listing += (i + 1) + ". " + "Слишком много буков при закрытии сегмента!" + ":   " + Lines[i].trim()
							+ "\n";
				
				if (i == (Lines.length - 1)) {
					Listing += (i + 1) + ". " + "Неожиданное окончание программы" + ":   " + Lines[i].trim() + "\n";
					break;
				}
				
				PosFragment = position.getNextPosSeg(PosFragment);
				continue;
			}

			if (PosFragment == 4) {
				if (CurrentLine.trim().equals(CurrentCommand[0])) {
					if ((Lines.length - 1) != i) {
						Listing += (i + 1) + ". " + "Конец у программы пристутствует\n";
						break;
					}

					Listing += (i + 1) + ". " + "   :                                        " + Lines[i].trim() + "\n";
				} else 
					Listing += (i + 1) + ". " + "Ожидалось окончание программы!" + ":   " + Lines[i].trim() + "\n";
			
				break; 
			}
		}
		
		if (PosFragment == 1) {
			Listing += (Lines.length + 1) + ". "
					+ "Неожиданное окончание программы (в этой строке нехватает директив закрытия сегмента и окончания программы)"
					+ ":   " + Lines[Lines.length - 1].trim() + "\n";
		}
		
		// формирование карты Е
		E = new Cards('E', "0000");
		//writeCards();
		return !ErrorFlag;
	}
	
	// Запись объектного кода
	void writeObject() {
		try {
			ObjectCode = new RandomAccessFile(file + ".bin", "rw");

			// запись карты H
			ObjectCode.writeByte(H.Signature);
			ObjectCode.writeBytes(H.SegName);
			ObjectCode.writeByte(from_16_to_10(HexSchak(H.AdressOfCode, 4).substring(2, 4).toUpperCase()));
			ObjectCode.writeByte(from_16_to_10(HexSchak(H.AdressOfCode, 4).substring(0, 2).toUpperCase()));

			// запись карты Т
			for (int j = 0; j < T.size(); j++) {
				ObjectCode.writeByte(T.get(j).Signature);
				ObjectCode.writeByte(from_16_to_10(HexSchak(T.get(j).AdressOfCode, 4).substring(0, 2).toUpperCase()));
				ObjectCode.writeByte(from_16_to_10(HexSchak(T.get(j).AdressOfCode, 4).substring(2, 4).toUpperCase()));
				ObjectCode.writeByte(T.get(j).CodeLength);
				
				for (int i = 0; i < T.get(j).CodeLength; i++)
					ObjectCode.writeByte(from_16_to_10(T.get(j).Code.substring(2 * i, 2 * i + 2).toUpperCase()));
			}

			// запись карты Е
			ObjectCode.writeByte(E.Signature);
			ObjectCode.writeByte(from_16_to_10(E.Enter.substring(0, 2).toUpperCase()));
			ObjectCode.writeByte(from_16_to_10(E.Enter.substring(2, 4).toUpperCase()));

			ObjectCode.close();
		} catch (IOException e) {
		}
	}
	
	// Запись листинга
	void writeListing() {
		try {
			ListingFile = new PrintWriter(file + ".lst");

			ListingFile.write(Listing);

			ListingFile.close();
		} catch (IOException e) {
		}
	}
	
	// Запись таблицы символических имён
	void writeSymbol() {
		try {
			SymbolFile = new PrintWriter(file + ".sym");

			SymbolFile.println("  Имя" + "     	" + "Адрес       " + "Тип");
			for (int i = 0; i < SymbolTab.size(); i++)
				SymbolFile.println("  " + SymbolTab.get(i).Name + "             " + SymbolTab.get(i).Offset + "       "
						+ SymbolTab.get(i).Type);

			SymbolFile.close();
		} catch (IOException e) {
		}
	}
	
	// Запись карт
	void writeCards() {
		try {
			Cardfile = new PrintWriter(file + ".map");

			// запись карты H
			Cardfile.print(H.Signature + " ");
			Cardfile.print(H.SegName + " ");
			Cardfile.print(
					HexSchak(H.AdressOfCode, 4).substring(0, 2) + HexSchak(H.AdressOfCode, 4).substring(2, 4) + " ");
			Cardfile.println();

			// запись карты Т
			for (int j = 0; j < T.size(); j++) {
				Cardfile.print(T.get(j).Signature + " ");
				Cardfile.print(HexSchak(T.get(j).AdressOfCode, 4) + " ");
				Cardfile.print(HexSchak(T.get(j).CodeLength, 2) + " ");
				Cardfile.print(T.get(j).Code + " ");
				Cardfile.println();
			}

			// запись карты Е
			Cardfile.print(E.Signature + " ");
			Cardfile.print(E.Enter + " ");

			Cardfile.close();
		} catch (IOException e) {
		}
	}
	
	// Получение кода инструкции
	String getCode(String Command, String Op1, String Op2, String Op1Kind, String Op2Kind, int CodeLen) {
		// получаем начальный код инструкции и продолжаем формирование кода
		String code = ""; 

		// если командой является MOV
		if (Command.equals("MOV")) {
			code = TabOfCom.getCode(Command, Op1Kind, Op2Kind);
			if ((Op1Kind.equals("Reg8") && Op2Kind.equals("Reg8"))
					|| (Op1Kind.equals("Reg16") && Op2Kind.equals("Reg16"))) {
				code += "11";
				code += TabOfReg.getRegCode(Op1) + TabOfReg.getRegCode(Op2);
				return code = code.substring(0, 2) + from_2_to_16(code.substring(2, 6))
						+ from_2_to_16(code.substring(6, 10));
			}
			// если происходит запись непосредственного операнда в регистр
			else if ((Op1Kind.equals("Reg8") && Op2Kind.equals("Imm8"))
					|| (Op1Kind.equals("Reg16") && Op2Kind.equals("Imm8"))
					|| (Op1Kind.equals("Reg16") && Op2Kind.equals("Imm16"))) {
				code += "11000";
				code += TabOfReg.getRegCode(Op1);
				if (Op2.indexOf("OFFSET ") != -1) // если директива ОФФСЕТ
					return code = code.substring(0, 2) + from_2_to_16(code.substring(2, 6))
							+ from_2_to_16(code.substring(6, 10))
							+ getOffset(Op2.substring(6, Op2.length()).trim()).substring(2, 4)
							+ getOffset(Op2.substring(6, Op2.length()).trim()).substring(0, 2);

				code = code.substring(0, 2) + from_2_to_16(code.substring(2, 6)) + from_2_to_16(code.substring(6, 10));
				if (Op2Kind.equals("Imm8"))
					if (Op1Kind.equals("Reg16"))
						return code += HexSchak(Value, 2) + "00";
					else
						return code += HexSchak(Value, 2);
				else if (Op2Kind.equals("Imm16"))
					return code += HexSchak(Value, 4).substring(2, 4) + HexSchak(Value, 4).substring(0, 2);
			}
			// если происходит базовая-индексная адресация в регистр
			else if ((Op1Kind.equals("Reg8") && Op2Kind.equals("Mem8"))
					|| (Op1Kind.equals("Reg8") && Op2Kind.equals("Mem16")
							|| (Op1Kind.equals("Reg16") && Op2Kind.equals("Mem8"))
							|| (Op1Kind.equals("Reg16") && Op2Kind.equals("Mem16")))) {
				code += "10";

				if (Op2.contains("BX") && Op2.contains("SI"))
					code += "000";
				else if (Op2.contains("BX") && Op2.contains("DI"))
					code += "001";
				else if (Op2.contains("BP") && Op2.contains("SI"))
					code += "010";
				else
					code += "011";
				code += TabOfReg.getRegCode(Op1);
				code = code.substring(0, 2) + from_2_to_16(code.substring(2, 6)) + from_2_to_16(code.substring(6, 10));

				if (Op2.indexOf('-') == -1 && Op2.indexOf('+') == -1) {
					// this.CodeLen = CodeLen-1;
					return code;
				} else if (Op2Kind.equals("Mem8"))
					return code += HexSchak(Value, 2);
				else if (Op2Kind.equals(""))
					return code += getOffset(Op2);
				else
					return code += HexSchak(Value, 4).substring(2, 4) + HexSchak(Value, 4).substring(0, 2);
			}
			// если происходит базовая-индекстна адресация из регистра
			else if ((Op2Kind.equals("Reg8") && Op1Kind.equals("Mem8"))
					|| (Op2Kind.equals("Reg8") && Op1Kind.equals("Mem16")
							|| (Op2Kind.equals("Reg16") && Op1Kind.equals("Mem8"))
							|| (Op2Kind.equals("Reg16") && Op1Kind.equals("Mem16")))) {
				code += "10";
				code += TabOfReg.getRegCode(Op2);
				if (Op1.contains("BX") && Op1.contains("SI"))
					code += "000";
				else if (Op1.contains("BX") && Op1.contains("DI"))
					code += "001";
				else if (Op1.contains("BP") && Op1.contains("SI"))
					code += "010";
				else
					code += "011";

				code = code.substring(0, 2) + from_2_to_16(code.substring(2, 6)) + from_2_to_16(code.substring(6, 10));

				if (Op1Kind.equals("Mem8"))
					return code += HexSchak(Value, 2);
				else if (Op2Kind.equals(""))
					return code += getOffset(Op2);
				else
					return code += HexSchak(Value, 4).substring(2, 4) + HexSchak(Value, 4).substring(0, 2);
			}
		}
		// если команда XCHG
		else if (Command.equals("XCHG")) {
			code = TabOfCom.getCode(Command, Op1Kind, Op2Kind);
			if ((Op1Kind.equals("Reg8") && Op2Kind.equals("Reg8"))
					|| (Op1Kind.equals("Reg16") && Op2Kind.equals("Reg16"))) {
				code += "11";
				code += TabOfReg.getRegCode(Op1) + TabOfReg.getRegCode(Op2);
				return code = code.substring(0, 2) + from_2_to_16(code.substring(2, 6))
						+ from_2_to_16(code.substring(6, 10));
			}
			// если происходит базовая-индексная адресация в регистр
			else if ((Op1Kind.equals("Reg8") && Op2Kind.equals("Mem8")) 
					|| (Op1Kind.equals("Reg16") && Op2Kind.equals("Mem8"))
					|| (Op1Kind.equals("Reg16") && Op2Kind.equals("Mem16"))) {
				code += "10";
				code += TabOfReg.getRegCode(Op1);
				if (Op2.contains("BX") && Op2.contains("SI"))
					code += "000";
				else if (Op2.contains("BX") && Op2.contains("DI"))
					code += "001";
				else if (Op2.contains("BP") && Op2.contains("SI"))
					code += "010";
				else
					code += "011";
				
				code = code.substring(0, 2) + from_2_to_16(code.substring(2, 6)) + from_2_to_16(code.substring(6, 10));

				if (Op2.indexOf('-') == -1 && Op2.indexOf('+') == -1) {
					// this.CodeLen = CodeLen-1;
					return code;
				} else if (Op2Kind.equals("Mem8"))
					return code += HexSchak(Value, 4);
				else if (Op2Kind.equals(""))
					return code += getOffset(Op2);
				else
					return code += HexSchak(Value, 4).substring(2, 4) + HexSchak(Value, 4).substring(0, 2);
			}
			// если происходит базовая-индекстна адресация из регистра
			else if ((Op2Kind.equals("Reg8") && Op1Kind.equals("Mem8"))
					|| (Op2Kind.equals("Reg16") && Op1Kind.equals("Mem8"))
					|| (Op2Kind.equals("Reg16") && Op1Kind.equals("Mem16"))) {
				code += "10";
				code += TabOfReg.getRegCode(Op2);
				if (Op1.contains("BX") && Op1.contains("SI"))
					code += "000";
				else if (Op1.contains("BX") && Op1.contains("DI"))
					code += "001";
				else if (Op1.contains("BP") && Op1.contains("SI"))
					code += "010";
				else
					code += "011";

				code = code.substring(0, 2) + from_2_to_16(code.substring(2, 6)) + from_2_to_16(code.substring(6, 10));

				if (Op1Kind.equals("Mem8"))
					return code += HexSchak(Value, 4);
				else if (Op2Kind.equals(""))
					return code += getOffset(Op2);
				else
					return code += HexSchak(Value, 4).substring(2, 4) + HexSchak(Value, 4).substring(0, 2);
			}
		}
		// если команда IMUL
		else if (Command.equals("IMUL")) {
			code = TabOfCom.getCode(Command, Op1Kind, Op2Kind);
			code += "11101";
			if (Op1Kind.equals("Reg8") || Op1Kind.equals("Reg16"))
				code += TabOfReg.getRegCode(Op1);

			code = code.substring(0, 2) + from_2_to_16(code.substring(2, 6)) + from_2_to_16(code.substring(6, 10));

		}
		// если команда LOOP
		else if (Command.equals("LOOP")) {
			code = TabOfCom.getCode(Command, Op1Kind, Op2Kind);
			if (from_16_to_10(getOffset(Op1)) - (Schak + CodeLen) < 0)
				return code = code.substring(0, 2)
						+ HexSchak(256 + from_16_to_10(getOffset(Op1)) - (Schak + CodeLen), 2);
			else
				return code = code.substring(0, 2) + HexSchak(from_16_to_10(getOffset(Op1)) - (Schak + CodeLen), 2);
		}
		// если команда INT
		else if (Command.equals("INT")) {
			code = TabOfCom.getCode(Command, Op1Kind, Op2Kind);
			return code += HexSchak(Value, 2);
		}
		// если директива DB
		else if (Command.equals("DB")) {
			if (Op2Kind.equals("Imm8"))
				if (Op1.indexOf('H') != -1)
					return code = HexSchak(from_16_to_10(Op1.substring(0, Op1.length() - 1)), 2) + HexSchak(Value, 2);
				else
					return code = HexSchak(Integer.parseInt(Op1), 2) + HexSchak(Value, 2);
			else
				return code = HexSchak(Value, 2);
		}
		// если директива DW
		else if (Command.equals("DW")) {
			if (Op2Kind.equals("Imm8") || Op2Kind.equals("Imm16"))
				if (Op1.indexOf('H') != -1)
					return code = HexSchak(from_16_to_10(Op1.substring(0, Op1.length() - 1)), 4).substring(2, 4)
							+ HexSchak(from_16_to_10(Op1.substring(0, Op1.length() - 1)), 4).substring(0, 2)
							+ HexSchak(Value, 4).substring(2, 4) + HexSchak(Value, 4).substring(0, 2);
				else
					return code = HexSchak(Integer.parseInt(Op1), 4).substring(2, 4)
							+ HexSchak(Integer.parseInt(Op1), 4).substring(0, 2) + HexSchak(Value, 4).substring(2, 4)
							+ HexSchak(Value, 4).substring(0, 2);
			else
				return code = HexSchak(Value, 4).substring(2, 4) + HexSchak(Value, 4).substring(0, 2);
		}
		return code;
	}

	boolean HaveErr(int i) {
		if (ListOfErrors.equals(""))
			return false;
		
		String[] m = ListOfErrors.split("\\n+");
		
		for (int j = 0; j < m.length; j++)
			if (Integer.parseInt(m[j].split("[.]{1}")[0]) == i)
				return true;
		
		return false;
	}
	
	// приведение Счака к нормальному виду
	String HexSchak(int s, int n) {
		String HexS = Integer.toHexString(s).toUpperCase();
		int len = HexS.length();
		
		if (len > 4)
			HexS = HexS.substring(len - n, len);
		
		for (int j = 0; j < n - len; j++)
			HexS = "0" + HexS;
		
		return HexS;
	}
	
	// метод реакции на ошибку
	void Err(String err) {
		ErrorFlag = true;
		ListOfErrors += err;
	}
	
	// определение типа операнда
	String OpKind(String Op, int pass) {
		String Result = "";
		// если операнда нет изначально
		if (Op.equals(Result))
			return Result;
		// если регистр
		else if (TabOfReg.isReg(Op))
			Result = TabOfReg.getRegType(Op);
		// если число
		else if (Op.charAt(0) >= '0' && Op.charAt(0) <= '9' || Op.charAt(0) == '-') {
			boolean minus = false;
			if (Op.charAt(0) == '-') {
				minus = true;
				Op = Op.substring(Op.indexOf('-') + 1, Op.length()).trim();
			}
			
			boolean decimal = true;
			if (Op.charAt(Op.length() - 1) == 'H') {
				Op = Op.substring(0, Op.indexOf('H')).trim();
				decimal = false;
			}
			
			if (!number(Op))
				return Result;
			
			if (!decimal)
				Value = from_16_to_10(Op);
			else
				Value = Integer.parseInt(Op);

			if (minus)
				Value *= (-1);
			
			// если больше двух байт
			if (Value < -32768 || Value > 65535)
				return Result;
			
			// если в пределах одного байта
			if (Value >= -128 && Value <= 255)
				Result = "Imm8";
			// иначе 2 байта
			else
				Result = "Imm16";
		}
		// если директива
		else if (TabOfDir.DirEn(Op)) {
			// если директива ОФФСЕТ
			if (Op.indexOf("OFFSET ") != -1) {
				// если просмотр первый, то...
				if (pass == 1)
					Result = "Imm16"; // Op = Op.substring(6, Op.length()).trim();
				// если просмотр второй, то...
				else if (isVariable(Op.substring(6, Op.length()).trim()))
					Result = "Imm16";
				else if (isLabel(Op.substring(6, Op.length()).trim()))
					Result = "";
				else
					Result = "UnknVar";
			} else // иначе ошибка, так как такой директивы тут быть не должно
				return Result;
			// если адресация из памяти
		}
		// если метка
		else if (Op.charAt(0) == '[' && Op.charAt(Op.length() - 1) == ']') {
			String n1 = "", n2 = "", n3 = "";
			Op = Op.substring(Op.indexOf('[') + 1, Op.indexOf(']')).trim();
			try {
				n1 = Op.split("[-+]+")[0].trim();
				n2 = Op.split("[-+]+")[1].trim();
				n3 = Op.split("[-+]+")[2].trim();
			} catch (java.lang.ArrayIndexOutOfBoundsException e) {
				ErrorFlag = true;
				return Result = "UnkAdr";
			}

			if ((n1.equals("BX") || n1.equals("BP")) && (n2.equals("SI") || n2.equals("DI"))
					&& OpKind(n3, pass).equals("Imm8"))
				Result = "Mem8";
			else if ((n1.equals("BX") || n1.equals("BP")) && (n2.equals("SI") || n2.equals("DI"))
					&& (OpKind(n3, 2).equals("Imm16") || OpKind(n3, pass).equals("Label")
							|| OpKind(n3, 2).equals("VAR"))) {
				Result = "Mem16";
				if (pass == 2 && OpKind(n3, 2).equals("VAR")) {
					if (!getOffset(n3).equals("false"))
						Value = from_16_to_10(getOffset(n3));
					else {
						Result = "UnknVar";
						ErrorFlag = true;
					}
				}
			} else {
				Result = "UnkAdr";
				ErrorFlag = true;
			}
		}
		// остальное - как переменная или метка
		else {
			if (pass == 1)
				Result = "Label";
			else if (isVariable(Op))
				Result = "VAR";
			else if (isLabel(Op))
				Result = "Label";
			else
				Result = "UnknVar";
		}

		return Result;
	}

	boolean number(String n) {
		String p = "0123456789ABCDEF";
		
		for (int j = 0; j < n.length(); j++)
			if (p.indexOf(n.charAt(j)) == -1)
				return false;
		
		return true;
	}

	String from_2_to_16(String s) {
		String[] h = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "A", "B", "C", "D", "E", "F" };

		if (s.equals("0000"))
			return h[0];
		
		if (s.equals("0001"))
			return h[1];
		
		if (s.equals("0010"))
			return h[2];
		
		if (s.equals("0011"))
			return h[3];
		
		if (s.equals("0100"))
			return h[4];
		
		if (s.equals("0101"))
			return h[5];
		
		if (s.equals("0110"))
			return h[6];
		
		if (s.equals("0111"))
			return h[7];
		
		if (s.equals("1000"))
			return h[8];
		
		if (s.equals("1001"))
			return h[9];
		
		if (s.equals("1010"))
			return h[10];
		
		if (s.equals("1011"))
			return h[11];
		
		if (s.equals("1100"))
			return h[12];
		
		if (s.equals("1101"))
			return h[13];
		
		if (s.equals("1110"))
			return h[14];

		return h[15];
	}

	int from_16_to_10(String g) {
		char[] h = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };
		int a = 1;
		int s = 0;
		
		for (int i = g.length() - 1; i >= 0; i--) {
			for (int j = 0; j < 16; j++) {
				if (g.charAt(i) == h[j]) {
					s += (a * j);
					a = a * 16;
					break;
				}
			}
		}
		return (s);
	}

	String FindType(String operation) {
		for (int i = 0; i < ComAndDir.length; i++)
			if (ComAndDir[i].equals(operation))
				return Types[i];
		return "Unknown";
	}
	
	/*
	 * проверка на соответствие команды из поступившего массива с командой из
	 * эталонного
	 */
	boolean OurComAndDir(String[] CAD, String[] NAD) {
		for (int i = 0; i < CAD.length; i++)
			for (int j = 0; j < NAD.length; j++)
				if (NAD[j].equals(CAD[i]))
					return true;
		
		return false;
	}
	
	/*
	 * проверка на соответствие команды из поступившего массива с командой из
	 * эталонного
	 */
	boolean OurComAndDir(String CAD, String[] NAD) {
		for (int i = 0; i < NAD.length; i++)
			if (NAD[i].equals(CAD))
				return true;
		
		return false;
	}

	String DeleteComments(String s) {
		for (int j = 0; j < s.length(); j++) {
			if (s.charAt(j) == ';')
				return s.substring(0, j);
		}
		
		return s;
	}

	boolean DoubleVarOrLabel(String label) {
		for (int j = 0; j < SymbolTab.size(); j++)
			if (SymbolTab.get(j).Name.equals(label))
				return true;
		
		return false;
	}

	boolean isVariable(String var) {
		int j;
		
		for (j = 0; j < SymbolTab.size(); j++)
			if (SymbolTab.get(j).Name.equals(var))
				break;
		
		if (j == SymbolTab.size())
			return false;
		
		if (SymbolTab.get(j).Type.equals("VAR"))
			return true;
		
		return false;
	}

	boolean isLabel(String label) {
		int j;
		
		for (j = 0; j < SymbolTab.size(); j++)
			if (SymbolTab.get(j).Name.equals(label))
				break;
		
		if (j == SymbolTab.size())
			return false;
		
		if (SymbolTab.get(j).Type.equals("Label"))
			return true;
		
		return false;
	}

	String getOffset(String varlabel) {
		int j;
		String s = "false";
		
		for (j = 0; j < SymbolTab.size(); j++)
			if (SymbolTab.get(j).Name.equals(varlabel)) {
				s = SymbolTab.get(j).Offset;
				break;
			}
		
		return s;
	}
}