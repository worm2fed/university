<?php

namespace core\exceptions;


use Exception;

/**
 * Class ControllerException
 * @package core\exceptions
 */
class ControllerException extends Exception
{
    protected $message = 'Unknown exception';
    protected $code = E_USER_ERROR;

    /**
     * ControllerException constructor
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