package test;
import database.DBDriver;

public class TestMySQL {
	public static void main(String[] args){
		String url = "jdbc:mysql://ratewhatever.cloudapp.net/mysql";
		String username = "user";
		String password = "1whatever";
		DBDriver driver = new DBDriver(url, username, password);
		System.out.println(driver);
	}
}
