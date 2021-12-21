<?php

namespace core\exceptions;


use Exception;

/**
 * Class ModelException
 * @package core\exceptions
 */
class ModelException extends Exception
{
    protected $message = 'Unknown exception';
    protected $code = E_USER_ERROR;

    /**
     * ModelException constructor
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