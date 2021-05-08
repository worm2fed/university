<?php
// Класс, предостовляющий базовую функциональность доступа к данным
class DatabaseHandler {
	// Переменная для хранения экземпляра класса
  	private static $_mHandler;

  	// private-конструктор, не позволяющий напрямую создавать объекты класса
  	private function __construct() {
  	}

  	// Возвращает проинициализированный дескриптор БД
  	private static function getHandler() {
    	// Создаём соединение с БД, только если его ещё нет
	    if (!isset(self::$_mHandler)) {
	     	// Выполняем код, перехватывающий потенциальные исключения
	    	try {
	        	// Создаём новый экземпляр класса mysqli
	        	self::$_mHandler = new mysqli(SQL_HOST, SQL_USER, SQL_PASS, SQL_DB);
	        	// Устанавливаем кодировку соединения
	        	self::$_mHandler->set_charset("utf8");
	      	} catch (mysqliException $e) {
	        	// Закрываем дескриптор и генерируем ошибку
	        	self::closeConnection();
	        	trigger_error($e->getMessage(), E_USER_ERROR);
	      	}
	    }

	    // Возвращаем дескриптор БД
	    return self::$_mHandler;
	}

	// Очищаем экземпляр класса mysqli и закрываем соединение
	public static function closeConnection() {
		self::$_mHandler = null;
	}

	// Метод для выполнения sql-запроса к БД
	public static function executeQuery($sqlQuery) {
		// Пытаемся выполнить sql-запрос или хранимую процедуру
	    try {
	    	// Получаем дескриптор БД
	    	$database_handler = self::getHandler();

	    	// Подготавливаем запрос
	    	$statement_handler = $database_handler->prepare($sqlQuery);

	    	// Выполняем запрос
	    	$statement_handler->execute();
	    } catch(mysqliException $e) {
	    	// Закрываем дескриптор БД и генерируем ошибку
	    	self::closeConnection();
	    	trigger_error($e->getMessage(), E_USER_ERROR);
	    }
	}

	// Метод для получения данных из БД
	public static function getData($sqlQuery, $num = false) {
	    // Инициализируем возвращаемое значение в null
	    $result = null;

	    // Пытаемся выполнить sql-запрос или хранимую процедуру
	    try {
	    	// Получаем дескриптор БД
	    	$database_handler = self::getHandler();

	    	// Подготавливаем запрос
	    	$statement_handler = $database_handler->prepare($sqlQuery);

	    	// Выполняем запрос
	    	$statement_handler->execute();

	    	// Получаем результат
	    	$get_result = $statement_handler->get_result();
	    	$result = $get_result->fetch_all(MYSQLI_ASSOC);

	    	if ($num)
	    		$result['num_rows'] = $get_result->num_rows;
	    } catch(mysqliException $e) {
	    	// Закрываем дескриптор БД и генерируем ошибку
	    	self::closeConnection();
	    	trigger_error($e->getMessage(), E_USER_ERROR);
	    }

	    // Возвращаем результат
	    return $result;
	}

	// Метод для получения данных из БД
	public static function getRow($sqlQuery, $num = false) {
	    // Инициализируем возвращаемое значение в null
	    $result = null;

	    // Пытаемся выполнить sql-запрос или хранимую процедуру
	    try {
	    	// Получаем дескриптор БД
	    	$database_handler = self::getHandler();

	    	// Подготавливаем запрос
	    	$statement_handler = $database_handler->prepare($sqlQuery);

	    	// Выполняем запрос
	    	$statement_handler->execute();

	    	// Получаем результат
	    	$get_result = $statement_handler->get_result();
	    	$result = $get_result->fetch_assoc();

	    	if ($num)
	    		$result['num_rows'] = $get_result->num_rows;
	    } catch(mysqliException $e) {
	    	// Закрываем дескриптор БД и генерируем ошибку
	    	self::closeConnection();
	    	trigger_error($e->getMessage(), E_USER_ERROR);
	    }

	    // Возвращаем результат
	    return $result;
	}

	// Метод для удаления данных из таблицы
	public static function deleteFromTable($name, $id) {
		$sql = "DELETE FROM 
					`"
						.$name.
					"` 
				WHERE "
					.$name.
					"_id='"
						.intval($id).
					"'";
		
		self::executeQuery($sql);
		self::closeConnection();
	}
}
?>