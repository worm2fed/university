--
-- Скрипт создания базы данных с данными.
--

-- Удалить старую базу и аккаунт, если они существуют
DROP DATABASE IF EXISTS mir_lab7;
DROP USER IF EXISTS mir;

-- Создать новую базу и аккаунт
CREATE DATABASE mir_lab7;
CREATE USER mir IDENTIFIED BY 'mir';
GRANT ALL PRIVILEGES ON mir_lab7.* TO mir;

-- Использовать созданную базу
USE mir_lab7;

-- Создать таблицу без партицирования
CREATE TABLE IF NOT EXISTS `blogs` (
  `id` INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  `title` TEXT CHARACTER SET utf8mb4 NOT NULL,
  `posts_count` INT NOT NULL DEFAULT 0
) ENGINE = InnoDB;

-- Удалить процедуру
DROP PROCEDURE IF EXISTS `generate_data`;

-- Создать процедуру для генерации случайных данных
DELIMITER //
CREATE PROCEDURE `generate_data` (IN start_id INT, IN items_count INT)
BEGIN
    DECLARE i INT DEFAULT start_id;
    WHILE i < start_id + items_count DO
        INSERT INTO `blogs` (`id`, `title`, `posts_count`)
                     VALUES (i, MD5(i), RAND() * 1000);
        SET i = i + 1;
    END WHILE;
END //
DELIMITER ;
