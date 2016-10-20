/**
 * @author Gabriel Hounsome
 * Model for Users
 */

package gonqbox.models;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

import gonqbox.dao.DAO;

public class User {
	private int user_id;
	private String username;
	private Date account_creation_date;
	private Date last_logged_in;
	private String user_email;
	private String password;

	public User(ResultSet rs) throws SQLException {
		processRow(rs);
	}
	
	/**
	 * Only for registration and login. Objects that use this should be destroyed after function call.
	 * e.g. for registering a user: 
	 * User user = DAO.getInstance().registerUser(new User(username, password, email));
	 *  
	 * @param username
	 * @param account_creation_date
	 * @param last_logged_in
	 * @param password
	 */
	public User(String username, String password, String user_email) {
		if(null == username) throw new NullPointerException("Username must not be null");
		if(null == password) throw new NullPointerException("Password must not be null");
		if(null == user_email) throw new NullPointerException("Email must not be null");
		
		this.username = username;
		this.password = password;
		this.user_email = user_email;
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
	
	public String getPassword() {
		return password;
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