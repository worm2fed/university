<?php
//		--- НАЧАЛО ОБЪЯВЛЕНИЯ КОНСТАНТ И ПЕРЕМЕННЫХ ---		//

// В SITE_ROOT содержится полный путь к корневой директории сайта
define ('SITE_ROOT', dirname(dirname(__FILE__)));

// В SITE_NAME хранится название ресурса
define('SITE_NAME', 'Webtask');

// В SESSION_TIME записано время сессии (в часах)
define ('SESSION_TIME', 6);

// Настройки соединения с БД.
define ("SQL_HOST", "localhost");
define ("SQL_DB", "webtask");
define ("SQL_USER", "root");
define ("SQL_PASS", "root");

// Эти значения должны быть равны true на этапе разработки
define ('IS_WARNING_FATAL', true);
define ('DEBUGGING', true);

// Типы ошибок, о которых должны оставляться сообщения
define ('ERROR_TYPES', E_WARNING);

// По умолчанию мы не записываем сообщения в журнал
define ('LOG_ERRORS', false);
define ('LOG_ERRORS_FILE', SITE_ROOT . '/errors_log.txt');

// Общее сообщение об ошибке, которое должно отображаться вместо подробной информации (если DEBUGGING равно false)
define ('SITE_GENERIC_ERROR_MESSAGE', 
	'<h1>Упс! Произошла ошибочка, наши программисты уже работают над этим. Приносим свои извинения за неудобства!</h1>');

// Значение ошибки по умолчанию
$error = "";
// Часовой пояс по умолчанию
//date_default_timezone_set("Europe/Moscow");

//		--- КОНЕЦ ОБЪЯВЛЕНИЯ КОНСТАНТ И ПЕРЕМЕННЫХ---		//



//		--- НАЧАЛО ПОДКЛЮЧЕНИЯ ЭЛЕМЕНТОВ УРОВНЯ ЛОГИКИ ---		//

// Обработчик базы данных
require_once SITE_ROOT . '/include/database_handler.php';
// DatabaseHandler::executeQuery($sql)									// Выполнить запрос
// DatabaseHandler::getData($sql) 										// Получить данные
// DatabaseHandler::getData($sql, true) 								// Получить данные и количество полученых строк
// DatabaseHandler::getRow($sql) 										// Получить строку
// DatabaseHandler::getRow($sql, true) 									// Получить строку и количество совпадений
// DatabaseHandler::closeConnection() 									// Закрыть соединение
// DatabaseHandler::deleteFromTable($name, $id)							// Получить строку

// Обработчик ошибок
require_once SITE_ROOT . '/include/error_handler.php';
// ErrorHandler::setHandler()											// Задать обработчик ошибок

// Обработчик аутентификации
require_once SITE_ROOT . '/include/authentification_handler.php';
// AuthentificationHandler::generateHashCode()							// Генерирование хэш-кода длинной 6
// AuthentificationHandler::generateHashCode($length)					// Генерирование хэш-кода
// AuthentificationHandler::loginUser($login, $password) 				// Авторизовать пользователя
// AuthentificationHandler::checkIsUserLogined() 						// Проверить авторизован ли пользователь
// AuthentificationHandler::logout() 

// Обработчик польщователей
require_once SITE_ROOT . '/include/user_handler.php';
// UserHandler::addUser($login, $password, $name)						// Функция для добавления пользователей
// UserHandler::deleteUser($user)										// Функция для удаления пользователей
// UserHandler::changeUserPassword($user, $old_password, $new_password)	// Функция для смены пароля
// UserHandler::getUserInfo($user)										// Получить данные о пользователе
// UserHandler::getUserList()											// Функция получения списка работников

// Обработчик фото
require_once SITE_ROOT . '/include/photo_handler.php';
//		--- КОНЕЦ ПОДКЛЮЧЕНИЯ ЭЛЕМЕНТОВ УРОВНЯ ЛОГИКИ ---		//



// Задаём обработчик ошибок
ErrorHandler::setHandler();

// Создаём объекты обработчиков
$authentification_handler = new AuthentificationHandler();
$user_handler = new UserHandler();
$photo_handler = new PhotoHandler();
?>