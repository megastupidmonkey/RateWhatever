package data;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import main.JSONBuilder;

public class Rating {
	private int id;
	private int numStars;
	private String description;
	private Entity entity;
	private String owner;
	private List<String> replies;
	public Rating(int numStars, String description, Entity entity, String owner){
		this.numStars = numStars;
		this.description = description;
		this.entity = entity;
		this.owner = owner;
		this.replies = new ArrayList<>();
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
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
	public void setReplies(List<String> replies) {
		this.replies = replies;
	}
	public void addReply(String reply){
		this.replies.add(reply);
	}
	
	public String toString() {
		JSONBuilder jb = new JSONBuilder();
		
		jb.addValue("id", String.valueOf(id));
		jb.addValue("numStars", String.valueOf(numStars));
		try {
			jb.addValue("description", URLEncoder.encode(description, "ASCII"));
			
			String[] repliesEncoded = new String[replies.size()];
			for (int i = 0; i < repliesEncoded.length; i++) {
				repliesEncoded[i] = "\"" + replies.get(i) + "\"";
			}
			jb.addArray("replies", repliesEncoded);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		jb.addValue("username", owner);
		
		
		
		return jb.toString();
	}
}
