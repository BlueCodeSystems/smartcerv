package zm.gov.moh.core;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Calendar;

public class DBConnection {
	
	private Connection dbConnection;
	
	public void getDBConnection() throws ClassNotFoundException, SQLException {		
		Class.forName("com.mysql.jdbc.Driver");
		dbConnection = DriverManager.getConnection("jdbc:mysql://localhost:6666/jcg", "root", "password");
		// create a sql date object so we can use it in our INSERT statement
		Calendar calendar = Calendar.getInstance();
		java.sql.Date startDate = new java.sql.Date(calendar.getTime().getTime());

		// the mysql insert statement
		String query = " insert into users (first_name, last_name, date_created)"
				+ " values (?, ?, ?, ?, ?)";

		// create the mysql insert preparedstatement
		PreparedStatement preparedStmt = dbConnection.prepareStatement(query);
		preparedStmt.setInt    (5, 1000);

		// execute the preparedstatement
		preparedStmt.execute();

		dbConnection.close();
	}
	
	public int executeQuery(String query) throws ClassNotFoundException, SQLException {
		return dbConnection.createStatement().executeUpdate(query);
	}

}
