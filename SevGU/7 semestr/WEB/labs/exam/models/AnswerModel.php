<?php

namespace models;


use Config;
use core\DatabaseHandler;
use core\exceptions\ModelException;
use core\exceptions\ValidationException;
use core\Model;

/**
 * Class AnswerModel
 * @package models
 */
class AnswerModel extends Model
{
    public function __construct()
    {
        // Set fields
        $this->_fields = array_fill_keys([
            'answer_id', 'question_id', 'title', 'is_correct'
        ], null);
        // Specify required fields
        $this->_required = ['question_id', 'title', 'is_correct'];
    }

    /**
     * Get table name
     *
     * @return string
     */
    public static function getTableName()
    : string {
        return 'answer';
    }

    /**
     * Find answers
     *
     * @param array $where
     * @return array
     */
    public static function find(array $where = [])
    : array {
        // Fill array with tasks
        $answers = [];
        foreach (DatabaseHandler::selectFromTable(self::getTableName(), $where, $params, false, false) as $answer) {
            $answer_model = new self();
            $answer_model->load($answer);
            array_push($answers, $answer_model);
        }
        return $answers;
    }
}
