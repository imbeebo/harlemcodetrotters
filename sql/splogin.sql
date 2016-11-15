CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_login`(
	IN _username	VARCHAR(20),
	IN _hash 	VARCHAR(128)
)
BEGIN
	UPDATE gonqbox.tbluser SET `last_logged_in_date` = NOW() WHERE `username` = _username;
	SELECT `user_id`, `username`, `account_creation_date`, `last_logged_in_date`, `user_mail`
	FROM gonqbox.tbluser
	WHERE `username` = _username
	AND `hash` = _hash
	LIMIT 1;
END