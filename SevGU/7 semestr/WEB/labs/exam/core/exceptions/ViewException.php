<?php

namespace core\exceptions;


use Exception;

/**
 * Class ViewException
 * @package core\exceptions
 */
class ViewException extends Exception
{
    protected $message = 'Unknown exception';
    protected $code = E_USER_ERROR;

    /**
     * ViewException constructor
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