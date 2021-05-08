class
	APPLICATION

inherit
	ARGUMENTS

create
	make

feature {NONE}
	-- Initialization

	make
		local
			hero: HERO
			warrior: WARRIOR
			healer: HEALER
			l: LINKED_LIST [HERO]
		do
			create {WARRIOR} hero.make ("Thor")
			warrior := hero
			warrior.attack (hero)
		end

end
