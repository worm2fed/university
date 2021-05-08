note
	description: "Summary description for {APPLICATION_EXECUTION}."
	author: ""
	date: "$Date$"
	revision: "$Revision$"

class
	APPLICATION_EXECUTION

inherit

	WSF_ROUTED_EXECUTION
		redefine
			initialize
		end

	WSF_URI_HELPER_FOR_ROUTED_EXECUTION

	WSF_URI_TEMPLATE_HELPER_FOR_ROUTED_EXECUTION

	SHARED_EXECUTION_ENVIRONMENT
		export
			{NONE} all
		end

create
	make

feature {NONE} -- Initialization

	initialize
			-- Initialize current service.
		do
			Precursor
			initialize_router
		end

	setup_router
			-- Setup `router'
		local
			fhdl: WSF_FILE_SYSTEM_HANDLER
		do
			map_uri_template_agent ("/api/request", agent handle_request, router.all_allowed_methods)
			map_uri_template_agent ("/api/save", agent handle_save, router.all_allowed_methods)
			map_uri_template_agent ("/api/test_message", agent handle_test_message, router.all_allowed_methods)
			map_uri_template_agent ("/api/{operation}", agent handle_api, router.all_allowed_methods)
			create fhdl.make_hidden (document_root)
			fhdl.set_directory_index (<<"index.html">>)
			router.handle ("", fhdl, router.methods_GET)
		end

feature -- Execution

	handle_request (req: WSF_REQUEST; res: WSF_RESPONSE)
		local
			db: SQLITE_DATABASE
			db_query_statement: SQLITE_QUERY_STATEMENT
			db_insert_statement: SQLITE_INSERT_STATEMENT
			cursor: SQLITE_STATEMENT_ITERATION_CURSOR
			cursor_2: SQLITE_STATEMENT_ITERATION_CURSOR
			res_body : STRING
			query: READABLE_STRING_8
			i: INTEGER
			response_id: INTEGER
			page: WSF_HTML_PAGE_RESPONSE
			db_table_names: ARRAY[STRING]
			initial_date : STRING
			final_date : STRING
			laboratory : STRING
			industry : STRING
			query_2:READABLE_STRING_8
		do
			res_body := ""
			db_table_names := <<"CoursesTaught",
								"Examinations",
								"StudentsSupervised",
								"CompletedStudentsReports",
								"CompletedPhDTheses",
								"Grants",
								"ResearchProjects",
								"ResearchCollaborations",
								"ConferencePublications",
								"JournalPublications",
								"Patents",
								"IPLicensing",
								"BestPaperAwards",
								"Memberships",
								"Prizes",
								"IndustryCollaborations",
								"OtherInformation",
								"NameOfUniversityUnit",
								"NameOfHeadOfUniversityUnit",
								"StartOfReportingPeriod",
								"EndOfReportingPeriod">>
			create db.make_open_read_write ("Eiffel_forms_DB.db")

			if attached {WSF_STRING} req.query_parameter ("id") as p_id then
				if p_id.url_encoded_value.is_equal ("1") then
					query := "SELECT RESPONSE_ID FROM StartOfReportingPeriod WHERE ANSWER LIKE '%%2017%%' ;"
					create db_query_statement.make (query, db)
					cursor := db_query_statement.execute_new
					io.put_string ("id == 1")
					io.new_line
					from
					until
						cursor.after
					loop
						query := "SELECT ANSWER FROM ConferencePublications WHERE RESPONSE_ID =" + cursor.item.integer_value (1).out + ";"
						cursor.forth
						create db_query_statement.make (query, db)
						cursor_2 := db_query_statement.execute_new
						res_body := res_body + "<BR>" + cursor_2.item.string_value (1)
					end

				elseif p_id.url_encoded_value.is_equal ("2") then
					io.put_string ("id == 2")
					io.new_line
					laboratory := "te"
					query := "SELECT RESPONSE_ID FROM NameOfUniversityUnit WHERE NameOfUniversityUnit.ANSWER = '" + laboratory + "' ;"
					create db_query_statement.make (query, db)
					cursor := db_query_statement.execute_new

					res_body := "Name of University Unit: " + laboratory + "<BR>"
					res_body := res_body + "Name of head of University Unit: "
					from
					until
						cursor.after
					loop
						query_2 := "SELECT ANSWER FROM NameOfHeadOfUniversityUnit WHERE RESPONSE_ID =" + cursor.item.integer_value (1).out + ";"
						cursor.forth
						create db_query_statement.make (query_2, db)
						cursor_2 := db_query_statement.execute_new
						if not res_body.has_substring (cursor_2.item.string_value (1)) then
							res_body := res_body + cursor_2.item.string_value (1) + ", "
						end
					end
					res_body := res_body + "<BR>"


					create db_query_statement.make (query, db)
					cursor := db_query_statement.execute_new
					res_body := res_body + "Courses taught: "
					from
					until
						cursor.after
					loop
						query_2 := "SELECT ANSWER FROM CoursesTaught WHERE RESPONSE_ID =" + cursor.item.integer_value (1).out + ";"
						cursor.forth
						create db_query_statement.make (query_2, db)
						cursor_2 := db_query_statement.execute_new
						if not res_body.has_substring (cursor_2.item.string_value (1)) then
							res_body := res_body + cursor_2.item.string_value (1)+ ", "
						end
					end
					res_body := res_body + "<BR>"


					create db_query_statement.make (query, db)
					cursor := db_query_statement.execute_new
					res_body := res_body + "Research projects: "
					from
					until
						cursor.after
					loop
						query_2 := "SELECT ANSWER FROM ResearchProjects WHERE RESPONSE_ID =" + cursor.item.integer_value (1).out + ";"
						cursor.forth
						create db_query_statement.make (query_2, db)
						cursor_2 := db_query_statement.execute_new
						if not res_body.has_substring (cursor_2.item.string_value (1)) then
							res_body := res_body + cursor_2.item.string_value (1)+ ", "
						end
					end
					res_body := res_body + "<BR>"

					create db_query_statement.make (query, db)
					cursor := db_query_statement.execute_new
					res_body := res_body + "Conference publications: "
					from
					until
						cursor.after
					loop
						query_2 := "SELECT ANSWER FROM ConferencePublications WHERE RESPONSE_ID =" + cursor.item.integer_value (1).out + ";"
						cursor.forth
						create db_query_statement.make (query_2, db)
						cursor_2 := db_query_statement.execute_new
						if not res_body.has_substring (cursor_2.item.string_value (1)) then
							res_body := res_body + cursor_2.item.string_value (1)+ ", "
						end
					end
					res_body := res_body + "<BR>"

					create db_query_statement.make (query, db)
					cursor := db_query_statement.execute_new
					res_body := res_body + "Journal publications: "
					from
					until
						cursor.after
					loop
						query_2 := "SELECT ANSWER FROM JournalPublications WHERE RESPONSE_ID =" + cursor.item.integer_value (1).out + ";"
						cursor.forth
						create db_query_statement.make (query_2, db)
						cursor_2 := db_query_statement.execute_new
						if not res_body.has_substring (cursor_2.item.string_value (1)) then
							res_body := res_body + cursor_2.item.string_value (1)+ ", "
						end
					end
					res_body := res_body + "<BR>"

					create db_query_statement.make (query, db)
					cursor := db_query_statement.execute_new
					res_body := res_body + "Patents: "
					from
					until
						cursor.after
					loop
						query_2 := "SELECT ANSWER FROM Patents WHERE RESPONSE_ID =" + cursor.item.integer_value (1).out + ";"
						cursor.forth
						create db_query_statement.make (query_2, db)
						cursor_2 := db_query_statement.execute_new
						if not res_body.has_substring (cursor_2.item.string_value (1)) then
							res_body := res_body + cursor_2.item.string_value (1)+ ", "
						end
					end
					res_body := res_body + "<BR>"

					create db_query_statement.make (query, db)
					cursor := db_query_statement.execute_new
					res_body := res_body + "Prizes: "
					from
					until
						cursor.after
					loop
						query_2 := "SELECT ANSWER FROM Prizes WHERE RESPONSE_ID =" + cursor.item.integer_value (1).out + ";"
						cursor.forth
						create db_query_statement.make (query_2, db)
						cursor_2 := db_query_statement.execute_new
						if not res_body.has_substring (cursor_2.item.string_value (1)) then
							res_body := res_body + cursor_2.item.string_value (1)+ ", "
						end
					end
					res_body := res_body + "<BR>"

					create db_query_statement.make (query, db)
					cursor := db_query_statement.execute_new
					res_body := res_body + "Other: "
					from
					until
						cursor.after
					loop
						query_2 := "SELECT ANSWER FROM OtherInformation WHERE RESPONSE_ID =" + cursor.item.integer_value (1).out + ";"
						cursor.forth
						create db_query_statement.make (query_2, db)
						cursor_2 := db_query_statement.execute_new
						if not res_body.has_substring (cursor_2.item.string_value (1)) then
							res_body := res_body + cursor_2.item.string_value (1)+ ", "
						end
					end
					res_body := res_body + "<BR>"




				elseif p_id.url_encoded_value.is_equal ("3") then
					initial_date := "2017-01-01"
					final_date := "2017-07-01"

					query := "SELECT StartOfReportingPeriod.RESPONSE_ID FROM StartOfReportingPeriod, EndOfReportingPeriod WHERE StartOfReportingPeriod.ANSWER > '" + initial_date + "' AND EndOfReportingPeriod.ANSWER < '" + final_date +"' AND StartOfReportingPeriod.RESPONSE_ID = EndOfReportingPeriod.RESPONSE_ID ; "
					create db_query_statement.make (query, db)
					cursor := db_query_statement.execute_new
					io.put_string ("id == 3")
					io.new_line
					from
					until
						cursor.after
					loop
						query := "SELECT ANSWER FROM CoursesTaught WHERE RESPONSE_ID =" + cursor.item.integer_value (1).out + ";"
						cursor.forth
						create db_query_statement.make (query, db)
						cursor_2 := db_query_statement.execute_new
						res_body := res_body + "<BR>" + cursor_2.item.string_value (1)
					end

				elseif p_id.url_encoded_value.is_equal ("4") then
					io.put_string ("id == 4")
					io.new_line
					query := "SELECT RESPONSE_ID FROM StartOfReportingPeriod WHERE ANSWER LIKE '%%2017%%' ;"
					create db_query_statement.make (query, db)
					cursor := db_query_statement.execute_new
					from
					until
						cursor.after
					loop
						query := "SELECT ANSWER FROM NameOfUniversityUnit WHERE RESPONSE_ID =" + cursor.item.integer_value (1).out + ";"
						create db_query_statement.make (query, db)
						cursor_2 := db_query_statement.execute_new
						res_body := res_body + "<BR>" + cursor_2.item.string_value (1)

						query := "SELECT ANSWER FROM StudentsSupervised WHERE RESPONSE_ID =" + cursor.item.integer_value (1).out + ";"
						create db_query_statement.make (query, db)
						cursor_2 := db_query_statement.execute_new
						res_body := res_body + " - " + cursor_2.item.string_value (1) + " Students"
						cursor.forth
					end

				elseif p_id.url_encoded_value.is_equal ("5") then
					io.put_string ("id == 5")
					io.new_line
					laboratory := "tes"
					query := "SELECT RESPONSE_ID FROM NameOfUniversityUnit WHERE ANSWER ='" + laboratory + "' ;"
					create db_query_statement.make (query, db)
					cursor := db_query_statement.execute_new
					i := 0
					from
					until
						cursor.after
					loop
						i := i + 1
						cursor.forth
					end
					res_body := i.out


				elseif p_id.url_encoded_value.is_equal ("6") then
					io.put_string ("id == 6")
					io.new_line
					initial_date := "2017-01-01"
					final_date := "2017-12-31"
					laboratory := "te"
					query := "SELECT StartOfReportingPeriod.RESPONSE_ID FROM StartOfReportingPeriod, EndOfReportingPeriod, NameOfUniversityUnit WHERE StartOfReportingPeriod.ANSWER >= '" + initial_date + "' AND EndOfReportingPeriod.ANSWER <= '" + final_date +"' AND StartOfReportingPeriod.RESPONSE_ID = EndOfReportingPeriod.RESPONSE_ID AND NameOfUniversityUnit.RESPONSE_ID = EndOfReportingPeriod.RESPONSE_ID  AND NameOfUniversityUnit.ANSWER = '"+ laboratory  + "' ;"
					create db_query_statement.make (query, db)
					cursor := db_query_statement.execute_new
					i := 0
					from
					until
						cursor.after
					loop
						query := "SELECT ANSWER FROM StudentsSupervised WHERE RESPONSE_ID =" + cursor.item.integer_value (1).out + ";"
						cursor.forth
						create db_query_statement.make (query, db)
						cursor_2 := db_query_statement.execute_new
						i := i + cursor_2.item.integer_value (1)
						io.put_string (i.out)
						io.new_line
						cursor.forth
					end

					res_body := i.out

				elseif p_id.url_encoded_value.is_equal ("7") then
					io.put_string ("id == 7")
					io.new_line
					industry := "q"
					query := "SELECT RESPONSE_ID FROM IndustryCollaborations WHERE ANSWER LIKE '%%" + industry +"%%';"
					create db_query_statement.make (query, db)
					cursor := db_query_statement.execute_new

					from
					until
						cursor.after
					loop
						query := "SELECT ANSWER FROM NameOfUniversityUnit WHERE RESPONSE_ID = '" + cursor.item.integer_value (1).out + "' ;"
						io.put_string (cursor.item.integer_value (1).out)
						io.new_line
						cursor.forth
						create db_query_statement.make (query, db)
						cursor_2 := db_query_statement.execute_new
						io.put_string (cursor_2.after.out)
						io.new_line
						if not cursor_2.after then
							io.put_string (cursor_2.item.string_value (1))
							io.new_line
							res_body := res_body + "<BR>" + cursor_2.item.string_value (1)
						end


					end


				else
					--nothing
				end



				create page.make
				res_body :="[
							<html>
							<head>
							    <meta charset=%"utf-8%">
							    <meta name=%"viewport%" content=%"width=device-width, initial-scale=1.0%">
							    <title>Eiffel Web Form - Answer</title>
							    <link href=%"css/bootstrap.min.css%" rel=%"stylesheet%">
							    <link href=%"css/animate.css%" rel=%"stylesheet%">
							    <link href=%"css/style.css%" rel=%"stylesheet%">
							</head>
							<body>
							    <div id=%"wrapper%">
							        <div class=%"row%">
							            <div class=%"col-lg-6 center-block%" style=%"float: none;%">
							                <div class=%"ibox-content%">
							                	<h1></h1>
                    							<p>
                    							]" + res_body + "[
                    							</p>
								                </div>
								            </div>
								        </div>
								    </div>

								<!-- Mainly scripts -->
								<script src=%"js/jquery-3.1.1.min.js%"></script>
								<script src=%"js/bootstrap.min.js%"></script>

								<!-- Custom and plugin javascript -->
								<script src=%"js/custom.js%"></script>

							</body>
							</html>
							]"
				page.set_body(res_body)
				res.send (page)
			end

		end


	handle_save (req: WSF_REQUEST; res: WSF_RESPONSE)
		local
			db: SQLITE_DATABASE
			db_query_statement: SQLITE_QUERY_STATEMENT
			db_insert_statement: SQLITE_INSERT_STATEMENT
			cursor: SQLITE_STATEMENT_ITERATION_CURSOR
			query: READABLE_STRING_8
			i: INTEGER
			response_id: INTEGER
			db_table_names: ARRAY[STRING]
		do
			db_table_names := <<"CoursesTaught",
								"Examinations",
								"StudentsSupervised",
								"CompletedStudentsReports",
								"CompletedPhDTheses",
								"Grants",
								"ResearchProjects",
								"ResearchCollaborations",
								"ConferencePublications",
								"JournalPublications",
								"Patents",
								"IPLicensing",
								"BestPaperAwards",
								"Memberships",
								"Prizes",
								"IndustryCollaborations",
								"OtherInformation",
								"NameOfUniversityUnit",
								"NameOfHeadOfUniversityUnit",
								"StartOfReportingPeriod",
								"EndOfReportingPeriod">>
			create db.make_open_read_write ("Eiffel_forms_DB.db")
			query := "INSERT INTO RESPONSES (RESPONSE_NAME) VALUES ('response');"
			create db_insert_statement.make (query, db)
			db_insert_statement.execute
			query := "SELECT MAX(ID) FROM RESPONSES;"
			create db_query_statement.make (query, db)
			cursor := db_query_statement.execute_new
			response_id := cursor.item.integer_value (1)
			from
				i := 1
			until
				i > db_table_names.count
			loop
				if attached {WSF_STRING} req.query_parameter (db_table_names.at (i)) as p_i then
					query := "INSERT INTO "+ db_table_names.at (i) + " (ANSWER, RESPONSE_ID) VALUES ('" + p_i.url_encoded_value + "'," + response_id.out + ");"
					io.put_string (query)
					io.put_new_line
					create db_insert_statement.make (query, db)
					db_insert_statement.execute
				end
				i := i + 1
			end
			db.close
			res.redirect_now ("/finish.html")
		end

	handle_test_message (req: WSF_REQUEST; res: WSF_RESPONSE)
		local
			n: READABLE_STRING_8
			s: STRING
		do
			if attached {WSF_STRING} req.query_parameter ("test") as p_name then
				n := p_name.url_encoded_value
			else
				n := "?"
			end
			s := n
			res.set_status_code ({HTTP_STATUS_CODE}.ok)
			res.put_header_line ("Content-Type: text/plain")
			res.put_header_line ("Content-Length: " + s.count.out)
			res.put_string (s)

		end

	handle_api (req: WSF_REQUEST; res: WSF_RESPONSE)
		local
			pg: WSF_PAGE_RESPONSE
		do
			if attached {WSF_STRING} req.path_parameter ("operation") then

			end
			create pg.make_with_body (req.path_info)
			res.send (pg)
		end

	document_root: STRING
			-- Server's document root
		local
			l_service_options: WSF_SERVICE_LAUNCHER_OPTIONS
		do
			create {WSF_SERVICE_LAUNCHER_OPTIONS_FROM_INI} l_service_options.make_from_file ("server.ini")
			if
				attached {STRING} l_service_options.option ("document_root") as l_doc_root
			then
				Result := l_doc_root
			else
				Result := "www"
			end
		end



end
