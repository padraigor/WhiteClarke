package org.porourke.examples.liquidbase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class MySQLTest {
	
	public static void main(String[] args) {
		String dbUrl = "jdbc:mysql://localhost:3306";
		String dbClass = "com.mysql.jdbc.Driver";
		String query = "Select distinct(table_name) from INFORMATION_SCHEMA.TABLES";
		String username = "root";
		String password = "Password123";
		try {
		 
			Class.forName(dbClass);
			Connection connection = DriverManager.getConnection(dbUrl,username, password);
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(query);
			int x = 0;
			while (resultSet.next()) {
				String tableName = resultSet.getString(1);
				System.out.println("Table name : " + tableName);
				x++;
		}
			System.out.println(x);
		connection.close();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
