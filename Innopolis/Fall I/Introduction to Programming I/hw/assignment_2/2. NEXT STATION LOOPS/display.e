note
	description: "Route information displays."

class
	DISPLAY

inherit
	ZURICH_OBJECTS

feature -- Explore Zurich

	add_public_transport
			-- Add a public transportation unit per line.
		do
			Across Zurich.lines as line
			loop
				line.item.add_transport
			end
		end

	update_transport_display (t: PUBLIC_TRANSPORT)
			-- Update route information display inside transportation unit `t'.
		require
			t_exists: t /= Void
		local
			station_after_arriving: STATION
			next_station_after_arriving: STATION
		do
			-- Your code here
			console.clear
			console.append_line (t.line.number.out +  " Welcome")
			-- Info about next station
			console.append_line (stop_info (t, t.arriving))

			if t.arriving /= t.destination then
				station_after_arriving := t.line.next_station (t.arriving,
															t.destination)
				-- Info about station after arriving
				console.append_line (stop_info (t, station_after_arriving))

				if station_after_arriving /= t.destination then
					next_station_after_arriving := t.line.next_station (station_after_arriving,
																	t.destination)
					console.append_line (stop_info (t, next_station_after_arriving))

					if next_station_after_arriving /= t.destination then
						console.append_line ("...")
						console.append_line (stop_info (t, t.destination))
					end
				end
			end
		end

	stop_info (t: PUBLIC_TRANSPORT; s:STATION) : STRING
			-- Information about stop
		require
			t_exists: t /= Void
			s_exists: s /= Void
		local
			time_to_arrive: REAL_64
			res: STRING
		do
			res := "%T "

			-- If time greater than 60 seconds, then we convert it to minutes
			if t.time_to_station (s) > 60 then
				time_to_arrive := t.time_to_station (s) // 60

				res.append (time_to_arrive.out + " min. ")
			else
				res.append (t.time_to_station (s).out + " sec. ")
			end

			res.append ("%T" + s.name + " | Connected with:")

			Across s.lines as line
			loop
--				 if t.line /= line.item
--				 	and not t.line.follows (line.item,
--				 						t.line.next_station (s, t.departed),
--				 						t.line.next_station (s, t.destination))

				if t.line /= line.item
					and not t.line.follows (line.item,
										s,
										t.line.next_station (s, t.destination))
				then
					res.append (" " + line.item.number.out)
				end
			end

			Result := res
		end

end
