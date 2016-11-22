/**
 * @author Gabriel Hounsome
 * DAO for the project. Creates a single connection to the database, 
 * and has functions for all the queries, which maps them to the
 * objects in gonqbox.models
 */

package gonqbox.dao;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

import org.apache.catalina.tribes.util.Arrays;

import gonqbox.models.Collaborator;
import gonqbox.models.Comment;
import gonqbox.models.File;
import gonqbox.models.Folder;
import gonqbox.models.Permission;
import gonqbox.models.User;
import gonqbox.models.UserList;

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
	
	public User getUserByID(int userID) {
		if(userID <= 0) throw new NullPointerException("User ID is invalid");
		try {
			PreparedStatement statement = null;
			ResultSet rs = null;
			
			String query = "";
			query += "SELECT ";
			query += "user_id, username, account_creation_date, last_logged_in_date, user_mail ";
			query += "FROM tbluser ";
			query += "WHERE user_id = ? ";
			
			statement = conn.prepareStatement(query);
			
			statement.setInt(1, userID);
			
			rs = statement.executeQuery();
			rs.first();
			return new User(rs);
			
		} catch (SQLException e) {
			System.out.println("Problem with the SQL in method DAO.getUserByID: " + e);
			return null;
		}
	}

	/**
	 * Calls stored procedure to check if valid login, and return user object
	 * @param password 
	 * @param username 
	 * 
	 * @return User object if valid, otherwise null
	 */
	public User loginUser(String username, String password) { // these functions shouldn't take a user object, they should make one.
		try {
			PreparedStatement verifyStatement = conn.prepareStatement("SELECT salt, hash FROM gonqbox.tbluser WHERE BINARY username = ?");
			verifyStatement.setString(1, username);
			ResultSet verifyResultSet = verifyStatement.executeQuery();
			verifyResultSet.first();
			
			byte[] salt = Base64.getDecoder().decode(verifyResultSet.getString("salt"));
			String keyAlgorithm = new String("PBKDF2WithHmacSHA1");
			KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, 65536, 128);
			byte[] hash = SecretKeyFactory.getInstance(keyAlgorithm).generateSecret(spec).getEncoded();
			
			if (Arrays.equals(hash, Base64.getDecoder().decode(verifyResultSet.getString("hash")))){				
				PreparedStatement loginStatement = conn.prepareCall("{call sp_login(?, ?)}");
				loginStatement.setString(1, username);
				loginStatement.setString(2, Base64.getEncoder().encodeToString(hash));
				loginStatement.execute();
				ResultSet loginResultSet = loginStatement.getResultSet();
				loginResultSet.first();
				return new User(loginResultSet);
			}else return null;		
		} catch (SQLException e) {
			System.out.println("Problem with the SQL in method DAO.loginUser: " + e);
			return null;
		} catch (InvalidKeySpecException e) {
			System.out.println("Invalid spec: " + e);
			return null;
		} catch (NoSuchAlgorithmException e) {
			System.out.println("Invalid key generating algorithm: " + e);
			return null;
		}
	}

	/**
	 * Registers user if there is no conflicting username or email
	 * 
	 * @return User object if no conflict, otherwise null
	 */
	/* JUSTIFICATION: register/login should return a user object and not accept one, they should be generators, before this user should be unknown
	 * 
	 */
	public User registerUser(String username, String email, String password) {
		try {
			
			//Establishing user salt/hash.
			String keyAlgorithm = new String("PBKDF2WithHmacSHA1");
			SecureRandom random = new SecureRandom();
			byte[] salt = new byte[16];
			random.nextBytes(salt);
			KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, 65536, 128);
			byte[] hash = SecretKeyFactory.getInstance(keyAlgorithm).generateSecret(spec).getEncoded();
						
			PreparedStatement statement = conn.prepareCall("{call sp_register(?, ?, ?, ?, ?)}");
			statement.setString(1, username);
			statement.setString(2, email);
			statement.setString(3, password);
			statement.setString(4, Base64.getEncoder().encodeToString(salt));
			statement.setString(5, Base64.getEncoder().encodeToString(hash));
			statement.execute();

			User newUser = loginUser(username, password);
			createUserFolder(newUser);
			return newUser;
			
		} catch (SQLException e) {
			System.out.println("Problem with the SQL: " + e);
			return null;
		} catch (InvalidKeySpecException e) {
			System.out.println("Invalid spec: " + e);
			return null;
		} catch (NoSuchAlgorithmException e) {
			System.out.println("Invalid key generating algorithm: " + e);
			return null;
		}
	}
	
	private void createUserFolder(User user) {
		if(null == user) throw new NullPointerException("User object is null.");
		try {
			PreparedStatement statement = null;

			String query = "INSERT INTO `tblfolder` (`owner_id`, "+
					"`folder_size`, `file_count`) "+
					"VALUES(?, 0, 0);";

			statement = conn.prepareStatement(query);
			statement.setInt(1, user.getUserID());
			statement.executeUpdate();

		} catch (SQLException e) {
			System.out.println("Problem with the SQL: " + e);
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
		
			String query = "SELECT f.*, u.username FROM tblfile f INNER JOIN tbluser u " +
					"ON u.user_id = f.uploader_id WHERE f.folder_id = ?";
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
	
	public List<UserList> getListOfUsers() {
		try {
			PreparedStatement statement = null;
			ResultSet rs = null;
			
			String query = "";
			query += "SELECT ";
			query += "user_id, username ";
			query += "FROM tbluser;";
			
			statement = conn.prepareStatement(query);
			rs = statement.executeQuery();
			
			
			List<UserList> users = null;
			if(rs.next()){
				users = new ArrayList<>();
				rs.beforeFirst();
				while (rs.next()) {
					users.add(new UserList(rs));
				}
			}
			return users;
			
		} catch (SQLException e) {
			System.out.println("Problem with the SQL in method DAO.getUserByID: " + e);
			return null;
		}
	}

	public Optional<File> getFileByID(int id) {
		try {
			PreparedStatement statement = null;
			ResultSet rs = null;

			String query = "SELECT tblfile.*, tbluser.username FROM tblfile INNER JOIN tbluser ON tbluser.user_id = tblfile.uploader_id WHERE tblfile.file_id = ? ";
			statement = conn.prepareStatement(query);
			statement.setInt(1, id);
			rs = statement.executeQuery();

			if(rs.first()) {
				return Optional.of(new File(rs));
			}
		} catch (SQLException e) {
			System.err.println("Problem with the SQL: " + e);
			e.printStackTrace();
		}
		return Optional.empty();
	}

	public List<File> getPublicFolderFiles(int folderId) {
		
		try {
			String query = "SELECT fi.*, u.username FROM tblfile fi " +
				"INNER JOIN tblfolder fo ON fi.folder_id = fo.folder_id " +
				"INNER JOIN tblfilepublic p ON fi.file_id = p.file_id " +
				"INNER JOIN tbluser u ON u.user_id = fi.uploader_id " +
				"WHERE fo.folder_id = ? AND p.public = true;";
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

			String query = "SELECT tblfile.*, tbluser.username FROM tblfile INNER JOIN tbluser ON tbluser.user_id = tblfile.uploader_id WHERE tblfile.uploader_id = ?";
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

			String query = "INSERT INTO `tblfile` (`name`, `sequence`, `uploader_id`, `folder_id`, `checksum`, " +
					"`checksum_date`, `checksum_date_last_verified`, `file_size`) VALUES (?, ?, ?, ?, ?, ?, ?, ?);";

			statement = conn.prepareStatement(query);
			statement.setString(1, file.getName());
			statement.setString(2, file.getSequence());
			statement.setInt(3, file.getUploaderID());
			statement.setInt(4, file.getFolderID());
			statement.setString(5, file.getChecksum());
			statement.setDate(6, file.getChecksumDate());
			statement.setDate(7, file.getChecksumDateLastChecked());
			statement.setLong(8, file.getFileSize());
			if(statement.executeUpdate() <= 0) {
				return false;
			} else {
				updateFolderSize(file.getFolderID());
			}

		} catch (SQLException e) {
			System.out.println("Problem with the SQL: " + e);
			return false;
		}
		return true;
	}

	private void updateFolderSize(int folderID) {
		try {
			String query = "update tblfolder natural join (select sum(file_size) as size, count(*) as count, folder_id "
					+ "from tblfile group by folder_id) as calc set folder_size = calc.size, file_count = calc.count "
					+ "where tblfolder.folder_id = ?";
			PreparedStatement stmt = conn.prepareStatement(query);
			stmt.setInt(1, folderID);
			stmt.executeUpdate();
		} catch (SQLException e) {
			System.err.println("Problem with the SQL in updateFolderSize(): " + e.getMessage());
			e.printStackTrace();
		}
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
			
			String query = "INSERT INTO `tblComment` (`user_id`, "+
					"`file_id`,"+"`body`, "+"`dt`) VALUES(?, ?, ?, ?);";

			statement = conn.prepareStatement(query);			
			statement.setInt(1, commentToAdd.getUploaderID());
			statement.setInt(2, commentToAdd.getFileID());
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

	public List<Comment> getCommentsByFileID(int fileID) {
		try {
			PreparedStatement statement = null;
			ResultSet rs = null;

			String query = "SELECT tblcomment.*, tbluser.username FROM tblComment INNER JOIN tbluser ON tbluser.user_id = tblcomment.user_id WHERE file_id = ? ";
			statement = conn.prepareStatement(query);
			statement.setInt(1, fileID);
			rs = statement.executeQuery();
			List<Comment> comments = new ArrayList<>();
			while (rs.next()) {
				comments.add(new Comment(rs));
			}
			return comments;
		} catch (SQLException e) {
			System.out.println("Problem with the SQL: " + e);
			return null;
		}
	}
	
	public boolean changePublicity(int fileID, boolean checkedState) {
		try {
			PreparedStatement statement = null;
			ResultSet rs = null;

			String query = "SELECT * FROM tblfilepublic WHERE file_id = ? ";
			statement = conn.prepareStatement(query);
			statement.setInt(1, fileID);
			rs = statement.executeQuery();
			int count = 0;
			while(rs.next()){
				count++;
			}
			if(count == 0) {
				query = "INSERT INTO `tblfilepublic` (`file_id`, "+
						"`public`) VALUES(?, ?);";

				statement = conn.prepareStatement(query);			
				statement.setInt(1, fileID);
				statement.setBoolean(2, checkedState);
				statement.executeUpdate();	
			}
			else {
				query = "UPDATE `tblfilepublic` SET `public`=? "+
						"WHERE `file_id`=?;";

				statement = conn.prepareStatement(query);	
				statement.setBoolean(1, checkedState);		
				statement.setInt(2, fileID);
				statement.executeUpdate();	
			}
			
			return true;
		} catch (SQLException e) {
			System.out.println("Problem with the SQL: " + e);
			return false;
		}
	}
}
