class
	PLAYER

create
	make

feature
	make (a_name: STRING; a_id: INTEGER)
			-- Initialisation
		require
			not_empty_name: a_name /= Void
			has_id: a_id >= 0 and a_id <= 6
		do
			name 	 := a_name
			id 		 := a_id
			position := 1
			money	 := 50

			create die_1.make
			create die_2.make
		end

feature -- Access
	die_1: DIE
	die_2: DIE
	id: INTEGER
	name: STRING
	money: INTEGER
	position: INTEGER

feature -- Money Operations
	add_money
			-- Get money
		do
			money := money + 100
		ensure
			money_up: money = old money + 100
		end

	substarct_money
			-- Substarct money
		do
			if money >= 50 then
				money := money - 50
			else
				money := 0
			end
		ensure
			money_down: money = old money - 50 or money = 0
		end

feature -- Play actions
	set_position (step: INTEGER)
			-- Set position
		do
			if position + step <= 40 then
				if step = 1 then
					position := 1
				else
					position := position + step
				end
			else
				position := position + step - 40
			end

			check_square
		end

	check_square
			-- Check square
		local
			pos: INTEGER
		do
			pos := position

			if pos = 6 or pos = 16 or pos = 26 or pos = 36 then
				add_money
			elseif pos = 9 or pos = 19 or pos = 29 or pos = 39 then
				substarct_money
			end
		end

	make_step
			-- Make step
		do
			throw_dices
		end

	throw_dices
			-- Throw dices
		local
			nb_steps: INTEGER
		do
			die_1.roll
			die_2.roll

			if die_1.value = die_2.value then
				nb_steps := die_1.value

				if position - nb_steps >= 1 then
					set_position (-nb_steps)
				else
					set_position (1)
				end
			else
				nb_steps := die_1.value + die_2.value

				if position + nb_steps <= 40 then
					set_position (nb_steps)
				end
			end
		end


invariant
	has_id: id > 0 and id <= 6
	money >= 0
end
