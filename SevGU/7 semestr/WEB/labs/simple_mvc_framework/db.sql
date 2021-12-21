DROP DATABASE IF EXISTS task_manager;
CREATE DATABASE task_manager;
USE task_manager;

--
-- Structure of table `task`
--
CREATE TABLE `task` (
  `task_id` int(11) NOT NULL,
  `status` tinyint(1) NOT NULL DEFAULT '0',
  `username` varchar(32) NOT NULL,
  `email` varchar(32) NOT NULL,
  `text` text NOT NULL,
  `image` varchar(128) DEFAULT NULL,
  `published` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Structure of table `user`
--
CREATE TABLE `user` (
  `user_id` int(11) NOT NULL,
  `username` varchar(32) NOT NULL,
  `password` varchar(64) DEFAULT NULL,
  `is_admin` tinyint(1) NOT NULL DEFAULT '0',
  `hash` varchar(64) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dump of table `user`
--
INSERT INTO `user` (`user_id`, `username`, `password`, `is_admin`, `hash`) VALUES
(1, 'admin', '40bd001563085fc35165329ea1ff5c5ecbdbbeef', 1, '8d6dc7bd64d87669b62e1a0b854b472e4c5860ad');

--
-- Indexes of table `task`
--
ALTER TABLE `task`
  ADD PRIMARY KEY (`task_id`);

--
-- Indexes of table `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`user_id`);

--
-- AUTO_INCREMENT for table `task`
--
ALTER TABLE `task`
  MODIFY `task_id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `user`
--
ALTER TABLE `user`
  MODIFY `user_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;
