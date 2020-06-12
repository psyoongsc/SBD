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
//import Handler.RegisterHandler;
//import Handler.ViewHandler;
import Model.Protocol;

//메인서버클래스는 ServerSocket의 바인딩과 클라이언트의 Socket 요청 승락, DataBase연결, 쓰레드생성이 목적이다. 
public class MainServer {
	private ServerSocket server;
	private MainServerThread clients[];
	private int clientCount;

	// 생성자 - 서버소켓 생성
	public MainServer(int port) throws IOException {
		clients = new MainServerThread[50];
		clientCount = 0;
		server = new ServerSocket(port);
	}

	// start시 호출 run , 클라이언트 요청시 accept
	public void run() throws Exception {
		while (server != null) {
			// 소켓연결
			Socket socket = server.accept();
			// DB연결
			Class.forName("com.mysql.jc.jdbc.Driver");
			String url = "jdbc:mysql://localhost:3307/sbd?useUnicode=true&characterEncoding=utf8";
			Connection conn = DriverManager.getConnection(url, "root", "123123");
			System.out.println("DB연결 성공");
			// 새로운쓰레드생성
			addThread(socket, conn);
		}
	}

	// 새로운 쓰레드 생성
	public synchronized void addThread(Socket socket, Connection conn) throws Exception {
		if (clientCount < clients.length) {
			clients[clientCount] = new MainServerThread(socket, conn);
			clients[clientCount].start();
			clientCount++;
		} else{
			System.out.println("Client refused: maximum " + clients.length + " reached.");
		}
	}

	// 스레드풀관리함수1
	public int findClient(int ID) {
		for (int i = 0; i < clientCount; i++)
			if (clients[i].getID() == ID)
				return i;
		return -1;
	}

	// 스레드풀관리함수2
	public synchronized void remove(int ID) throws IOException {
		int pos = findClient(ID);
		if (pos >= 0) {
			MainServerThread toTerminate = clients[pos];
			System.out.println("Removing client thread " + ID + " at " + pos);
			if (pos < clientCount - 1)
				for (int i = pos + 1; i < clientCount; i++)
					clients[i - 1] = clients[i];
			clientCount--;
			toTerminate.getIo().close();
			toTerminate.stop();
		}
	}

	public static void main(String[] args) throws Exception {
		MainServer mServer = new MainServer(3434);
		mServer.run();
	}
}
