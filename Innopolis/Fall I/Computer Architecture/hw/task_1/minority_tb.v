`timescale 1ns/1ps
`default_nettype none

module minority_test;
	logic a, b, c;
	wire y;
	minority dut(.a(a),.b(b),.c(c),.y(y));

	initial
	begin
    		$dumpfile("minority.vcd");
    		$dumpvars;
		#100 $stop;
	end
initial
begin	
	assign a = 0; assign b = 0; assign c = 0; #5
	if(y == 1)$display("OK. ");
	else $error("Wrong. for %b %b %b result is not %b", a, b, c, y);
	#5
	assign a = 0; assign b = 0; assign c = 1; #5
	if(y == 1)$display("OK.");
	else $error("Wrong. for %b %b %b result is not %b", a, b, c, y);
	#5
	assign a = 0; assign b = 1; assign c = 0; #5
	if(y == 1)$display("OK.");
	else $error("Wrong. for %b %b %b result is not %b", a, b, c, y);
	#5
	assign a = 0; assign b = 1; assign c = 1; #5
	if(y == 0)$display("OK.");
	else $error("Wrong. for %b %b %b result is not %b", a, b, c, y);
	#5
	assign a = 1; assign b = 0; assign c = 0; #5
	if(y == 0)$display("OK.");
	else $error("Wrong. for %b %b %b result is not %b", a, b, c, y);
	#5
	assign a = 1; assign b = 0; assign c = 1; #5
	if(y == 0)$display("OK.");
	else $error("Wrong. for %b %b %b result is not %b", a, b, c, y);
	#5
	assign a = 1; assign b = 1; assign c = 0; #5
	if(y == 0)$display("OK.");
	else $error("Wrong. for %b %b %b result is not %b", a, b, c, y);
	#5
	assign a = 1; assign b = 1; assign c = 1; #5
	if(y == 0)$display("OK.");
	else $error("Wrong. for %b %b %b result is not %b", a, b, c, y);
end
 
endmodule
