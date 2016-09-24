DROP DATABASE IF EXISTS GONQBOX;

CREATE DATABASE IF NOT EXISTS GONQBOX;

USE GONQBOX;

CREATE TABLE IF NOT EXISTS `GONQBOX`.`tblUser` (
	`user_id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
	`username` VARCHAR(20) NOT NULL,
	`account_creation_date` DATE NOT NULL,
	`last_logged_in_date` DATE NOT NULL,
	`user_mail` VARCHAR(40) NOT NULL,
	`password` VARCHAR(255) NULL,
	`salt` NVARCHAR(128) NULL,
	`hash` NVARCHAR(128) NULL,
	PRIMARY KEY (`user_id`)
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS `GONQBOX`.`tblPermission` (
	`permission_id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
	`permission_name` VARCHAR(24) NOT NULL,
	PRIMARY KEY (`permission_id`)
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS `GONQBOX`.`tblFolder` (
	`folder_id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
	`owner_id` INT UNSIGNED NOT NULL,
	`size` INT UNSIGNED NOT NULL,
	`file_count` INT UNSIGNED NOT NULL,
	PRIMARY KEY (`folder_id`),
    FOREIGN KEY (`owner_id`) REFERENCES tblUser(`user_id`) ON DELETE CASCADE
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS `GONQBOX`.`tblFile` (
	`file_id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
	`name` VARCHAR(255) NOT NULL,
	`sequence` VARCHAR(24) NOT NULL,
	`uploader_id` INT UNSIGNED NOT NULL,
	`foler_id` INT UNSIGNED NOT NULL,
	`checksum` VARCHAR(64) NOT NULL,
	`checksum_date` DATE NOT NULL,
	`checksum_date_last_verified` DATE NOT NULL,
	PRIMARY KEY (`file_id`),
	FOREIGN KEY (`uploader_id`) REFERENCES tblUser(`user_id`) ON DELETE CASCADE,
	FOREIGN KEY (`foler_id`) REFERENCES tblFolder(`folder_id`) ON DELETE CASCADE
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS `GONQBOX`.`tblCollaborator` (
	`collaborator_id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
	`user_id` INT UNSIGNED NOT NULL,
	`file_id` INT UNSIGNED NOT NULL,
	PRIMARY KEY (`collaborator_id`),
	FOREIGN KEY (`user_id`) REFERENCES tblUser(`user_id`) ON DELETE CASCADE,
	FOREIGN KEY (`file_id`) REFERENCES tblFile(`file_id`) ON DELETE CASCADE
) ENGINE=InnoDB;

