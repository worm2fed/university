package com.worm2fed.sp.kursach;

// ********************************************************************** //
// ******************* ТАБЛИЦА КОММАНД ********************************** //
// ********************************************************************** //

public class TableOfCommand {
	String 				MnemonicName,
						length,
						code;
	int 				OperandNumbers;
	String[] 			OperandsFormat 	= new String[2];
	TableOfCommand[] 	ComTab 			= new TableOfCommand[5];

	TableOfCommand() {
		MnemonicName 		= "";
		OperandNumbers 		= 0;
		OperandsFormat[0] 	= "";
		OperandsFormat[1] 	= "";
		length 				= "";
		code 				= "";
	}

	TableOfCommand(int i) {
		for (int j = 0; j < i; j++)
			ComTab[j] = new TableOfCommand();

		ComTab[0].MnemonicName 		= "MOV";
		ComTab[0].OperandNumbers 	= 2;
		ComTab[0].OperandsFormat[0] = "Reg8,Reg16,Reg8,Reg16,Reg16,Reg8,Reg8,Reg16,Reg16,Mem8,Mem8,Mem16,Mem16";
		ComTab[0].OperandsFormat[1] = "Reg8,Reg16,Imm8,Imm8,Imm16,Mem8,Mem16,Mem8,Mem16,Reg8,Reg16,Reg8,Reg16";
		ComTab[0].length 			= "2,2,3,4,4,3,4,3,4,3,3,4,4,3,4";
		ComTab[0].code 				= "8A11,8B11,C6,C7,C7,8A,8A,8B,8B,88,89,88,89";

		ComTab[1].MnemonicName 		= "XCHG";
		ComTab[1].OperandNumbers 	= 2;
		ComTab[1].OperandsFormat[0] = "Reg8,Reg16,Reg8,Reg16,Mem8,Reg16,Mem16,Mem16";
		ComTab[1].OperandsFormat[1] = "Reg8,Reg16,Mem8,Mem8,Reg8,Mem16,Reg16,Reg8";
		ComTab[1].length 			= "2,2,4,4,4,4,4,4";
		ComTab[1].code 				= "86,87,86,87,86,87,87,86";

		ComTab[2].MnemonicName 		= "IMUL";
		ComTab[2].OperandNumbers 	= 1;
		ComTab[2].OperandsFormat[0] = "Reg8,Reg16";
		ComTab[2].OperandsFormat[1] = " , ";
		ComTab[2].length 			= "2,2";
		ComTab[2].code 				= "F6,F7";
		
		ComTab[3].MnemonicName 		= "LOOP";
		ComTab[3].OperandNumbers 	= 1;
		ComTab[3].OperandsFormat[0] = "Label";
		ComTab[3].OperandsFormat[1] = " ";
		ComTab[3].length 			= "2";
		ComTab[3].code 				= "E2";

		ComTab[4].MnemonicName 		= "INT";
		ComTab[4].OperandNumbers 	= 1;
		ComTab[4].OperandsFormat[0] = "Imm8";
		ComTab[4].OperandsFormat[1] = " ";
		ComTab[4].length 			= "2";
		ComTab[4].code 				= "CD";
	}

	int getOpNum(String Name) {
		int i;
		
		a: for (i = 0; i < ComTab.length; i++)
			if (ComTab[i].MnemonicName.equals(Name))
				break a;
		
		return ComTab[i].OperandNumbers;
	}

	boolean isRightOperands(String Com, String TOp1, String TOp2) {
		int i;
		
		for (i = 0; i < ComTab.length; i++)
			if (Com.equals(ComTab[i].MnemonicName))
				break;
		
		String[] t1 = ComTab[i].OperandsFormat[0].split(",+");
		String[] t2 = ComTab[i].OperandsFormat[1].split(",+");
		int j;
		
		for (j = 0; j < t1.length; j++)
			if (t1[j].equals(TOp1) && t2[j].trim().equals(TOp2))
				return true;
		
		return false;
	}

	int getInstrLen(String Com, String TOp1, String TOp2) {
		int i;
		
		for (i = 0; i < ComTab.length; i++)
			if (Com.equals(ComTab[i].MnemonicName))
				break;
		
		String[] t1 = ComTab[i].OperandsFormat[0].split(",+");
		String[] t2 = ComTab[i].OperandsFormat[1].split(",+");
		String[] l = ComTab[i].length.split(",+");
		int j;
		
		for (j = 0; j < t1.length; j++)
			if (t1[j].equals(TOp1) && t2[j].trim().equals(TOp2))
				break;
		
		return Integer.parseInt(l[j]);
	}

	String getCode(String Com, String TOp1, String TOp2) {
		int i;
		
		for (i = 0; i < ComTab.length; i++)
			if (Com.equals(ComTab[i].MnemonicName))
				break;
		
		String[] t1 = ComTab[i].OperandsFormat[0].split(",+");
		String[] t2 = ComTab[i].OperandsFormat[1].split(",+");
		String[] code = ComTab[i].code.split(",+");
		int j;
		
		for (j = 0; j < t1.length; j++)
			if (t1[j].equals(TOp1) && t2[j].trim().equals(TOp2))
				break;
		
		return code[j];
	}
}