package com.worm2fed.kursach_prog;

public class Stack {
	// ïåðåìåííàÿ äëÿ áóäóùåãî ñòåêà
	private byte[] stack;
	// ïåðåìåííàÿ äëÿ ðàçìåðà ñòåêà
	private int size;

	// ñòåê ïî óìîë÷àíèþ
	public Stack() {
		// ñîçäà¸ì ñòåê ðàçìåðîì â 16 ÿ÷ååê.
		this(16);
	}

	public Stack(int n) {
		// ñîçäà¸ì ñòåê ðàçìåðîì â n ÿ÷ååê.
		stack = new byte[n];
		size = 0;
	}

	// ôóíêöèÿ äëÿ çàïèñè äàííûõ â ñòåê
	public boolean push(byte data) {
		if (size == st.length)
			return false;
		else
			if (size != 0)
				for (int i = (size - 1); i >= 0; i--)
					stack[i + 1] = stack[i];

			stack[0] = data;
			size++;

			return true;
		}
	}

	// ôóíêöèÿ äëÿ èçâëå÷åíèÿ äàííûõ èç ñòåêà
	public byte pop(boolean[] empty) {
		if (size == 0) {
			empty[0] = true;
			return (byte) -128;
		}
		else {
			byte data = stack[0];
			for (int i = 0; i < (size - 1); i++)
				stack[i] = stack[i + 1];

			size--;
			return data;
		}
	}

	public int returnStackSizeValue() {
		return size;
	}

	public byte[] showLeftData(int size) {
		// TODO Auto-generated method stub
		byte[] left_mem = new byte[size];

		for (int i = 0; i < size; i++)
			left_mem[i] = stack[i];

		return left_mem;
	}
}