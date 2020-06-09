package Main;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.sql.Connection;

import Model.Protocol;

public class MainServerThread extends Thread{
	private MainServer server;
	private Socket socket;
	private int ID;
	private InputStream is;
	private OutputStream os;
	private Protocol protocol;
	private Connection conn;
	
	public MainServerThread(MainServer _server, Socket _socket, Connection _conn) {
		server = _server;
		socket = _socket;
		ID = socket.getPort();
		conn = _conn;
	}
	
	public int getID() { return ID; }
	
	public void send(byte[] packet) {
		try {
			os.write(packet);
			os.flush();
		} catch (IOException ioe) {
			System.out.println(ID + " Error sending: " + ioe.getMessage());
			server.remove(ID);
			close();
			System.out.println(ID + " has closed.");
			stop();
		}
	}
	
	public void run() {
		System.out.println("Server Thread " + ID + " running.");
		try {
			while(true) {
				protocol = new Protocol();
				byte[] head = new byte[protocol.LEN_HEADER];
				is.read(head);
				protocol.setHeader(head);
				byte[] body = new byte[protocol.getBodyLength()];
				is.read(body);
				protocol.setBody(body);
				
				server.handle(os, ID, protocol, conn);
			}
		} catch (Exception ioe) {
			System.out.println(ID + " Error reading: " + ioe.getMessage());
			server.remove(ID);
			close();
			System.out.println(ID + " has closed.");
			stop();
		}
	}
	
	public void open() throws IOException {
		is = socket.getInputStream();
		os = socket.getOutputStream();
	}
	
	public void close() {
		try {
			if (socket != null)
				socket.close();
			if (is != null)
		    	is.close();
			if (os != null)
		    	os.close();
		} catch (IOException ioe) {
			System.out.println(ID + " Error closing: " + ioe.getMessage());
		}
	}
}
