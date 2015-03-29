package main;

import java.net.Socket;

public class ClientSocket {
	private Socket socket;
	
	public ClientSocket(Socket socket) {
		this.socket = socket;
	}
}
