`timescale 1ns/1ps
`default_nettype none

module priority_test;
	logic a0, a1, a2, a3, a4, a5, a6, a7;
	wire y0, y1, y2;
	priority dut(.a0(a0),.a1(a1),.a2(a2),.a3(a3),.a4(a4),.a5(a5),.a6(a6),.a7(a7),.y0(y0),.y1(y1),.y2(y2));

	initial
	begin
    		$dumpfile("priority.vcd");
    		$dumpvars;
		#200 $stop;
	end
initial
begin		
	// 1  X  X  X  X  X  X  X | 1  1  1
	assign a0 = 1; assign a1 = 1'bx; assign a2 = 1'bx; assign a3 = 1'bx; assign a4 = 1'bx; assign a5 = 1'bx; assign a6 = 1'bx; assign a7 = 1'bx; #5
	if(y0 == 1 && y1 == 1 && y2 == 1)
		$display("OK. ");
	else 
		$error("Wrong. Result is %b %b %b", y0, y1, y2);
	#5
	
	// 0  1  X  X  X  X  X  X | 1  1  0
	assign a0 = 0; assign a1 = 1; assign a2 = 1'bx; assign a3 = 1'bx; assign a4 = 1'bx; assign a5 = 1'bx; assign a6 = 1'bx; assign a7 = 1'bx; #5
	if(y0 == 1 && y1 == 1 && y2 == 0)
		$display("OK. ");
	else 
		$error("Wrong. Result is %b %b %b", y0, y1, y2);
	#5
	
	// 0  0  1  X  X  X  X  X | 1  0  1
	assign a0 = 0; assign a1 = 0; assign a2 = 1; assign a3 = 1'bx; assign a4 = 1'bx; assign a5 = 1'bx; assign a6 = 1'bx; assign a7 = 1'bx; #5
	if(y0 == 1 && y1 == 0 && y2 == 1)
		$display("OK. ");
	else 
		$error("Wrong. Result is %b %b %b", y0, y1, y2);
	#5
	
	// 0  0  0  1  X  X  X  X | 1  0  0
	assign a0 = 0; assign a1 = 0; assign a2 = 0; assign a3 = 1; assign a4 = 1'bx; assign a5 = 1'bx; assign a6 = 1'bx; assign a7 = 1'bx; #5
	if(y0 == 1 && y1 == 0 && y2 == 0)
		$display("OK. ");
	else 
		$error("Wrong. Result is %b %b %b", y0, y1, y2);
	#5
	
	// 0  0  0  0  1  X  X  X | 0  1  1
	assign a0 = 0; assign a1 = 0; assign a2 = 0; assign a3 = 0; assign a4 = 1; assign a5 = 1'bx; assign a6 = 1'bx; assign a7 = 1'bx; #5
	if(y0 == 0 && y1 == 1 && y2 == 1)
		$display("OK. ");
	else 
		$error("Wrong. Result is %b %b %b", y0, y1, y2);
	#5
	
	// 0  0  0  0  0  1  X  X | 0  1  0
	assign a0 = 0; assign a1 = 0; assign a2 = 0; assign a3 = 0; assign a4 = 0; assign a5 = 1; assign a6 = 1'bx; assign a7 = 1'bx; #5
	if(y0 == 0 && y1 == 1 && y2 == 0)
		$display("OK. ");
	else 
		$error("Wrong. Result is %b %b %b", y0, y1, y2);
	#5
	
	// 0  0  0  0  0  0  1  X | 0  0  1
	assign a0 = 0; assign a1 = 0; assign a2 = 0; assign a3 = 0; assign a4 = 0; assign a5 = 0; assign a6 = 1; assign a7 = 1'bx; #5
	if(y0 == 0 && y1 == 0 && y2 == 1)
		$display("OK. ");
	else 
		$error("Wrong. Result is %b %b %b", y0, y1, y2);
	#5
	
	// 0  0  0  0  0  0  0  1 | 0  0  0
	assign a0 = 0; assign a1 = 0; assign a2 = 0; assign a3 = 0; assign a4 = 0; assign a5 = 0; assign a6 = 0; assign a7 = 1; #5
	if(y0 == 0 && y1 == 0 && y2 == 0)
		$display("OK. ");
	else 
		$error("Wrong. Result is %b %b %b", y0, y1, y2);
end
 
endmodule
