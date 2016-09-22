package gonqbox.models;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Collaborator {
	private int collaborator_id;
	private int user_id;
	private int file_id;

	public Collaborator(ResultSet rs) throws SQLException {
		processRow(rs);
	}
	
	public void processRow(ResultSet rs) throws SQLException {
		collaborator_id = rs.getInt("collaborator_id");
		user_id = rs.getInt("user_id");
		file_id = rs.getInt("file_id");
	}
	
	public int getCollaboratorID() {
		return collaborator_id;
	}
	
	public int getUserID() {
		return user_id;
	}
	
	public int getFileID() {
		return file_id;
	}

}
