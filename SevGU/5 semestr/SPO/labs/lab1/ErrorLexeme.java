package com.spo.lab1;

public class ErrorLexeme {
	private int begin;
	private int end;
	private String errorLexeme;

	public int getEnd() {
		return end;
	}

	public int getBegin() {
		return begin;
	}

	public String getErrorLexeme() {
		return errorLexeme;
	}

	public ErrorLexeme create(int begin, int end, String errorLexeme) {
		this.begin = begin;
		this.end = end;
		this.errorLexeme = errorLexeme;
		return this;
	}

	public String toString() {
		return new String("Недопустимая лексема: " + getErrorLexeme()
				+ " находится в позиции " + getBegin() + "-" + (getEnd()-1));
	}
}
