deferred class
	HERO

feature
	-- Initialisation

	make (s: STRING)
			-- Create a hero with name `s'
		require
			s /= Void
		do
			name 	:= s
			level 	:= 1
			health 	:= 100
		end

feature
	-- Access

	name: 	STRING
	level: 	INTEGER
	health: INTEGER

feature
	-- Basic operations

	do_action (other: HERO)
			-- Perfom main action on `other'
		require
			alive: health > 0
		deferred
		end

	level_up
			-- Increase level
		do
			level := level + 1
			set_health (100)
		end

feature {HERO}
	-- Setters

	set_health (h: INTEGER)
			-- Set `health' to `h'
		require
			0 <= h and h <= 100
		do
			health := h

			if health = 0
			then print (name + "is dead. %N")
			end
		end

invariant
	name /= Void
	0 <= health and health <= 100
	level > 0
end
