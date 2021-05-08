class
	APPLICATION

inherit
	ARGUMENTS

create
	make

feature
	line: LINKED_LIST [STRING]
			-- Stores string represantation of line

feature {NONE} -- Initialization

	make
			-- Run application.
		local
			number: INTEGER
				-- Stores user's input
			correct: BOOLEAN
				-- Stores is user input correct
		do
			from
				correct := FALSE
			until
				correct
			loop
				io.put_string ("%NEnter positive integer number: ")
				io.read_integer
				number := io.last_integer

				if number > 0 then
					correct := TRUE
				else
					io.put_string ("Try again. You should enter positive integer number!%N")
				end
			end

			-- Create linked list with triangle in it's items
			create line.make

			-- Paint triangle
			create_triangle (number)
			paint_triangle
		end


	create_triangle (number: INTEGER)
			-- Create triangle with hypotenuse as number of `number'
		local
			current_line: INTEGER
				-- Number of current line
			digits_in_line: INTEGER
				-- Count number of digits in line
			counter: INTEGER
				-- Counter of digits
			digits_counter: INTEGER
				-- Counter for digits in line
			temp_string: STRING
				-- Temporary variable for line
		do
			from
				current_line := 0
				digits_in_line := 1
				counter := 1
			until
				current_line = number
			loop
					-- Create temp string
				create temp_string.make_empty

					-- Print white spaces
				if ((current_line + 1) \\ 2) = 0 then
					temp_string.append (" ")
				end

					-- Count digits in line
				if (current_line > 0) and (current_line \\ 2) = 0 then
					digits_in_line := digits_in_line + 1
				end

					-- Print digits into line
				from
					digits_counter := 0
				until
					digits_counter = digits_in_line
				loop
					temp_string.append (counter.out)
					temp_string.append (" ")

					counter := counter + 1
					digits_counter := digits_counter + 1
				end

				line.extend (temp_string)

				current_line := current_line + 1
			end

		end

	spaces (n: INTEGER): STRING
			-- Add spaces on length `n'.
		do
			Result := " "
			Result.multiply (n)
		end

	paint_triangle
			-- Paint two triangles with hypotenuse as number of `number'
		local
			digits: LIST [STRING]
				-- Stores temporary list of digits from line
		do
			from
				line.start
			until
				line.exhausted
			loop
				if line.item /= Void then
						-- Print first triangle
					io.put_string (line.item)
						-- Print white spaces
					io.put_string (spaces (2 + line.last.count * 2 - line.item.count * 2))
						-- Print second triangle
					digits := line.item.split (' ')
					from
						digits.finish
					until
						digits.index = 0
					loop
						io.put_string (digits.item)
						io.put_character (' ')

						digits.back
					end

					io.put_character ('%N')
				end

				if not line.exhausted then
					line.forth
				end
			end
		end
end