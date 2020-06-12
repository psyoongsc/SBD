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

//���μ���Ŭ������ ServerSocket�� ���ε��� Ŭ���̾�Ʈ�� Socket ��û �¶�, DataBase����, ����������� �����̴�. 
public class MainServer {
	private ServerSocket server;
	private MainServerThread clients[];
	private int clientCount;

	// ������ - �������� ����
	public MainServer(int port) throws IOException {
		clients = new MainServerThread[50];
		clientCount = 0;
		server = new ServerSocket(port);
	}

	// start�� ȣ�� run , Ŭ���̾�Ʈ ��û�� accept
	public void run() throws Exception {
		while (server != null) {
			// ���Ͽ���
			Socket socket = server.accept();
			// DB����
			Class.forName("com.mysql.jc.jdbc.Driver");
			String url = "jdbc:mysql://localhost:3307/sbd?useUnicode=true&characterEncoding=utf8";
			Connection conn = DriverManager.getConnection(url, "root", "123123");
			System.out.println("DB���� ����");
			// ���ο�������
			addThread(socket, conn);
		}
	}

	// ���ο� ������ ����
	public synchronized void addThread(Socket socket, Connection conn) throws Exception {
		if (clientCount < clients.length) {
			clients[clientCount] = new MainServerThread(socket, conn);
			clients[clientCount].start();
			clientCount++;
		} else{
			System.out.println("Client refused: maximum " + clients.length + " reached.");
		}
	}

	// ������Ǯ�����Լ�1
	public int findClient(int ID) {
		for (int i = 0; i < clientCount; i++)
			if (clients[i].getID() == ID)
				return i;
		return -1;
	}

	// ������Ǯ�����Լ�2
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
