/**
 * @author Gabriel Hounsome
 * Model for Files
 */

package gonqbox.models;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

public class File {
	private int fileId;
	private String name;
	private String sequence;
	private int uploader_id;
	private int folder_id;
	private String checksum;
	private Date checksumDate;
	private Date checksumDateLastChecked;
	private long fileSize;
	
	public File(String name, String sequence, int uploader_id, int folder_id, String checksum,
			Date checksumDate, Date checksumDateLastChecked, long fileSize) {
		this.name = name;
		this.sequence = sequence;
		this.uploader_id = uploader_id;
		this.folder_id = folder_id;
		this.checksum = checksum;
		this.checksumDate = checksumDate;
		this.checksumDateLastChecked = checksumDateLastChecked;
		this.fileSize = fileSize;
	}

	public File(ResultSet rs) throws SQLException {
		processRow(rs);
	}
	
	public void processRow(ResultSet rs) throws SQLException {
		fileId = rs.getInt("file_id");
		name = rs.getString("name");
		sequence = rs.getString("sequence");
		uploader_id = rs.getInt("uploader_id");
		folder_id = rs.getInt("folder_id");
		checksum = rs.getString("checksum");
		checksumDate = rs.getDate("checksum_date");
		checksumDateLastChecked = rs.getDate("checksum_date_last_verified");
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
	
	public long getFileSize() {
		return fileSize;
	}
	
	public int getUploaderID() {
		return uploader_id;
	}
	
	public int getFolderID() {
		return folder_id;
	}
	
	public String getChecksum() {
		return checksum;
	}
	
	public Date getChecksumDate() {
		return checksumDate;
	}

	
	public Date getChecksumDateLastChecked() {
		return checksumDateLastChecked;
	}
	@Override
	public String toString(){
		String string = "";
		string += "fileId:" 		+ ((Integer)fileId).toString() 		+ "\n"; 
		string += "name:" 			+ name 								+ "\n"; 
		string += "sequence:" 		+ sequence							+ "\n"; 
		string += "uploaderId:" 	+ ((Integer)uploader_id).toString()	+ "\n"; 
		string += "folderId:"		+ ((Integer)folder_id).toString()	+ "\n";
		string += "fileSize:"		+ ((Long)fileSize).toString()		+ "\n";
		return string;
	}

}
