DROP USER IF EXISTS repl;
CREATE USER repl IDENTIFIED BY 'replPassword';
GRANT REPLICATION SLAVE ON *.* TO repl;
