package data;
import java.util.ArrayList;
import java.util.List;

public class Entity {
	private int id;
	private List<String> properties;
	private List<String> values;
	private int totalStars;
	private int numRatings;
	public Entity(){
		this.properties = new ArrayList<>();
		this.values = new  ArrayList<>();
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public List<String> getProperties() {
		return properties;
	}
	public void addProperty(String property, String value){
		this.properties.add(property);
		this.values.add(value);
	}
	public String getValue(String property){
		return this.values.get(this.properties.indexOf(property));
	}
	public double getTotalStars() {
		return totalStars;
	}
	public void setAverageRating(int totalStars) {
		this.totalStars = totalStars;
	}
	public int getNumRatings() {
		return numRatings;
	}
	public void setNumRatings(int numRatings) {
		this.numRatings = numRatings;
	}
}
