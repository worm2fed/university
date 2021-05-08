package com.worm2fed.kursach_prog;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Assembler {
	private String path, name, mes, listing_name, object_code_name;

	public Assembler(String file_path) {
		mes = "";
		path = "";
		// переменная хранит разбитую строку
		String[] nm = file_path.split("/");
		// записываем имя файла
		name = nm[nm.length - 1];
		// формируем путь к файлу (без его имени)
		for (int i = 0; i < (nm.length - 1); i++)
			path = path + nm[i] + "/";

		// имя для файла листинга
		listing_name = "listing.w2f";
		// имя для файла с объектным кодом
		object_code_name = "object_code.w2f";
	}

	// функция проверки расширения
	private boolean checkName(String name) {
		// перменная хранит расширение файла
		String[] check = name.split("\\.");

		// если файл имеет не подходяещее разрешение - то "давай досвидания" ;D
		if (check[check.length - 1].equals("txt")
				|| check[check.length - 1].equals("w2f"))
			return true;
		else
			return false;
	}

	// функция для преобразования операции из мнемокода в двоичный код
	private String mnemoCodeToBinaryCode(String mnemo) {
		String binary_comand;

		// поиск соответствия
		switch (mnemo) {
		case "LOAD":
			binary_comand = "000";
			break;
		case "STORE":
			binary_comand = "001";
			break;
		case "ADD":
			binary_comand = "010";
			break;
		case "SUB":
			binary_comand = "011";
			break;
		case "AND":
			binary_comand = "100";
			break;
		case "JO":
			binary_comand = "101";
			break;
		case "REV":
			binary_comand = "110";
			break;
		case "DUP":
			binary_comand = "111";
			break;
		default:
			binary_comand = "err";
		}

		return binary_comand;
	}

	// функция проверки корректнсти операндов
	private boolean checkAreOperandsCorect(String[] operand, int num,
			String mnemo) {
		byte data = 0;
		// если один операнд
		if (num == 1) {
			if (operand[0].equals("reg1") == false
					&& operand[0].equals("reg2") == false)
				return false;
			else if (!mnemo.equals("STORE") && !mnemo.equals("LOAD"))
				return false;
			else
				return true;
		}
		// если два операнда
		else if (num == 2) {
			if (!operand[0].equals("reg0"))
				return false;
			else if (!mnemo.equals("STORE"))
				return false;
			else {
				// преобразуем второй операнд в число
				try {
					data = Byte.parseByte(operand[1], 16);
				} catch (Exception e) {
					return false;
				}
				// проверим диапазон
				if (data > 127 || data < -127)
					return false;
				else
					return true;
			}
		}
		// если не 1 и не 2
		else
			return false;
	}

	// функция подготовки данных для файла листинга
	private String prepareDataForListingFile(String data) {
		String mnemo, listing_line;
		String[] operands = new String[2];
		int operands_num = 0;

		// разбиваем строку по пробелам
		String[] data_split = data.split(" ");
		// выделяем первое слово, которое должно содержать команду
		mnemo = data_split[0];
		// переменная хранит двоичный код команды
		String binary_comand = mnemoCodeToBinaryCode(mnemo);

		// проверка команды на корректность
		if (binary_comand.equals("err"))
			listing_line = data + ";" + " > UNKNOWN COMAND";
		else {
			// проверяем наличие операндов, и если они есть - выделяем их
			if (data_split.length == 1)
				listing_line = binary_comand + " " + mnemo;
			else {
				for (int i = 1; i < data_split.length; i++) {
					operands_num = i;
					if (i >= 3)
						listing_line = data + ";" + " > INCORECT OPERANDS NUM";
					else
						operands[i - 1] = data_split[i];
				}

				if (checkAreOperandsCorect(operands, operands_num, mnemo) == false)
					listing_line = data + ";" + " > UNKNOWN OPERANDS";
				else {
					if (operands_num == 1)
						listing_line = binary_comand + "_" + operands[0] + " "
								+ mnemo;
					else
						listing_line = binary_comand + "_" + operands[0] + "_"
								+ operands[1] + " " + mnemo;
				}
			}
		}
		return listing_line;
	}

	// функция создания файла листинга
	private boolean makeListingFile(String[] data) {
		String listing_line = null;
		BufferedWriter bf_writer = null;

		try {
			// инициализируем запись файла
			FileWriter file_writer = new FileWriter(path + listing_name);
			bf_writer = new BufferedWriter(file_writer);

			for (int i = 0; data[i] != null; i++) {
				// подготавливаем строку для записи
				listing_line = prepareDataForListingFile(data[i]);
				bf_writer.write(listing_line);
				bf_writer.newLine();

				if (listing_line.contains(">"))
					break;
			}
		} catch (IOException e) {
			return false;
		}

		closeFile(bf_writer);

		// если строка содержит характерный символ ошбики - возращаем ошибку
		if (listing_line.contains(">"))
			return false;
		else
			return true;
	}

	// функция подготовки данных для файла с объектным кодом
	private String prepareDataForObjectCodeFile(String data) {
		String mnemo, object_code_line;
		String operands[] = new String[2];
		String op = null;
		int operands_num = 0;

		// разбиваем строку по пробелам
		String data_split[] = data.split(" ");
		// выделяем первое слово, которое должно содержать команду
		mnemo = data_split[0];
		// переменная хранит двоичный код команды
		String binary_comand = mnemoCodeToBinaryCode(mnemo);

		// проверяем наличие операндов, и если они есть - выделяем их
		if (data_split.length == 1)
			object_code_line = "00000" + binary_comand;
		else {
			for (int i = 1; i < data_split.length; i++) {
				operands_num = i;
				operands[i - 1] = data_split[i];
			}

			if (operands_num == 1) {
				if (operands[0].equals("reg1"))
					op = "01";
				else if (operands[0].equals("reg2"))
					op = "10";
				else
					op = "11";
				object_code_line = "00" + op + "0" + binary_comand;
			} else {
				// получаем наши данные в двоичном виде
				op = Integer.toBinaryString(Integer.parseInt(operands[1], 16));
				// дополняем нулями недостающие разряды
				while (op.length() != 8)
					op = "0" + op;

				object_code_line = "00" + op + "001" + binary_comand;
			}
		}

		return object_code_line;
	}

	// функция преобразования исходного файла в объектный код
	private boolean makeObjectCodeFile(String[] data) {
		String object_code_line = null;
		BufferedWriter bf_writer = null;

		try {
			FileWriter file_writer = new FileWriter(path + object_code_name);
			bf_writer = new BufferedWriter(file_writer);

			for (int i = 0; data[i] != null; i++) {
				object_code_line = prepareDataForObjectCodeFile(data[i]);
				bf_writer.write(object_code_line);
				bf_writer.newLine();
			}
		} catch (IOException e) {
			return false;
		}

		closeFile(bf_writer);

		return true;
	}

	// функция для закрытия потока записи
	private boolean closeFile(BufferedWriter bf_writer) {
		try {
			if (bf_writer != null)
				bf_writer.close();
		} catch (IOException e) {
			return false;
		}

		return true;
	}

	// функция для закрытия потока чтения
	private boolean closeFile(BufferedReader bf_reader) {
		try {
			if (bf_reader != null)
				bf_reader.close();
		} catch (IOException e) {
			return false;
		}

		return true;
	}

	// основная функция класса, непосредственно выполняющая ассемблирование
	public String toAssembl() {
		String line = null;
		int i = 0;
		String[] data_for_file = new String[255];

		BufferedReader bf_reader = null;

		// проверка расширения файла
		if (checkName(name) == false)
			mes = "ext_err";
		else {
			try {
				// открываем поток для чтения
				FileReader file_reader = new FileReader(path + name);
				bf_reader = new BufferedReader(file_reader);

				// читаем содержимое
				while ((line = bf_reader.readLine()) != null) {
					// убираем комментарии
					if (!line.contains("") && !line.contains(";")) {
						mes = "lis_err";
						return mes;
					} else {
						String line_done[] = line.split(";");
						// если строка пуста - пропускаем её
						if (line_done[0].equals(""))
							continue;
						else
							// тогда зановсим в массив наши данные попутно
							// удаляя
							// лишние пробелы
							data_for_file[i] = line_done[0].replaceAll(
									"[\\s]{2,}", " ");
						i++;
					}
				}
				if (makeListingFile(data_for_file) == false)
					mes = "lis_err";
				else {
					if (makeObjectCodeFile(data_for_file) == false)
						mes = "obj_err";
					else
						mes = "suc";
				}
			} catch (FileNotFoundException e) {
				mes = "404_err";
			} catch (IOException e) {
				mes = "read_err";
			} finally {
				if (closeFile(bf_reader) == false)
					mes = "close_err";
			}
		}
		return mes;
	}
}