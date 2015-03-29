package database;
import java.sql.SQLException;
import java.util.List;

import data.Entity;
import data.Rating;

public class RatingDB {
	private static String ENT_TABLE = "entities";
	private static String RATE_TABLE = "ratings";
	private DBUpdater updater;
	private RatingGetter fetcher;
	public RatingDB(String url, String username, String password){
		this.updater = new DBUpdater(url, username, password);
		this.fetcher = new RatingGetter(url, username, password);
	}
	public boolean addNewRating(String owner, String location, int numStars, String description){
		try {
			Entity entity = fetcher.getEntityWithProperty(ENT_TABLE, "location", location);
			if(entity == null){
				entity = new Entity();
				entity.addProperty("location", location);
				entity.setAverageRating(numStars);
				updater.addEntity("entities", entity);
			}
			Rating rating = new Rating(numStars, description, entity, owner);
			updater.addRating(rating);
			
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			
			return false;
		}
	}
	public List<Rating> getRatings(String location){
		try {
			Entity entity = fetcher.getEntityWithProperty(ENT_TABLE, "location", location);
			return fetcher.getRatingsForEntity(RATE_TABLE, entity);
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	public Rating getRating(int ratingID){
		try {
			return fetcher.getRating(RATE_TABLE, ENT_TABLE, ratingID);
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	public boolean addNewReply(Rating rating, String reply){
		try {
			rating.addReply(reply);
			updater.updateRating(rating.getId(), "replies", "\'" + 
					rating.getReplies().toString() + "\'");
			
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	public void commit(){
		try {
			updater.executeBatch();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
