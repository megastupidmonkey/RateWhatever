package main;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JSONBuilder {
	private Map<String, String> map;
	
	public JSONBuilder() {
		map = new HashMap<>();
	}
	
	public void addValue(String key, String value) {
		map.put(key, value);
	}
	
	public void addArray(String key, String[] arr) {
		StringBuilder sb = new StringBuilder();
		
		sb.append('[');
		
		if (arr.length > 0) {
			sb.append(arr[0]);
		}
		
		for (int i = 1; i < arr.length; i++) {
			sb.append(arr[i]);
			sb.append(',');
		}
		sb.append(']');
	}
	
	public void addArray(String key, List<String> list) {
		
	}
	
	public String toString() {
		return null;
	}
}
