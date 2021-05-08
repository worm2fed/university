<?php
// Подключаем уровень логики
require_once "include/config.php";
// Стартуем сессию
session_start();

// Проверяем авториован ли пользователь
if ($authentification_handler->checkIsUserLogined()) {
	$login = false;
	$user_info = $user_handler->getUserInfo($_SESSION['id']);
	
	$presentation_file = SITE_ROOT . '/admin_content.php';

	if (isset($_GET['action'])) {
		switch ($_GET['action']) {
			case 'logout':
				$authentification_handler->logout();
				break;
			case 'add':
				$presentation_file = SITE_ROOT . '/admin_add_content.php';

				if (isset($_POST['add_photo_btn'])) {
					//$photo_handler->addPhoto($user_info['user_id'], $_POST['photo_name'], $_POST['photo_description'], basename($_FILES['img']['name']));
					header("Location: http://webtask.ru/admin.php");
				}
				break;
			case 'edit':
				$presentation_file = SITE_ROOT . '/admin_edit_content.php';
				$photo_info = $photo_handler->getPhotoInfo($_GET['id']);

				if (isset($_POST['edit_photo_btn'])) {
					$photo_handler->editPhoto($_GET['id'], $_POST['photo_name'], $_POST['photo_description']);
					header("Location: http://webtask.ru/admin.php");
				}
				break;
			case 'del':
				$presentation_file = SITE_ROOT . '/admin_del_content.php';
				$photo_info = $photo_handler->getPhotoInfo($_GET['id']);
				
				if (isset($_POST['del_photo_btn'])) {
					$photo_handler->deletePhoto($_GET['id']);
					header("Location: http://webtask.ru/admin.php");
				}
				break;
		}
	}
}
else {
	$login = true;
	$presentation_file = SITE_ROOT . '/login.php';

	// Логиним пользователя
	if (isset($_POST['sign_in'])) {
		if (!$authentification_handler::loginUser($_POST['login'], $_POST['password']))
			$error = "\n<hr>\n<center style='color: red; font-size: 20px;'>Неверные персональные данные!</center>";
	}
}
?>

<!DOCTYPE html>
<html>
<head>
	<title>Webtask - Control Panel</title>
	<link rel="stylesheet" type="text/css" href="css/reset.css">
    <link rel="stylesheet" type="text/css" href="css/fonts.css">
    <link rel="stylesheet" type="text/css" href="css/admin.css">
    <link rel="stylesheet" type="text/css" href="css/bootstrap.min.css">
    <link rel="stylesheet" type="text/css" href="css/custom.css">
        
    <link rel="shortcut icon" href="favicon.png" type="image/png">
    
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

	<script src="js/jquery.min.js"></script>
</head>

<body>
<?php
include_once $presentation_file;
?>
</body>
</html>