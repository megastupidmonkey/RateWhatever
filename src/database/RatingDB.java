package database;
import java.sql.SQLException;

public class RatingDB {
	private DBUpdater updater;
	private RatingGetter fetcher;
	public RatingDB(String url, String username, String password){
		this.updater = new DBUpdater(url, username, password);
		this.fetcher = new RatingGetter(null);
	}
	public void addNewRating(String owner, String location, int numStars, String description){
		try {
			Entity entity = fetcher.getEntityWithProperty("location", location);
			if(entity == null){
				entity = new Entity();
				entity.addProperty("location", location);
				updater.addEntity("entities", entity);
			}
			Rating rating = new Rating(numStars, description, entity, description);
			updater.addRating(rating);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
