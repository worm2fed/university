package com.spo.lab1;

import java.util.LinkedList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Analyzer {
	private String[] typeLexeme;
	private LinkedList<Lexeme> listLexeme = new LinkedList<Lexeme>();
	private LinkedList<ErrorLexeme> listError = new LinkedList<ErrorLexeme>();

	public Analyzer() {
		typeLexeme = new String[2];
		typeLexeme[0] = "(00)+1(00)+\\.";
		typeLexeme[1] = "(11)+0(11)+\\.";
	}

	public void run(String input) {
		listLexeme.clear();
		listError.clear();
		Pattern pat = Pattern.compile("(00)+1(00)+\\.|(11)+0(11)+\\.");
		Matcher mat = pat.matcher(input);

		int startError = 0;
		int endError = 0;
		
		int matStart = 0;
		int matEnd = 0;
		
		while (mat.find()) {
			matStart = mat.start();
			matEnd = mat.end();
			for (int i = 0; i < typeLexeme.length; i++) {
				if (Pattern.matches(typeLexeme[i], mat.group())) {
					listLexeme.add(new Lexeme().create(matStart, matEnd,
							i + 1, mat.group()));

				}
			}
			endError = matStart;
			if (endError - startError > 0)
				listError.add(new ErrorLexeme().create(startError, endError,
						input.substring(startError, endError)));
			startError = matEnd;
		}
		if (matEnd < input.length()) {
			listError.add(new ErrorLexeme().create(matEnd, input.length(),
					input.substring(matEnd, input.length())));
		}
	}

	void printLexeme() {
		for (int i = 0; i < listLexeme.size(); i++) {
			System.out.println(listLexeme.get(i));
		}
	}

	void printError() {
		for (int i = 0; i < listError.size(); i++) {
			System.out.println(listError.get(i));
		}
	}
}
