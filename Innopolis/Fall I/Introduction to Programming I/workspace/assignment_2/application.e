class
	APPLICATION

inherit
	ARGUMENTS

create
	make

feature {NONE} -- Initialization

	make
			-- Simulate flu epidemic
		local
			joe, mary, tim, sarah, bill, cara, adam: PERSON
		do
			create joe.make("Joe")
			create mary.make ("Mary")
			create tim.make ("Tim")
			create sarah.make ("Sarah")
			create bill.make ("Bill")
			create cara.make ("Cara")
			create adam.make ("Adam")

			joe.set_coworker (sarah)
			adam.set_coworker (joe)
			tim.set_coworker (sarah)
			sarah.set_coworker (cara)
			bill.set_coworker (tim)
			cara.set_coworker (mary)
			mary.set_coworker (bill)

			infect (bill)
		end

	infect (p: PERSON)
			-- Infecting feature
		require
			p_exists: p /= Void
		do
			from
				p.coworker.start
			until
			   	p.coworker.exhausted
			loop
				if p.coworker.item /= Void
					and then not p.coworker.item.has_flu
				then
					p.coworker.item.set_flu

					infect (p.coworker.item)
				end
				if not p.coworker.exhausted then
					p.coworker.forth
				end
			end

			p.set_flu
		end
end
