<?php

namespace core\exceptions;


use Exception;

/**
 * Class DatabaseException
 * @package core\exceptions
 */
class DatabaseException extends Exception
{
    protected $message = 'Unknown exception';
    protected $code = E_USER_ERROR;

    /**
     * DatabaseException constructor
     *
     * @param string $message
     * @param int $code
     */
    public function __construct($message, $code)
    {
        $this->message = $message;
        $this->code = $code;
    }
}