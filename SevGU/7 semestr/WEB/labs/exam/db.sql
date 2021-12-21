DROP DATABASE IF EXISTS poll_constructor;
CREATE DATABASE poll_constructor;
USE poll_constructor;

--
-- Structure of table `poll`
--
CREATE TABLE `poll` (
  `poll_id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(64) NOT NULL,
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,

  PRIMARY KEY (`poll_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Structure of table `question`
--
CREATE TABLE `question` (
  `question_id` int(11) NOT NULL AUTO_INCREMENT,
  `poll_id` int(11) NOT NULL,
  `title` varchar(64) NOT NULL,

  PRIMARY KEY (`question_id`),
  FOREIGN KEY (`poll_id`) REFERENCES poll(`poll_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Structure of table `answer`
--
CREATE TABLE `answer` (
  `answer_id` int(11) NOT NULL AUTO_INCREMENT,
  `question_id` int(11) NOT NULL,
  `title` varchar(64) NOT NULL,
  `is_corroct` tinyint(1) NOT NULL,

  PRIMARY KEY (`answer_id`),
  FOREIGN KEY (`question_id`) REFERENCES question(`question_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

