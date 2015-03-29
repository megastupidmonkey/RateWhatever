package main;

import java.util.HashMap;
import java.util.Map;

import database.RatingDB;

public class Processor {
	private RatingDB db;
	
	public Processor() {
		db = new RatingDB("jdbc:mysql://ratewhatever.cloudapp.net/ratewhatever", 
				"user", "1whatever");
	}
	
	public void process(ClientSocket client, String str) {
		String[] pairs = str.split("&");
		Map<String, String> map = new HashMap<>();
		
		for (int i = 0; i < pairs.length; i++) {
			String pair = pairs[i];
			String[] tmp = pair.split("=");
			String key = decode(tmp[0]);
			String value = decode(tmp[1]);
			
			map.put(key, value);
			
			System.out.println("**" + key + " : " + value + "**");
		}
		
		switching(client, map);
	}
	
	private void switching(ClientSocket client, Map<String, String> map) {
		String action = map.get("action");
		
		if (action == null) {
			// Something bad happened!
			return;
		}
		
		if (action.equals("new")) {
			/*
			 * Add New Rating
			 */
			String username = map.get("username");
			String location = map.get("location");
			int numStars = Integer.parseInt(map.get("numStars"));
			String description = map.get("description");
			
			db.addNewRating(username, location, numStars, description);
			
			// respond to client
			
		}
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
				
				char ch = (char)Integer.parseInt(new String(chars, i + 1, 2), 16);
				sb.append(ch);
				i += 2;
			} else {
				sb.append(chars[i]);
			}
		}
		
		
		return sb.toString();
	}
}
