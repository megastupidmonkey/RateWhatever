package test;
import data.Rating;
import database.*;

public class TestMySQL {
	public static void main(String[] args){
		String url = "jdbc:mysql://ratewhatever.cloudapp.net/ratewhatever";
		String username = "user";
		String password = "1whatever";
		RatingDB db = new RatingDB(url, username, password);
		java.util.List<Rating> ratings = db.getRatings("\'chipotle\'");
		db.addNewReply(ratings.get(0), "really?");
		db.commit();
	}
}
