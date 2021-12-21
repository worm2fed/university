<?php

namespace core;

/**
 * Class SessionHandler provides handling a sessions
 * @package core
 */
class SessionHandler
{
    const SESSION_STARTED       = true;
    const SESSION_NOT_STARTED   = false;

    // The state of the session
    private $_sessionState = self::SESSION_NOT_STARTED;

    // THE only instance of the class
    private static $_instance;

    private function __construct()
    {
    }

    /**
     * Get session instance
     *
     * @return SessionHandler
     */
    public static function getInstance()
    : SessionHandler {
        if (!isset(self::$_instance)) {
            self::$_instance = new self;
        }
        self::$_instance->startSession();
        return self::$_instance;
    }


    /**
     * Start or restart session
     *
     * @return bool
     */
    public function startSession()
    : bool{
        if ($this->_sessionState === self::SESSION_NOT_STARTED ) {
            $this->_sessionState = session_start();
        }
        return $this->_sessionState;
    }

    /**
     * Set session data, e.g. $session->var = $value
     *
     * @param string $name
     * @param string $value
     */
    public function __set(string $name, string $value)
    : void {
        $_SESSION[$name] = $value;
    }

    /**
     * Get data from session, e.g. $session->var
     *
     * @param string $name
     * @return string
     */
    public function __get($name)
    : string {
        if (isset($_SESSION[$name])) {
            return $_SESSION[$name];
        }
    }

    /**
     * Check is session data were set
     *
     * @param string $name
     * @return bool
     */
    public function __isset(string $name)
    : bool {
        return isset($_SESSION[$name]);
    }

    /**
     * Unset session data
     *
     * @param string $name
     */
    public function __unset(string $name)
    : void {
        unset($_SESSION[$name]);
    }

    /**
     * Destroy session
     *
     * @return bool
     */
    public function destroy()
    : bool {
        if ($this->_sessionState == self::SESSION_STARTED) {
            $this->_sessionState = !session_destroy();
            unset($_SESSION);
            return !$this->_sessionState;
        }
        return true;
    }
}
