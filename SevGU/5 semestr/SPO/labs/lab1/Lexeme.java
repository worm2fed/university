package com.spo.lab1;

public class Lexeme {
	private int begin;
	private int end;
	private String lexeme;
	private int type;

	public int getEnd() {
		return end;
	}

	public int getType() {
		return type;
	}

	public int getBegin() {
		return begin;
	}
	
	public String getLexeme() {
		return lexeme;
	}

	public Lexeme create(int begin, int end, int type, String lexeme) {
		this.begin = begin;
		this.end = end;
		this.type = type;
		this.lexeme = lexeme;
		return this;
	}

	public String toString() {
		return new String("Лексема: " + getLexeme() + " находится в позиции "
				+ getBegin() + "-" + (getEnd()-1)+ "\nИмеет тип: L" + getType());
	}
}
