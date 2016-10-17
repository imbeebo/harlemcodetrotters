SELECT * FROM gonqbox.tbluser;
DELIMITER $$
CREATE DEFINER=`root`@`localhost` PROCEDURE `spregister`(
	IN _username	VARCHAR(20),
	IN _user_mail	VARCHAR(40),
	IN _password 	VARCHAR(255),
	IN _salt 		VARCHAR(128),
	IN _hash	 	VARCHAR(128)
)
BEGIN
	INSERT INTO `tbluser` (`username`,`account_creation_date`, `last_logged_in_date`, `user_mail`, `password`, `salt`, `hash`)
	VALUES (
		_username,
        NOW(),
        NOW(),
        _user_mail,
        _password,
        _salt,
        _hash
	);

	INSERT INTO `tblfolder` (`owner_id`,`folder_size`, `file_count`)
	VALUES (LAST_INSERT_ID(), 0, 0);
END$$
DELIMITER ;

