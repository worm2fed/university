note
	description: "Clock counting seconds, minutes, and hours."

class
	CLOCK

create
	make

feature -- Initialization

	make (a_hours, a_minutes, a_seconds: INTEGER)
			-- Initialize clock.
		require
			-- Add here a precondition (if any)
		do
			-- Add the code
		ensure
			-- Add here a postcondition (if any)
		end

feature -- Access

	hours: INTEGER
			-- Hours of clock.

	minutes: INTEGER
			-- Minutes of clock.

	seconds: INTEGER
			-- Seconds of clock.

feature -- Element change

	set_hours (a_value: INTEGER)
			-- Set `hours' to `a_value'.
		require
			check_hour (a_value)
		do
			-- Add the code
		ensure
			-- Add here a postcondition (if any)
		end

	set_minutes (a_value: INTEGER)
			-- Set `minutes' to `a_value'.
		require
			-- Add here a precondition (if any)
		do
			-- Add the code
		ensure
			-- Add here a postcondition (if any)
		end

	set_seconds (a_value: INTEGER)
			-- Set `seconds' to `a_value'.
		require
			-- Add here a precondition (if any)
		do
			-- Add the code
		ensure
			-- Add here a postcondition (if any)
		end

feature -- Basic operations

	increase_hours
			-- Increase `hours' by one.
		require
			-- Add here a precondition (if any)

		do
			-- Add the code
		ensure
			-- Add here a postcondition (if any)
		end

	increase_minutes
			-- Increase `minutes' by one.
		require
			-- Add here a precondition (if any)
		do
			-- Add the code
		ensure
			-- Add here a postcondition (if any)
		end

	increase_seconds
			-- Increase `seconds' by one.
		require
			-- Add here a precondition (if any)
		do
			-- Add the code	
		ensure
			-- Add here a postcondition (if any)
		end

feature -- Helper queries
	check_hour (a: INTEGER): BOOLEAN
			-- checks if `a' is ok hour
		do
			result := a >= 0 and then a < 25
		end

invariant
	check_hour (hours)
	minutes > 0 and then minutes < 61
	seconds > 0 and then seconds < 61
end
