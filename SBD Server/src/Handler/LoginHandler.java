package Handler;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import Main.IO;
import Main.MainServerThread;
import Model.Protocol;

public class LoginHandler {
	IO io;
	Connection conn;

	public LoginHandler(IO io, Connection conn) {
		this.io = io;
		this.conn = conn;
	}

	public void login(Protocol protocol) throws Exception {
		String[] temp = protocol.getString().split("/");
		String id = temp[0];
		String pw = temp[1];

		PreparedStatement pstmt = conn
				.prepareStatement("select EXISTS (select * from user where ID=? and PW=?) as success");

		pstmt.setString(1, id);
		pstmt.setString(2, pw);

		ResultSet rs = pstmt.executeQuery();
		Protocol packet;
		if (rs.next()) {
			if (rs.getString(1).equals("1")) {
				System.out.println("success");
				packet = new Protocol(Protocol.TYPE2_LOGIN_RES, Protocol.T2_CD1_SUCCESS);
			} else {
				System.out.println("fail");
				packet = new Protocol(Protocol.TYPE2_LOGIN_RES, Protocol.T2_CD0_FAIL);
			}
			io.send(packet);
		}else{
			System.out.println("fail");
			packet = new Protocol(Protocol.TYPE2_LOGIN_RES, Protocol.T2_CD0_FAIL);
		}
	}
}
