package main;

import java.io.IOException;
import java.util.Scanner;

public class RateWhatever {
	
	private RateWhatever() {
		WebServer server;
		
		try {
			server = new WebServer(8100);
			
			server.start();
			
			Scanner in = new Scanner(System.in);
			while (true) {
				String line = in.nextLine();
				
				if (line.trim().equalsIgnoreCase("stop")) {
					server.stopServer();
					break;
				}
			}
			
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
	}
	
	public static void main(String[] args) {
		new RateWhatever();
	}
	
}
