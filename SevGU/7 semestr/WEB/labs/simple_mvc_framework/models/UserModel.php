<?php

namespace models;


use Config;
use core\DatabaseHandler;
use core\exceptions\AuthenticationException;
use core\exceptions\ModelException;
use core\Model;
use core\SessionHandler;
use core\SystemTools;

/**
 * Class UserModel provides user model and method for authentication
 * @package models
 */
class UserModel extends Model
{
    public function __construct()
    {
        // Set fields
        $this->_fields = array_fill_keys([
            'user_id', 'username', 'password', 'is_admin', 'hash'
        ], null);
        // Specify required fields
        $this->_required = ['username', 'password'];
    }

    /**
     * Get table name
     *
     * @return string
     */
    public static function getTableName()
    : string {
        return 'user';
    }

    /**
     * Get user instance
     *
     * @return UserModel
     */
    public static function getUserIfLoggedIn()
    : UserModel {
        $user = new self();
        if (!self::isGuest()) {
            $user->load(['user_id' => SessionHandler::getInstance()->_user_id], true);
        }
        return $user;
    }

    protected function beforeCreate()
    : void {
        // Check if exists
        if (DatabaseHandler::countEntry(self::getTableName(), 'user_id', ['username' => $this->username]) != 0) {
            throw new ModelException("`User $this->username already exist`", E_USER_ERROR);
        }
        $this->is_admin = 0;
    }

    /**
     * Check is user logged in
     *
     * @return bool
     */
    public static function isGuest()
    : bool{
        $session = SessionHandler::getInstance();
        // Check is hash and user id are set
        if (!isset($_COOKIE['_user_hash']) or !isset($session->_user_id)) {
            return true;
        }
        // Check salt
        if ($session->_user_salt != sha1(crypt($session->_user_id, $_COOKIE['_user_hash']))) {
            return true;
        }
        // Get user data
        $user_data = DatabaseHandler::selectFromTable(self::getTableName(), ['user_id' => $session->_user_id],
            null, true);
        // Check
        if (($user_data['hash'] != $_COOKIE['_user_hash']) or ($user_data['user_id'] != $session->_user_id)) {
            self::logout();
            return true;
        }
        return false;
    }

    /**
     * Login user
     *
     * @param string $username
     * @param string $password
     * @throws AuthenticationException
     */
    public static function login(string $username, string $password)
    : void {
        // If user already logged in
        if (!self::isGuest()) {
            throw new AuthenticationException('`You are already logged in`', E_USER_ERROR);
        }
        $password = sha1($password);
        // Look for user in database
        if (DatabaseHandler::countEntry(self::getTableName(), 'user_id',
                ['username' => $username, 'password' => $password]) !== 1) {
            throw new AuthenticationException('`There are no such user in database`', E_USER_ERROR);
        }
        // Load user data
        $user_data = new self();
        $user_data->load(['username' => $username, 'password' => $password], true);
        // Set up hash code
        $user_data->hash = sha1(SystemTools::generate_hash_code(10));
        // Save hash to database
        $user_data->update();
        // Login
        $time = time() + 3600 * Config::SESSION_TIME;
        setCookie('_user_hash', $user_data->hash, $time, '/', $_SERVER['SERVER_NAME']);
        // Set session values
        $session = SessionHandler::getInstance();
        $session->_user_id = $user_data->user_id;
        $session->_user_salt = sha1(crypt($user_data->user_id, $user_data->hash));
    }

    /**
     * Logout user
     */
    public static function logout()
    : void {
        setCookie('_user_hash', '', time() -3600 * Config::SESSION_TIME, '');
        SessionHandler::getInstance()->destroy();
    }
}