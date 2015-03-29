package database;
import java.util.List;

public class Entity {
	private int id;
	private List<String> properties;
	private List<String> values;
	private double averageRating;
	public Entity(){
		this.properties = new java.util.ArrayList<>();
		this.values = new java.util.ArrayList<>();
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
	public double getAverageRating() {
		return averageRating;
	}
	public void setAverageRating(double averageRating) {
		this.averageRating = averageRating;
	}
}
