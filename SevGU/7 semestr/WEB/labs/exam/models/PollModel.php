<?php

namespace models;


use Config;
use core\DatabaseHandler;
use core\exceptions\ModelException;
use core\exceptions\ValidationException;
use core\Model;

/**
 * Class PollModel
 * @package models
 */
class PollModel extends Model
{
    public function __construct()
    {
        // Set fields
        $this->_fields = array_fill_keys([
            'poll_id', 'title', 'questions', 'created_at'
        ], null);
        // Specify required fields
        $this->_required = ['title'];
    }

    /**
     * Get table name
     *
     * @return string
     */
    public static function getTableName()
    : string {
        return 'poll';
    }

    /**
     * Find polls
     *
     * @param array $where
     * @param array $args
     * @return array
     */
    public static function find(array $where = [], array $args = [])
    : array {
        if (!empty($args)) {
            // Add sort
            $params = ' ORDER BY ' . (isset($args['order_by']) ?  $args['order_by'] : ' poll_id DESC');
            // Add pagination
            $params .= (isset($args['page']) and $args['page'] > 1) ?
                ' LIMIT ' . Config::PAGINATION_LIMIT * ($args['page'] - 1) . ', ' . Config::PAGINATION_LIMIT :
                ' LIMIT ' . Config::PAGINATION_LIMIT;
        } else {
            $params = ' ORDER BY poll_id DESC LIMIT ' . Config::PAGINATION_LIMIT;
        }
        // Fill array with polls
        $polls = [];
        foreach (DatabaseHandler::selectFromTable(self::getTableName(), $where, $params, false, false) as $poll) {
            $poll_model = new self();
            $poll_model->load($poll);
            array_push($polls, $poll_model);
        }
        return $polls;
    }

    /**
     * Get number of pages
     *
     * @return int
     */
    public static function getPagesNum()
    : int {
        return ceil(DatabaseHandler::countEntry(self::getTableName(), 'poll_id', []) / Config::PAGINATION_LIMIT);
    }

    protected function beforeCreate()
    : void {
        $this->created_at = date('Y-m-d H:m:s', time());
    }

    /**
     * Get questions
     *
     * @return array
     */
    public function getQuestions()
    : array {
        return QuestionModel::find(['poll_id' => self::getId()]);
    }
}
