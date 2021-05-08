package com.spo.lab2;

import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner in = new Scanner(System.in);
		String s = "";
		System.out
				.println("Программа готова к работе.\nДля завершения введите: \"exit\"");
		exit: while (true) {
			System.out.println("Введите строку для анализа: ");
			s = in.next();
			if (s.equals("exit")) {
				break exit;
			} else {
				Analyzer analyzer = new Analyzer(s);
				analyzer.run();
				//analyzer.printLexeme();
				//analyzer.printError();
			}
		}
		System.out.println("Программа завершена!");
	}
}
