package Handler;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import Main.IO;
import Model.Protocol;

public class UpdateHandler {
	IO io;
	Connection conn;
	
	public UpdateHandler(IO io, Connection conn) {
		this.io = io;
		this.conn = conn;
	}
	
//	public void CODE0(Protocol protocol) throws IOException {
//		PreparedStatement pstmt = null;
//		String[] bodyToken = null;
//		int affectedRow = 0;
//		Protocol packet = new Protocol(Protocol.UNDEFINED);
//		
//		String sql = "UPDATE user\r\n" + 
//				"SET\r\n" + 
//				"`PW` = ?,\r\n" + 
//				"`Phone` = ?,\r\n" + 
//				"`Addr1` = ?,\r\n" + 
//				"`Addr2` = ?\r\n" + 
//				"WHERE `ID` = ?";
//		
//		try {
//			bodyToken = (new String(protocol.getBody())).trim().split("/");
//			pstmt = conn.prepareStatement(sql);
//			pstmt.setString(1, bodyToken[0]);
//			pstmt.setString(2, bodyToken[1]);
//			pstmt.setString(3, bodyToken[2]);
//			pstmt.setString(4, bodyToken[3]);
//			pstmt.setString(5, bodyToken[4]);
//			
//			affectedRow = pstmt.executeUpdate();
//
//		} catch(SQLException sqle) {
//			packet = new Protocol(Protocol.TYPE8_UPDATE_RES, Protocol.T8_CD0_FAIL);
//			sqle.printStackTrace();
//			output.write(packet.getPacket());
//			output.flush(); 
//		}
//		
//		if(affectedRow == 1) {
//			packet = new Protocol(Protocol.TYPE8_UPDATE_RES, Protocol.T8_CD1_SUCCESS);
//		} else {
//			packet = new Protocol(Protocol.TYPE8_UPDATE_RES, Protocol.T8_CD0_FAIL);
//		}
//		output.write(packet.getPacket());
//		output.flush();
//	}
//	
//	public void CODE1(Protocol protocol) throws IOException {
//		PreparedStatement pstmt = null;
//		String[] bodyToken = null;
//		int affectedRow = 0;
//		Protocol packet = new Protocol(Protocol.UNDEFINED);
//		
//		String sql = "UPDATE `sbd`.`team`\r\n" + 
//				"SET\r\n" + 
//				"`Gym_ID` = ?,\r\n" + 
//				"`Max_Age` = ?,\r\n" + 
//				"`Min_Age` = ?,\r\n" + 
//				"`Addr1` = ?,\r\n" + 
//				"`Addr2` = ?,\r\n" + 
//				"`Team_Intro` = ?\r\n" + 
//				"WHERE `ID` = ?";
//		
//		try {
//			bodyToken = (new String(protocol.getBody())).trim().split("/");
//			pstmt = conn.prepareStatement(sql);
//			pstmt.setInt(1, Integer.parseInt(bodyToken[0]));
//			pstmt.setInt(2, Integer.parseInt(bodyToken[1]));
//			pstmt.setInt(3, Integer.parseInt(bodyToken[2]));
//			pstmt.setString(4, bodyToken[3]);
//			pstmt.setString(5, bodyToken[4]);
//			pstmt.setString(6, bodyToken[5]);
//			pstmt.setString(7, bodyToken[6]);
//			
//			affectedRow = pstmt.executeUpdate();
//
//		} catch(SQLException sqle) {
//			packet = new Protocol(Protocol.TYPE8_UPDATE_RES, Protocol.T8_CD0_FAIL);
//			sqle.printStackTrace();
//			output.write(packet.getPacket());
//			output.flush(); 
//		}
//		
//		if(affectedRow == 1) {
//			packet = new Protocol(Protocol.TYPE8_UPDATE_RES, Protocol.T8_CD1_SUCCESS);
//		} else {
//			packet = new Protocol(Protocol.TYPE8_UPDATE_RES, Protocol.T8_CD0_FAIL);
//		}
//		output.write(packet.getPacket());
//		output.flush();
//	}
//	
//	public void CODE2(Protocol protocol) throws IOException {
//		PreparedStatement pstmt = null;
//		String[] bodyToken = null;
//		int affectedRow = 0;
//		Protocol packet = new Protocol(Protocol.UNDEFINED);
//		
//		String sql = "UPDATE `sbd`.`team`\r\n" + 
//				"SET\r\n" + 
//				"`User_Captain` = ?\r\n" + 
//				"WHERE `ID` = ?";
//		
//		try {
//			bodyToken = (new String(protocol.getBody())).trim().split("/");
//			pstmt = conn.prepareStatement(sql);
//			pstmt.setString(1, bodyToken[0]);
//			pstmt.setInt(2, Integer.parseInt(bodyToken[1]));
//			
//			affectedRow = pstmt.executeUpdate();
//
//		} catch(SQLException sqle) {
//			packet = new Protocol(Protocol.TYPE8_UPDATE_RES, Protocol.T8_CD0_FAIL);
//			sqle.printStackTrace();
//			output.write(packet.getPacket());
//			output.flush(); 
//		}
//		
//		if(affectedRow == 1) {
//			packet = new Protocol(Protocol.TYPE8_UPDATE_RES, Protocol.T8_CD1_SUCCESS);
//		} else {
//			packet = new Protocol(Protocol.TYPE8_UPDATE_RES, Protocol.T8_CD0_FAIL);
//		}
//		output.write(packet.getPacket());
//		output.flush();
//	}
}
