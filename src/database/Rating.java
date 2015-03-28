package database;
import java.util.List;

public class Rating {
	private int numStars;
	private String description;
	private Entity entity;
	private String owner;
	private List<String> replies;
	public Rating(int numStars, String description, Entity entity, String owner, List<String> replies){
		this.numStars = numStars;
		this.description = description;
		this.entity = entity;
		this.owner = owner;
		this.replies = replies;
	}
	public int getNumStars() {
		return numStars;
	}
	public String getDescription() {
		return description;
	}
	public Entity getEntity() {
		return entity;
	}
	public String getOwner() {
		return owner;
	}
	public List<String> getReplies() {
		return replies;
	}
}
