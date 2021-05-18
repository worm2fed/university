--
-- Скрипт создания базы данных с данными.
--

-- Удалить старую базу и аккаунт, если они существуют
DROP DATABASE IF EXISTS mir_lab5;
DROP USER IF EXISTS mir;

-- Создать новую базу и аккаунт
CREATE DATABASE mir_lab5;
CREATE USER mir IDENTIFIED BY 'mir';
GRANT ALL PRIVILEGES ON mir_lab5.* TO mir;

-- Использовать созданную базу
USE mir_lab5;

-- Создать таблицу без партицирования
CREATE TABLE IF NOT EXISTS `blogs` (
  `id` INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  `title` TEXT CHARACTER SET utf8mb4 NOT NULL,
  `posts_count` INT NOT NULL DEFAULT 0
) ENGINE = InnoDB;

-- Создать таблицу с партицированием
CREATE TABLE IF NOT EXISTS `partitioned_blogs` (
  `id` INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  `title` TEXT CHARACTER SET utf8mb4 NOT NULL,
  `posts_count` INT NOT NULL DEFAULT 0
) ENGINE = InnoDB PARTITION BY RANGE (id) (
    PARTITION p0 VALUES LESS THAN (200),
    PARTITION p1 VALUES LESS THAN (700),
    PARTITION p2 VALUES LESS THAN (1300),
    PARTITION p3 VALUES LESS THAN (1900),
    PARTITION p4 VALUES LESS THAN (MAXVALUE)
);


-- Создать процедуру для генерации случайных данных
DELIMITER //
CREATE PROCEDURE `generate_data` (IN items_count INT)
BEGIN
    DECLARE i INT DEFAULT 0;
    WHILE i < items_count DO
        INSERT INTO `blogs`
            (`title`, `posts_count`)
        VALUES (
            MD5(i),
            RAND() * 1000
        );

        SET i = i + 1;
    END WHILE;

    -- Скопировать те же данные в партицированную таблицу
    INSERT INTO `partitioned_blogs` SELECT * FROM `blogs`;
END //
DELIMITER ;

-- Заполнить таблицу данными
CALL generate_data(1900);

-- Удалить процедуру
DROP PROCEDURE `generate_data`;
