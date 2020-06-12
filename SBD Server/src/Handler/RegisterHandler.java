package Handler;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.Calendar;

import Main.IO;
import Main.MainServerThread;
import Model.Protocol;

public class RegisterHandler {
	IO io;
	Connection conn;

	public RegisterHandler(IO io, Connection conn) {
		this.io = io;
		this.conn = conn;
	}

	public void CODE0(Protocol protocol) throws Exception {
		PreparedStatement pstmt = null;
		int rowsAffected = 0;
		Protocol packet = null;

		String[] temp = protocol.getString().split("/");

		String sql = "insert into user(ID, PW, FullName, Sex, Birthday, Phone, Addr1, Addr2) "
				+ "values (?,?,?,?,?,?,?,?)";

		pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, temp[0]); // ID
		pstmt.setString(2, temp[1]); // PW
		pstmt.setString(3, temp[2]); // FullName
		pstmt.setString(4, temp[3]); // Sex
		LocalDate dt = LocalDate.parse(temp[4]);
		pstmt.setDate(5, Date.valueOf(dt)); // Birthday (yyyy-MM-dd)
		pstmt.setString(6, temp[5]); // Phone
		pstmt.setString(7, temp[6]); // Addr1
		pstmt.setString(8, temp[7]); // Addr2

		rowsAffected = pstmt.executeUpdate();

		if (rowsAffected == 1) {
			packet = new Protocol(Protocol.TYPE4_REGISTER_RES, Protocol.T4_CD1_SUCCESS);
		} else {
			packet = new Protocol(Protocol.TYPE4_REGISTER_RES, Protocol.T4_CD0_FAIL);
		}

		io.send(packet);
	}

	public void CODE1(Protocol protocol) throws Exception {
		Protocol packet = null;
		String[] temp = protocol.getString().split("/");
		
		//GymID
		String sql1 = "select ID from sbd.gym where Gym_Name= '"+temp[1]+"'";
		Statement stmt1 = conn.createStatement();
		ResultSet rs = stmt1.executeQuery(sql1);
		int gid=0;
		if(rs.next()){
			gid = rs.getInt(1);
		}else{
			packet = new Protocol(Protocol.TYPE4_REGISTER_RES, Protocol.T4_CD0_FAIL);
		}
		
		//InsertTeam
		String sql2 = "INSERT INTO `sbd`.`team` (`Team_Name`, `Gym_ID`, `User_Captain`, `Member_Num`, `Max_Age`, `Min_Age`, `Addr1`, `Team_Sex`, `Team_Intro`) "
				+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?);";
		
		PreparedStatement stmt2 = conn.prepareStatement(sql2);
		stmt2.setString(1, temp[0]); // Team_Name
		stmt2.setInt(2, gid); // Gym_ID
		stmt2.setString(3, temp[2]); // User_Captain
		stmt2.setInt(4, 1); // Member_Num
		stmt2.setInt(5, Integer.parseInt(temp[4])); // Max_Age
		stmt2.setInt(6, Integer.parseInt(temp[3])); // Min_Age
		stmt2.setString(7, temp[5]); // Addr1
		stmt2.setString(8, temp[6]); // Team_Sex
		stmt2.setString(9, temp[7]); // Team_Intro
		int rs2 = stmt2.executeUpdate();
		if (rs2 != 1) {
			packet = new Protocol(Protocol.TYPE4_REGISTER_RES, Protocol.T4_CD0_FAIL);
		}
		
		//TeamID
		String sql3 = "SELECT ID FROM sbd.team where Team_Name='"+temp[0]+"'";
		Statement stmt3 = conn.createStatement();
		ResultSet rs3 = stmt3.executeQuery(sql3);
		int tid=0;
		if(rs3.next()){
			tid = rs3.getInt(1);
		}else{
			packet = new Protocol(Protocol.TYPE4_REGISTER_RES, Protocol.T4_CD0_FAIL);
		}
		
		//insert
		String sql4 = "INSERT INTO `sbd`.`team_member` (`Team_ID`, `User_ID`) VALUES ('"+tid+"', '"+temp[2]+"')";
		Statement stmt4 = conn.createStatement();
		int rs4 = stmt4.executeUpdate(sql4);
		if(rs4==1){
			packet = new Protocol(Protocol.TYPE4_REGISTER_RES, Protocol.T4_CD1_SUCCESS);
		}else{
			packet = new Protocol(Protocol.TYPE4_REGISTER_RES, Protocol.T4_CD0_FAIL);
		}

		io.send(packet);
	}

	// public void CODE2(Protocol protocol) throws IOException {
	// PreparedStatement pstmt = null;
	// int rowsAffected = 0;
	// Protocol packet = null;
	//
	// String[] temp = (new String(protocol.getBody())).trim().split("/");
	//
	// String sql = "insert into team_note(Team_ID, datetime, User_Writer, Note)
	// "
	// + "values(?, ?, ?, ?)";
	//
	// try {
	// pstmt = conn.prepareStatement(sql);
	// pstmt.setInt(1, Integer.parseInt(temp[0]));
	// pstmt.setTimestamp(2, new
	// Timestamp(Calendar.getInstance().getTimeInMillis()));
	// pstmt.setString(3, temp[1]);
	// pstmt.setString(4, temp[2]);
	//
	// rowsAffected = pstmt.executeUpdate();
	// } catch(SQLException sqle) {
	// packet = new Protocol(Protocol.TYPE4_REGISTER_RES, Protocol.T4_CD0_FAIL);
	// sqle.printStackTrace();
	// output.write(packet.getPacket());
	// output.flush();
	// }
	//
	// if(rowsAffected == 1) {
	// packet = new Protocol(Protocol.TYPE4_REGISTER_RES,
	// Protocol.T4_CD1_SUCCESS);
	// } else {
	// packet = new Protocol(Protocol.TYPE4_REGISTER_RES, Protocol.T4_CD0_FAIL);
	// }
	//
	// output.write(packet.getPacket());
	// output.flush();
	// }
	//
	// public void CODE3(Protocol protocol) throws IOException {
	// PreparedStatement pstmt = null;
	// int rowsAffected = 0;
	// Protocol packet = null;
	//
	// String[] temp = (new String(protocol.getBody())).trim().split("/");
	//
	// String sql = "insert into recruit(Team_ID, Note, Date_Req, Due_Date) " //
	// sql 변경되어야함
	// + "values(?, ?, ?, ?, ?)";
	//
	// try {
	// pstmt = conn.prepareStatement(sql);
	// pstmt.setInt(1, Integer.parseInt(temp[0]));
	// pstmt.setString(2, temp[1]);
	// pstmt.setDate(3, new Date(new java.util.Date().getDate()));
	// pstmt.setInt(4, Integer.parseInt(temp[2]));
	//
	// rowsAffected = pstmt.executeUpdate();
	// } catch(SQLException sqle) {
	// packet = new Protocol(Protocol.TYPE4_REGISTER_RES, Protocol.T4_CD0_FAIL);
	// sqle.printStackTrace();
	// output.write(packet.getPacket());
	// output.flush();
	// }
	//
	// if(rowsAffected == 1) {
	// packet = new Protocol(Protocol.TYPE4_REGISTER_RES,
	// Protocol.T4_CD1_SUCCESS);
	// } else {
	// packet = new Protocol(Protocol.TYPE4_REGISTER_RES, Protocol.T4_CD0_FAIL);
	// }
	//
	// output.write(packet.getPacket());
	// output.flush();
	// }
	//
	// public void CODE4(Protocol protocol) throws IOException {
	// PreparedStatement pstmt = null;
	// int rowsAffected = 0;
	// Protocol packet = null;
	//
	// String[] temp = (new String(protocol.getBody())).trim().split("/");
	//
	// String sql = "insert into application(Recruit_ID, User_ID, Note, Status,
	// datetime) "
	// + "values(?,?,?,?)";
	//
	// try {
	// pstmt = conn.prepareStatement(sql);
	// pstmt.setInt(1, Integer.parseInt(temp[0]));
	// pstmt.setString(2, temp[1]);
	// pstmt.setString(3, temp[2]);
	// pstmt.setInt(4, 0);
	// pstmt.setTimestamp(5, new
	// Timestamp(Calendar.getInstance().getTimeInMillis()));
	//
	// rowsAffected = pstmt.executeUpdate();
	// } catch(SQLException sqle) {
	// packet = new Protocol(Protocol.TYPE4_REGISTER_RES, Protocol.T4_CD0_FAIL);
	// sqle.printStackTrace();
	// output.write(packet.getPacket());
	// output.flush();
	// }
	//
	// if(rowsAffected == 1) {
	// packet = new Protocol(Protocol.TYPE4_REGISTER_RES,
	// Protocol.T4_CD1_SUCCESS);
	// } else {
	// packet = new Protocol(Protocol.TYPE4_REGISTER_RES, Protocol.T4_CD0_FAIL);
	// }
	//
	// output.write(packet.getPacket());
	// output.flush();
	// }
	//
	// public void CODE5(Protocol protocol) throws IOException {
	// PreparedStatement pstmt = null;
	// int rowsAffected = 0;
	// Protocol packet = null;
	//
	// String[] temp = (new String(protocol.getBody())).trim().split("/");
	//
	// String sql = "update application set Status=? where Recruit_ID=? and
	// User_ID=?";
	//
	// try {
	// pstmt = conn.prepareStatement(sql);
	// pstmt.setInt(1, Integer.parseInt(temp[2]));
	// pstmt.setInt(2, Integer.parseInt(temp[0]));
	// pstmt.setString(3, temp[1]);
	//
	// rowsAffected = pstmt.executeUpdate();
	// } catch(SQLException sqle) {
	// packet = new Protocol(Protocol.TYPE4_REGISTER_RES, Protocol.T4_CD0_FAIL);
	// sqle.printStackTrace();
	// output.write(packet.getPacket());
	// output.flush();
	// }
	//
	// if(rowsAffected == 1) {
	// packet = new Protocol(Protocol.TYPE4_REGISTER_RES,
	// Protocol.T4_CD1_SUCCESS);
	// } else {
	// packet = new Protocol(Protocol.TYPE4_REGISTER_RES, Protocol.T4_CD0_FAIL);
	// }
	//
	// output.write(packet.getPacket());
	// output.flush();
	// }
	//
	// public void CODE6(Protocol protocol) throws IOException {
	// PreparedStatement pstmt = null;
	// int rowsAffected = 0;
	// Protocol packet = null;
	//
	// String[] temp = (new String(protocol.getBody())).trim().split("/");
	//
	// String sql = "insert into match_application(Team_ID, AvailableStart,
	// AvailableEnd, Match_Gym, Status) "
	// + "values(?,?,?,?)";
	//
	// try {
	// pstmt = conn.prepareStatement(sql);
	// pstmt.setInt(1, Integer.parseInt(temp[0]));
	// pstmt.setTimestamp(2, Timestamp.valueOf(temp[1]));
	// pstmt.setTimestamp(3, Timestamp.valueOf(temp[2]));
	// pstmt.setInt(4, Integer.parseInt(temp[3]));
	// pstmt.setInt(5, 1);
	//
	// rowsAffected = pstmt.executeUpdate();
	// } catch(SQLException sqle) {
	// packet = new Protocol(Protocol.TYPE4_REGISTER_RES, Protocol.T4_CD0_FAIL);
	// sqle.printStackTrace();
	// output.write(packet.getPacket());
	// output.flush();
	// }
	//
	// if(rowsAffected == 1) {
	// packet = new Protocol(Protocol.TYPE4_REGISTER_RES,
	// Protocol.T4_CD1_SUCCESS);
	// } else {
	// packet = new Protocol(Protocol.TYPE4_REGISTER_RES, Protocol.T4_CD0_FAIL);
	// }
	//
	// output.write(packet.getPacket());
	// output.flush();
	// }
	//
	// public void CODE7(Protocol protocol) throws IOException {
	// PreparedStatement pstmt = null;
	// int rowsAffected = 0;
	// Protocol packet = null;
	//
	// String[] temp = (new String(protocol.getBody())).trim().split("/");
	//
	// String sql = "select b.ID\r\n" +
	// "from sbd.match_application a, sbd.match_game b\r\n" +
	// "where a.Team_ID = ? AND a.ID = b.Team_1_Application_ID";
	//
	// try {
	// pstmt = conn.prepareStatement(sql);
	// pstmt.setInt(1, Integer.parseInt(temp[1]));
	//
	// ResultSet rs = pstmt.executeQuery();
	//
	// if(rs.next()) {
	// if(temp[2].equals("accept")) {
	// String sql2 = "select Status\r\n" +
	// "from sbd.match_game\r\n" +
	// "where ID = ?";
	// PreparedStatement pstmt2 = conn.prepareStatement(sql2);
	// pstmt2.setInt(1, rs.getInt(1));
	//
	// ResultSet rs2 = pstmt2.executeQuery();
	// rs2.next();
	// if(rs2.getInt(1) == 01) {
	// String sql3 = "update sbd.match_game\r\n" +
	// "set Status = 02 where ID = ?";
	// PreparedStatement pstmt3 = conn.prepareStatement(sql3);
	// pstmt.setInt(1, rs.getInt(1));
	//
	// rowsAffected = pstmt3.executeUpdate();
	// }
	// else if(rs2.getInt(1) == 03) {
	// String sql3 = "update sbd.match_game\r\n" +
	// "set Status = 06 where ID = ?";
	// PreparedStatement pstmt3 = conn.prepareStatement(sql3);
	// pstmt.setInt(1, rs.getInt(1));
	//
	// rowsAffected = pstmt3.executeUpdate();
	// }
	// else
	// {
	// throw new SQLException();
	// }
	// } else {
	// String sql3 = "update sbd.match_game\r\n" +
	// "set Status = 04 where ID = ?";
	// PreparedStatement pstmt3 = conn.prepareStatement(sql3);
	// pstmt.setInt(1, rs.getInt(1));
	//
	// rowsAffected = pstmt3.executeUpdate();
	// }
	// } else {
	// if(temp[2].equals("deny")) {
	// String sql2 = "select Status\r\n" +
	// "from sbd.match_game\r\n" +
	// "where ID = ?";
	// PreparedStatement pstmt2 = conn.prepareStatement(sql2);
	// pstmt2.setInt(1, rs.getInt(1));
	//
	// ResultSet rs2 = pstmt2.executeQuery();
	// rs2.next();
	// if(rs2.getInt(1) == 01) {
	// String sql3 = "update sbd.match_game\r\n" +
	// "set Status = 03 where ID = ?";
	// PreparedStatement pstmt3 = conn.prepareStatement(sql3);
	// pstmt.setInt(1, rs.getInt(1));
	//
	// rowsAffected = pstmt3.executeUpdate();
	// }
	// else if(rs2.getInt(1) == 02) {
	// String sql3 = "update sbd.match_game\r\n" +
	// "set Status = 06 where ID = ?";
	// PreparedStatement pstmt3 = conn.prepareStatement(sql3);
	// pstmt.setInt(1, rs.getInt(1));
	//
	// rowsAffected = pstmt3.executeUpdate();
	// }
	// else
	// {
	// throw new SQLException();
	// }
	// } else {
	// String sql3 = "update sbd.match_game\r\n" +
	// "set Status = 05 where ID = ?";
	// PreparedStatement pstmt3 = conn.prepareStatement(sql3);
	// pstmt.setInt(1, rs.getInt(1));
	//
	// rowsAffected = pstmt3.executeUpdate();
	// }
	// }
	// } catch(SQLException sqle) {
	// packet = new Protocol(Protocol.TYPE4_REGISTER_RES, Protocol.T4_CD0_FAIL);
	// sqle.printStackTrace();
	// output.write(packet.getPacket());
	// output.flush();
	// }
	//
	// if(rowsAffected == 1) {
	// packet = new Protocol(Protocol.TYPE4_REGISTER_RES,
	// Protocol.T4_CD1_SUCCESS);
	// } else {
	// packet = new Protocol(Protocol.TYPE4_REGISTER_RES, Protocol.T4_CD0_FAIL);
	// }
	//
	// output.write(packet.getPacket());
	// output.flush();
	// }
}
