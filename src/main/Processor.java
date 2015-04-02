package main;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import data.Rating;
import database.RatingDB;

public class Processor {
	private RatingDB	db;
	
	public Processor() {
		try {
			Scanner in = new Scanner(new FileInputStream("db"));
			
			String url = in.nextLine();
			String username = in.nextLine();
			String password = in.nextLine();

			this.db = new RatingDB(url, username, password);

			in.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			System.exit(-1);
		}
	}
	
	public void process(ClientSocket client, String str) throws IOException {
		String[] pairs = str.split("&");
		Map<String, String> map = new HashMap<>();
		
		for (int i = 0; i < pairs.length; i++) {
			String pair = pairs[i];
			String[] tmp = pair.split("=");
			String key = this.decode(tmp[0]);
			String value = this.decode(tmp[1]);
			
			map.put(key, value);
			
			System.out.println("**" + key + " : " + value + "**");
		}
		
		this.switching(client, map);
	}
	
	private void switching(ClientSocket client, Map<String, String> map)
			throws IOException {
		String action = map.get("action");
		
		if (action == null) {
			// Something bad happened!
			return;
		}
		
		if (action.equals("new")) {
			this.addNewRating(client, map);
		} else if (action.equals("search")) {
			this.searchLocation(client, map);
		} else if (action.equals("reply")) {
			this.addReply(client, map);
		}
	}
	
	private void addReply(ClientSocket client, Map<String, String> map) {
		int ratingID = Integer.parseInt(map.get("ratingID"));
		String reply = map.get("reply");
		
		// respond to client
		JSONBuilder jb = new JSONBuilder();
		jb.addValue("action", "reply");
		
		System.out.println(reply);
		
		if (this.db.addNewReply(ratingID, reply)) {
			jb.addValue("status", "success");
		} else {
			jb.addValue("status", "failed");
		}
		
		client.sendToClient(jb.toString());
		
		client.stopClient();
	}
	
	private void searchLocation(ClientSocket client, Map<String, String> map) {
		String location = map.get("location");

		List<Rating> ratingsList = this.db.getRatings(location);
		
		if (ratingsList == null) {
			// no such place
			JSONBuilder jb = new JSONBuilder();
			jb.addValue("action", "search");
			jb.addValue("status", "failed");
			
			client.sendToClient(jb.toString());
			
			client.stopClient();
			return;
		}
		
		// respond to client
		JSONBuilder jb = new JSONBuilder();
		jb.addValue("action", "search");
		jb.addValue("status", "success");
		
		int average = this.db.getAverageRating(location);
		jb.addValue("average", average);
		
		String[] ratings = new String[ratingsList.size()];
		for (int i = 0; i < ratings.length; i++) {
			ratings[i] = ratingsList.get(i).toString();
		}
		jb.addArray("list", ratings);
		
		client.sendToClient(jb.toString());
		
		client.stopClient();
	}
	
	private void addNewRating(ClientSocket client, Map<String, String> map) {
		String username = map.get("username");
		String location = map.get("location");
		int numStars = Integer.parseInt(map.get("numStars"));
		String description = map.get("description");
		
		// respond to client
		JSONBuilder jb = new JSONBuilder();
		jb.addValue("action", "new");
		
		if (this.db.addNewRating(username, location, numStars, description)) {
			jb.addValue("status", "success");
			
			client.sendToClient(jb.toString());
		} else {
			// Database error!
			jb.addValue("status", "failed");
			
			client.sendToClient(jb.toString());
		}
		
		// Close connection
		client.stopClient();
	}
	
	private String decode(String str) {
		char[] chars = str.toCharArray();
		StringBuilder sb = new StringBuilder();
		
		for (int i = 0; i < chars.length; i++) {
			if (chars[i] == '+') {
				sb.append(' ');
			} else if (chars[i] == '%') {
				if (i + 2 >= chars.length) {
					// Something bad happened!
					return "";
				}
				
				char ch = (char) Integer.parseInt(new String(chars, i + 1, 2),
						16);
				sb.append(ch);
				i += 2;
			} else {
				sb.append(chars[i]);
			}
		}

		return sb.toString();
	}
}
