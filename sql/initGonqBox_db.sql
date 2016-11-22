DROP DATABASE IF EXISTS GONQBOX;
DROP DATABASE IF EXISTS gonqbox;

CREATE DATABASE IF NOT EXISTS gonqbox;

USE gonqbox;


CREATE TABLE IF NOT EXISTS `gonqbox`.`tbluser` (
	`user_id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
	`username` VARCHAR(20) NOT NULL UNIQUE,
	`account_creation_date` DATE NOT NULL,
	`last_logged_in_date` DATE NOT NULL,
	`user_mail` VARCHAR(40) NOT NULL UNIQUE,
	`password` VARCHAR(255) NULL,
	`salt` NVARCHAR(128) NOT NULL,
	`hash` NVARCHAR(128) NOT NULL,
	PRIMARY KEY (`user_id`)
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS `gonqbox`.`tblpermission` (
	`permission_id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
	`permission_name` VARCHAR(24) NOT NULL UNIQUE,
	PRIMARY KEY (`permission_id`)
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS `gonqbox`.`tblfolder` (
	`folder_id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
	`owner_id` INT UNSIGNED NOT NULL,
	`folder_size` INT UNSIGNED NOT NULL,
	`file_count` INT UNSIGNED NOT NULL,
	PRIMARY KEY (`folder_id`),
    FOREIGN KEY (`owner_id`) REFERENCES tbluser(`user_id`) ON DELETE CASCADE
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS `gonqbox`.`tblfile` (
	`file_id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
	`name` VARCHAR(255) NOT NULL,
	`file_size` INT UNSIGNED NOT NULL,
	`sequence` VARCHAR(24) NOT NULL UNIQUE,
	`uploader_id` INT UNSIGNED NOT NULL,
	`folder_id` INT UNSIGNED NOT NULL,
	`checksum` VARCHAR(64) NOT NULL,
	`checksum_date` DATE NOT NULL,
	`checksum_date_last_verified` DATE NOT NULL,
	PRIMARY KEY (`file_id`),
	FOREIGN KEY (`uploader_id`) REFERENCES tbluser(`user_id`) ON DELETE CASCADE,
	FOREIGN KEY (`folder_id`) REFERENCES tblfolder(`folder_id`) ON DELETE CASCADE
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS `gonqbox`.`tblcollaborator` (
	`collaborator_id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
	`user_id` INT UNSIGNED NOT NULL,
	`file_id` INT UNSIGNED NOT NULL,
	`folder_id` INT UNSIGNED NOT NULL,
	PRIMARY KEY (`collaborator_id`),
	FOREIGN KEY (`user_id`) REFERENCES tbluser(`user_id`) ON DELETE CASCADE,
	FOREIGN KEY (`file_id`) REFERENCES tblfile(`file_id`) ON DELETE CASCADE,
	FOREIGN KEY (`folder_id`) REFERENCES tblfolder(`folder_id`) ON DELETE CASCADE
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS `gonqbox`.`tblComment` (
  	`comment_id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  	`user_id` INT UNSIGNED NOT NULL,
  	`file_id` INT UNSIGNED NOT NULL,
  	`body` text collate utf8_unicode_ci NOT NULL,
  	`dt` timestamp NOT NULL default CURRENT_TIMESTAMP,
  	PRIMARY KEY (`comment_id`),
	FOREIGN KEY (`user_id`) REFERENCES tbluser(`user_id`) ON DELETE CASCADE,
	FOREIGN KEY (`file_id`) REFERENCES tblfile(`file_id`) ON DELETE CASCADE
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS `gonqbox`.`tblfilepublic` (
	`id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
	`file_id` INT UNSIGNED NOT NULL,
    `public` BOOL NOT NULL DEFAULT FALSE,
	PRIMARY KEY (`id`),
	FOREIGN KEY (`file_id`) REFERENCES tblfile(`file_id`) ON DELETE CASCADE
) ENGINE=InnoDB;

/*tblUser Test Data*/

INSERT INTO `gonqbox`.`tbluser`(`username`,`account_creation_date`,`last_logged_in_date`,`user_mail`,`password`,`salt`,`hash`)
VALUES('~Billy',CURDATE(),CURDATE(),'~Billy@mail.com','~test-password','~test-salt','~test-hash');
INSERT INTO `gonqbox`.`tbluser`(`username`,`account_creation_date`,`last_logged_in_date`,`user_mail`,`password`,`salt`,`hash`)
VALUES('~Sally',CURDATE(),CURDATE(),'~Sally@mail.com','~test-password','~test-salt','~test-hash');
INSERT INTO `gonqbox`.`tbluser`(`username`,`account_creation_date`,`last_logged_in_date`,`user_mail`,`password`,`salt`,`hash`)
VALUES('~Greg',CURDATE(),CURDATE(),'~Greg@mail.com','~test-password','~test-salt','~test-hash');
INSERT INTO `gonqbox`.`tbluser`(`username`,`account_creation_date`,`last_logged_in_date`,`user_mail`,`password`,`salt`,`hash`)
VALUES('~Laquisha',CURDATE(),CURDATE(),'~Laquisha@mail.com','~test-password','~test-salt','~test-hash');

/*tblPermission Test Data*/

INSERT INTO `gonqbox`.`tblpermission`(`permission_name`)
VALUES('CONTENT_OWNER');
INSERT INTO `gonqbox`.`tblpermission`(`permission_name`)
VALUES('CONTENT_COLLABORATOR');
INSERT INTO `gonqbox`.`tblpermission`(`permission_name`)
VALUES('CONTENT_VIEWER');
INSERT INTO `gonqbox`.`tblpermission`(`permission_name`)
VALUES('ADMIN');
INSERT INTO `gonqbox`.`tblpermission`(`permission_name`)
VALUES('GUEST');

/*tblFolder Test Data*/

INSERT INTO `gonqbox`.`tblfolder`(`owner_id`,`folder_size`,`file_count`)
VALUES(1,800,2);
INSERT INTO `gonqbox`.`tblfolder`(`owner_id`,`folder_size`,`file_count`)
VALUES(2,0,0);
INSERT INTO `gonqbox`.`tblfolder`(`owner_id`,`folder_size`,`file_count`)
VALUES(3,200,1);
INSERT INTO `gonqbox`.`tblfolder`(`owner_id`,`folder_size`,`file_count`)
VALUES(4,0,0);

/*tblFile Test Data*/

INSERT INTO `gonqbox`.`tblfile`(`name`,`file_size`,`sequence`,`uploader_id`,`folder_id`,`checksum`,`checksum_date`,`checksum_date_last_verified`)
VALUES('cat.jpg','200','sequence1',3,3,'checksum',CURDATE(),CURDATE());
INSERT INTO `gonqbox`.`tblfile`(`name`,`file_size`,`sequence`,`uploader_id`,`folder_id`,`checksum`,`checksum_date`,`checksum_date_last_verified`)
VALUES('two_cats.png','600','sequence2',1,1,'checksum',CURDATE(),CURDATE());
INSERT INTO `gonqbox`.`tblfile`(`name`,`file_size`,`sequence`,`uploader_id`,`folder_id`,`checksum`,`checksum_date`,`checksum_date_last_verified`)
VALUES('dog.webm','200','sequence3',2,1,'checksum',CURDATE(),CURDATE());

/*tblCollaborator Test Data*/

INSERT INTO `gonqbox`.`tblcollaborator`(`user_id`,`file_id`,`folder_id`)
VALUES(2,3,1);

/*tblComent Test data*/

INSERT INTO `gonqbox`.`tblComment`(`user_id`,`file_id`,`body`, `dt`)
VALUES(2,2,'First Post', CURDATE());
INSERT INTO `gonqbox`.`tblComment`(`user_id`,`file_id`,`body`, `dt`)
VALUES(3,2,'Youre so lame', CURDATE());
INSERT INTO `gonqbox`.`tblComment`(`user_id`,`file_id`,`body`, `dt`)
VALUES(2,2,'Second Post', CURDATE());
INSERT INTO `gonqbox`.`tblComment`(`user_id`,`file_id`,`body`, `dt`)
VALUES(3,2,'Go away', CURDATE());
