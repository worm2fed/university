class
	GAME

create
	begin

feature {APPLICATION} -- Initialization
	nb_players: INTEGER
	players: LINKED_LIST [PLAYER]
	game_end: BOOLEAN
	draw_game: BOOLEAN

feature
	begin
			-- Run application.
		local
			i,j: INTEGER
			temp: PLAYER
		do
			get_nb_players
			create players.make

			from
				i := 1
			until
				i > nb_players
			loop
				create temp.make (get_a_name(i), i)
				players.extend (temp)
				i := i + 1
			end

			from
			until
				game_end
			loop
				from
					j := 1
					players.start
				until
					j > nb_players
				loop
					players.item.make_step

					if j < nb_players then
						players.forth
					end

					j := j + 1
				end

				current_info
			end

		end

	get_nb_players
			-- Input the number of players
		local
			correct_input: BOOLEAN
		do
			print ("Enter the number of players: ")

			from
				correct_input := false
			until
				correct_input
			loop
				io.read_integer

				if (io.last_integer >= 2) and (io.last_integer <= 6) then
					correct_input := true
					set_nb_players (io.last_integer)
				else
					print ("Incorrect input, try again: ")
				end
			end
		end

	get_a_name(id: INTEGER): STRING
			-- Get player name
		local
			correct_input: BOOLEAN
		do
			print ("Enter the name of player number " + id.out + ": ")

			from
				Result := ""
				correct_input := false
			until
				correct_input
			loop
				io.read_line

				if (io.last_string.count >= 2) and (io.last_string.is_valid_as_string_8) then
					correct_input := true
					Result := io.last_string
				else
					print ("%NThe name if player number " + id.out + " is incorrect, try again: ")
				end
			end
		end

	check_winner
		local
			win_cash, win_id : INTEGER
		do
			win_cash := -1
			win_id := 0

			across
				players as l
			loop
				if l.item.position = 40 then
					game_end := true

					across players as k
					loop
						if k.item.money > win_cash then
							win_cash := k.item.money
							win_id := k.item.id
						elseif
							k.item.money = win_cash then
							draw_game := true
						end
					end
				end
			end

			print("%N")
			if game_end and not draw_game then
				print ("Player number " + win_id.out + " wins with cash " + win_cash.out)
				elseif
					draw_game and game_end
				then
					draw
			end
		end

	current_info
			-- Cutrent game state
	do
		across players as l
		loop
			print ("Player number " + l.item.id.out + " is on position " + l.item.position.out + " and has " +
			l.item.money.out + " money. (" + l.item.die_1.value.out + ":" + l.item.die_2.value.out + ") %N")
		end

		check_winner
	end

	draw
		-- Draw game
		do
			print ("Draw")
		end

feature {GAME}
	set_nb_players(new_n: INTEGER)
		require
			not_more: new_n <= 6
			not_less: new_n >= 2
		do
			nb_players := new_n
		ensure
			nb_players_is_set: nb_players = new_n
		end

end
