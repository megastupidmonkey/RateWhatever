package database;
import java.util.Map;
import java.util.List;

public class Entity {
	private int id;
	private Map<String,String> properties;
	private List<Rating> ratings;
	public Entity(){
		this.properties = new java.util.HashMap<>();
		this.ratings = new java.util.ArrayList<>();
	}
	public Entity(Map<String,String> properties, List<Rating> ratings){
		this.properties = properties;
		this.ratings = ratings;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Map<String, String> getProperties() {
		return properties;
	}
	public void addProperty(String property, String value){
		this.properties.put(property, value);
	}
	public List<Rating> getRatings() {
		return ratings;
	}
	public void addRating(Rating rating){
		this.ratings.add(rating);
	}
}
