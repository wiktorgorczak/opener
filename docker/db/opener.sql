CREATE TABLE `documents` (
  `id` int NOT NULL,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,  
  `path` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `type` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `expectedSuffix` varchar(7) CHARACTER SET utf8 COLLATE utf8_bin,
  `realSuffix` varchar(7) CHARACTER SET utf8 COLLATE utf8_bin,
  `verified` tinyint(1) NOT NULL,
  `uploadDate` timestamp NOT NULL,
  `pathToUnsignedFile` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8_bin;

--
-- Indexes
--
ALTER TABLE `documents`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `document_name` (`name`),
  ADD UNIQUE KEY `document_path` (`path`);

--
-- AUTO_INCREMENT
--
ALTER TABLE `documents`
  MODIFY `id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=1;
  
CREATE USER 'opener'@'%' IDENTIFIED BY 'mysecretpassword';
GRANT ALL PRIVILEGES ON *.* TO 'opener'@'%' WITH GRANT OPTION;
