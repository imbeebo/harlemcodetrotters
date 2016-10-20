/**
 * @author Gabriel Hounsome
 * DAO for the project. Creates a single connection to the database, 
 * and has functions for all the queries, which maps them to the
 * objects in gonqbox.models
 */

package gonqbox.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import gonqbox.models.Collaborator;
import gonqbox.models.Comment;
import gonqbox.models.File;
import gonqbox.models.Folder;
import gonqbox.models.Permission;
import gonqbox.models.User;

public class DAO {
	private static Connection conn = null;
	private static DAO dao = new DAO();

	private DAO() {
	}

	public static DAO getInstance() {
		
		if (conn == null) {
			
			try {
				String url = "jdbc:mysql://localhost:3306/";
				String dbName = "gonqbox";
				String driver = "com.mysql.jdbc.Driver";
				String userName = System.getenv("db.username");
				String password = System.getenv("db.password");
				
				Class.forName(driver).newInstance();
				conn = DriverManager.getConnection(url + dbName, userName, password);
			
			} catch (SQLException e) {
				System.out.println("SQL Exception: " + e);
				return null;
			} catch (InstantiationException e) {
				System.out.println("Could not instantiate: " + e);
				return null;
			} catch (IllegalAccessException e) {
				System.out.println("Cannot access: " + e);
				return null;
			} catch (ClassNotFoundException e) {
				System.out.println("Class not found: " + e);
				return null;
			}
		}
		
		return dao;
		
	}

	/**
	 * Calls stored procedure to check if valid login, and return user object
	 * 
	 * @return User object if valid, otherwise null
	 */
	public User loginUser(User user) {
		if(null == user) throw new NullPointerException("User object is null.");
		try {
			PreparedStatement statement = null;
			ResultSet rs = null;
			
			String query = "";
			query += "SELECT ";
			query += "user_id, username, account_creation_date, last_logged_in_date, user_mail ";
			query += "FROM tbluser ";
			query += "WHERE username = ? ";
			query += "AND password = ?";
			
			statement = conn.prepareStatement(query);
			
			statement.setString(1, user.getUsername());
			statement.setString(2, user.getPassword());
			
			rs = statement.executeQuery();
			rs.first();
			return new User(rs);
			
		} catch (SQLException e) {
			System.out.println("Problem with the SQL in method DAO.loginUser: " + e);
			return null;
		}
		
	}

	/**
	 * Registers user if there is no conflicting username or email
	 * 
	 * @return User object if no conflict, otherwise null
	 */
	public User registerUser(User user) {
		if(null == user) throw new NullPointerException("User object is null.");
		try {
			PreparedStatement statement = null;
			java.sql.Date date = new java.sql.Date(Calendar.getInstance().getTime().getTime());

			String query = "INSERT INTO `tbluser` (`username`, "+
					"`account_creation_date`, `last_logged_in_date`, "+
					"`user_mail`, `password`, `salt`, `hash`) VALUES(?, ?, ?, ?, ?, 'test-salt', 'test-hash');";

			statement = conn.prepareStatement(query);
			statement.setString(1, user.getUsername());
			statement.setDate(2, date);
			statement.setDate(3, date);
			statement.setString(4, user.getEmail());
			statement.setString(5, user.getPassword());
			statement.executeUpdate();

			return loginUser(user);
		} catch (SQLException e) {
			System.out.println("Problem with the SQL: " + e);
			return null;
		}
	}
	
	public Folder getUserFolder(int userId) {
		
		try {
			
			String query = "";
			query += "SELECT ";
			query += "folder_id, owner_id, folder_size, file_count ";
			query += "FROM tblfolder ";
			query += "WHERE owner_id = ?";

			PreparedStatement statement = conn.prepareStatement(query);
			
			statement.setInt(1, userId);
			
			ResultSet results = statement.executeQuery();
			results.first();
			return new Folder(results);
			
		} catch (SQLException e) {
			System.out.println("Problem with the SQL in method DAO.getUserFolder: " + e);
			return null;
		}
		
	}
	
	public List<File> getFolderFiles(int folderId) {
		
		try {
		
			String query = "SELECT * FROM tblfile WHERE folder_id = ?";
			PreparedStatement statement = conn.prepareStatement(query);
			
			statement.setInt(1, folderId);
			
			ResultSet results = statement.executeQuery();
			
			List<File> files = null;
			if(results.next()){
				files = new ArrayList<>();
				results.beforeFirst();
				while (results.next()) {
					files.add(new File(results));
				}
			}
			
			return files;
			
		} catch (SQLException e) {
			System.out.println("Problem with the SQL in method DAO.loginUser: " + e);
			return null;
		}
	}

	public List<File> getUserFiles(int userId) {
		try {
			PreparedStatement statement = null;
			ResultSet rs = null;

			String query = "SELECT * FROM tblfile WHERE user_id = ? ";
			statement = conn.prepareStatement(query);
			statement.setInt(1, userId);
			rs = statement.executeQuery();
			List<File> files = new ArrayList<>();
			while (rs.next()) {
				files.add(new File(rs));
			}
			return files;
		} catch (SQLException e) {
			System.out.println("Problem with the SQL: " + e);
			return null;
		}
	}
	
	public ArrayList<Permission> getPermissions() {
		return null;
	}

	public ArrayList<Collaborator> getCollaboratorsByFile(int fileID) {
		return null;
	}

	public ArrayList<Collaborator> getCollaboratorsByUserID(int userID) {
		return null;
	}

	public boolean addFile(File file) {
		try {
			PreparedStatement statement = null;
			java.sql.Date date = new java.sql.Date(Calendar.getInstance().getTime().getTime());

			String query = "INSERT INTO `tblfile` (`name`, `sequence`, `uploader_id`, `foler_id`, `checksum`, " +
					"`checksum_date`, `checksum_date_last_verified`) VALUES (?, ?, ?, ?, ?, ?, ?);";

			statement = conn.prepareStatement(query);
			statement.setString(1, file.getName());
			statement.setString(2, file.getSequence());
			statement.setInt(3, file.getUploaderID());
			statement.setInt(4, file.getFolderID());
			statement.setString(5, file.getChecksum());
			statement.setDate(6, file.getChecksumDate());
			statement.setDate(7, file.getChecksumDateLastChecked());
			if(statement.executeUpdate() <= 0) {
				return false;
			}

		} catch (SQLException e) {
			System.out.println("Problem with the SQL: " + e);
			return false;
		}
		return true;
	}

	public boolean addCollaboratorToFile(File fileToShare) {
		return false;
	}

	public boolean deleteCollaboratorFromFile(Collaborator toDelete) {
		return false;
	}
	
	/**
	 * 
	 * @param commentToAdd
	 * @return true on successful addition, false on failure
	 */
	public boolean addComment(Comment commentToAdd){
		try {
			PreparedStatement statement = null;
			java.sql.Date date = new java.sql.Date(Calendar.getInstance().getTime().getTime());
			
			String query = "INSERT INTO `tblComment` (`comment_id`, "+
					"`uploader_id`,"+"`body`, "+"`dt`) VALUES(?, ?, ?, ?);";

			statement = conn.prepareStatement(query);
			statement.setInt(1, commentToAdd.getCommentID());
			statement.setInt(2, commentToAdd.getUploaderID());
			statement.setString(3, commentToAdd.getBody());
			statement.setDate(4, date);
			statement.executeUpdate();			
			
			//execution successful, return true
			return true;
		} catch (SQLException e) {
			System.out.println("Problem with the SQL: " + e);
			return false;
		}
	}

	public ArrayList<Comment> getCommentsByFileID(int fileID) {
		return null;
	}
}
