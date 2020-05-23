package Handler;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Calendar;

import Model.Protocol;

public class RegisterHandler {
	InputStream input;
	OutputStream output;
	Connection conn;
	
	public RegisterHandler(OutputStream os, Connection conn) {
		output = os;
		this.conn = conn;
	}
	
	public void CODE0(Protocol protocol) throws IOException {
		PreparedStatement pstmt = null;
		int rowsAffected = 0;
		Protocol packet = null;
		
		String[] temp = (new String(protocol.getBody())).trim().split("/");
		
		String sql = "insert into user(ID, PW, FullName, Sex, Birthday, Phone, Addr1, Addr2) "
						+ "values (?,?,?,?,?,?,?,?)";
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, temp[0]);				// ID
			pstmt.setString(2, temp[1]);				// PW
			pstmt.setString(3, temp[2]);				// FullName
			pstmt.setString(4, temp[3]);				// Sex
			pstmt.setDate(5, Date.valueOf(temp[4]));	// Birthday (yyyy-MM-dd)
			pstmt.setString(6, temp[5]);				// Phone
			pstmt.setString(7, temp[6]);				// Addr1
			pstmt.setString(8, temp[7]);				// Addr2
	
			rowsAffected = pstmt.executeUpdate();
		} catch(SQLException sqle) {
			packet = new Protocol(Protocol.TYPE4_REGISTER_RES, Protocol.T4_CD0_FAIL);
			sqle.printStackTrace();
			output.write(packet.getPacket());
			output.flush();
		}
		
		if(rowsAffected == 1) {
			packet = new Protocol(Protocol.TYPE4_REGISTER_RES, Protocol.T4_CD1_SUCCESS);
		} else {
			packet = new Protocol(Protocol.TYPE4_REGISTER_RES, Protocol.T4_CD0_FAIL);
		}
		
		output.write(packet.getPacket());
		output.flush();
	}
	
	public void CODE1(Protocol protocol) throws IOException {
		PreparedStatement pstmt = null;
		int rowsAffected = 0;
		Protocol packet = null;
		
		String[] temp = (new String(protocol.getBody())).trim().split("/");
		
		String sql = "insert into team(Team_Name, Gym_ID, User_Captain, Member_Num, Max_Age, Min_Age, Addr1, Addr2, Team_Sex, Team_Intro) "
						+ "values(?,?,?,?,?,?,?,?,?,?)";
		
		try {
			pstmt = conn.prepareStatement(sql);	
			pstmt.setString(1, temp[0]);					// Team_Name
			pstmt.setInt(2, Integer.parseInt(temp[1]));		// Gym_ID
			pstmt.setString(3, temp[2]);					// User_Captain
			pstmt.setInt(4, 1); 							// Mumber_Num (ÆÀ ¸â¹ö´Â ÆÀÀå ÇÑ¸í)
			pstmt.setInt(5, Integer.parseInt(temp[3]));		// Max_Age
			pstmt.setInt(6, Integer.parseInt(temp[4]));		// Min_Age
			pstmt.setString(7, temp[5]);					// Addr1
			pstmt.setString(8, temp[6]);					// Addr2
			pstmt.setString(9, temp[7]);					// Team_Sex
			pstmt.setString(10, temp[8]);					// Team_Intro
			
			rowsAffected = pstmt.executeUpdate();
		} catch(SQLException sqle) {
			packet = new Protocol(Protocol.TYPE4_REGISTER_RES, Protocol.T4_CD0_FAIL);
			sqle.printStackTrace();
			output.write(packet.getPacket());
			output.flush(); 
		}
		
		if(rowsAffected == 1) {
			packet = new Protocol(Protocol.TYPE4_REGISTER_RES, Protocol.T4_CD1_SUCCESS);
		} else {
			packet = new Protocol(Protocol.TYPE4_REGISTER_RES, Protocol.T4_CD0_FAIL);
		}
		
		output.write(packet.getPacket());
		output.flush();
	}
	
	public void CODE2(Protocol protocol) throws IOException {
		PreparedStatement pstmt = null;
		int rowsAffected = 0;
		Protocol packet = null;
		
		String[] temp = (new String(protocol.getBody())).trim().split("/");
		
		String sql = "insert into team_note(Team_ID, datetime, User_Writer, Note) "
						+ "values(?, ?, ?, ?)";
		
		try {
			pstmt = conn.prepareStatement(sql);	
			pstmt.setInt(1, Integer.parseInt(temp[0]));
			pstmt.setTimestamp(2, new Timestamp(Calendar.getInstance().getTimeInMillis()));
			pstmt.setString(3, temp[1]);
			pstmt.setString(4, temp[2]);
			
			rowsAffected = pstmt.executeUpdate();
		} catch(SQLException sqle) {
			packet = new Protocol(Protocol.TYPE4_REGISTER_RES, Protocol.T4_CD0_FAIL);
			sqle.printStackTrace();
			output.write(packet.getPacket());
			output.flush(); 
		}
		
		if(rowsAffected == 1) {
			packet = new Protocol(Protocol.TYPE4_REGISTER_RES, Protocol.T4_CD1_SUCCESS);
		} else {
			packet = new Protocol(Protocol.TYPE4_REGISTER_RES, Protocol.T4_CD0_FAIL);
		}
		
		output.write(packet.getPacket());
		output.flush();
	}
	
	public void CODE3(Protocol protocol) throws IOException {
		PreparedStatement pstmt = null;
		int rowsAffected = 0;
		Protocol packet = null;
		
		String[] temp = (new String(protocol.getBody())).trim().split("/");
		
		String sql = "insert into recruit(Team_ID, Note, Date_Req, Date_Post, Date_End) "
						+ "values(?, ?, ?, ?, ?)";
		
		try {
			pstmt = conn.prepareStatement(sql);	
			pstmt.setInt(1, Integer.parseInt(temp[0]));
			pstmt.setString(2, temp[1]);
			pstmt.setDate(3, new Date(new java.util.Date().getDate()));
			pstmt.setDate(4, Date.valueOf(temp[2]));
			pstmt.setDate(5, Date.valueOf(temp[3]));
			
			rowsAffected = pstmt.executeUpdate();
		} catch(SQLException sqle) {
			packet = new Protocol(Protocol.TYPE4_REGISTER_RES, Protocol.T4_CD0_FAIL);
			sqle.printStackTrace();
			output.write(packet.getPacket());
			output.flush(); 
		}
		
		if(rowsAffected == 1) {
			packet = new Protocol(Protocol.TYPE4_REGISTER_RES, Protocol.T4_CD1_SUCCESS);
		} else {
			packet = new Protocol(Protocol.TYPE4_REGISTER_RES, Protocol.T4_CD0_FAIL);
		}
		
		output.write(packet.getPacket());
		output.flush();
	}
	
	public void CODE4(Protocol protocol) throws IOException {
		PreparedStatement pstmt = null;
		int rowsAffected = 0;
		Protocol packet = null;
		
		String[] temp = (new String(protocol.getBody())).trim().split("/");
		
		String sql = "insert into application(Recruit_ID, User_ID, Note, Status, datetime) "
						+ "values(?,?,?,?)";
		
		try {
			pstmt = conn.prepareStatement(sql);	
			pstmt.setInt(1, Integer.parseInt(temp[0]));
			pstmt.setString(2, temp[1]);
			pstmt.setString(3, temp[2]);
			pstmt.setInt(4, 0);
			pstmt.setTimestamp(5, new Timestamp(Calendar.getInstance().getTimeInMillis()));
			
			rowsAffected = pstmt.executeUpdate();
		} catch(SQLException sqle) {
			packet = new Protocol(Protocol.TYPE4_REGISTER_RES, Protocol.T4_CD0_FAIL);
			sqle.printStackTrace();
			output.write(packet.getPacket());
			output.flush(); 
		}
		
		if(rowsAffected == 1) {
			packet = new Protocol(Protocol.TYPE4_REGISTER_RES, Protocol.T4_CD1_SUCCESS);
		} else {
			packet = new Protocol(Protocol.TYPE4_REGISTER_RES, Protocol.T4_CD0_FAIL);
		}
		
		output.write(packet.getPacket());
		output.flush();
	}
	
	public void CODE5(Protocol protocol) throws IOException {
		PreparedStatement pstmt = null;
		int rowsAffected = 0;
		Protocol packet = null;
		
		String[] temp = (new String(protocol.getBody())).trim().split("/");
		
		String sql = "update application set Status=? where Recruit_ID=? and User_ID=?";
		
		try {
			pstmt = conn.prepareStatement(sql);	
			pstmt.setInt(1, Integer.parseInt(temp[2]));
			pstmt.setInt(2, Integer.parseInt(temp[0]));
			pstmt.setString(3, temp[1]);
			
			rowsAffected = pstmt.executeUpdate();
		} catch(SQLException sqle) {
			packet = new Protocol(Protocol.TYPE4_REGISTER_RES, Protocol.T4_CD0_FAIL);
			sqle.printStackTrace();
			output.write(packet.getPacket());
			output.flush(); 
		}
		
		if(rowsAffected == 1) {
			packet = new Protocol(Protocol.TYPE4_REGISTER_RES, Protocol.T4_CD1_SUCCESS);
		} else {
			packet = new Protocol(Protocol.TYPE4_REGISTER_RES, Protocol.T4_CD0_FAIL);
		}
		
		output.write(packet.getPacket());
		output.flush();
	}
	
	public void CODE6(Protocol protocol) throws IOException {
		PreparedStatement pstmt = null;
		int rowsAffected = 0;
		Protocol packet = null;
		
		String[] temp = (new String(protocol.getBody())).trim().split("/");
		
		String sql = "insert into match_application(Team_ID, AvailableStart, AvailableEnd, Status) "
						+ "values(?,?,?,?)";
		
		try {
			pstmt = conn.prepareStatement(sql);	
			pstmt.setInt(1, Integer.parseInt(temp[0]));
			pstmt.setTimestamp(2, Timestamp.valueOf(temp[1]));
			pstmt.setTimestamp(3, Timestamp.valueOf(temp[2]));
			pstmt.setInt(4, 1);
			
			rowsAffected = pstmt.executeUpdate();
		} catch(SQLException sqle) {
			packet = new Protocol(Protocol.TYPE4_REGISTER_RES, Protocol.T4_CD0_FAIL);
			sqle.printStackTrace();
			output.write(packet.getPacket());
			output.flush(); 
		}
		
		if(rowsAffected == 1) {
			packet = new Protocol(Protocol.TYPE4_REGISTER_RES, Protocol.T4_CD1_SUCCESS);
		} else {
			packet = new Protocol(Protocol.TYPE4_REGISTER_RES, Protocol.T4_CD0_FAIL);
		}
		
		output.write(packet.getPacket());
		output.flush();
	}
	
	public void CODE7(Protocol protocol) throws IOException {
		PreparedStatement pstmt = null;
		int rowsAffected = 0;
		Protocol packet = null;
		
		String[] temp = (new String(protocol.getBody())).trim().split("/");
		
		String sql = "insert into dialogue(Push_Type, Match_ID, Sender, Reciever, Note, datetime) "
						+ "values(?,?,?,?,?,?)";
		
		try {
			pstmt = conn.prepareStatement(sql);	
			pstmt.setInt(1, Integer.parseInt(temp[0]));
			pstmt.setInt(2, Integer.parseInt(temp[1]));
			pstmt.setString(3, temp[2]);
			pstmt.setString(4, temp[3]);
			pstmt.setString(5, temp[4]);
			pstmt.setTimestamp(6, new Timestamp(Calendar.getInstance().getTimeInMillis()));
			
			rowsAffected = pstmt.executeUpdate();
		} catch(SQLException sqle) {
			packet = new Protocol(Protocol.TYPE4_REGISTER_RES, Protocol.T4_CD0_FAIL);
			sqle.printStackTrace();
			output.write(packet.getPacket());
			output.flush(); 
		}
		
		if(rowsAffected == 1) {
			packet = new Protocol(Protocol.TYPE4_REGISTER_RES, Protocol.T4_CD1_SUCCESS);
		} else {
			packet = new Protocol(Protocol.TYPE4_REGISTER_RES, Protocol.T4_CD0_FAIL);
		}
		
		output.write(packet.getPacket());
		output.flush();
	}
}
