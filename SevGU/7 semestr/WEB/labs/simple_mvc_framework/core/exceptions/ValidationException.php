<?php

namespace core\exceptions;


use Exception;

/**
 * Class ValidationException
 * @package core\exceptions
 */
class ValidationException extends Exception
{
    protected $message = 'Unknown exception';
    protected $code = E_USER_ERROR;

    /**
     * ValidationException constructor
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