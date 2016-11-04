package gonqbox.models;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserList {
	private int user_id;
	private String username;

	public UserList(ResultSet rs) throws SQLException {
		processRow(rs);
	}
	
	public void processRow(ResultSet rs) throws SQLException {
			user_id = rs.getInt("user_id");
			username = rs.getString("username");
	}
	
	public int getUserID() {
		return user_id;
	}

	public String getUsername() {
		return username;
	}

	@Override
	public String toString(){
		String string = "";
		string += "user_id:" 				+ ((Integer)user_id).toString() 	+ "\n"; 
		string += "username:" 				+ username 							+ "\n"; 
		return string;
	}
}
