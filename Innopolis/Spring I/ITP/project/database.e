note
	description: "Summary description for {DATABASE}."
	author: ""
	date: "$Date$"
	revision: "$Revision$"

class
	DATABASE

create
	make

feature

	make
		local
			db: SQLITE_DATABASE
			db_insert_statement: SQLITE_INSERT_STATEMENT
			query: READABLE_STRING_8
		do
			create db.make_create_read_write ("Eiffel_forms_DB.db")
			create db.make_open_read_write ("Eiffel_forms_DB.db")
			query := "[
				CREATE TABLE IF NOT EXISTS RESPONSES
				(
				ID integer PRIMARY KEY AUTOINCREMENT,
				RESPONSE_NAME text NOT NULL
				);
			]"
			create db_insert_statement.make (query, db)
			db_insert_statement.execute

			query :="[
			CREATE TABLE IF NOT EXISTS NameOfUniversityUnit
			(
			ANSWER_ID integer PRIMARY KEY AUTOINCREMENT,
			RESPONSE_ID integer,
			ANSWER text NOT NULL,
			FOREIGN KEY(RESPONSE_ID) REFERENCES RESPONSES(ID)
			);
			]"

			create db_insert_statement.make (query, db)
			db_insert_statement.execute

			query :="[
			CREATE TABLE IF NOT EXISTS NameOfHeadOfUniversityUnit
			(
			ANSWER_ID integer PRIMARY KEY AUTOINCREMENT,
			RESPONSE_ID integer,
			ANSWER text NOT NULL,
			FOREIGN KEY(RESPONSE_ID) REFERENCES RESPONSES(ID)
			);
			]"

			create db_insert_statement.make (query, db)
			db_insert_statement.execute

			query :="[
			CREATE TABLE IF NOT EXISTS StartOfReportingPeriod
			(
			ANSWER_ID integer PRIMARY KEY AUTOINCREMENT,
			RESPONSE_ID integer,
			ANSWER text NOT NULL,
			FOREIGN KEY(RESPONSE_ID) REFERENCES RESPONSES(ID)
			);
			]"

			create db_insert_statement.make (query, db)
			db_insert_statement.execute

			query :="[
			CREATE TABLE IF NOT EXISTS EndOfReportingPeriod
			(
			ANSWER_ID integer PRIMARY KEY AUTOINCREMENT,
			RESPONSE_ID integer,
			ANSWER text NOT NULL,
			FOREIGN KEY(RESPONSE_ID) REFERENCES RESPONSES(ID)
			);
			]"

			create db_insert_statement.make (query, db)
			db_insert_statement.execute

			query :="[
			CREATE TABLE IF NOT EXISTS CoursesTaught
			(
			ANSWER_ID integer PRIMARY KEY AUTOINCREMENT,
			RESPONSE_ID integer,
			ANSWER text NOT NULL,
			FOREIGN KEY(RESPONSE_ID) REFERENCES RESPONSES(ID)
			);
			]"

			create db_insert_statement.make (query, db)
			db_insert_statement.execute

			query :="[
			CREATE TABLE IF NOT EXISTS Examinations
			(
			ANSWER_ID integer PRIMARY KEY AUTOINCREMENT,
			RESPONSE_ID integer,
			ANSWER text NOT NULL,
			FOREIGN KEY(RESPONSE_ID) REFERENCES RESPONSES(ID)
			);
			]"

			create db_insert_statement.make (query, db)
			db_insert_statement.execute

			query :="[
			CREATE TABLE IF NOT EXISTS StudentsSupervised
			(
			ANSWER_ID integer PRIMARY KEY AUTOINCREMENT,
			RESPONSE_ID integer,
			ANSWER text NOT NULL,
			FOREIGN KEY(RESPONSE_ID) REFERENCES RESPONSES(ID)
			);
			]"

			create db_insert_statement.make (query, db)
			db_insert_statement.execute

			query :="[
			CREATE TABLE IF NOT EXISTS CompletedStudentsReports
			(
			ANSWER_ID integer PRIMARY KEY AUTOINCREMENT,
			RESPONSE_ID integer,
			ANSWER text NOT NULL,
			FOREIGN KEY(RESPONSE_ID) REFERENCES RESPONSES(ID)
			);
			]"

			create db_insert_statement.make (query, db)
			db_insert_statement.execute

			query :="[
			CREATE TABLE IF NOT EXISTS CompletedPhDTheses
			(
			ANSWER_ID integer PRIMARY KEY AUTOINCREMENT,
			RESPONSE_ID integer,
			ANSWER text NOT NULL,
			FOREIGN KEY(RESPONSE_ID) REFERENCES RESPONSES(ID)
			);
			]"

			create db_insert_statement.make (query, db)
			db_insert_statement.execute

			query :="[
			CREATE TABLE IF NOT EXISTS Grants
			(
			ANSWER_ID integer PRIMARY KEY AUTOINCREMENT,
			RESPONSE_ID integer,
			ANSWER text NOT NULL,
			FOREIGN KEY(RESPONSE_ID) REFERENCES RESPONSES(ID)
			);
			]"

			create db_insert_statement.make (query, db)
			db_insert_statement.execute

			query :="[
			CREATE TABLE IF NOT EXISTS ResearchProjects
			(
			ANSWER_ID integer PRIMARY KEY AUTOINCREMENT,
			RESPONSE_ID integer,
			ANSWER text NOT NULL,
			FOREIGN KEY(RESPONSE_ID) REFERENCES RESPONSES(ID)
			);
			]"

			create db_insert_statement.make (query, db)
			db_insert_statement.execute

			query :="[
			CREATE TABLE IF NOT EXISTS ResearchCollaborations
			(
			ANSWER_ID integer PRIMARY KEY AUTOINCREMENT,
			RESPONSE_ID integer,
			ANSWER text NOT NULL,
			FOREIGN KEY(RESPONSE_ID) REFERENCES RESPONSES(ID)
			);
			]"

			create db_insert_statement.make (query, db)
			db_insert_statement.execute

			query :="[
			CREATE TABLE IF NOT EXISTS ConferencePublications
			(
			ANSWER_ID integer PRIMARY KEY AUTOINCREMENT,
			RESPONSE_ID integer,
			ANSWER text NOT NULL,
			FOREIGN KEY(RESPONSE_ID) REFERENCES RESPONSES(ID)
			);
			]"

			create db_insert_statement.make (query, db)
			db_insert_statement.execute

			query :="[
			CREATE TABLE IF NOT EXISTS JournalPublications
			(
			ANSWER_ID integer PRIMARY KEY AUTOINCREMENT,
			RESPONSE_ID integer,
			ANSWER text NOT NULL,
			FOREIGN KEY(RESPONSE_ID) REFERENCES RESPONSES(ID)
			);
			]"

			create db_insert_statement.make (query, db)
			db_insert_statement.execute

			query :="[
			CREATE TABLE IF NOT EXISTS Patents
			(
			ANSWER_ID integer PRIMARY KEY AUTOINCREMENT,
			RESPONSE_ID integer,
			ANSWER text NOT NULL,
			FOREIGN KEY(RESPONSE_ID) REFERENCES RESPONSES(ID)
			);
			]"

			create db_insert_statement.make (query, db)
			db_insert_statement.execute

			query :="[
			CREATE TABLE IF NOT EXISTS IPLicensing
			(
			ANSWER_ID integer PRIMARY KEY AUTOINCREMENT,
			RESPONSE_ID integer,
			ANSWER text NOT NULL,
			FOREIGN KEY(RESPONSE_ID) REFERENCES RESPONSES(ID)
			);
			]"

			create db_insert_statement.make (query, db)
			db_insert_statement.execute

			query :="[
			CREATE TABLE IF NOT EXISTS BestPaperAwards
			(
			ANSWER_ID integer PRIMARY KEY AUTOINCREMENT,
			RESPONSE_ID integer,
			ANSWER text NOT NULL,
			FOREIGN KEY(RESPONSE_ID) REFERENCES RESPONSES(ID)
			);
			]"

			create db_insert_statement.make (query, db)
			db_insert_statement.execute

			query :="[
			CREATE TABLE IF NOT EXISTS Memberships
			(
			ANSWER_ID integer PRIMARY KEY AUTOINCREMENT,
			RESPONSE_ID integer,
			ANSWER text NOT NULL,
			FOREIGN KEY(RESPONSE_ID) REFERENCES RESPONSES(ID)
			);
			]"

			create db_insert_statement.make (query, db)
			db_insert_statement.execute

			query :="[
			CREATE TABLE IF NOT EXISTS Prizes
			(
			ANSWER_ID integer PRIMARY KEY AUTOINCREMENT,
			RESPONSE_ID integer,
			ANSWER text NOT NULL,
			FOREIGN KEY(RESPONSE_ID) REFERENCES RESPONSES(ID)
			);
			]"

			create db_insert_statement.make (query, db)
			db_insert_statement.execute

			query :="[
			CREATE TABLE IF NOT EXISTS IndustryCollaborations
			(
			ANSWER_ID integer PRIMARY KEY AUTOINCREMENT,
			RESPONSE_ID integer,
			ANSWER text NOT NULL,
			FOREIGN KEY(RESPONSE_ID) REFERENCES RESPONSES(ID)
			);
			]"

			create db_insert_statement.make (query, db)
			db_insert_statement.execute

			query :="[
			CREATE TABLE IF NOT EXISTS OtherInformation
			(
			ANSWER_ID integer PRIMARY KEY AUTOINCREMENT,
			RESPONSE_ID integer,
			ANSWER text NOT NULL,
			FOREIGN KEY(RESPONSE_ID) REFERENCES RESPONSES(ID)
			);
			]"

			create db_insert_statement.make (query, db)
			db_insert_statement.execute

			db.close
		end

end
