class
	PERSON

create
	make

feature -- Initialization
	make (a_name: STRING)
			-- Create a person named `a_name'
		require
			a_name_valid: a_name /= Void and then not a_name.is_empty
		do
			name := a_name
			create coworker.make
		ensure
			name_set: name = a_name
		end

feature -- Access
	name: STRING
			-- Person's name

	coworker: LINKED_LIST [PERSON]
			-- Person's coworker

	has_flu: BOOLEAN
			-- Is person ill

feature -- Element change
	set_coworker (p: PERSON)
			-- Set `coworker' to `p'
		require
			p_exists: p /= Void
			p_different: p /= Current
		do
			coworker.extend (p)
		ensure
			coworker_set: coworker.last = p
		end

	set_flu
			-- Set `has_flu' to True
		do
			has_flu := True
		ensure
			has_flu: has_flu
		end

invariant
	name_valid: name /= Void and then not name.is_empty
end
