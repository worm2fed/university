package com.worm2fed.sp.kursach;

// ************************************************************************ //
// ******************* ТАБЛИЦА РЕГИСТРОВ ********************************** //
// ************************************************************************ //

public class TableOfRegisters {
	String 				RegName, 
						type, 
						code;
	TableOfRegisters[] 	RegTab = new TableOfRegisters[16];

	TableOfRegisters() {
		RegName = "";
		type 	= "";
		code 	= "";
	}

	TableOfRegisters(int i) {
		for (int j = 0; j < i; j++)
			RegTab[j] = new TableOfRegisters();

		RegTab[0].RegName 	= "AL";
		RegTab[0].type 		= "Reg8";
		RegTab[0].code 		= "000";

		RegTab[1].RegName 	= "AH";
		RegTab[1].type 		= "Reg8";
		RegTab[1].code 		= "100";

		RegTab[2].RegName 	= "AX";
		RegTab[2].type 		= "Reg16";
		RegTab[2].code 		= "000";

		RegTab[3].RegName 	= "BL";
		RegTab[3].type 		= "Reg8";
		RegTab[3].code 		= "011";

		RegTab[4].RegName 	= "BH";
		RegTab[4].type 		= "Reg8";
		RegTab[4].code 		= "111";

		RegTab[5].RegName 	= "BX";
		RegTab[5].type 		= "Reg16";
		RegTab[5].code 		= "011";

		RegTab[6].RegName 	= "CL";
		RegTab[6].type 		= "Reg8";
		RegTab[6].code 		= "001";

		RegTab[7].RegName 	= "CH";
		RegTab[7].type 		= "Reg8";
		RegTab[7].code 		= "101";

		RegTab[8].RegName 	= "CX";
		RegTab[8].type 		= "Reg16";
		RegTab[8].code 		= "001";

		RegTab[9].RegName 	= "DL";
		RegTab[9].type 		= "Reg8";
		RegTab[9].code 		= "010";

		RegTab[10].RegName 	= "DH";
		RegTab[10].type 	= "Reg8";
		RegTab[10].code 	= "110";

		RegTab[11].RegName 	= "DX";
		RegTab[11].type 	= "Reg16";
		RegTab[11].code 	= "010";

		RegTab[12].RegName 	= "SP";
		RegTab[12].type 	= "Reg16";
		RegTab[12].code 	= "100";

		RegTab[13].RegName 	= "BP";
		RegTab[13].type 	= "Reg16";
		RegTab[13].code 	= "101";

		RegTab[14].RegName 	= "SI";
		RegTab[14].type 	= "Reg16";
		RegTab[14].code 	= "110";

		RegTab[15].RegName 	= "DI";
		RegTab[15].type 	= "Reg16";
		RegTab[15].code 	= "111";
	}

	boolean isReg(String r) {
		for (int i = 0; i < RegTab.length; i++)
			if (r.equals(RegTab[i].RegName))
				return true;
		
		return false;
	}

	String getRegType(String r) {
		int i;
		
		for (i = 0; i < RegTab.length; i++)
			if (r.equals(RegTab[i].RegName))
				break;
		
		return RegTab[i].type;
	}

	String getRegCode(String r) {
		int i;
		
		for (i = 0; i < RegTab.length; i++)
			if (r.equals(RegTab[i].RegName))
				break;
		
		return RegTab[i].code;
	}
}