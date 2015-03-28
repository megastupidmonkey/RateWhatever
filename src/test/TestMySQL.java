package test;
import database.DBDriver;

public class TestMySQL {
	public static void main(String[] args){
		String url = null;
		String username = null;
		String password = null;
		DBDriver driver = new DBDriver(url, username, password);
		System.out.println(driver);
	}
}
