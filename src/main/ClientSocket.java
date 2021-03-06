package main;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ClientSocket extends Thread {
	private Socket socket;
	private BufferedInputStream bin;
	private BufferedOutputStream bout;
	
	private Processor processor;
	
	public ClientSocket(Socket socket, Processor processor) {
		this.socket = socket;
		this.processor = processor;
		
		try {
			bin = new BufferedInputStream(socket.getInputStream());
			bout = new BufferedOutputStream(socket.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void stopClient() {
		try {
			System.out.println("Closing...");
			
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void sendToClient(String str) {
		StringBuilder sb = new StringBuilder();
		
		try {
			sb.append("HTTP/1.1 200 OK\r\nConnection: close\r\n");
			sb.append("Access-Control-Allow-Origin: *\r\n\r\n");
			sb.append(str);
			
			bout.write(sb.toString().getBytes("ASCII"));
			bout.flush();
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
			
			String str = new String(content, 0, nread);
			processor.process(this, str);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
