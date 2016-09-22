/**
 * @author Gabriel Hounsome
 * Model for Folders
 */

package gonqbox.models;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Folder {
	private int folder_id;
	private int owner_id;
	private int size;
	private int file_count;

	public Folder(ResultSet rs) throws SQLException {
		processRow(rs);
	}
	
	public void processRow(ResultSet rs) throws SQLException {
		folder_id = rs.getInt("folder_id");
		owner_id = rs.getInt("owner_id");
		size = rs.getInt("size");
		file_count = rs.getInt("file_count");
	}
	
	public int getFolderID() {
		return folder_id;
	}
	
	public int getOwnerID() {
		return owner_id;
	}

	public int getSize() {
		return size;
	}
	
	public int getFileCount() {
		return file_count;
	}
	
	public void setSize(int size) {
		this.size = size;
	}
	
	public void setFileCount(int count) {
		this.file_count = count;
	}
}
