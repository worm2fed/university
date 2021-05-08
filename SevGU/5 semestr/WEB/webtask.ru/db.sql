-- Создаём базу данных
CREATE DATABASE `webtask` DEFAULT CHARACTER SET 'utf8' COLLATE 'utf8_unicode_ci';
USE `webtask`;

CREATE TABLE IF NOT EXISTS `user` (
	`user_id` int(11) NOT NULL AUTO_INCREMENT,
	`user_name` varchar(50) NOT NULL,
	`user_login` varchar(50) NOT NULL,
	`user_password` varchar(60) NOT NULL,
	`user_hash` varchar(32) DEFAULT NULL,
	PRIMARY KEY (`user_id`) 
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS `photo` (
	`photo_id` int(11) NOT NULL AUTO_INCREMENT,
	`user_id` int(11) NOT NULL,
	`photo_name` varchar(30) NOT NULL,
	`photo_date` date,
	`photo_description` varchar(150) DEFAULT NULL,
	`photo_file` varchar(250) NOT NULL,
	PRIMARY KEY(`photo_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

ALTER TABLE `photo` ADD INDEX ( `user_id` );
ALTER TABLE `photo` ADD FOREIGN KEY ( `user_id` ) REFERENCES `user` (`user_id`) ON DELETE SET NULL ON UPDATE RESTRICT;
