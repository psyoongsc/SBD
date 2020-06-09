package application;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import Model.Protocol;

public class Network {
	private static OutputStream os;
	private static InputStream is;
	private static Socket socket;
	
	public static void Connection(){
		System.out.println("Establishing connection. Please wait ...");
		try {
			socket = new Socket("localhost", 3434);
			System.out.println("Connected: " + socket);
			is = socket.getInputStream();
			os = socket.getOutputStream();
			System.out.println("This client binding to server using port: " + socket.getPort());
		} catch (UnknownHostException uhe) {
			System.out.println("Host unknown: " + uhe.getMessage());
			System.exit(0);
		} catch (IOException ioe) {
			System.out.println("Unexpected exception: " + ioe.getMessage());
			System.exit(0);
		}
	}
	
	public static Protocol read() throws IOException{
		Protocol p = new Protocol();
		byte[] header = new byte[Protocol.LEN_HEADER];
		is.read(header);
		p.setHeader(header);
		if(p.getBodyLength()!=0){
			byte[] body = new byte[p.getBodyLength()];
			is.read(body);
			p.setBody(body);
		}
		return p;
	}

	public static void send(Protocol p) throws IOException{
		os.write(p.getPacket());
		os.flush();
	}
	
	public static void t_stop() {
		try {
			if (is != null)
				is.close();
			if (os != null)
				os.close();
			if (socket != null)
				socket.close();
		} catch (IOException ioe) {
			System.out.println("Error closing ...");
		}
	}
}
