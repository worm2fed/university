package com.worm2fed.sp.kursach;

// ************************************************************************ //
// ******************* ТАБЛИЦА ДИРЕКТИВ *********************************** //
// ************************************************************************ //

public class TableOfDirective {
	String 				DirName, 
						length;
	String[] 			OperandsFormat 	= new String[2];
	TableOfDirective[] 	DirTab 			= new TableOfDirective[4];

	TableOfDirective() {
		DirName 			= "";
		length 				= "";
		OperandsFormat[0] 	= "";
		OperandsFormat[1] 	= "";
	}

	TableOfDirective(int i) {
		for (int j = 0; j < i; j++)
			DirTab[j] = new TableOfDirective();

		DirTab[0].DirName 			= "ORG";
		DirTab[0].length 			= "";
		DirTab[0].OperandsFormat[0] = "Imm8,Imm16";
		DirTab[0].OperandsFormat[1] = " , ";

		DirTab[1].DirName 			= "OFFSET";
		DirTab[1].length			= "4";
		DirTab[1].OperandsFormat[0] = "Imm16";
		DirTab[1].OperandsFormat[1] = " ";

		DirTab[2].DirName 			= "DB";
		DirTab[2].length 			= "1,2";
		DirTab[2].OperandsFormat[0] = "Imm8,Imm8";
		DirTab[2].OperandsFormat[1] = " ,Imm8";

		DirTab[3].DirName 			= "DW";
		DirTab[3].length 			= "2,4,2,4,4,4";
		DirTab[3].OperandsFormat[0] = "Imm8,Imm8,Imm16,Imm16,Imm8,Imm16";
		DirTab[3].OperandsFormat[1] = " ,Imm8, ,Imm16,Imm16,Imm8";
	}

	boolean DirEn(String Dir) {
		for (int i = 0; i < DirTab.length; i++)
			if (Dir.contains(DirTab[i].DirName))
				return true;
		
		return false;
	}

	boolean isRightOperands(String Com, String TOp1, String TOp2) {
		int i;
		
		for (i = 0; i < DirTab.length; i++)
			if (Com.equals(DirTab[i].DirName))
				break;
		
		String[] t1 = DirTab[i].OperandsFormat[0].split(",+");
		String[] t2 = DirTab[i].OperandsFormat[1].split(",+");
		int j;
		
		for (j = 0; j < t1.length; j++)
			if (t1[j].equals(TOp1) && t2[j].trim().equals(TOp2))
				return true;
		
		return false;
	}

	int getInstrLen(String Com, String TOp1, String TOp2) {
		int i;
		
		for (i = 0; i < DirTab.length; i++)
			if (Com.equals(DirTab[i].DirName))
				break;
		
		String[] t1 = DirTab[i].OperandsFormat[0].split(",+");
		String[] t2 = DirTab[i].OperandsFormat[1].split(",+");
		String[] l = DirTab[i].length.split(",+");
		int j;
		
		for (j = 0; j < t1.length; j++)
			if (t1[j].trim().equals(TOp1) && t2[j].trim().equals(TOp2))
				break;
		
		return Integer.parseInt(l[j]);
	}
}