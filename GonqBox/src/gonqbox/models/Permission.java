/**
 * @author Gabriel Hounsome
 * Model for Permissions
 */

package gonqbox.models;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Permission {
	private int permission_id;
	private String permission_name;

	public Permission(ResultSet rs) throws SQLException {
		processRow(rs);
	}
	
	public void processRow(ResultSet rs) throws SQLException {
		permission_id = rs.getInt("permission_id");
		permission_name = rs.getString("permission_name");
	}
	
	public int getPermissionID() {
		return permission_id;
	}

	public String getPermissionName() {
		return permission_name;
	}
	
	public void setPermissionName(String permissionName) {
		permission_name = permissionName;
	}
	
}
