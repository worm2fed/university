note
	description: "web_forms application root class"
	date: "$Date$"
	revision: "$Revision$"

class
	APPLICATION

inherit
	WSF_DEFAULT_SERVICE[APPLICATION_EXECUTION]
		redefine
			initialize
		end

create
	make_and_launch

feature {NONE} -- Initialization

	initialize
			-- Initialises the Web Services

		local
			l_message: STRING
			database: DATABASE
		do
				-- Specific to `standalone' connector (the EiffelWeb server).
				-- See `{WSF_STANDALONE_SERVICE_LAUNCHER}.initialize'
	 		set_service_option ("port", port)
			import_service_options (create {WSF_SERVICE_LAUNCHER_OPTIONS_FROM_INI}.make_from_file ("server.ini"))
			create database.make

		end

feature {NONE} -- Implementation

	port: INTEGER = 9090
			-- Port number

end
