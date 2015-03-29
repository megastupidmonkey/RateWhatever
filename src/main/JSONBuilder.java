package main;

import java.util.Iterator;
import java.util.List;

public class JSONBuilder {
	private StringBuilder sb;
	private int count;
	
	public JSONBuilder() {
		clear();
	}
	
	public void clear() {
		sb = new StringBuilder();
		count = 0;
	}
	
	public void addValue(String key, String value) {
		if (count > 0) {
			sb.append(',');
		}
		
		count++;
		
		sb.append('"');
		sb.append(key);
		sb.append('"');
		
		sb.append(':');
		
		sb.append('"');
		sb.append(value);
		sb.append('"');
	}
	
	private void addArray(String key, String arrStr) {
		if (count > 0) {
			sb.append(',');
		}
		
		count++;
		
		sb.append('"');
		sb.append(key);
		sb.append('"');
		
		sb.append(':');
		
		sb.append(arrStr);
	}
	
	public void addArray(String key, String[] arr) {
		StringBuilder sb = new StringBuilder();
		
		sb.append('[');
		
		if (arr.length > 0) {
			sb.append(arr[0]);
		}
		
		for (int i = 1; i < arr.length; i++) {
			sb.append(',');
			sb.append(arr[i]);
		}
		sb.append(']');
		
		addArray(key, sb.toString());
	}
	
	public void addArray(String key, List<String> list) {
		StringBuilder sb = new StringBuilder();
		Iterator<String> it = list.iterator();
		sb.append('[');
		
		if (it.hasNext()) {
			sb.append(it.next());
		}
		
		while (it.hasNext()) {
			sb.append(',');
			sb.append(it.next());
		}
		sb.append(']');
		
		addArray(key, sb.toString());
	}
	
	public String toString() {
		return "{" + sb.toString() + "}";
	}
}
