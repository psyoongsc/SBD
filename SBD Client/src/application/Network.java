package application;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class Network {
	public static OutputStream os;
	public static InputStream is;
	public static Socket socket;
	
	Network(){
		System.out.println("Establishing connection. Please wait ...");
		try {
			socket = new Socket("172.20.10.3", 3333);
			System.out.println("Connected: " + socket);
			t_start();
		} catch (UnknownHostException uhe) {
			System.out.println("Host unknown: " + uhe.getMessage());
			System.exit(0);
		} catch (IOException ioe) {
			System.out.println("Unexpected exception: " + ioe.getMessage());
			System.exit(0);
		}
	}
	
	public static void t_start() throws IOException {
		is = socket.getInputStream();
		os = socket.getOutputStream();
		System.out.println("This client binding to server using port: " + socket.getPort());
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
