package database;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;

import main.JSONBuilder;
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
				entity.setTotalStars(numStars);
				entity.setId(updater.getRowCount(ENT_TABLE));
				updater.addEntity("entities", entity);
			}
			Rating rating = new Rating(numStars, description, entity, owner);
			rating.setId(updater.getRowCount(RATE_TABLE));
			updater.addRating(rating);
			entity.setTotalStars(entity.getTotalStars() + numStars);
			entity.setNumRatings(entity.getNumRatings() + 1);
			updater.updateEntity(ENT_TABLE, entity.getId(), "totalStars", "" + entity.getTotalStars());
			updater.updateEntity(ENT_TABLE, entity.getId(), "numRatings", "" + entity.getNumRatings());
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	public List<Rating> getRatings(String location){
		try {
			Entity entity = fetcher.getEntityWithProperty(ENT_TABLE, "location", location);
			if (entity == null) {
				return null;
			}
			return fetcher.getRatingsForEntity(RATE_TABLE, entity);
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	public int getAverageRating(String location){
		try {
			Entity entity = fetcher.getEntityWithProperty(ENT_TABLE, "location", location);
			double numStars = (double)entity.getTotalStars();
			return (int)Math.round(numStars/(entity.getNumRatings()));
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
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
	public boolean addNewReply(int ratingID, String reply){
		try {
			Rating rating = this.getRating(ratingID);
			rating.addReply(reply);
			
			StringBuilder sb = new StringBuilder();
			Iterator<String> it = rating.getReplies().iterator();
			sb.append('[');
			
			if (it.hasNext()) {
				sb.append(it.next());
			}
			
			while (it.hasNext()) {
				sb.append(',');
				sb.append(it.next());
			}
			sb.append(']');
			
			System.out.println(sb.toString());
			
			updater.updateRating(ratingID, "replies", sb.toString());
			
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
