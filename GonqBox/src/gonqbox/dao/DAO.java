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
import java.util.List;

import gonqbox.models.Collaborator;
import gonqbox.models.File;
import gonqbox.models.Folder;
import gonqbox.models.Permission;
import gonqbox.models.User;

public class DAO {
	private static Connection conn = null;
	private static DAO dao = new DAO();

	private DAO() {	}

	public static DAO getInstance() {
		
		if (conn == null) {
			
			try {
				
				String url = "jdbc:mysql://localhost:3306/";
				String dbName = "gonqbox";
				String driver = "com.mysql.jdbc.Driver";
				String userName = "root";
				String password = "Basque";
				
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
	 * @return User object if valid, otherwise null
	 */
	public User loginUser(String username, String password) {
		
		try {
			
			String query = "";
			query += "SELECT ";
			query += "user_id, username, account_creation_date, last_logged_in_date, user_mail ";
			query += "FROM tbluser ";
			query += "WHERE username = ? ";
			query += "AND password = ?";
			
			PreparedStatement statement = conn.prepareStatement(query);
			
			statement.setString(1, username);
			statement.setString(2, password);
			
			ResultSet results = statement.executeQuery();
			results.first();
			return new User(results);
			
		} catch (SQLException e) {
			System.out.println("Problem with the SQL in method DAO.loginUser: " + e);
			return null;
		}
		
	}
	/**
	 * Registers user if there is no conflicting username or email
	 * @return User object if no conflict, otherwise null
	 */
	public User registerUser(String username, String password, String email) {
		
		try {			
			
			System.out.println(username + password + email);
			CallableStatement statement = conn.prepareCall("{call gonqbox.spregister(?, ?, ?, ?, ?)}");
			
			statement.setString(1, username);
			statement.setString(2, email);
			statement.setString(3, password);
			statement.setString(4, "test-salt");
			statement.setString(5, "test-hash");
			statement.executeUpdate();

			return this.loginUser(username, password);
		} catch (SQLException e) {
			System.out.println("Problem with the SQL in method DAO.registerUser: " + e);
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
	
	public ArrayList<Permission> getPermissions() {
		return null;
	}
	
	public ArrayList<Collaborator> getCollaboratorsByFile(int fileID) {
		return null;
	}
	
	public ArrayList<Collaborator> getCollaboratorsByUserID(int userID) {
		return null;
	}
	
	public boolean addFile(File fileToAdd) {
		return false;
	}
	
	public boolean addCollaboratorToFile(File fileToShare) {
		return false;
	}
	
	public boolean deleteCollaboratorFromFile(Collaborator toDelete) {
		return false;
	}
}
