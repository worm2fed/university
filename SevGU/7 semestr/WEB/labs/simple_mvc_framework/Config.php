<?php
/**
 * This class provides access to app configuration
 */

class Config
{
    private function __construct() {
    }

    // Stores full path to root
    const ROOT_DIR = __DIR__;

    // Stores app name
    const APP_NAME = 'Simple Task Manager';

    // Stores session time in hours
    const SESSION_TIME = 3;

    // Stores number of items per page for pagination
    const PAGINATION_LIMIT = 3;

    # Image settings
    // Stores full path to image dir
    const IMAGE_DIR = self::ROOT_DIR . '/static/images/';
    const IMAGE_MAX_WIDTH = 320;
    const IMAGE_MAX_HEIGHT = 240;

    # Database connection settings
    const SQL_HOST 	= 'db';
    const SQL_DB 	= 'task_manager';
    const SQL_USER 	= 'root';
    const SQL_PASS 	= 'root';

    # Mailer settings
    // Content type
    const EMAIL_CONTENT_TYPE 	= 'text/html';
    // Message charset
    const EMAIL_CHARSET			= 'utf-8';

    # Error handler settings
    // Debug mode
    const DEBUGGING			= true;
    // Class (css style) to show error
    const DISPLAY_CLASS		= 'php_error';
    // Log to file
    const LOG_ERRORS		= true;
    // Type of errors to handle
    const ERROR_TYPE 		= E_ALL;
    // Path to log file
    const LOG_ERRORS_FILE 	= self::ROOT_DIR . '/error.log';
    // Error types and its levels
    public static $ERROR_LEVELS = [
        1 		=> 'E_ERROR',
        2 		=> 'E_WARNING',
        4 		=> 'E_PARSE',
        8 		=> 'E_NOTICE',
        16 		=> 'E_CORE_ERROR',
        32 		=> 'E_CORE_WARNING',
        64 		=> 'E_COMPILE_ERROR',
        128 	=> 'E_COMPILE_WARNING',
        256 	=> 'E_USER_ERROR',
        512 	=> 'E_USER_WARNING',
        1024 	=> 'E_USER_NOTICE',
        2048 	=> 'E_STRICT',
        4096 	=> 'E_RECOVERABLE_ERROR',
        8192 	=> 'E_DEPRECATED',
        16384 	=> 'E_USER_DEPRECATED',
        32767 	=> 'E_ALL'
    ];
}
