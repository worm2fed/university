<?php

namespace core\exceptions;


use Exception;

/**
 * Class RouterException
 * @package core\exceptions
 */
class RouterException extends Exception
{
    protected $message = 'Unknown exception';
    protected $code = E_USER_ERROR;

    /**
     * RouterException constructor
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