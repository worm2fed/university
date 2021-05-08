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
