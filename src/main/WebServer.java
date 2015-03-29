package main;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class WebServer extends Thread {
	private ServerSocket serverSocket;
	private boolean running;
	private List<ClientSocket> clients;
	private Processor processor;
	
	public WebServer(int port) throws IOException {
		serverSocket = new ServerSocket(port);
		clients = new ArrayList<>();
		processor = new Processor();
	}
	
	public void run() {
		System.out.println("WebServer running...");
		
		running = true;
		while (running) {
			
			try {
				Socket socket = serverSocket.accept();
				ClientSocket clientSocket = new ClientSocket(socket, processor);
				
				clients.add(clientSocket);
				clientSocket.start();
			} catch (IOException e) {
				// Ignore Exception
			}
			
			
		}
	}
	
	public void stopServer() throws IOException {
		running = false;
		for (ClientSocket client : clients) {
			client.stopClient();
		}
		serverSocket.close();
	}
}
