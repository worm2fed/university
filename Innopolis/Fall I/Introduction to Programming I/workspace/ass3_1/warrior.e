class
	WARRIOR

inherit
	HERO

	rename
		do_action as attack
	redefine
		level_up
	end

create
	make

feature
	-- Basic operations

	attack (other: HERO)
			-- Attack `other'
		local
			damage: INTEGER
		do
			damage := (5 * level).min (other.health)
			other.set_health(other.health - damage)

			print (name + " attacks " + other.name + ". Does " + damage.out + " damage %N")
		end

	level_up
		do
			Precursor
			print (name + " is now a level " + level.out + " warrior %N")
		end
end
