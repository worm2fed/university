class
	TEST_BANK

create
	make

feature
	make
			-- Initialising TEST_BANK
		local
			b: BANK_ACCOUNT
		do
			create b.make
			b.set_credit_limit (200)
			print (b.available_amount)
			io.new_line
		end

end
