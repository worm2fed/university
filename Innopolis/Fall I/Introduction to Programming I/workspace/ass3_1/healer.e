class
	HEALER

inherit
	HERO

	rename
		do_action as heal
	redefine
		make,
		level_up
	end

create
	make

feature
	-- Initialisation

	make (s: STRING)
			-- Create a healer with name `s'
		do
			Precursor (s)
			mana := 100
		end

feature
	-- Access

	mana: INTEGER

feature
	-- Basic operations

	heal (other: HERO)
			-- Heal `other'
		local
			h: INTEGER
		do
			if mana >= 10
			then h := (10 * level).min(100 - other.health)
					other.set_health (other.health + h)
					mana := mana - 10
					print (name + " heals " + other.name + " by " + h.out + " points %N")
			end
		end

	level_up
		do
			Precursor
			mana := 100
			print (name + " is now a level " + level.out + " healer %N")
		end
end
