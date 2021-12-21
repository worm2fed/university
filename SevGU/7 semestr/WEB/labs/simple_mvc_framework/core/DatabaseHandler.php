<?php

namespace core;


use Config;
use core\exceptions\DatabaseException;
use mysqli;
use mysqli_sql_exception;

/**
 * Class DatabaseHandler provides interaction with database
 * @package core
 */
class DatabaseHandler
{
    // Stores instance of handler
    private static $_mHandler;

    // private-construct do not allow to create instances directly
    private function __construct()
    {
    }

    /**
     * Initialise database descriptor
     * @return mysqli
     * @throws DatabaseException
     */
    private static function getHandler()
    : mysqli {
        // Create connection iff it doesn't exist
        if (is_null(self::$_mHandler)) {
            try {
                // Create new instance
                self::$_mHandler = new mysqli(Config::SQL_HOST, Config::SQL_USER, Config::SQL_PASS,
                    Config::SQL_DB);
                // Set connection charset
                self::$_mHandler->set_charset("utf8");
            } catch (mysqli_sql_exception $e) {
                // Close descriptor and generate error
                self::closeConnection();
                throw new DatabaseException($e->getMessage(), E_USER_ERROR);
            }
        }
        return self::$_mHandler;
    }

    /**
     * Clear instance and close connection
     */
    private static function closeConnection()
    : void {
        self::$_mHandler = null;
    }

    /**
     * Execute SQL-query
     *
     * @param string $sqlQuery
     * @return bool
     * @throws DatabaseException
     */
    private static function executeQuery(string $sqlQuery)
    : bool {
        try {
            // Get database descriptor
            $database_handler = self::getHandler();
            // Prepare query
            $statement_handler = $database_handler->prepare($sqlQuery);
            // Execute query
            return $statement_handler->execute();
        } catch (mysqli_sql_exception $e) {
            self::closeConnection();
            throw new DatabaseException($e->getMessage(), E_USER_ERROR);
        }
    }

    /**
     * Get data form database
     *
     * @param string $sqlQuery
     * @param bool $num_rows
     * @return array
     * @throws DatabaseException
     */
    private static function getData(string $sqlQuery, bool $num_rows = true)
    : array {
        try {
            $database_handler = self::getHandler();
            $statement_handler = $database_handler->prepare($sqlQuery);
            $statement_handler->execute();

            // Get result
            $get_result = $statement_handler->get_result();
            $result = $get_result->fetch_all(MYSQLI_ASSOC);
            if ($num_rows) {
                $result['num_rows'] = $get_result->num_rows;
            }
        } catch (mysqli_sql_exception $e) {
            self::closeConnection();
            throw new DatabaseException($e->getMessage(), E_USER_ERROR);
        }
        return $result ?? [];
    }

    /**
     * Get row from database
     *
     * @param string $sqlQuery
     * @return array
     * @throws DatabaseException
     */
    private static function getRow(string $sqlQuery)
    : array {
        try {
            $database_handler = self::getHandler();
            $statement_handler = $database_handler->prepare($sqlQuery);
            $statement_handler->execute();
            $get_result = $statement_handler->get_result();
            $result = $get_result->fetch_assoc();
        } catch (mysqli_sql_exception $e) {
            self::closeConnection();
            throw new DatabaseException($e->getMessage(), E_USER_ERROR);
        }
        return $result ?? [];
    }

    /**
     * Delete item from table
     *
     * @param string $table_name
     * @param array $where
     * @return bool
     */
    public static function deleteFromTable(string $table_name, array $where)
    : bool {
        // Compile query
        $sqlQuery = "DELETE FROM $table_name WHERE";
        foreach ($where as $field => $value) {
            $sqlQuery .= " $field = '$value' AND";
        }
        $sqlQuery = substr($sqlQuery, 0, -3);
        return self::executeQuery($sqlQuery);
    }

    /**
     * Count entries in table
     *
     * @param string $table_name
     * @param string $count_field
     * @param array $where
     * @return int
     */
    public static function countEntry(string $table_name, string $count_field, array $where)
    : int {
        $sqlQuery = "SELECT COUNT($count_field) AS num FROM $table_name WHERE";
        foreach ($where as $field => $value) {
            $sqlQuery .= " $field = '$value' AND";
        }
        $sqlQuery = substr($sqlQuery, 0, -3);
        return self::getRow($sqlQuery)['num'];
    }

    /**
     * Insert record to table
     *
     * @param string $table_name
     * @param array $data
     * @return bool
     */
    public static function insertToTable(string $table_name, array $data)
    : bool {
        // Split $data to columns and values
        $columns = array_keys($data);
        $values = array_values($data);
        $sqlQuery = "INSERT INTO $table_name (";
        // Add columns to query
        foreach ($columns as $column) {
            $sqlQuery .= $column .', ';
        }
        // Remove comma and space from the end
        $sqlQuery = substr($sqlQuery, 0, -2);
        $sqlQuery .= ") VALUES (";
        // Add values to query
        foreach ($values as $value) {
            $sqlQuery .= is_null($value) ? 'null, ' : "'$value', ";
        }
        $sqlQuery = substr($sqlQuery, 0, -2);
        $sqlQuery .= ")";
        return self::executeQuery($sqlQuery);
    }

    /**
     * Select all data from table
     *
     * @param string $table_name
     * @param array $where
     * @param string|null $params
     * @param bool $only_row
     * @param bool $num_rows
     * @return array
     */
    public static function selectFromTable(string $table_name, array $where, string $params = null,
                                           bool $only_row = false, bool $num_rows = true)
    : array {
        $sqlQuery = "SELECT * FROM $table_name ";
        if (!empty($where)) {
            $sqlQuery .= 'WHERE ';
            foreach ($where as $field => $value) {
                $sqlQuery .= " $field = '$value' AND";
            }
            $sqlQuery = substr($sqlQuery, 0, -3);
        }
        $sqlQuery .= $params;
        return $only_row ? self::getRow($sqlQuery) : self::getData($sqlQuery, $num_rows);
    }

    /**
     * Update data in table
     *
     * @param string $table
     * @param array $data
     * @param array $where
     * @return bool
     */

    public static function updateInTable(string $table, array $data, array $where)
    : bool {
        $sqlQuery = "UPDATE $table SET ";
        foreach ($data as $column => $value) {
            $sqlQuery .= "$column = '$value', ";
        }
        $sqlQuery = substr($sqlQuery, 0, -2);
        $sqlQuery .= " WHERE ";
        foreach ($where as $field => $value) {
            $sqlQuery .= " $field = '$value' AND";
        }
        $sqlQuery = substr($sqlQuery, 0, -3);
        return self::executeQuery($sqlQuery);
    }
}
