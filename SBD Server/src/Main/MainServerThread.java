package Main;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.sql.Connection;

import Model.Protocol;

public class MainServerThread extends Thread{
	private MainServer server = null;
	private Socket socket = null;
	private int ID = -1;
	private InputStream is = null;
	private OutputStream os= null;
	private Protocol protocol = null;
	private Connection conn = null;
	
	public MainServerThread(MainServer _server, Socket _socket, Connection _conn) {
		super();
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
				byte[] buf = new byte[Protocol.LEN_PROTOCOL_MAX];
				is.read(buf);
				protocol = new Protocol();
				protocol.setPakcet(buf);
				
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
