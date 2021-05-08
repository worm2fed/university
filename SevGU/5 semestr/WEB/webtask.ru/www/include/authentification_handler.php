<?php
// Класс, реализующий аутентификацию пользователей
class AuthentificationHandler {

	public function __construct() {
	}

	// Функций генерации хеш-кода
	public static function generateHashCode($length = 6) {
		$chars = "abcdefghijklmnopqrstuvwxyzABCDEFGHI JKLMNOPRQSTUVWXYZ0123456789";
		$code = "";
		$clen = strlen($chars) - 1;  
   
		while (strlen($code) < $length)
    		$code .= $chars[mt_rand(0, $clen)]; 

		return $code;
	}

	// Авторизовать пользователя
	public static function loginUser($login, $password) {
		$sql = "SELECT 
					user_id 
				FROM 
					`user` 
				WHERE 
					user_login='"
						.$login.
					"' 
				AND 
					user_password='"
						.sha1($password).
					"' 
				LIMIT 1";

   		$user_data = DatabaseHandler::getRow($sql, true);

   		if ($user_data['num_rows'] === 1) {
    		$hash = md5(self::generateHashCode(10));

      		$sql = "UPDATE 
      					`user` 
      				SET 
      					user_hash='"
      						.$hash.
      					"' 
      				WHERE 
      					user_id='"
      						.$user_data['user_id'].
      					"'";
      		
      		DatabaseHandler::executeQuery($sql);
      		
      		// Если активный пользователь с указанными данными найден, стартуем сессию.
      		$time = time() + 3600 * SESSION_TIME;
      		setCookie("hash", $hash, $time);

      		$_SESSION['id'] = $user_data['user_id'];
      		$_SESSION['solt'] = md5(crypt($user_data['user_id'], $hash)); 

      		header("Location: http://".$_SERVER['SERVER_NAME']."/admin.php");
      		
      		DatabaseHandler::closeConnection();
      		
      		return true;
   		}
   		else {
   			DatabaseHandler::closeConnection();
      		
      		return false;
   		}
	}

	// Проверить неподменены ли данные
	public static function checkIsUserLogined() {
		if (isset($_COOKIE['hash']) and 
				isset($_SESSION['id'])) {
			if ($_SESSION['solt'] != md5(crypt($_SESSION['id'], $_COOKIE['hash'])))
				return false;
			else {
	    		$sql = "SELECT 
							user_id,
							user_hash 
						FROM 
							`user` 
						WHERE 
							user_id='"
								.intval($_SESSION['id']).
							"' 
						LIMIT 1";
				
				$user_data = DatabaseHandler::getRow($sql, true);

				DatabaseHandler::closeConnection();

				if (($user_data['user_hash'] != $_COOKIE['hash']) or 
						($user_data['user_id'] != $_SESSION['id'])) {
					self::logout();
					return false;
				}
				else
					return true;
			}
    	}
		else
			return false;
	}

	// Завершить сеанс
	public function logout() {
		setCookie("hash", "", time() -3600 * SESSION_TIME, "");
		session_unset();
		session_destroy();
		header("Location: http://".$_SERVER['SERVER_NAME']."/admin.php");
	}
}
