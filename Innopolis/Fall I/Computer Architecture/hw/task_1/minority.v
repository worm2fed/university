module minority (a, b, c, y);
	input a, b, c;
	output y;
	
	// y = !a!b + !a!c + !b!c = 
	// = !a(!b + !c) + !b!c =
	// = !(a + bc (b + c))
	
	assign y = !(a | (b & c) & (b | c));
endmodule
