<?php

namespace core\exceptions;


use Exception;

/**
 * Class AuthenticationException
 * @package core\exceptions
 */
class AuthenticationException extends Exception
{
    protected $message = 'Unknown exception';
    protected $code = E_USER_ERROR;

    /**
     * AuthenticationException constructor
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