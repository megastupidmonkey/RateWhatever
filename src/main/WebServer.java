package main;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class WebServer extends Thread {
	private ServerSocket serverSocket;
	private boolean running;
	
	public WebServer(int port) throws IOException {
		serverSocket = new ServerSocket(port);
	}
	
	public void run() {
		System.out.println("WebServer running...");
		
		running = true;
		while (running) {
			
			try {
				Socket socket = serverSocket.accept();
				ClientSocket clientSocket = new ClientSocket(socket);
				
				clientSocket.start();
			} catch (IOException e) {
				// Ignore Exception
			}
			
			
		}
	}
	
	public void stopServer() throws IOException {
		running = false;
		serverSocket.close();
	}
}
