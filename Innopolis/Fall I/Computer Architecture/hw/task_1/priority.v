module priority (a0, a1, a2, a3, a4, a5, a6, a7, y0, y1, y2);
	input a0, a1, a2, a3, a4, a5, a6, a7;
	output y0, y1, y2;
	
	// a0 a1 a2 a3 a4 a5 a6 a7| y0 y1 y2		
	// -----------------------|---------
	// 1  X  X  X  X  X  X  X | 1  1  1		y2 = (0 + 2 + 4 + 6) = a0 + !a0!a1a2 + !a0!a1!a2!a3a4 + !a0!a1!a2!a3!a4!a5a6 = a0 + !a1a2 + !a1!a3a4 + !a1!a3!a5a6
	// 0  1  X  X  X  X  X  X | 1  1  0		   = a0 + !a1(a2 + !a3a4 + !a3!a5a6)
	// 0  0  1  X  X  X  X  X | 1  0  1
	// 0  0  0  1  X  X  X  X | 1  0  0		y1 = (0 + 1 + 4 + 5) = a0 + !a0a1 + !a0!a1!a2!a3a4 + !a0!a1!a2!a3!a4a5 = a0 + a1 + !a2!a3a4 + !a2!a3a5 = 
	// 0  0  0  0  1  X  X  X | 0  1  1		   = a0 + a1 + !a2!a3(a4 + a5)
	// 0  0  0  0  0  1  X  X | 0  1  0
	// 0  0  0  0  0  0  1  X | 0  0  1		y0 = (0 + 1 + 2 + 3) = a0 + !a0a1 + a0!a1a2 + a0!a1!a2a3 = a0 + a1 + a2 + a3
	// 0  0  0  0  0  0  0  1 | 0  0  0
	
	assign y0 = a0 | a1 | a2 | a3;
	assign y1 = a0 | a1 | !a2 & !a3 & (a4 | a5);
	assign y2 = a0 | !a1 & (a2 | !a3 & a4 | !a3 & !a5 & a6);
endmodule
