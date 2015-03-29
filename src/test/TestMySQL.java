package test;
import java.sql.SQLException;

import database.*;

public class TestMySQL {
	public static void main(String[] args){
		try {
			String url = "jdbc:mysql://ratewhatever.cloudapp.net/ratewhatever";
			String username = "user";
			String password = "1whatever";
			DBDriver driver = new DBDriver(url, username, password);
			Rating rating = new Rating();
			driver.executeBatch();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
