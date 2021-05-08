class
	CARD
inherit
	COMPARABLE

	redefine
		is_less
	end

create
	make

feature -- Initialisation

	make (n: INTEGER; c: CHARACTER)
			-- initialises one card with number `n' and colour `c'
		require
			number_in_range: n >= 1 and n <= 20
			valid_colour: c = 'R' or c = 'W' or c = 'G' or c = 'B'
		do
			number := n
			colour := c
		ensure
			number = n
			colour = c
		end

	is_less alias "<" (other: CARD): BOOLEAN
	do
		if number < other.number then
			Result := true
		else
			if number = other.number
			then
				if colour < other.colour then
					Result := true
				else
					Result := false
				end
			else
				Result := false
			end
		end
	end

feature -- Helper

	info_card: STRING
			-- returns the card information
		do
			Result := "("
			if number < 10 then
				Result := Result + " "
			end
			Result := Result + number.out + ", " + colour.out + ")"
		end

feature -- Attributes

	number: INTEGER
		-- number of the card: value from 1 .. 20

	colour: CHARACTER
		-- colour of the card

invariant
	number >= 1 and number <= 20
	colour = 'R' or colour = 'W' or colour = 'G' or colour = 'B'
end
