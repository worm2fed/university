note
	description: "Introduction to Traffic."

class
	PREVIEW

inherit
	ZURICH_OBJECTS

feature -- Explore Zurich
	zoo_station : STATION

	explore
			-- Modify the map.
		do
			Zurich.add_station ("Zoo", 800, -1200)
			Zurich.connect_station (31, "Zoo")

			Zurich_map.update
			Zurich_map.fit_to_window

			zoo_station := Zurich.station ("Zoo")
			Zurich_map.station_view (zoo_station).highlight
			wait(3)
			Zurich_map.station_view (zoo_station).unhighlight
		end

end
