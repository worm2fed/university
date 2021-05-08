class
	LINKED_BAG [G -> COMPARABLE] -- Complete if necessary

feature -- Access

	occurrences (v: G): INTEGER
			-- Number of occurrences of 'v'.
		local
			c: BAG_CELL [G]
		do
			from
				c := first
			until
				c = Void or else c.value = v
			loop
				c := c.next
			end
			if c /= Void then
				Result := c.count
			end
		ensure
			non_negative_result: Result >= 0
		end

feature -- Element change

	add (v: G; n: INTEGER)
			-- Add 'n' copies of 'v'.
		require
			n_positive: n > 0
		local
			c: BAG_CELL [G]
		do
			if first = void then
				first := create {BAG_CELL [G]}.make (v)
				first.set_count (n)
			else
				from
					c := first
				until
					c.next = Void or else c.value = v
				loop
					c := c.next
				end
				if c.value = v then
					c.set_count (c.count + n)
				else
					c.set_next (create {BAG_CELL [G]}.make (v))
					c.next.set_count (n)
				end
			end
		ensure
			n_more: occurrences (v) = old occurrences (v) + n
		end

	remove (v: G; n: INTEGER)
			-- Remove as many copies of 'v' as possible, up to 'n'.
		require
			n_positive: n > 0
		local
			c: BAG_CELL [G]
			prev: BAG_CELL [G]
		do
			from
				c := first
				prev := void
			until
				c.next = Void or else c.value = v
			loop
				prev := c
				c := c.next
			end

			if c.value = v then
				if occurrences (v) <= n then
					if prev = void then
						first := c.next
					else
						prev.set_next (c.next)
					end
				else
					c.set_count (c.count - n)
				end
			end
		ensure
			n_less: occurrences (v) = (old occurrences (v) - n).max (0)
		end

	subtract (other: LINKED_BAG [G])
			-- Remove all elements of 'other'.
		require
			other_exists: other /= Void
		local
			c: BAG_CELL [G]
		do
			from
				c := first
			until
				c = Void
			loop
				remove (c.value, c.count)
				c := c.next
			end
		end

feature -- min and max

	min: G
			-- Get element with minimum value in bag
		local
			c: BAG_CELL [G]
		do
			if first /= void then
				Result := first.value

				from
					c := first
				until
					c = Void
				loop
					if c.value < Result then
						Result := c.value
					end

					c := c.next
				end
			end
		ensure
			minimum: first = void or else Result <= first.value
		end

	max: G
			-- Get element with maximum value in bag
		local
			c: BAG_CELL [G]
		do
			if first /= void then
				Result := first.value
				from
					c := first
				until
					c = Void
				loop
					if c.value > Result then
						Result := c.value
					end

					c := c.next
				end
			end
		ensure
			maximum: first = void or else Result >= first.value
		end

feature {LINKED_BAG} -- Implementation

	first: BAG_CELL [G] -- First cell.

end
