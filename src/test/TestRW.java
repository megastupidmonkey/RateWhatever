package test;

import java.util.ArrayList;
import java.util.List;

import main.JSONBuilder;

public class TestRW {
	
	public static void main(String[] args) {
		JSONBuilder jb = new JSONBuilder();
		
		jb.addValue("key", "value");
		List<String> list = new ArrayList<>();
		
		list.add("item 1");
		list.add("item 2");
		jb.addArray("array", list);
		
		jb.addArray("arr", new String[] {"something"});
		
		System.out.println(jb.toString());
	}
	
}
