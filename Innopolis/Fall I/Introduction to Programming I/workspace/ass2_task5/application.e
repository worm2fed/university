class
	APPLICATION

inherit
	ARGUMENTS

create
	make

feature
	row_num: INTEGER
		-- Stores number of rows
	column_num: INTEGER
		-- Stores number of rows
	board: LINKED_LIST [ LINKED_LIST[CHARACTER] ]
		-- Board for game
	is_end: BOOLEAN
		-- Tell us is there a winner or board full

feature {NONE}

	make
			-- Run application.
		local
			player: BOOLEAN
				-- Player number: 0 - first, 1 - second
		do
				-- Initialization
			create board.make
			create_board

				-- Print board
			print_board

				-- Start game
			from is_end := FALSE
					player := FALSE
			until is_end
			loop
					-- Make step
				make_step (player.to_integer)
					-- Print board
				print_board
					-- Check is game end
				is_end := check_winner (player.to_integer)

				if not is_end
				then is_end := check_is_full
					if is_end
					then io.put_string ("%NThere are no winners =)") end
				else io.put_string ("%NThe winner is Player " + (player.to_integer + 1).out) end

					-- Change player number
				player := player.negated
			end
		end

	make_step (player: INTEGER)
			-- Let player make a move
		local
			column: INTEGER
				-- Number of column for move
			correct: BOOLEAN
				-- Is input correct
			row_counter: INTEGER
				-- Counter for row
			is_step_was_made: BOOLEAN
				-- Variable for loop end
			cap: CHARACTER
				-- Character for player
		do
			if player = 0
			then cap := '*'
			else cap := 'x' end

				-- Get column for move
			from correct := FALSE
			until correct
			loop
				io.put_string ("%NPlayer " + (player + 1).out + " ( " + cap.out + " ): ")
				io.read_integer
				column := io.last_integer

				if column <= column_num
				then correct := TRUE
				else io.put_string ("Check your choice!%N") end
			end

			from is_step_was_made := FALSE
					row_counter := row_num
			until is_step_was_made or row_counter = 0
			loop
				if board.i_th (row_counter).i_th (column).is_space
				then board.i_th (row_counter).i_th (column) := cap
						is_step_was_made := TRUE
				end

				row_counter := row_counter - 1
			end
		end

	check_winner (player: INTEGER): BOOLEAN
			-- Check is there a winner
		local
			cap: CHARACTER
				-- Character for player
			h_counter, v_counter, d_counter: INTEGER
				-- Counter
		do
			if player = 0
			then cap := '*'
			else cap := 'x' end

				-- Check horizontal match
			from board.finish
					h_counter := 0
			until board.before
			loop
				from board.item.start
				until board.item.index = board.item.count
				loop
					if board.item.i_th (board.item.index).is_equal (cap)
					then h_counter := h_counter + 1
						if board.item.i_th (board.item.index + 1) /= Void
							and then board.item.i_th (board.item.index + 1).is_equal (cap)
						then h_counter := h_counter + 1
								board.item.forth
						else h_counter := 0 end
					end

					if h_counter = 4
					then board.item.go_i_th (board.item.count)
					else board.item.forth end
				end

				if h_counter < 4
				then h_counter := 0
				else board.start end
				board.back
			end

			if h_counter = 4 then Result := TRUE
			else
					-- Check vertical match
				from board.i_th (board.count).start
						v_counter := 0
				until board.i_th (board.count).exhausted
				loop
					from board.finish
					until board.index = 1
					loop
						if board.item.i_th (board.i_th (board.count).index).is_equal (cap)
						then v_counter := v_counter + 1
							if board.i_th (board.index - 1).i_th (board.i_th (board.count).index) /= Void
								and then board.i_th (board.index - 1).i_th (board.i_th (board.count).index).is_equal (cap)
							then v_counter := v_counter + 1
									board.back
							else v_counter := 0 end
						end

						if v_counter = 4
						then board.go_i_th (1)
						else board.back end
					end

					if v_counter < 4
					then v_counter := 0
					else board.i_th (board.count).finish end
					board.i_th (board.count).forth
				end

				if v_counter = 4 then Result := TRUE
				else
						-- Check diagonal match
					from board.start
							d_counter := 0
					until board.exhausted
					loop
						if (board.index - 1 - board.count) > -4
						then board.finish
						else
							from board.item.start
							until board.item.exhausted
							loop
								if (board.item.index - 1 - board.item.count) > -4
								then board.item.finish
								else
										-- If cell match
									if board.item.i_th (board.item.index).is_equal (cap)
									then d_counter := d_counter + 1
											-- If next cell match
										if board.i_th (board.index + 1).i_th (board.item.index + 1).is_equal (cap)
										then d_counter := d_counter + 1
													-- If third cell match
												if board.i_th (board.index + 2).i_th (board.item.index + 2).is_equal (cap)
												then d_counter := d_counter + 1
															-- If fourth cell match
														if board.i_th (board.index + 3).i_th (board.item.index + 3).is_equal (cap)
														then d_counter := d_counter + 1
														else d_counter := 0 end
												else d_counter := 0 end
										end
									else d_counter := 0 end
								end

								if d_counter = 4
								then board.finish
										board.item.finish
										board.item.forth
								else board.item.forth end
							end
						end

						if d_counter < 4
						then d_counter := 0
						else board.finish end
						board.forth
					end

					if d_counter = 4 then Result := TRUE
					else
						from board.start
								d_counter := 0
						until board.exhausted
						loop
							if (board.index - 1 - board.count) > -4
							then board.finish
							else
								from board.item.finish
								until board.item.before
								loop
									if (board.item.count - board.item.index - 1) <= 2
									then board.item.start
									else
											-- If cell match
										if board.item.i_th (board.item.index).is_equal (cap)
										then d_counter := d_counter + 1
												-- If next cell match
											if board.i_th (board.index + 1).i_th (board.item.index - 1).is_equal (cap)
											then d_counter := d_counter + 1
														-- If third cell match
													if board.i_th (board.index + 2).i_th (board.item.index - 2).is_equal (cap)
													then d_counter := d_counter + 1
																-- If fourth cell match
															if board.i_th (board.index + 3).i_th (board.item.index - 3).is_equal (cap)
															then d_counter := d_counter + 1
															else d_counter := 0 end
													else d_counter := 0 end
											end
										else d_counter := 0 end
									end

									if d_counter = 4
									then board.finish
											board.item.start
											board.item.back
									else board.item.back end
								end
							end

							if d_counter < 4
							then d_counter := 0
							else board.finish end
							board.forth
						end

						if d_counter = 4 then Result := TRUE
						else Result := FALSE end
					end
				end
			end
		end

	check_is_full: BOOLEAN
			-- Check is board full
		local
			cell_counter: INTEGER
				-- count full cels
		do
			from board.i_th (1).start
			until board.i_th (1).exhausted
			loop
				if not board.i_th (1).item.is_space
				then cell_counter := cell_counter + 1 end
				board.i_th (1).forth
			end

			if cell_counter = column_num
			then Result := TRUE
			else Result := FALSE end
		end

	print_board
			-- Print board
		do
			from board.start
			until board.exhausted
			loop
				io.put_character('|')
				from board.item.start
				until board.item.exhausted
				loop
					io.put_character(board.item.item)
					io.put_character ('|')

					board.item.forth
				end
				io.put_character('%N')

				board.forth
			end
		end

	create_board
			-- Allows to enter board size
		local
			correct: BOOLEAN
				-- Stores is user input correct
			counter_row, counter_column: INTEGER
				-- Counter for board creation
			tmp_list: LINKED_LIST [CHARACTER]
		do
			from correct := FALSE
			until correct
			loop
				io.put_string ("%NEnter positive number (row): ")
				io.read_integer
				row_num := io.last_integer
				io.put_string ("%NEnter positive number (column): ")
				io.read_integer
				column_num := io.last_integer

				if row_num >= 4 and column_num >= 4
				then correct := TRUE
				else io.put_string ("The board must have size at least 4x4!%N") end
			end

			from counter_row := 0
			until counter_row = row_num
			loop
				create tmp_list.make
				from counter_column := 0
				until counter_column = column_num
				loop
					tmp_list.extend (' ')
					counter_column := counter_column + 1
				end

				board.extend (tmp_list)
				counter_row := counter_row + 1
			end
		ensure
			row_set: row_num >= 4
			column_set: column_num >= 4
		end
end
