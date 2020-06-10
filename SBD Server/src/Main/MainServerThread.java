package Main;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.sql.Connection;

import Handler.CheckHandler;
import Handler.DeleteHandler;
import Handler.LoginHandler;
import Handler.RegisterHandler;
import Handler.UpdateHandler;
import Handler.ViewHandler;
import Model.Protocol;

public class MainServerThread extends Thread {

	private int ID;
	private Socket socket;
	private Connection conn;
	private Protocol p;
	private IO io;

	public MainServerThread(Socket socket, Connection conn) throws IOException {
		ID = socket.getPort();
		this.socket = socket;
		this.conn = conn;
		io = new IO(socket);
	}

	@Override
	public void run() {
		try {
			while (true) {
				p = io.read();
				handle(p);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void handle(Protocol p) throws Exception {

		System.out.printf("%d %d %d %d %d %d\n", p.getType(), p.getCode(), p.getBodyLength(), p.getFrag(),
				p.getIsLast(), p.getSeqNum());
		System.out.println(p.getString());

		LoginHandler lh = new LoginHandler(io, conn);
		RegisterHandler rh = new RegisterHandler(io,conn);
		ViewHandler vh = new ViewHandler(io, conn);
		UpdateHandler uh = new UpdateHandler(io,conn);
		DeleteHandler dh = new DeleteHandler(io, conn);
		CheckHandler ch = new CheckHandler(io,conn);

		int packetType = p.getType();
		
		switch (packetType) {
		
		case Protocol.TYPE1_LOGIN_REQ:
			lh.login(p);
			break;
			
		case Protocol.TYPE3_REGISTER_REQ:
			switch (p.getCode()) {
			case Protocol.T3_CD0_USER:
				rh.CODE0(p);
				break;
			case Protocol.T3_CD1_TEAM:
				// rh.CODE1(p);
				break;
			case Protocol.T3_CD2_TEAMNOTICE:
				// rh.CODE2(p);
				break;
			case Protocol.T3_CD3_TEAMRECRUIT:
				// rh.CODE3(p);
				break;
			case Protocol.T3_CD4_TEAMAPPLICATION:
				// rh.CODE4(p);
				break;
			case Protocol.T3_CD5_JOIN:
				// rh.CODE5(p);
				break;
			case Protocol.T3_CD6_MATCH:
				// rh.CODE6(p);
				break;
			case Protocol.T3_CD7_MATCHANSWER:
				// rh.CODE7(p);
				break;
			}
			break;
			
		case Protocol.TYPE5_VIEW_REQ:
			switch (p.getCode()) {
			case Protocol.T5_CD0_USERINFO:
				//vh.CODE0(p);
				break;
			case Protocol.T5_CD1_SCHEDULE:
				//vh.CODE1(p);
				break;
			case Protocol.T5_CD2_TEAM:
				vh.CODE2(p);
				break;
			case Protocol.T5_CD3_TEAMINFO:
				// vh.CODE3(p);
				break;
			case Protocol.T5_CD4_TEAMNOTICE:
				// vh.CODE4(p);
				break;
			case Protocol.T5_CD5_TEAMMEMBER:
				// vh.CODE5(p);
				break;
			case Protocol.T5_CD6_TEAMRECRUIT:
				// vh.CODE6(p);
				break;
			case Protocol.T5_CD7_TEAMAPPLICATION:
				// vh.CODE7(p);
				break;
			case Protocol.T5_CD8_JOIN:
				// vh.CODE8(p);
				break;
			case Protocol.T5_CD9_MATCHAPPLICATION:
				// vh.CODE9(p);
				break;
			case Protocol.T5_CD10_MATCH:
				// vh.CODE10(p);
				break;
			case Protocol.T5_CD11_ADDRESS:
				vh.CODE11(p);
				break;
			case Protocol.T5_CD12_ADDRESSSPECIFIC:
				vh.CODE12(p);
				break;
			case Protocol.T5_CD13_GYM:
				// vh.CODE13(p);
				break;
			case Protocol.T5_CD14_MYRECRUIT:
				// vh.CODE14(p);
				break;
			}
			break;

		case Protocol.TYPE7_UPDATE_REQ:
			switch (p.getCode()) {
			case Protocol.T7_CD0_USERINFO:
				// uh.CODE0(p);
				break;
			case Protocol.T7_CD1_TEAMINFO:
				// uh.CODE1(p);
				break;
			case Protocol.T7_CD2_CAPTAIN:
				// uh.CODE2(p);
				break;
			}
			break;
			
		case Protocol.TYPE9_DELETE_REQ:
			switch (p.getCode()) {
			case Protocol.T9_CD0_USER:
				// dh.CODE0(p);
				break;
			case Protocol.T9_CD1_TEAM:
				// dh.CODE1(p);
				break;
			case Protocol.T9_CD2_TEAMNOTICE:
				// dh.CODE2(p);
				break;
			case Protocol.T9_CD3_TEAMMEMBER:
				// dh.CODE3(p);
				break;
			case Protocol.T9_CD4_TEAMRECRUIT:
				// dh.CODE4(p);
				break;
			}
			break;
			
		case Protocol.TYPE11_CHECK_REQ:
			switch (p.getCode()) {
			case Protocol.T11_CD0_USERID:
				ch.CODE0(p);
				break;
			case Protocol.T11_CD1_TEAMNAME:
				// ch.CODE1(p);
				break;
			}
			break;
		}
	}

	public IO getIo() {
		return io;
	}

	public void setIo(IO io) {
		this.io = io;
	}

	public int getID() {
		return ID;
	}

	public void setID(int iD) {
		ID = iD;
	}

}
