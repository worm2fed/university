class
	APPLICATION

create
	execute

feature {NONE} -- Initialization

	execute
			-- Run application.
		do
			-- Add your code here.
			io.put_string("Name: ")
			io.put_string("Andrey Demidenko")
			io.new_line
			io.put_string("Age: ")
			io.put_integer(20)
			io.new_line
			io.put_string("Native language: ")
			io.put_string("Russion")
			io.new_line
			io.put_string("Has a cat: ")
			io.put_boolean(False)
		end

end
