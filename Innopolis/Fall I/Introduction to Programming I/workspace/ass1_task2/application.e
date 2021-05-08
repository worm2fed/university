note
	description: "ass1_task2 application root class"
	date: "$Date$"
	revision: "$Revision$"

class
	APPLICATION

inherit
	ARGUMENTS

create
	make

feature {NONE} -- Initialization
	binary_searh (x: INTEGER)
		do
			from
				i := 1; j := n
			until
				i = j
			loop
				m := (i + j) // 2
				if t [m] <= x then
					i := m
				else
					j := m
				end
			end

			Result := (x = t[i])
		end

	make
		local
			t: ARRAY [INTEGER]
			i: INTEGER
			j: INTEGER
			m: INTEGER
			n: INTEGER
			x: INTEGER

		do
			t.put(1,0)
			t.put(2,1)
			t.put(3,2)
			t.put(4,3)
			t.put(5,4)
			t.put(6,5)

			binary_searh(2)
		end

end
