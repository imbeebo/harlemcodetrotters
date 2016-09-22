/**
 * @author Gabriel Hounsome
 * DAO for the project. Creates a single connection to the database, 
 * and has functions for all the queries, which maps them to the
 * objects in gonqbox.models
 */

package gonqbox.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import gonqbox.models.*;

public class DAO {
	private static Connection conn = null;
	private static DAO dao = new DAO();

	private DAO() {	}

	public static DAO getInstance() {
		if (conn == null) {
			try {
				String url = "jdbc:mysql://localhost:3306/";
				String dbName = "form";
				String driver = "com.mysql.jdbc.Driver";
				String userName = "root";
				String password = "admin";
				Class.forName(driver).newInstance();
				conn = DriverManager.getConnection(url + dbName, userName, password);
			} catch (SQLException e) {
				System.out.println("SQL Exception: " + e);
				return null;
			} catch (InstantiationException e) {
				System.out.println("Could not instansiate: " + e);
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
			PreparedStatement statement = null;
			ResultSet rs = null;
			String query = "LOGIN_USER_SP";

			statement = conn.prepareStatement(query);
			rs = statement.executeQuery();
			User currentUser = null;
			while (rs.next()) {
				currentUser = new User(rs);
			}
			return currentUser;
		} catch (SQLException e) {
			System.out.println("Problem with the SQL: " + e);
			return null;
		}
	}
	/**
	 * Registers user if there is no conflicting username or email
	 * @return User object if no conflict, otherwise null
	 */
	public User registerUser(String username, String password) {
		try {
			PreparedStatement statement = null;
			ResultSet rs = null;
			String query = "REGISTER_USER_SP";

			statement = conn.prepareStatement(query);
			rs = statement.executeQuery();
			User currentUser = null;
			while (rs.next()) {
				currentUser = new User(rs);
			}
			return currentUser;
		} catch (SQLException e) {
			System.out.println("Problem with the SQL: " + e);
			return null;
		}
	}
	
	public Folder getUserFolder(int userId) {
		return null;
	}
	
	public ArrayList<File> getUserFiles(int userId) {
		return null;
	}
	
	public ArrayList<Permissions> getPermissions() {
		return null;
	}
	
	public ArrayList<Collaborator> getCollaboratorsByFile(int fileID) {
		return null;
	}
	
	public ArrayList<Collaborator> getCollaboratorsByUserID(int userID) {
		return null;
	}
}
