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
	private long folder_size;
	private int file_count;

	public Folder(ResultSet rs) throws SQLException {
		processRow(rs);
	}
	
	public void processRow(ResultSet rs) throws SQLException {
		folder_id = rs.getInt("folder_id");
		owner_id = rs.getInt("owner_id");
		folder_size = rs.getInt("folder_size");
		file_count = rs.getInt("file_count");
	}
	
	public int getFolderID() {
		return folder_id;
	}
	
	public int getOwnerID() {
		return owner_id;
	}

	public long getSize() {
		return folder_size;
	}
	
	public int getFileCount() {
		return file_count;
	}
	
	public void setSize(long l) {
		this.folder_size = l;
	}
	
	public void setFileCount(int count) {
		this.file_count = count;
	}
	
	@Override
	public String toString(){
		String string = "";
		string += "folder_id:" 				+ ((Integer)folder_id).toString() 	+ "\n"; 
		string += "owner_id:" 				+ ((Integer)owner_id).toString() 	+ "\n"; 
		string += "folder_size:" 			+ ((Long)folder_size).toString()	+ "\n"; 
		string += "file_count:" 			+ ((Integer)file_count).toString() 	+ "\n"; 
		return string;
	}

	
}
