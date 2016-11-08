/**
 * @author Mathew Boland
 * Model for the comments
 */

package gonqbox.models;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Comment {
	private int comment_id;
	private String body;
	private int uploader_id;
	private int file_id;
	private String username;

	public Comment(String body, int uploader_id, int file_id) {
		if(null == body) throw new NullPointerException("Comment cannot be null");
		this.body = body;
		this.uploader_id = uploader_id;
		this.file_id = file_id;
	}
	
	public Comment(ResultSet rs) throws SQLException {
		processRow(rs);
	}
	
	public void processRow(ResultSet rs) throws SQLException {
		comment_id = rs.getInt("comment_id");
		body = rs.getString("body");
		uploader_id = rs.getInt("user_id");
		file_id = rs.getInt("file_id");
		username = rs.getString("username");
	}

	public int getCommentID() {
		return comment_id;
	}
	
	public String getBody() {
		return body;
	}
	
	public String getUsername() {
		return username;
	}
	
	public int getUploaderID() {
		return uploader_id;
	}
	
	public int getFileID() {
		return file_id;
	}
}
