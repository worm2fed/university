package com.spo.lab1;

import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Analyzer analyzer = new Analyzer();
		Scanner in = new Scanner(System.in);
		String s = "";
		System.out
				.println("Программа готова к работе.\n Для завершения введите: \"exit\"");
		exit: while (true) {
			System.out.println("Введите строку для анализа: ");
			s = in.next();
			if (s.equals("exit")) {
				break exit;
			} else {
				analyzer.run(s);
				analyzer.printLexeme();
				analyzer.printError();
			}
		}
		System.out.println("Программа завершена!");
	}

}
