/**
 * @author Gabriel Hounsome
 * Model for Users
 */

package gonqbox.models;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

public class User {
	private int user_id;
	private String username;
	private Date account_creation_date;
	private Date last_logged_in;
	private String user_email;

	public User(ResultSet rs) throws SQLException {
		processRow(rs);
	}
	
	public void processRow(ResultSet rs) throws SQLException {
			user_id = rs.getInt("user_id");
			username = rs.getString("username");
			account_creation_date = rs.getDate("account_creation_date");
			last_logged_in = rs.getDate("last_logged_in_date");
			user_email = rs.getString("user_mail");
	}
	
	public int getUserID() {
		return user_id;
	}

	public String getUsername() {
		return username;
	}
	
	public Date getCreationDate() {
		return account_creation_date;
	}

	
	public Date getLastLoggedIn() {
		return last_logged_in;
	}
	
	public String getEmail() {
		return user_email;
	}
	
	public void setEmail(String email) {
		user_email = email;
	}
	
	@Override
	public String toString(){
		String string = "";
		string += "user_id:" 				+ ((Integer)user_id).toString() 	+ "\n"; 
		string += "username:" 				+ username 							+ "\n"; 
		string += "account_creation_date:" 	+ account_creation_date.toString() 	+ "\n"; 
		string += "last_logged_in:" 		+ last_logged_in.toString() 		+ "\n"; 
		string += "user_email:"				+ user_email 						+ "\n";
		return string;
	}

}