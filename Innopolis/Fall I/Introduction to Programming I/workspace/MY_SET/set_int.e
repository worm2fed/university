note
	description: "{SET_INT} implements a set of integers"

class
	SET_INT

create
	empty_set

feature -- Initialisation

	empty_set
			-- initialises an empty set
		do
			create elements.make
		ensure
			no_elements: card = 0
		end

feature -- Queries

	is_empty: BOOLEAN
			-- Is the current set empty?
		do
			-- Add code here
		ensure
			Result = (card = 0)
		end

	has (v: INTEGER): BOOLEAN
			-- Does the Current set have 'v'?
		do
			-- Add code here
		ensure
			Result = (across 1 |..| card as ith some elements [ith.item] = v end)
		end

	card: INTEGER
			-- What is the cardinality of the Current set?
		do
			-- Add code here
		ensure
			Result = elements.count
		end

	is_subset (other: SET_INT): BOOLEAN
			-- Is the Current set a subset of 'other'?
		do
			-- Add code here
		ensure
			Result = across 1 |..| card as ith all other.has (elements [ith.item]) end
		end

	is_not_subset (other: SET_INT): BOOLEAN
			-- Is the Current set not a subset of 'other'?
		do
			-- Add code here
		ensure
			Result = across 1 |..| card as ith some not other.has (elements [ith.item]) end
		end

	is_strict_subset (other: SET_INT): BOOLEAN
			-- Is the Current set a strict subset of 'other'?
		do
			-- Add code here
		ensure
			other.card = card and then is_subset (other)
		end

	is_not_strict_subset (other: SET_INT): BOOLEAN
			-- Is the Current set not a strict subset of 'other'?
		do
			-- Add code here
		ensure
			not is_subset (other) or else other.card /= card
		end

	min: INTEGER
			-- gives the smallest number in the current set
		require
			at_least_one_element: not is_empty
		do
			-- Add code here
		ensure
			no_other_min: across 1 |..| card as e all Result <= elements.at (e.item) end
		end

	max: INTEGER
			-- gives the largest number in the current set
		require
			at_least_one_element: not is_empty
		do
			-- Add code here
		ensure
			no_other_max: across 1 |..| card as e all Result >= elements [e.item] end
		end


feature -- Modification

	add (v: INTEGER)
			-- adds an element to the set
		do
			-- Add code here
		ensure
			across 1 |..| card as ith  some elements [ith.item] = v end
		end

feature -- Operations (no side effect)
	union (other: SET_INT): SET_INT
			-- gives the union of the elements of other and Current
		do
			-- Add code here
		ensure
			all_elements: is_subset (Result) and then other.is_subset (Result)
			no_new_elements: across 1 |..| Result.card as ith all has (Result.elements [ith.item]) or other.has (Result.elements [ith.item]) end
		end

	intersection (other: SET_INT): SET_INT
			-- gives the intersection of the elements of other and Current
		do
			-- Add code here
		ensure
			inter_elements: across 1 |..| Result.card as ith all has (Result.elements [ith.item]) and other.has (Result.elements [ith.item]) end
		end

	difference (other: SET_INT): SET_INT
			-- gives the difference of the elements of Current and other
		do
			-- Add code here
		ensure
			inter_elements: across 1 |..| Result.card as ith all has (Result.elements [ith.item]) and not other.has (Result.elements [ith.item]) end
		end

	print_set
			-- `print_set' prints the set
		local
			index: INTEGER
		do
			print ("%N{")
			from
				index := 1
			until
				index > card
			loop
				print (elements [index])
				if index < card then
					print (", ")
				end
				index := index + 1
			end
			print ("}%N")
		end

feature -- Access

	elements: LINKED_LIST [INTEGER]
			-- `elements' contains the elements of the set


invariant
	no_repeated_elements: across 1 |..| card as ith all elements.occurrences (elements [ith.item]) = 1 end
end
