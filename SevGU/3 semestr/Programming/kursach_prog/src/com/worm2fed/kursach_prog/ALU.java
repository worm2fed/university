package com.worm2fed.kursach_prog;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class ALU {
	// путь к объектному файлу
	private String path;
	// создаём эмулируемую память
	private Stack memory;
	// создаём регистры общего назначения и регистр флагов
	private byte reg1, reg2, reg_flag;
	// массив, хранящий строки объектного кода
	private String[] data_for_run;
	// переменная для ручного режима
	private int count = 0;

	public ALU(int mem_size, String file) {
		// создаём эмулируемую память, размером mem_size байт
		memory = new Stack(mem_size);

		path = file + "object_code.w2f";
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

	// подгатавливаем данные для выполнения
	private boolean prepareDataFormFile() {
		String line = null;
		int i = 0;
		data_for_run = new String[255];

		BufferedReader bf_reader = null;

		try {
			// открываем поток для чтения
			FileReader file_reader = new FileReader(path);
			bf_reader = new BufferedReader(file_reader);

			// читаем содержимое
			while ((line = bf_reader.readLine()) != null) {
				// если строка пуста - пропускаем её
				if (line.equals(""))
					continue;
				// тогда зановсим в массив наши данные
				else
					data_for_run[i] = line;
				i++;
			}
		} catch (FileNotFoundException e) {
			return false;
		} catch (IOException e) {
			return false;
		} finally {
			if (closeFile(bf_reader) != false)
				return true;
		}

		return false;
	}

	private short getComandFromString(String string) {
		short com;
		try {
			com = Short.parseShort(string, 2);
			// выделяем три бита, отвечающие за команду, остальные обнуляем
			com = (short) (com & 0x0007);
		} catch (NumberFormatException e) {
			com = -1;
		}

		return com;
	}

	// функция для установки флагов
	private void setFlags(short data) {
		/*
		 * XXXX ZNPO X - doesn't matter Z - zero N - negative P - positive O -
		 * overflow
		 */

		// overflow
		if (data > 127 || data < -127)
			reg_flag = 0x01;
		// zero
		else if (data == 0)
			reg_flag = 0x08;
		// negative
		else if (data < 0)
			reg_flag = 0x04;
		// positive
		else
			reg_flag = 0x02;
	}

	// функция для команды
	private boolean comandRead(String raw_comand) {
		byte reg_num;
		// извлекаем данные из памяти
		byte data = memory.pop();

		// проверяем извлеклись ли данные
		if (data == -128)
			return false;
		else {
			/*
			 * XXRR XXXX X - doesn't matter RR - register code 0x20 - reg1 0x10
			 * - reg2 0x30 - err
			 */
			try {
				reg_num = Byte.parseByte(raw_comand, 2);
			} catch (NumberFormatException e) {
				return false;
			}
			reg_num = (byte) (reg_num & 0x30);

			// проверяем в какой регистр нужно записать данные
			if (reg_num == 0x10)
				reg1 = data;
			else if (reg_num == 0x20)
				reg2 = data;
			else
				return false;

			return true;
		}
	}

	// функция для команды
	private boolean comandWrite(String raw_comand) {
		// если длина команды 8 символов
		if (raw_comand.length() == 8) {
			byte com = Byte.parseByte(raw_comand, 2);
			// получаем бит t
			byte t = (byte) (com & 0x08);

			// если бит t не равен 0 - ошибка
			if (t != 0)
				return false;
			else {
				// получаем номер регистра
				byte reg_num = (byte) (com & 0x30);

				// записываем данные
				if (reg_num == 0x10) {
					// проверка на успешность записи в память
					if (!memory.push(reg1))
						return false;

					reg1 = 0;
				} else if (reg_num == 0x20) {
					// проверка на успешность записи в память
					if (!memory.push(reg2))
						return false;

					reg2 = 0;
				} else
					return false;
			}
		}
		// если длина команды 16 символов
		else if (raw_comand.length() == 16) {
			short com = Short.parseShort(raw_comand, 2);
			// получаем бит t
			short t = (short) (com & 0x0008);

			// если бит t не равен 1 - ошибка
			if (t != 8)
				return false;
			else {
				// получаем номер регистра
				short reg_num = (short) (com & 0x0030);

				if (reg_num != 0)
					return false;
				else {
					byte data = (byte) (com >> 6);

					// проверка на успешность записи в память
					if (!memory.push(data))
						return false;
				}
			}
		}
		// если не 8 и не 16 - ошибка
		else
			return false;

		return true;
	}

	// функция для команды
	private boolean comandAdd() {
		// сначала записываем результат в переменную типа short для проверки на
		// переполнение
		short res = (short) (reg1 + reg2);
		// записываем результат в регистр1
		reg1 = (byte) res;
		reg2 = 0;
		// устанавливаем флаги
		setFlags(res);

		return true;
	}

	// функция для команды
	private boolean comandSup() {
		// сначала записываем результат в переменную типа short для проверки на
		// переполнение
		short res = (short) (reg1 - reg2);
		// записываем результат в регистр1
		reg1 = (byte) res;
		reg2 = 0;
		// устанавливаем флаги
		setFlags(res);

		return true;
	}

	// функция для команды
	private boolean comandAnd() {
		// сначала записываем результат в переменную типа short для проверки на
		// переполнение
		short res = (short) (reg1 & reg2);
		// записываем результат в регистр1
		reg1 = (byte) res;
		reg2 = 0;
		// устанавливаем флаги
		setFlags(res);

		return true;
	}

	// функция для команды
	private boolean comandJump() {
		// если переподнение - возвращаем неудачное выполнение
		if (reg_flag == 0x01)
			return false;
		else
			return true;
	}

	// функция для команды REV
	private boolean comandRev() {
		byte data1 = memory.pop();
		byte data2 = memory.pop();

		// проверяем извлеклись ли данные
		if (data1 == -128)
			return false;
		else if (data2 == -128) {
			// если не извлеклось второе значение - возвращаем первое наместо
			memory.push(data1);
			return false;
		}
		// записываем дублированное значение
		else if (!memory.push(data1))
			return false;
		else if (!memory.push(data2))
			return false;
		else
			return true;
	}

	// функция для команды DUP
	private boolean comandDup() {
		// получаем данные из памяти
		byte data = memory.pop();

		// если чтение вернуло ошибку, то выход
		if (data == -128)
			return false;
		// возвращаем обратно в память
		else if (memory.push(data) == false)
			return false;
		// записываем дублированное значение
		else if (memory.push(data) == false)
			return false;
		else
			return true;
	}

	private boolean executeComand(int index) {
		byte comand = (byte) getComandFromString(data_for_run[index]);
		boolean status = false;

		// проверяем успешность выполнения получения команды
		if (comand == -1)
			status = false;
		else {
			// в зависимости от полученного кода выполняем соответствующую
			// операцию
			switch (comand) {
			// чтение
			case 0x00:
				status = comandRead(data_for_run[index]);
				break;
			// запись
			case 0x01:
				status = comandWrite(data_for_run[index]);
				break;
			// сложение
			case 0x02:
				status = comandAdd();
				break;
			// вычитание
			case 0x03:
				status = comandSup();
				break;
			// логическое И
			case 0x04:
				status = comandAnd();
				break;
			// переход
			case 0x05:
				status = comandJump();
				break;
			// реверсировать
			case 0x06:
				status = comandRev();
				break;
			// дублировать
			case 0x07:
				status = comandDup();
				break;
			// если соответсвий нет - ошибка
			default:
				status = false;
			}
		}

		return status;
	}

	public String runAutoMode() {
		// TODO Auto-generated method stub
		String status = "suc";

		if (prepareDataFormFile() == false)
			status = "prepare_err";
		else {
			for (int i = 0; data_for_run[i] != null; i++)
				if (!executeComand(i)) {
					if (reg_flag == 0x01)
						status = "jo";
					else
						status = "ex_err";
					break;
				}
		}

		return status;
	}

	public String runManualMode() {
		// TODO Auto-generated method stub
		String status = "";

		if (prepareDataFormFile() == false)
			status = "prepare_err";
		else if (data_for_run[count] == null)
			status = "suc";
		else {
			if (!executeComand(count)) {
				if (reg_flag == 0x01)
					status = "jo";
				else
					status = "ex_err";
			}
			count++;
		}

		return status;
	}

	public byte returnReg1Value() {
		return reg1;
	}

	public byte returnReg2Value() {
		return reg2;
	}

	public byte returnRegFlagValue() {
		return reg_flag;
	}

	public int returnMemorySizeValue() {
		return memory.returnStackSizeValue();
	}

	public Stack returnMemory() {
		return memory;
	}
}