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

	public Comment(ResultSet rs) throws SQLException {
		processRow(rs);
	}
	
	public void processRow(ResultSet rs) throws SQLException {
		comment_id = rs.getInt("comment_id");
		body = rs.getString("body");
		uploader_id = rs.getInt("uploader_id");
		file_id = rs.getInt("file_id");
	}

	public int getCommentID() {
		return comment_id;
	}
	
	public String getBody() {
		return body;
	}
	
	public int getUploaderID() {
		return uploader_id;
	}
	
	public int getFileID() {
		return file_id;
	}
}
