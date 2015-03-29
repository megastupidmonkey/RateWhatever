package main;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ClientSocket extends Thread {
	private Socket socket;
	private BufferedInputStream bin;
	private BufferedOutputStream bout;
	
	public ClientSocket(Socket socket) {
		this.socket = socket;
		
		try {
			bin = new BufferedInputStream(socket.getInputStream());
			bout = new BufferedOutputStream(socket.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void stopClient() {
		try {
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void run() {
		try {
			while (true) {
				if (bin.read() == 0xD && bin.read() == 0xA && 
						bin.read() == 0xD && bin.read() == 0xA) {
					break;
				}
			}
			
			System.out.println("Reading...");
			
			byte[] content = new byte[8192];
			int nread = bin.read(content);
			
			System.out.println(new String(content, 0, nread));
			
			bout.write("HTTP/1.1 200 OK\r\nConnection: close\r\n".getBytes("ASCII"));
			bout.write("Access-Control-Allow-Origin: null\r\n\r\n".getBytes("ASCII"));
			bout.write("Response".getBytes("ASCII"));
			bout.flush();
			
			System.out.println("Closing...");
			
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
