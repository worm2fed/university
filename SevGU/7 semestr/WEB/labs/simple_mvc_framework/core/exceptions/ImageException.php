<?php

namespace core\exceptions;


use Exception;

/**
 * Class ImageException
 * @package core\exceptions
 */
class ImageException extends Exception
{
    protected $message = 'Unknown exception';
    protected $code = E_USER_ERROR;

    /**
     * ImageException constructor
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