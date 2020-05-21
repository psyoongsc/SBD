package Handler;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import Model.Protocol;

public class RegisterHandler {
	InputStream input;
	OutputStream output;
	Connection conn;
	
	public RegisterHandler(OutputStream os, Connection conn) {
		output = os;
		this.conn = conn;
	}
	
	public void CODE0(Protocol protocol) throws SQLException, IOException {
		PreparedStatement pstmt = null;
		int rowsAffected;
		
		String[] temp = (new String(protocol.getBody())).trim().split("/");
		
		String sql = "insert into user(ID, PW, FullName, Sex, Birthday, Phone, Addr1, Addr2) values (?,?,?,?,?,?,?,?)";
		pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, temp[0]);
		pstmt.setString(2, temp[1]);
		pstmt.setString(3, temp[2]);
		pstmt.setString(4, temp[3]);
		pstmt.setDate(5, Date.valueOf(temp[4]));
		pstmt.setString(6, temp[5]);
		pstmt.setString(7, temp[6]);
		pstmt.setString(8, temp[7]);

		rowsAffected = pstmt.executeUpdate();
		
		Protocol packet = null;
		if(rowsAffected == 1) {
			packet = new Protocol(Protocol.TYPE4_REGISTER_RES, Protocol.T4_CD1_SUCCESS);
		} else {
			packet = new Protocol(Protocol.TYPE4_REGISTER_RES, Protocol.T4_CD0_FAIL);
		}
		
		output.write(packet.getPacket());
		output.flush();
	}
}
