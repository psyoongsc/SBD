package Main;
import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import Handler.CheckHandler;
import Handler.LoginHandler;
import Handler.RegisterHandler;
import Handler.ViewHandler;
import Model.Protocol;

public class MainServer extends Thread {
	private MainServerThread clients[];
	private ServerSocket server;
	private int clientCount;
	private OutputStream os;
	private Protocol protocol;
	
	public MainServer(int port) {
		try {
			protocol = new Protocol();
			clients = new MainServerThread[50];
			clientCount = 0;
			
			System.out.println("Binding to port " + port + ", please wait ...");
			server = new ServerSocket(port);
			System.out.println("Server started: " + server);
		} catch (IOException ioe) {
			System.out.println("Can't bind to port " + port + " : " + ioe.getMessage());
		}
	}
	
	public void run() {
		try {
			System.out.println("Server is ready for clients ...");
			
			Class.forName("com.mysql.cj.jdbc.Driver");
			String url = "jdbc:mysql://localhost:3306/sbd?characterEncoding=UTF-8&serverTimezone=UTC&useSSL=false";
			Connection conn = DriverManager.getConnection(url, "root", "fjssj258");
			System.out.println("DB연결 성공");
			
			addThread(server.accept(), conn);
		} catch (IOException ioe) {
			System.out.println("Server Error accept: " + ioe.getMessage());
		} catch (ClassNotFoundException cnfe) {
			System.out.println("드라이버 로딩 실패");
		} catch (SQLException sqle) {
			System.out.println("에러: " + sqle);
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
		System.out.println(p.getString());
		
		LoginHandler lh = new LoginHandler(os, conn);
		ViewHandler vh = new ViewHandler(os, conn);
		RegisterHandler rh = new RegisterHandler(os, conn);
		CheckHandler ch = new CheckHandler(os,conn);
		
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
				vh.CODE11(p);
				break;
			case Protocol.T5_CD12_ADDRESSSPECIFIC:
				System.out.println(ID + " : Request View Address Specific");
				vh.CODE12(p);
			}
			
		case Protocol.TYPE11_CHECK_REQ:
			switch(p.getCode()) {
			case Protocol.T11_CD0_USERID:
				System.out.println(ID + " : Request Check UserID");
				ch.CODE0(p);
				break;
			case Protocol.T11_CD1_TEAMNAME:
				System.out.println(ID + " : Request Check TeamName");
				//ch.CODE1(p);
			}
		}
		
	}
	
	public static void main(String[] args) {
		Thread mServer = new MainServer(3434);
		mServer.start();
	}
}
