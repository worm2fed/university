class
	TEST

create
	testing_impl

feature -- Access
	cards: LINKED_BAG [CARD]
	sorted_cards: LINKED_LIST[CARD]

feature -- Testing the implementation
	testing_impl
			-- it initialises this class which is devoted for testing
		do
			create cards

			create sorted_cards.make
			cards.add (create {CARD}.make (5, 'R'), 3)
			cards.add (create {CARD}.make (10, 'W'), 4)
			cards.add (create {CARD}.make (16, 'B'), 7)
			cards.add (create {CARD}.make (7, 'R'), 3)
			cards.add (create {CARD}.make (6, 'W'), 4)
			cards.add (create {CARD}.make (5, 'R'), 3)
			cards.add (create {CARD}.make (10, 'W'), 4)
			cards.add (create {CARD}.make (7, 'B'), 7)
			cards.add (create {CARD}.make (7, 'R'), 3)
			cards.add (create {CARD}.make (3, 'W'), 4)
			sorting

		end

	sorting
		local
			t:CARD
			i:INTEGER
			sub:LINKED_BAG[CARD]
		do
			from sub:= cards.deep_twin
			until sub.occurrences(sub.min) = 0
			loop
				t:= sub.min
				from i:=1
				until i > sub.occurrences (t)
				loop
					sorted_cards.force (t)
					print(sorted_cards.last.number.out + " " + sorted_cards.last.colour.out)
					io.new_line
					i:=i+1
				end
				sub.remove (t, sub.occurrences (t))
			end
		ensure
			sorted: across sorted_cards as a all a.item >= sorted_cards[(a.target_index - 1).max(0)]  end
		end

end
