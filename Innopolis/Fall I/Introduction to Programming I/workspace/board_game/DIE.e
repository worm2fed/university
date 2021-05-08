class
	DIE

create
	make

feature
	random: V_RANDOM
	value: INTEGER

feature
	make
			-- Initialisation
		do
			value := 0
		end

	roll
			-- Implements a dice rolling
		do
			from
				create random
			until
				random.bounded_item (1, 1000) = 13
			loop
				value := random.bounded_item (1, 6)
				random.forth
			end
		end
end
