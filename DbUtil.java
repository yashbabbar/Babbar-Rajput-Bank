package Bank;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class DbUtil {
	
	static String username = "root";
	static String password = "123456";
	static String dbUrl = "jdbc:mysql://localhost:3306/bank?" + "autoReconnect=true&useSSL=false";
	
	public static Connection getConnection() {
		Connection conn = null;
		try {
			
			 Class.forName("com.mysql.jdbc.Driver");
			 conn = DriverManager.getConnection(dbUrl, username, password);
		} catch (SQLException e) {
			
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} 
		return conn;
		
	}

}