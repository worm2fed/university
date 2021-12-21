<?php

namespace core;

use Config;

/**
 * Class Loader provides autoloader
 * @package core
 */
class Loader
{
    public function __construct() {
    }

    /**
     * @param string $file
     */
    public static function autoload(string $file)
    : void {
        $file = str_replace('\\', '/', $file);
        $path = Config::ROOT_DIR;
        $filepath = Config::ROOT_DIR . '/' . $file . '.php';

        if (file_exists($filepath)) {
            require_once($filepath);
        } else {
            self::recursive_autoload($file, $path, true);
        }
    }

    /**
     * @param string $file
     * @param string $path
     * @param bool $flag
     */
    public static function recursive_autoload(string $file, string $path, bool $flag)
    : void {
        if (($handle = opendir($path)) && $flag !== false) {
            while (($dir = readdir($handle)) && $flag !== false) {
                if (strpos($dir, '.') === false) {
                    $path2 = $path . '/' . $dir;
                    $filepath = $path2 . '/' . $file . '.php';
                    if (file_exists($filepath)) {
                        $flag = FALSE;
                        require_once($filepath);
                    }
                    self::recursive_autoload($file, $path2, $flag);
                }
            }
            closedir($handle);
        }
    }
}
