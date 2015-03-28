package test;
import database.DBDriver;

public class TestMySQL {
	public static void main(String[] args){
		String url = "jdbc:mysql://dev.lypst.com/msg";
		String username = "user";
		String password = "QuRWhaKHqHcfEbdx";
		DBDriver driver = new DBDriver(url, username, password);
		System.out.println(driver);
	}
}
