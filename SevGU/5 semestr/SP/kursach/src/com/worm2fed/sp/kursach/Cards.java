package com.worm2fed.sp.kursach;

//************************************************************************ //
//**************************** ТАБЛИЦА КАРТ ****************************** //
//************************************************************************ //

public class Cards {
	char 	Signature;
	String 	SegName,
			Code,
			Enter;
	int 	CodeLength,
			AdressOfCode;

	Cards(char s, String sn, int ca) {
		Signature 		= s;
		SegName 		= sn;
		AdressOfCode 	= ca;
	}

	Cards(char s, int ca, int cl, String code) {
		Signature 		= s;
		AdressOfCode 	= ca;
		CodeLength 		= cl;
		Code 			= code;
	}

	Cards(char s, String e) {
		Signature 		= s;
		Enter 			= e;
	}
}