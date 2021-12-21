<?php

namespace core;


use Config;

/**
 * Class SystemTools provides different tools
 * @package core
 */
class SystemTools
{
    private function __construct()
    {
    }

    /**
     * @var string[] Replace list for translit
     */
    private static $_replace = [
        'а' => 'a', 'б' => 'b', 'в' => 'v', 'г' => 'g', 'д' => 'd',
        'е' => 'e', 'ё' => 'e', 'ж' => 'j', 'з' => 'z', 'и' => 'i',
        'й' => 'y', 'к' => 'k', 'л' => 'l', 'м' => 'm', 'н' => 'n',
        'о' => 'o', 'п' => 'p', 'р' => 'r', 'с' => 's', 'т' => 't',
        'у' => 'u', 'ф' => 'f', 'х' => 'h', 'ц' => 'ts', 'ч' => 'ch',
        'ш' => 'sh', 'щ' => 'shh', 'ъ' => '', 'ы' => 'y', 'ь' => '',
        'э' => 'e', 'ю' => 'u', 'я' => 'ya',

        '-' => '-', ' ' => '-', '.' => '-', ',' => '-',
    ];

    /**
     * Translit
     *
     * @param string $text
     * @return string
     */
    public static function translit(string $text)
    : string {
        $text = mb_strtolower($text);
        // cyrilic and symbols translit
        $replace = self::$_replace;
        $s = '';
        for ($i = 0; $i < mb_strlen($text); $i++) {
            $c = mb_substr($text, $i, 1);
            if (array_key_exists($c, $replace)) {
                $s .= $replace[$c];
            } else {
                $s .= $c;
            }
        }
        // other translit
        // make sure that you set locale for using iconv
        $s = iconv('UTF-8', 'ASCII//TRANSLIT', $s);
        // remove symbols
        $s = preg_replace('/[^\-0-9a-z]+/i', '', $s);
        // double spaces
        $s = preg_replace('/\-+/', '-', $s);
        // spaces at begin and end
        $s = preg_replace('/^\-*(.*?)\-*$/', '$1', $s);
        return $s;
    }

    /**
     * Get error type by its level
     * @param int $level
     * @return string
     */
    public static function get_error_type(int $level)
    : string {
        if (array_key_exists($level, Config::$ERROR_LEVELS)) {
            return Config::$ERROR_LEVELS[$level];
        } else {
            return 'E_UNKNOWN';
        }
    }

    /**
     * Generate hash-code
     *
     * @param int $length
     * @return string
     */
    public static function generate_hash_code(int $length = 6)
    : string {
        $chars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPRQSTUVWXYZ0123456789";
        $code = "";
        $len = strlen($chars) - 1;

        while (strlen($code) < $length) {
            $code .= $chars[mt_rand(0, $len)];
        }
        return $code;
    }

    /**
     * Redirect
     *
     * @param string $url
     */
    public static function redirect(string $url) {
        header('Location: ' . $url);
        exit();
    }

    /**
     * Escape string: new line symbols, single and double quotes
     *
     * @param string $text
     * @return string
     */
    public static function escape(string $text)
    : string {
        return preg_replace("/\"/", '\"',
            preg_replace("/\r\n|\r|\n/", "",
                preg_replace("/\'/", '\'',
                    nl2br($text, false)
                )
            )
        );
    }
}
