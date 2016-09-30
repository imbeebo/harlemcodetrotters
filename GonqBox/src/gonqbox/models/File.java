/**
 * @author Gabriel Hounsome
 * Model for Files
 */

package gonqbox.models;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

public class File {
	private int file_id;
	private String name;
	private String sequence;
	private int uploader_id;
	private int folder_id;
	private String checksum;
	private Date checksumDate;
	private Date checksumDateLastChecked;

	public File(ResultSet rs) throws SQLException {
		processRow(rs);
	}
	
	public void processRow(ResultSet rs) throws SQLException {
		file_id = rs.getInt("file_id");
		name = rs.getString("name");
		sequence = rs.getString("sequence");
		uploader_id = rs.getInt("uploader_id");
		folder_id = rs.getInt("folder_id");
		checksum = rs.getString("checksum");
		checksumDate = rs.getDate("checksumDate");
		checksumDateLastChecked = rs.getDate("checksumDateLastChecked");
	}

	public int getFileID() {
		return file_id;
	}
	
	public String getName() {
		return name;
	}
	
	public String getSequence() {
		return sequence;
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
}
