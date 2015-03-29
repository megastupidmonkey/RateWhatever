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
			Entity entity = fetcher.getEntityWithProperty(ENT_TABLE, "location", "\'" + location + "\'");
			if(entity == null){
				entity = new Entity();
				entity.addProperty("location", location);
				entity.setTotalStars(numStars);
				entity.setId(updater.getRowCount(ENT_TABLE));
				updater.addEntity("entities", entity);
			}
			
			System.out.println(entity.getId());
			
			Rating rating = new Rating(numStars, description, entity, owner);
			rating.setId(updater.getRowCount(RATE_TABLE));
			updater.addRating(rating);
			updater.updateEntity(ENT_TABLE, entity.getId(), "totalStars", "" + (entity.getTotalStars() + numStars));
			updater.updateEntity(ENT_TABLE, entity.getId(), "numRatings", "" + (entity.getNumRatings() + 1));
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	public List<Rating> getRatings(String location){
		try {
			Entity entity = fetcher.getEntityWithProperty(ENT_TABLE, "location", "\'" + location + "\'");
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
