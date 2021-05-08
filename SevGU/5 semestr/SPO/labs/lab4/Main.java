package com.spo.lab4;

import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner in = new Scanner(System.in);
		String s = "";
		System.out
				.println("Программа готова к работе.\nДля завершения введите: \"exit\"");
		exit: while (true) {
			System.out.println("Введите выражение для преобразования: ");
			s = in.nextLine();
			if (s.equals("exit")) {
				break exit;
			} else {
				Analyzer analyzer = new Analyzer();
				String out = analyzer.run(s);
				System.out.println(out);
			}
		}
		System.out.println("Программа завершена!");
	}
}
