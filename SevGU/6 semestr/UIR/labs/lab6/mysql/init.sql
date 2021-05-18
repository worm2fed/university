--
-- Скрипт создания базы данных с данными.
--

-- Удалить старую базу и аккаунт, если они существуют
DROP DATABASE IF EXISTS mir_lab6;
DROP USER IF EXISTS mir;

-- Создать новую базу и аккаунт
CREATE DATABASE mir_lab6;
CREATE USER mir IDENTIFIED BY 'mir';
GRANT ALL PRIVILEGES ON mir_lab6.* TO mir;

-- Использовать созданную базу
USE mir_lab6;

-- Создать таблицу
CREATE TABLE IF NOT EXISTS `blogs` (
  `id` INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  `title` TEXT CHARACTER SET utf8mb4 NOT NULL,
  `posts_count` INT NOT NULL DEFAULT 0
) ENGINE = InnoDB;
