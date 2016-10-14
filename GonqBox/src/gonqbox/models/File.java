/**
 * @author Gabriel Hounsome
 * Model for Files
 */

package gonqbox.models;

import java.sql.ResultSet;
import java.sql.SQLException;

public class File {
	private int fileId;
	private String name;
	private String sequence;
	private int uploaderId;
	private int folderId;
	private int fileSize;

	public File(ResultSet rs) throws SQLException {
		processRow(rs);
	}
	
	public void processRow(ResultSet rs) throws SQLException {
		fileId = rs.getInt("file_id");
		name = rs.getString("name");
		sequence = rs.getString("sequence");
		uploaderId = rs.getInt("uploader_id");
		folderId = rs.getInt("folder_id");
		fileSize = rs.getInt("file_size");
	}

	public int getFileID() {
		return fileId;
	}
	
	public String getName() {
		return name;
	}
	
	public String getSequence() {
		return sequence;
	}
	
	public int getFileSize() {
		return fileSize;
	}
	
	public int getUploaderID() {
		return uploaderId;
	}
	
	public int getFolderID() {
		return folderId;
	}
	
	@Override
	public String toString(){
		String string = "";
		string += "fileId:" 		+ ((Integer)fileId).toString() 		+ "\n"; 
		string += "name:" 			+ name 								+ "\n"; 
		string += "sequence:" 		+ sequence							+ "\n"; 
		string += "uploaderId:" 	+ ((Integer)uploaderId).toString()	+ "\n"; 
		string += "folderId:"		+ ((Integer)folderId).toString()	+ "\n";
		string += "fileSize:"		+ ((Integer)fileSize).toString()	+ "\n";
		return string;
	}

}
