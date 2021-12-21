<?php

namespace models;


use Config;
use core\DatabaseHandler;
use core\exceptions\ModelException;
use core\exceptions\ValidationException;
use core\Model;

/**
 * Class QuestionModel
 * @package models
 */
class QuestionModel extends Model
{
    public function __construct()
    {
        // Set fields
        $this->_fields = array_fill_keys([
            'question_id', 'poll_id', 'title'
        ], null);
        // Specify required fields
        $this->_required = ['poll_id', 'title'];
    }

    /**
     * Get table name
     *
     * @return string
     */
    public static function getTableName()
    : string {
        return 'question';
    }

    /**
     * Find questions
     *
     * @param array $where
     * @return array
     */
    public static function find(array $where = [])
    : array {
        // Fill array with tasks
        $questions = [];
        foreach (DatabaseHandler::selectFromTable(self::getTableName(), $where, $params, false, false) as $question) {
            $question_model = new self();
            $question_model->load($question);
            array_push($questions, $question_model);
        }
        return $questions;
    }

    /**
     * Get answers
     *
     * @return array
     */
    public function getAnswers()
    : array {
        return AnswerModel::find(['question_id' => self::getId()]);
    }
}
