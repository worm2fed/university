<?php
// Класс, реализующий обработчик фото
class PhotoHandler {

	public function __construct() {
	}
	
	public static function getPhotoList() {
		$result = null;

		$sql = "SELECT 
					* 
				FROM 
					`photo`";

		$result = DatabaseHandler::getData($sql, true);
		DatabaseHandler::closeConnection();

		return $result;
	}

	public static function getPhotoInfo($photo) {
		$result = null;

		$sql = "SELECT 
					* 
				FROM 
					`photo` 
				WHERE 
					photo_id='"
						.$photo.
					"'";

		$result = DatabaseHandler::getRow($sql);
		DatabaseHandler::closeConnection();

		return $result;
	}

	public static function deletePhoto($photo) {
		if (DatabaseHandler::deleteFromTable("photo", $photo))
			return true;
		else
			return false;
	}

	/*public static function addPhoto($user, $name, $description, $file) {
		$date = date('Y-m-d', mktime());
				
		$sql = "INSERT INTO 
					`photo` 
						(user_id, 
						photo_date, 
						photo_name, 
						photo_description, 
						photo_file) 
				VALUES 
					('"
					.intval($user).
					"', '"
					.$date.
					"', '"
					.$name.
					"', '"
					.$description.
					"', '"
					.$file.
					"')";

		DatabaseHandler::executeQuery($sql);
		DatabaseHandler::closeConnection();
	} */

	public static function editPhoto($photo, $name, $description) {
		$sql = "UPDATE 
					`photo` 
				SET 
					photo_name='"
						.$name.
					"', 
					photo_description='"
						.$description.
					"'
				WHERE 
					photo_id='"
						.intval($photo).
					"'";

		DatabaseHandler::executeQuery($sql);
		DatabaseHandler::closeConnection();
	}
}
?>