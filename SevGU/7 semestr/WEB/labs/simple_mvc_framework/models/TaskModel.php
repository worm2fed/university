<?php

namespace models;


use Config;
use core\DatabaseHandler;
use core\exceptions\ModelException;
use core\exceptions\ValidationException;
use core\Model;

/**
 * Class TaskModel
 * @package models
 */
class TaskModel extends Model
{
    public function __construct()
    {
        // Set fields
        $this->_fields = array_fill_keys([
            'task_id', 'status', 'username', 'email', 'text', 'image', 'published'
        ], null);
        // Specify required fields
        $this->_required = ['username', 'email', 'text'];
    }

    /**
     * Get table name
     *
     * @return string
     */
    public static function getTableName()
    : string {
        return 'task';
    }

    /**
     * Find tasks
     *
     * @param array $where
     * @param array $args
     * @return array
     */
    public static function find(array $where = [], array $args = [])
    : array {
        if (!empty($args)) {
            // Add sort
            $params = ' ORDER BY ' . (isset($args['order_by']) ?  $args['order_by'] : ' task_id DESC');
            // Add pagination
            $params .= (isset($args['page']) and $args['page'] > 1) ?
                ' LIMIT ' . Config::PAGINATION_LIMIT * ($args['page'] - 1) . ', ' . Config::PAGINATION_LIMIT :
                ' LIMIT ' . Config::PAGINATION_LIMIT;
        } else {
            $params = ' ORDER BY task_id DESC LIMIT ' . Config::PAGINATION_LIMIT;
        }
        // Fill array with tasks
        $tasks = [];
        foreach (DatabaseHandler::selectFromTable(self::getTableName(), $where, $params, false, false) as $task) {
            $task_model = new self();
            $task_model->load($task);
            array_push($tasks, $task_model);
        }
        return $tasks;
    }

    /**
     * Get number of pages
     *
     * @return int
     */
    public static function getPagesNum()
    : int {
        return ceil(DatabaseHandler::countEntry(self::getTableName(), 'task_id', []) / Config::PAGINATION_LIMIT);
    }

    /**
     * Check is user owner
     *
     * @return bool
     */
    public function isUserOwnerOrAdmin()
    : bool {
        $user = UserModel::getUserIfLoggedIn();
        return $user->username == $this->email or $user->is_admin;
    }

    /**
     * Validate fields
     */
    public function validate()
    : void {
        // Check whether required fields are set
        foreach ($this->_required as $required) {
            if (!isset($this->_fields[$required])) {
                throw new ValidationException("`$required can not be blank`", E_USER_ERROR);
            }
        }
        // Validate email address
        if (!filter_var($this->email, FILTER_VALIDATE_EMAIL)) {
            throw new ValidationException("`email has wrong format`", E_USER_ERROR);
        }
    }

    protected function beforeCreate()
    : void {
        $this->status = 0;
        $this->published = date('Y-m-d H:m:s', time());

        // Create user
        try {
            $user = UserModel::getUserIfLoggedIn();
            $user->username = $user->username ?? $this->email;
            $user->password = $user->password ?? sha1($this->email);
            $user->create();
        } catch (ModelException $e) {
        }
    }
}
