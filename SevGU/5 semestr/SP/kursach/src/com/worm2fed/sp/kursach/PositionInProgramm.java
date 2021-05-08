package com.worm2fed.sp.kursach;

// ************************************************************************ //
// ******************* ТАБЛИЦА ОПРЕДЕЛЕНИЯ ПОЗИЦИИ В ПРОГРАММЕ ************ //
// ************************************************************************ //

public class PositionInProgramm {
	int[] 		PosInSeg 	= { 0, 1, 2, 3, 4 },
				NextPosSeg 	= { 1, 2, 3, 4, 4 };
	String[] 	CurrCom 	= { "SEGMENT", "ORG,MOV,XCHG,IMUL,LOOP,INT", "DB,DW", "ENDS", "END" },
				NextCom 	= { "ORG,MOV,XCHG,IMUL,LOOP,INT", "DB,DW", "ENDS", "END", "" };

	int getNextPosSeg(int i) {
		return NextPosSeg[i];
	}

	String getCurrCom(int i) {
		return CurrCom[i];
	}

	String getNextCom(int i) {
		return NextCom[i];
	}
}