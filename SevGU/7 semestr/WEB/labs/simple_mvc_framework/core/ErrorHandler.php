<?php

namespace core;


use Config;

/**
 * Class ErrorHandler provides error handler
 * @package core
 */
class ErrorHandler
{
    public function __construct(int $errType = Config::ERROR_TYPE)
    {
        // Set error handler
        set_error_handler(array($this, 'handler'), $errType);
        // Catch critical errors
        register_shutdown_function(array($this, 'fatalErrorHandler'));
        // Start output buffer
        ob_start();
    }

    /**
     * Error handler
     *
     * @param int $errNo
     * @param string $errStr
     * @param string $errFile
     * @param int $errLine
     */
    public static function handler(int $errNo, string $errStr, string $errFile, int $errLine)
    :void {
        /*
         *	First two array elements of trace do not interested us:
         *	 - ErrorHandler.getBacktrace
         *	 - ErrorHandler.handler
        */
        $backtrace = ErrorHandler::getBacktrace(2);
        // Error messages to display and/or to log
        $error_message  = "\nERRNO: $errNo (" .SystemTools::get_error_type($errNo). ")\nTEXT: $errStr";
        $error_message .= "\nLOCATION: $errFile, line ";
        $error_message .= "$errLine, at " . date('F j, Y, g:i a');
        $error_message .= "\nShowing backtrace: \n$backtrace\n";
        // Log to file
        if (Config::LOG_ERRORS == true) {
            error_log($error_message, 3, Config::LOG_ERRORS_FILE);
        }
        // Display error
        if (Config::DEBUGGING) {
            echo '<div class="' .Config::DISPLAY_CLASS. '"><pre>' .$error_message. '</pre></div>';
        }
    }

    /**
     * Fatal error handler
     */
    public static function fatalErrorHandler()
    :void {
        // Get info about last error
        $error = error_get_last();
        if (isset($error)) {
            if ($error['type'] & (E_ERROR | E_PARSE | E_COMPILE_ERROR | E_CORE_ERROR)) {
                // Clean buffer
                ob_end_clean();
                // If memory end allocate a bit to finish
                if (strpos($error['message'], 'Allowed memory size') === 0) {
                    ini_set('memory_limit', (intval(ini_get('memory_limit')) + 64) . 'M');
                }
                // Send to handler
                self::handler($error['type'], $error['message'], $error['file'], $error['line']);
            } else {
                // Finish buffer work
                ob_end_flush();
            }
        } else {
            ob_end_flush();
        }
    }

    /**
     * Get call backtrace
     *
     * @param int $irrelevantFirstEntries
     * @return string
     */
    public static function getBacktrace(int $irrelevantFirstEntries)
    : string {
        $s = '';
        $MAXSTRLEN = 64;
        // Get trace
        $trace_array = debug_backtrace();
        // Remove irrelevant entries
        for ($i = 0; $i < $irrelevantFirstEntries; $i++) {
            array_shift($trace_array);
        }
        // Handler trace
        $tabs = sizeof($trace_array) - 1;
        foreach ($trace_array as $arr) {
            $tabs -= 1;
            if (isset($arr['class'])) {
                $s .= $arr['class'] . '.';
            }
            // Handler args
            $args = array();
            if (!empty($arr['args'])) {
                foreach ($arr['args'] as $v) {
                    if (is_null($v)) {
                        $args[] = 'null';
                    } elseif (is_array($v)) {
                        $args[] = 'Array[' . sizeof($v) . ']';
                    } elseif (is_object($v)) {
                        $args[] = 'Object: ' . get_class($v);
                    } elseif (is_bool($v)) {
                        $args[] = $v ? 'true' : 'false';
                    } else {
                        $v = (string) @$v;
                        $str = htmlspecialchars(substr($v, 0, $MAXSTRLEN));

                        if (strlen($v) > $MAXSTRLEN) {
                            $str .= '...';
                        }
                        $args[] = "'$str'";
                    }
                }
            }
            // Compile trace
            $s .= $arr['function'] . '(' . implode(', ', $args) . ')';
            $line = (isset($arr['line']) ? $arr['line']: 'unknown');
            $file = (isset($arr['file']) ? $arr['file']: 'unknown');
            $s .= sprintf(', line: %d, file: %s', $line, $file);
            $s .= "\n";
        }
        return $s;
    }
}
