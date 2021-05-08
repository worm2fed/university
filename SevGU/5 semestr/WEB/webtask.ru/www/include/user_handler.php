<?php
// Класс, реализующий обработчик пользователей
class UserHandler {

	public function __construct() {
	}

	// Функция для добавления пользователей
	public static function addUser($login, $password, $name) {
		$password = sha1($password);

		$sql = "SELECT 
					COUNT(user_id) 
				FROM
					`user` 
				WHERE 
					user_login='"
						.$login.
					"'";
   		
   		$user_login = DatabaseHandler::getRow($sql);

   		if ($user_login['COUNT(user_id)'] != 0)
   			return false;
   		else {
			$sql = "INSERT INTO 
						`user` 
							(user_name, 
							user_login, 
							user_password) 
					VALUES ('"
						.$name.
						"', '"
						.$login.
						"', '"
						.$password.
					"')";
			
			DatabaseHandler::executeQuery($sql);
			DatabaseHandler::closeConnection();

			return true;
		}
	}

	// Функция для удаления пользователей
	public static function deleteUser($user) {
		if (DatabaseHandler::deleteFromTable("user", $user))
			return true;
		else
			return false;
	}

	// Функция для смены пароля
	public static function changeUserPassword($user, $old_password, $new_password) {
		$old_password = sha1($old_password);

		$sql = "SELECT 
					user_password 
				FROM 
					`user` 
				WHERE 
					user_id='"
						.intval($user).
					"' 
				LIMIT 1";

		$user_info = DatabaseHandler::getRow($sql);

		if ($old_password != $user_info['user_password'])
			return false;
		else {
			$new_password = sha1($new_password);

			$sql = "UPDATE 
						`user` 
					SET 
						user_password='"
							.$new_password.
						"' 
					WHERE 
						user_id='"
							.intval($user).
						"'";

			DatabaseHandler::executeQuery($sql);
			DatabaseHandler::closeConnection();

			return true;
		}
	}

	// Получить данные о пользователе
	public static function getUserInfo($user) {
		$result = null;

		$sql = "SELECT
					*   
				FROM 
					`user` 
				WHERE 
					user_id='"
						.intval($user).
					"'";

		$result = DatabaseHandler::getRow($sql);

		return $result;
	}

	// Функция получения списка работников
	public function getUserList() {
		$result = null;

		$sql = "SELECT 
					* 
				FROM 
					`user`";

		$result = DatabaseHandler::getData($sql, true);
		DatabaseHandler::closeConnection();

		return $result;
	}
}
?>