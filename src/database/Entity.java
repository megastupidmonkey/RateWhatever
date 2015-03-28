package database;
import java.util.Map;
import java.util.List;

public class Entity {
	private Map<String,String> properties;
	private List<Rating> ratings;
	public Entity(Map<String,String> properties, List<Rating> ratings){
		this.properties = properties;
		this.ratings = ratings;
	}
}
