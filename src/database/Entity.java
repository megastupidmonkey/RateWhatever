package database;
import java.util.Map;
import java.util.List;

public class Entity {
	private int id;
	private Map<String,String> properties;
	private List<Rating> ratings;
	public Entity(Map<String,String> properties, List<Rating> ratings){
		this.properties = properties;
		this.ratings = ratings;
	}
	public int getId() {
		return id;
	}
	public Map<String, String> getProperties() {
		return properties;
	}
	public List<Rating> getRatings() {
		return ratings;
	}
	public void addRating(Rating rating){
		this.ratings.add(rating);
	}
}
