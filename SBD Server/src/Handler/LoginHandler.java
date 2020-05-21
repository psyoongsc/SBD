package Handler;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import Model.Protocol;

public class LoginHandler {
	InputStream input;
	OutputStream output;
	Connection conn;
	
	public LoginHandler(OutputStream os, Connection conn) {
		output = os;
		this.conn = conn;
	}
	
	public void login(Protocol protocol) throws IOException {
		String[] temp = (new String(protocol.getBody())).split("/");
		String id = temp[0].trim();
		String pw = temp[1].trim();
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			pstmt = conn.prepareStatement("select EXISTS (select * from user where ID=? and PW=?) as success");
			
			pstmt.setString(1, id);
			pstmt.setString(2, pw);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				if(rs.getString(1).equals("1")) {
					System.out.println("success");
					Protocol packet = new Protocol(Protocol.TYPE2_LOGIN_RES, Protocol.T2_CD1_SUCCESS);
					output.write(packet.getPacket());
				} else {
					System.out.println("fail");
					Protocol packet = new Protocol(Protocol.TYPE2_LOGIN_RES, Protocol.T2_CD0_FAIL);
					output.write(packet.getPacket());
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		output.flush();
	}
}
