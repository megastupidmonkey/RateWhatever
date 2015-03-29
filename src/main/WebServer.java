package main;

import java.io.IOException;
import java.net.ServerSocket;

public class WebServer extends Thread {
	private ServerSocket serverSocket;
	private boolean running;
	
	public WebServer(int port) throws IOException {
		serverSocket = new ServerSocket(port);
	}
	
	public void run() {
		running = true;
		while (running) {
			
		}
	}
	
	public void stopServer() throws IOException {
		running = false;
		serverSocket.close();
	}
}
