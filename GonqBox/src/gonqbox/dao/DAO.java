package gonqbox.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import gonqbox.models.*;

public class DAO {
	private static Connection conn = null;

	private DAO() {
	}

	public static Connection getDAO() {
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
		return conn;
	}

	/**
	 * Calls stored procedure to check
	 * @return User object
	 */
	public static User loginUser(String username, String password) {
		try {
			PreparedStatement statement = null;
			ResultSet rs = null;
			String query = "SELECT first FROM tblUsers WHERE";

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
}
