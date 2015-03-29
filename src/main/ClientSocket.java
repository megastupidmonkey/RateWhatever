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
	
	public void run() {
		try {
			while (true) {
				if (bin.read() == 0xD && bin.read() == 0xA && 
						bin.read() == 0xD && bin.read() == 0xA) {
					break;
				}
			}
			
			byte[] bys = new byte[1024];
			
			bin.read(bys);
			
			System.out.println(new String(bys));
			
			
			bout.write("HTTP/1.1 200 OK\r\nConnection: close".getBytes("ASCII"));
			bout.write("\r\nAccess-Control-Allow-Origin: null\r\n".getBytes("ASCII"));
			bout.write("\r\nHello world!\r\n\r\nSome".getBytes("UTF-8"));
			bout.flush();
			
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
