`timescale 1ns/1ps
`default_nettype none

module segmentdisplay_test;
	logic x1, x2, x3, x4;
	wire a, b, c, d, e, f, g;
	segmentdisplay dut(.x1(x1), .x2(x2), .x3(x3), .x4(x4), .a(a), .b(b), .c(c), .d(d), .e(e), .f(f), .g(g));

	initial
	begin
    		$dumpfile("segmentdisplay.vcd");
    		$dumpvars;
		#200 $stop;
	end
initial
begin	
	// 0  0  0  0  | 1 1 1 1 1 1 0
	assign x1 = 0; assign x2 = 0; assign x3 = 0; assign x4 = 0; #5
	if(a == 1 && b == 1 && c == 1 && d == 1 && e == 1 && f == 1 && g == 0) 
		$display("OK. ");
	else 
		$error("Wrong. for %b %b %b %b result is %b %b %b %b %b %b %b", x1, x2, x3, x4, a, b, c, d, e, f, g); #5
	
	// 0  0  0  1  | 0 1 1 0 0 0 0
	assign x1 = 0; assign x2 = 0; assign x3 = 0; assign x4 = 1; #5
	if(a == 0 && b == 1 && c == 1 && d == 0 && e == 0 && f == 0 && g == 0) 
		$display("OK. ");
	else 
		$error("Wrong. for %b %b %b %b result is %b %b %b %b %b %b %b", x1, x2, x3, x4, a, b, c, d, e, f, g); #5
	
	// 0  0  1  0  | 1 1 0 1 1 0 1
	assign x1 = 0; assign x2 = 0; assign x3 = 1; assign x4 = 0; #5
	if(a == 1 && b == 1 && c == 0 && d == 1 && e == 1 && f == 0 && g == 1) 
		$display("OK. ");
	else 
		$error("Wrong. for %b %b %b %b result is %b %b %b %b %b %b %b", x1, x2, x3, x4, a, b, c, d, e, f, g); #5
	
	// 0  0  1  1  | 1 1 1 1 0 0 1
	assign x1 = 0; assign x2 = 0; assign x3 = 1; assign x4 = 1; #5
	if(a == 1 && b == 1 && c == 1 && d == 1 && e == 0 && f == 0 && g == 1) 
		$display("OK. ");
	else 
		$error("Wrong. for %b %b %b %b result is %b %b %b %b %b %b %b", x1, x2, x3, x4, a, b, c, d, e, f, g); #5
	
	// 0  1  0  0  | 0 1 1 0 0 1 1
	assign x1 = 0; assign x2 = 1; assign x3 = 0; assign x4 = 0; #5
	if(a == 0 && b == 1 && c == 1 && d == 0 && e == 0 && f == 1 && g == 1) 
		$display("OK. ");
	else 
		$error("Wrong. for %b %b %b %b result is %b %b %b %b %b %b %b", x1, x2, x3, x4, a, b, c, d, e, f, g); #5
	
	// 0  1  0  1  | 1 0 1 1 0 1 1
	assign x1 = 0; assign x2 = 1; assign x3 = 0; assign x4 = 1; #5
	if(a == 1 && b == 0 && c == 1 && d == 1 && e == 0 && f == 1 && g == 1) 
		$display("OK. ");
	else 
		$error("Wrong. for %b %b %b %b result is %b %b %b %b %b %b %b", x1, x2, x3, x4, a, b, c, d, e, f, g); #5
	
	// 0  1  1  0  | 1 0 1 1 1 1 1  	
	assign x1 = 0; assign x2 = 1; assign x3 = 1; assign x4 = 0; #5
	if(a == 1 && b == 0 && c == 1 && d == 1 && e == 1 && f == 1 && g == 1) 
		$display("OK. ");
	else 
		$error("Wrong. for %b %b %b %b result is %b %b %b %b %b %b %b", x1, x2, x3, x4, a, b, c, d, e, f, g); #5
	
	// 0  1  1  1  | 1 1 1 0 0 0 0
	assign x1 = 0; assign x2 = 1; assign x3 = 1; assign x4 = 1; #5
	if(a == 1 && b == 1 && c == 1 && d == 0 && e == 0 && f == 0 && g == 0) 
		$display("OK. ");
	else 
		$error("Wrong. for %b %b %b %b result is %b %b %b %b %b %b %b", x1, x2, x3, x4, a, b, c, d, e, f, g); #5
	
	// 1  0  0  0  | 1 1 1 1 1 1 1	
	assign x1 = 1; assign x2 = 0; assign x3 = 0; assign x4 = 0; #5
	if(a == 1 && b == 1 && c == 1 && d == 1 && e == 1 && f == 1 && g == 1) 
		$display("OK. ");
	else 
		$error("Wrong. for %b %b %b %b result is %b %b %b %b %b %b %b", x1, x2, x3, x4, a, b, c, d, e, f, g); #5
	
	// 1  0  0  1  | 1 1 1 0 0 1 1
	assign x1 = 1; assign x2 = 0; assign x3 = 0; assign x4 = 1; #5
	if(a == 1 && b == 1 && c == 1 && d == 0 && e == 0 && f == 1 && g == 1) 
		$display("OK. ");
	else 
		$error("Wrong. for %b %b %b %b result is %b %b %b %b %b %b %b", x1, x2, x3, x4, a, b, c, d, e, f, g); #5
	
	// 1  0  1  0  | 1 1 1 0 1 1 1
	assign x1 = 1; assign x2 = 0; assign x3 = 1; assign x4 = 0; #5
	if(a == 1 && b == 1 && c == 1 && d == 0 && e == 1 && f == 1 && g == 1) 
		$display("OK. ");
	else 
		$error("Wrong. for %b %b %b %b result is %b %b %b %b %b %b %b", x1, x2, x3, x4, a, b, c, d, e, f, g); #5
	
	// 1  0  1  1  | 0 0 1 1 1 1 1
	assign x1 = 1; assign x2 = 0; assign x3 = 1; assign x4 = 1; #5
	if(a == 0 && b == 0 && c == 1 && d == 1 && e == 1 && f == 1 && g == 1) 
		$display("OK. ");
	else 
		$error("Wrong. for %b %b %b %b result is %b %b %b %b %b %b %b", x1, x2, x3, x4, a, b, c, d, e, f, g); #5
	
	// 1  1  0  0  | 1 0 0 1 1 1 0
	assign x1 = 1; assign x2 = 1; assign x3 = 0; assign x4 = 0; #5
	if(a == 1 && b == 0 && c == 0 && d == 1 && e == 1 && f == 1 && g == 0) 
		$display("OK. ");
	else 
		$error("Wrong. for %b %b %b %b result is %b %b %b %b %b %b %b", x1, x2, x3, x4, a, b, c, d, e, f, g); #5
	
	// 1  1  0  1  | 0 1 1 1 1 0 1
	assign x1 = 1; assign x2 = 1; assign x3 = 0; assign x4 = 1; #5
	if(a == 0 && b == 1 && c == 1 && d == 1 && e == 1 && f == 0 && g == 1) 
		$display("OK. ");
	else 
		$error("Wrong. for %b %b %b %b result is %b %b %b %b %b %b %b", x1, x2, x3, x4, a, b, c, d, e, f, g); #5
	
	// 1  1  1  0  | 1 0 0 1 1 1 1
	assign x1 = 1; assign x2 = 1; assign x3 = 1; assign x4 = 0; #5
	if(a == 1 && b == 0 && c == 0 && d == 1 && e == 1 && f == 1 && g == 1) 
		$display("OK. ");
	else 
		$error("Wrong. for %b %b %b %b result is %b %b %b %b %b %b %b", x1, x2, x3, x4, a, b, c, d, e, f, g); #5
	
	// 1  1  1  1  | 1 0 0 0 1 1 1
	assign x1 = 1; assign x2 = 1; assign x3 = 1; assign x4 = 1; #5
	if(a == 1 && b == 0 && c == 0 && d == 0 && e == 1 && f == 1 && g == 1) 
		$display("OK. ");
	else 
		$error("Wrong. for %b %b %b %b result is %b %b %b %b %b %b %b", x1, x2, x3, x4, a, b, c, d, e, f, g);
end
 
endmodule
