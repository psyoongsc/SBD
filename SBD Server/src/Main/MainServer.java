package Main;
import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import Handler.LoginHandler;
import Handler.RegisterHandler;
import Handler.ViewHandler;
import Model.Protocol;

public class MainServer implements Runnable {
	private MainServerThread clients[] = new MainServerThread[50];
	private ServerSocket server = null;
	private Thread thread = null;
	private int clientCount = 0;
	
	private OutputStream os = null;
	private Protocol protocol = null;
	
	public MainServer(int port) {
		try {
			protocol = new Protocol();
			
			System.out.println("Binding to port " + port + ", please wait ...");
			server = new ServerSocket(port);
			System.out.println("Server started: " + server);
			start();
		} catch (IOException ioe) {
			System.out.println("Can't bind to port " + port + " : " + ioe.getMessage());
		}
	}
	
	public void run() {
		while(thread != null) {
			try {
				System.out.println("Server is ready for clients ...");
				
				Class.forName("com.mysql.jdbc.Driver");
				String url = "jdbc:mysql://127.0.0.1:3307/sbd?useUnicode=true&characterEncoding=utf8";
				Connection conn = DriverManager.getConnection(url, "root", "123123");
				System.out.println("DB연결 성공");
				
				addThread(server.accept(), conn);
			} catch (IOException ioe) {
				System.out.println("Server Error accept: " + ioe.getMessage());
				stop();
			} catch (ClassNotFoundException cnfe) {
				System.out.println("드라이버 로딩 실패");
			} catch (SQLException sqle) {
				System.out.println("에러: " + sqle);
			}
		}
	}
	
	public void start() {
		if (thread == null) {
			thread = new Thread(this);
			thread.start(); // run() 함수를 암묵적으로 호출
		}
	}
	
	public int findClient(int ID) {
		for (int i=0; i<clientCount; i++)
			if(clients[i].getID() == ID)
				return i;
		return -1;
	}
	
	public synchronized void remove(int ID) {
		int pos = findClient(ID);
		if(pos >= 0) {
			MainServerThread toTerminate = clients[pos];
			System.out.println("Removing client thread " + ID + " at " + pos);
			if(pos < clientCount - 1)
				for(int i=pos+1; i<clientCount; i++)
					clients[i-1] = clients[i];
			clientCount--;
			toTerminate.close();
			toTerminate.stop();
		}
	}
	
	public void stop() {
		if (thread != null) {
			thread.stop();
			thread = null;
		}
	}
	
	public synchronized void addThread(Socket socket, Connection conn) {
		if (clientCount < clients.length) {
			System.out.println("Client accepted: " + socket);
			clients[clientCount] = new MainServerThread(this, socket, conn);
			try {
				clients[clientCount].open();
				clients[clientCount].start();
				clientCount++;
			} catch (IOException ioe) {
				System.out.println("Error opening thread: " + ioe.getMessage());
			}
		} else
			System.out.println("Client refused: maximum " + clients.length + " reached.");
	}
	
	public synchronized void handle(OutputStream os, int ID, Protocol p, Connection conn) throws Exception {
		int packetType = p.getType();
		
		System.out.printf("%d %d %d %d %d %d\n", p.getType(), p.getCode(), p.getBodyLength(), p.getFrag(), p.getIsLast(), p.getSeqNum());
		System.out.println(new String(p.getBody()).trim());
		
		LoginHandler lh = new LoginHandler(os, conn);
		ViewHandler vh = new ViewHandler(os, conn);
		RegisterHandler rh = new RegisterHandler(os, conn);
		
		switch(packetType) {
		case Protocol.TYPE1_LOGIN_REQ:
			System.out.println(ID + " : Request Login");
			lh.login(p);
			break;
		case Protocol.TYPE3_REGISTER_REQ:
			switch(p.getCode()) {
			case Protocol.T3_CD0_USER:
				System.out.println(ID + " : Request Register User");
				rh.CODE0(p);
				break;
			}
		case Protocol.TYPE5_VIEW_REQ:
			switch(p.getCode()) {
			case Protocol.T5_CD11_ADDRESS:
				System.out.println(ID + " : Request View Address");
				vh.CODE10(p);
				break;
			case Protocol.T5_CD12_ADDRESSSPECIFIC:
				System.out.println(ID + " : Request View Address Specific");
				vh.CODE11(p);
			}
		}
	}
	
	public static void main(String[] args) {
		MainServer server = new MainServer(3333);
	}
}
