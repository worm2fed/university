package com.spo.lab2;

public class Analyzer {
	int pos;
	char[] ch;
	String trace = "";
	
	public Analyzer(String s) {
		pos = 0;
		s.replaceAll(" ", ""); // удаляем все пробелы
		ch = s.toCharArray(); // и преобразуем в массив символов
	}
	
	public void run() {
		// cV=N;vV,V;$
		if (D())
			System.out.println("Принадлежит грамматике!\nТрассировка" + trace);
		else
			System.out.println("Не принадлежит грамматике!");
	}
	
	public boolean D() {
		trace += "1";
		
		if (ch[pos] != '$') {
			if (DC()) {
				if (ch[pos] != '$') {
					if (DV()) {
						if (ch[pos] != '$') {
							if (DP()) {
								return true;
							} else
								return false;
						} else 
							return true;
					} else {
						if (DP())
							return true;
						else
							return false;
					}
				} else 
					return true;
			} else
				return false;
		}
		
		return true;
	}
	
	public boolean DC() {
		if (ch[pos] == 'c') {
			trace += ", 2";
			pos++;
			
			if (ch[pos] != '$') {
				if (DCL()) {
					if (ch[pos] == ';') {
						trace += ", 2";
						pos++;
						return true;
					} else 
						return false;
				} else 
					return false;
			} else
				return false;
		} else
			return false;
	}
	
	public boolean DV() {
		trace += ", 1";
		if (ch[pos] == 'v') {
			trace += ", 3";
			pos++;
			
			if (ch[pos] != '$') {
				if (VL()) {
					if (ch[pos] == ';') {
						trace += ", 3";
						pos++;
						return true;
					} else 
						return false;
				} else 
					return false;
			} else 
				return false;
		} else 
			return false;
	}
	
	public boolean DP() {
		trace += ", 4";
		if (DPL())
			return true;
		else 
			return false;
	}
	
	public boolean DCL() {
		if (CD()) {
			trace += ", 5";
			
			if (ch[pos] != '$') {
				if (X())
					return true;
				else
					return false;
			} else 
				return false;
		} else
			return false;
	}
	
	public boolean X() {
		if (ch[pos] == ',') {
			trace += ", 5'";
			pos++;
			
			if (ch[pos] != '$') {
				if (CD()) {
					if (ch[pos] != '$') {
						if (X())
							return true;
						else
							return false;
					} else 
						return false;
				} else
					return false;
			} else 
				return false;
		} else {
			trace += ", 5'";
			return true;
		}
	}
	
	public boolean CD() {
		if (CN()) {
			if (ch[pos] == '=') {
				trace += ", 6";
				pos++;
					
				if (ch[pos] == 'N') {
					trace += ", 6";
					pos++;
					return true;
				} else 
					return false;
			} else 
				return false;
		} else 
			return false;
	}
	
	public boolean CN() {
		if (ch[pos] == 'V') {
			trace += ", 5, 6, 7";
			pos++;
			return true;
		} else
			return false;
	}
	
	public boolean VL() {
		if (ch[pos] == 'V') {
			trace += ", 8";
			pos++;
			
			if (ch[pos] != '$') {
				if (Y()) {
					return true;
				} else 
					return false;
			} else
				return false;
		} else 
			return false;
	}
	
	public boolean Y() {
		if (ch[pos] == ',') {
			trace += ", 8'";
			pos++;
			
			if (ch[pos] == 'V') {
				trace += ", 8'";
				pos++;
				
				if (ch[pos] != '$') {
					if (Y()) {
						trace += ", 8'";
						return true;
					} else
						return false;
				} else 
					return false;
			} else 
				return false;
		} else 
			return true;
	}
	
	public boolean DPL() {
		if (ch[pos] == 'p') {
			trace += ", 9";
			pos++;
			
			if (ch[pos] == ';') {
				trace += ", 9";
				pos++;
				
				if (ch[pos] != '$') {
					if (DPL()) 
						return true;
					else 
						return false;
				} else 
					return true;
			} else 
				return false;
		} else if (ch[pos] == '$')
			return true;
		else 
			return false;
	}
}
