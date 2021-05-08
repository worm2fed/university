package com.worm2fed.sp.kursach;

// *********************************************************************** //
// ******************* ТАБЛИЦА СИМВОЛИЧЕСКИХ ИМЁН ************************ //
// *********************************************************************** //

public class SymbolTable {
	String Name;
	String Offset;
	String Type;

	SymbolTable(String n, String off, String type) {
		this.Name = n;
		this.Offset = off;
		this.Type = type;
	}

	String getAdress(String name) {
		return Offset;
	}
}