class
	TEST_CLOCK

create
	make

feature
	make
			-- Initialising TEST_CLOK
		local
			c: CLOCK
		do
			create c.make (3, 15, 15)
			-- Add you code here to test CLOCK
		end

end
