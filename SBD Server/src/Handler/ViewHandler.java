package Handler;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import Main.IO;
import Main.MainServerThread;
import Model.Protocol;

public class ViewHandler {
	IO io;
	Connection conn;

	public ViewHandler(IO io, Connection conn) {
		this.io = io;
		this.conn = conn;
	}

	// public void CODE0(Protocol protocol) throws IOException{
	// PreparedStatement pstmt = null;
	// ResultSet rs = null;
	// StringBuilder sb = new StringBuilder();
	// Protocol packet = new Protocol(Protocol.UNDEFINED);
	//
	// String sql = "select ID, PW, FullName, Sex, Birthday, Phone, Addr1, Addr2
	// from user where id=?";
	//
	// try {
	// pstmt = conn.prepareStatement(sql);
	// pstmt.setString(1, protocol.getString().trim());
	// rs = pstmt.executeQuery();
	//
	// if(rs.next()) {
	// sb.append(rs.getString(1) + "/");
	// sb.append(rs.getString(2) + "/");
	// sb.append(rs.getString(3) + "/");
	// sb.append(rs.getString(4) + "/");
	// sb.append(rs.getDate(5).toString() + "/");
	// sb.append(rs.getString(6) + "/");
	// sb.append(rs.getString(7) + "/");
	// sb.append(rs.getString(8));
	// }
	// } catch(SQLException sqle) {
	// packet = new Protocol(Protocol.TYPE6_VIEW_RES, Protocol.T6_FAIL);
	// sqle.printStackTrace();
	// output.write(packet.getPacket());
	// output.flush();
	// }
	//
	// packet = new Protocol(Protocol.TYPE6_VIEW_RES, Protocol.T6_CD0_USERINFO);
	// try {
	// packet.setBody(sb.toString().trim().getBytes());
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	// output.write(packet.getPacket());
	// output.flush();
	// }
	//
	// public void CODE1(Protocol protocol) throws IOException{
	// PreparedStatement pstmt = null;
	// ResultSet rs = null;
	// StringBuilder sb = new StringBuilder();
	// Protocol packet = new Protocol(Protocol.UNDEFINED);
	//
	// String sql = "select f.Team_Name, e.Gym_Name, c.Match_Date\r\n" +
	// "from user a, team_member b, match_game c, match_application d, gym e,
	// team f\r\n" +
	// "where a.ID = ? and b.User_ID = a.ID and b.Team_ID = d.Team_ID \r\n" +
	// "and (d.ID = c.Team_1_Application_ID OR d.ID = c.Team_2_Application_ID)
	// \r\n" +
	// "and e.ID = f.Gym_ID and c.Status = 06 and b.Team_ID = f.ID";
	//
	// try {
	// pstmt = conn.prepareStatement(sql);
	// pstmt.setString(1, new String(protocol.getBody()).trim());
	// rs = pstmt.executeQuery();
	//
	// if(rs.next()) {
	// while(true) {
	// sb.append(rs.getString(1) + "/");
	// sb.append(rs.getString(2) + "/");
	// sb.append(rs.getTimestamp(3).toString().split(".")[0]);
	//
	// if(rs.next())
	// sb.append("/");
	// else
	// break;
	// }
	// }
	// } catch(SQLException sqle) {
	// packet = new Protocol(Protocol.TYPE6_VIEW_RES, Protocol.T6_FAIL);
	// sqle.printStackTrace();
	// output.write(packet.getPacket());
	// output.flush();
	// }
	//
	// packet = new Protocol(Protocol.TYPE6_VIEW_RES, Protocol.T6_CD1_SCHEDULE);
	// try {
	// packet.setBody(sb.toString().trim().getBytes());
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	// output.write(packet.getPacket());
	// output.flush();
	// }
	//
	public void CODE2(Protocol protocol) throws Exception {
		String rq = "";
		String sql = "SELECT ID, Team_Name FROM sbd.team as a, sbd.team_member as b WHERE a.ID=b.Team_ID and User_ID=?";

		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, protocol.getString());
		ResultSet rs = pstmt.executeQuery();

		if (rs.next()) {
			rq += rs.getInt(1) + "/" + rs.getString(2);
		}
		while (rs.next()) {
			rq += "/" + rs.getInt(1) + "/" + rs.getString(2);
		}

		System.out.print(rq);
		Protocol packet = new Protocol(Protocol.TYPE6_VIEW_RES, Protocol.T6_CD2_TEAM);
		packet.setString(rq);
		io.send(packet);
	}
	//
	// public void CODE3(Protocol protocol) throws IOException{
	// PreparedStatement pstmt = null;
	// ResultSet rs = null;
	// StringBuilder sb = new StringBuilder();
	// Protocol packet = new Protocol(Protocol.UNDEFINED);
	//
	// String sql = "select b.Gym_Name, a.*\r\n" +
	// "from team a, gym b\r\n" +
	// "where a.ID = ? and b.ID = a.Gym_ID";
	//
	// try {
	// pstmt = conn.prepareStatement(sql);
	// pstmt.setString(1, new String(protocol.getBody()).trim());
	// rs = pstmt.executeQuery();
	//
	// if(rs.next()) {
	// sb.append(rs.getInt("Gym_ID") + "/");
	// sb.append(rs.getString("Gym_Name") + "/");
	// sb.append(rs.getInt("Max_Age") + "/");
	// sb.append(rs.getInt("Min_Age") + "/");
	// sb.append(rs.getString("Team_Sex") + "/");
	// sb.append(rs.getString("Team_Name") + "/");
	// sb.append(rs.getString("Team_Intro") + "/");
	// sb.append(rs.getString("User_Captain"));
	// }
	// } catch(SQLException sqle) {
	// packet = new Protocol(Protocol.TYPE6_VIEW_RES, Protocol.T6_FAIL);
	// sqle.printStackTrace();
	// output.write(packet.getPacket());
	// output.flush();
	// }
	//
	// packet = new Protocol(Protocol.TYPE6_VIEW_RES, Protocol.T6_CD3_TEAMINFO);
	// try {
	// packet.setBody(sb.toString().trim().getBytes());
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	// output.write(packet.getPacket());
	// output.flush();
	// }
	//
	// public void CODE4(Protocol protocol) throws IOException{
	// PreparedStatement pstmt = null;
	// ResultSet rs = null;
	// StringBuilder sb = new StringBuilder();
	// Protocol packet = new Protocol(Protocol.UNDEFINED);
	//
	// String sql = "select *\r\n" +
	// "from team_note\r\n" +
	// "where Team_ID = ?";
	//
	// try {
	// pstmt = conn.prepareStatement(sql);
	// pstmt.setString(1, new String(protocol.getBody()).trim());
	// rs = pstmt.executeQuery();
	//
	// if(rs.next()) {
	// while(true) {
	// sb.append(rs.getDate("datetime") + "/");
	// sb.append(rs.getString("User_Writer") + "/");
	// sb.append(rs.getString("Note"));
	//
	// if(rs.next())
	// sb.append("/");
	// else
	// break;
	// }
	// }
	// } catch(SQLException sqle) {
	// packet = new Protocol(Protocol.TYPE6_VIEW_RES, Protocol.T6_FAIL);
	// sqle.printStackTrace();
	// output.write(packet.getPacket());
	// output.flush();
	// }
	//
	// packet = new Protocol(Protocol.TYPE6_VIEW_RES,
	// Protocol.T6_CD4_TEAMNOTICE);
	// try {
	// packet.setBody(sb.toString().trim().getBytes());
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	// output.write(packet.getPacket());
	// output.flush();
	// }
	//
	// public void CODE5(Protocol protocol) throws IOException{
	// PreparedStatement pstmt = null;
	// ResultSet rs = null;
	// StringBuilder sb = new StringBuilder();
	// Protocol packet = new Protocol(Protocol.UNDEFINED);
	//
	// String sql = "select a.User_ID, b.FullName, b.Phone\r\n" +
	// "from team_member a, user b\r\n" +
	// "where a.Team_ID = ? and a.User_ID = b.ID";
	//
	// try {
	// pstmt = conn.prepareStatement(sql);
	// pstmt.setString(1, new String(protocol.getBody()).trim());
	// rs = pstmt.executeQuery();
	//
	// if(rs.next()) {
	// while(true) {
	// sb.append(rs.getString(1) + "/");
	// sb.append(rs.getString(2) + "/");
	// sb.append(rs.getString(3));
	//
	// if(rs.next())
	// sb.append("/");
	// else
	// break;
	// }
	// }
	// } catch(SQLException sqle) {
	// packet = new Protocol(Protocol.TYPE6_VIEW_RES, Protocol.T6_FAIL);
	// sqle.printStackTrace();
	// output.write(packet.getPacket());
	// output.flush();
	// }
	//
	// packet = new Protocol(Protocol.TYPE6_VIEW_RES,
	// Protocol.T6_CD5_TEAMMEMBER);
	// try {
	// packet.setBody(sb.toString().trim().getBytes());
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	// output.write(packet.getPacket());
	// output.flush();
	// }
	//
	// public void CODE6(Protocol protocol) throws IOException{
	// PreparedStatement pstmt = null;
	// ResultSet rs = null;
	// StringBuilder sb = new StringBuilder();
	// Protocol packet = new Protocol(Protocol.UNDEFINED);
	//
	// String sql = "select a.ID as RecruitID, b.ID as TeamID, b.Team_Name,
	// a.Note, date_format(a.Date_req + interval a.Due_Date DAY, \"%Y-%c-%d\")
	// as DueDate\r\n" +
	// "from recruit a, team b, user c\r\n" +
	// "where date_format(now(), \"%Y-%c-%d\") <= date_format(a.Date_req +
	// interval a.Due_Date DAY, \"%Y-%c-%d\")\r\n" +
	// "AND c.ID = ? AND c.Sex = b.Team_Sex\r\n" +
	// "AND c.Addr1 = b.Addr1\r\n" +
	// "AND FLOOR( (CAST(REPLACE(CURRENT_DATE,'-','') AS UNSIGNED) -
	// CAST(REPLACE(c.Birthday,'-','') AS UNSIGNED)) / 10000 ) >= b.Min_Age\r\n"
	// +
	// "AND FLOOR( (CAST(REPLACE(CURRENT_DATE,'-','') AS UNSIGNED) -
	// CAST(REPLACE(c.Birthday,'-','') AS UNSIGNED)) / 10000 ) <= b.Max_Age";
	//
	// try {
	// pstmt = conn.prepareStatement(sql);
	// pstmt.setString(1, new String(protocol.getBody()).trim());
	// rs = pstmt.executeQuery();
	//
	// if(rs.next()) {
	// while(true) {
	// sb.append(rs.getInt(1) + "/");
	// sb.append(rs.getInt(2) + "/");
	// sb.append(rs.getString(3) + "/");
	// sb.append(rs.getString(4) + "/");
	// sb.append(rs.getDate(5).toString());
	//
	// if(rs.next())
	// sb.append("/");
	// else
	// break;
	// }
	// }
	// } catch(SQLException sqle) {
	// packet = new Protocol(Protocol.TYPE6_VIEW_RES, Protocol.T6_FAIL);
	// sqle.printStackTrace();
	// output.write(packet.getPacket());
	// output.flush();
	// }
	//
	// packet = new Protocol(Protocol.TYPE6_VIEW_RES,
	// Protocol.T6_CD6_TEAMRECRUIT);
	// try {
	// packet.setBody(sb.toString().trim().getBytes());
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	// output.write(packet.getPacket());
	// output.flush();
	// }
	//
	// public void CODE7(Protocol protocol) throws IOException{ // 최근 2주일까지의 데이터
	// PreparedStatement pstmt = null;
	// ResultSet rs = null;
	// StringBuilder sb = new StringBuilder();
	// Protocol packet = new Protocol(Protocol.UNDEFINED);
	//
	// String sql = "select c.Team_Name, a.Note, d.Code_Name\r\n" +
	// "from application a, recruit b, team c, code d\r\n" +
	// "where a.User_ID = ?\r\n" +
	// "and date_format(now(), \"%Y-%c-%d\") <= date_format(a.datetime +
	// interval 14 DAY, \"%Y-%c-%d\")\r\n" +
	// "and a.Recruit_ID = b.ID and b.Team_ID = c.ID\r\n" +
	// "and d.Code_ID = 003 and d.Code = a.Status";
	//
	// try {
	// pstmt = conn.prepareStatement(sql);
	// pstmt.setString(1, new String(protocol.getBody()).trim());
	// rs = pstmt.executeQuery();
	//
	// if(rs.next()) {
	// while(true) {
	// sb.append(rs.getString(1) + "/");
	// sb.append(rs.getString(2) + "/");
	// sb.append(rs.getString(3));
	//
	// if(rs.next())
	// sb.append("/");
	// else
	// break;
	// }
	// }
	// } catch(SQLException sqle) {
	// packet = new Protocol(Protocol.TYPE6_VIEW_RES, Protocol.T6_FAIL);
	// sqle.printStackTrace();
	// output.write(packet.getPacket());
	// output.flush();
	// }
	//
	// packet = new Protocol(Protocol.TYPE6_VIEW_RES,
	// Protocol.T6_CD7_TEAMAPPLICATION);
	// try {
	// packet.setBody(sb.toString().trim().getBytes());
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	// output.write(packet.getPacket());
	// output.flush();
	// }
	//
	// public void CODE8(Protocol protocol) throws IOException{
	// PreparedStatement pstmt = null;
	// ResultSet rs = null;
	// StringBuilder sb = new StringBuilder();
	// Protocol packet = new Protocol(Protocol.UNDEFINED);
	//
	// String sql = "select b.ID, a.Team_Name, c.User_ID, d.FullName,
	// c.Note\r\n" +
	// "from team a, recruit b, application c, user d\r\n" +
	// "where a.ID = ? and b.Team_ID = a.ID and b.ID = c.Recruit_ID and d.ID =
	// c.User_ID and c.Status = 01";
	//
	// try {
	// pstmt = conn.prepareStatement(sql);
	// pstmt.setString(1, new String(protocol.getBody()).trim());
	// rs = pstmt.executeQuery();
	//
	// if(rs.next()) {
	// while(true) {
	// sb.append(rs.getInt(1) + "/");
	// sb.append(rs.getString(2) + "/");
	// sb.append(rs.getString(3) + "/");
	// sb.append(rs.getString(4) + "/");
	// sb.append(rs.getString(5));
	//
	// if(rs.next())
	// sb.append("/");
	// else
	// break;
	// }
	// }
	// } catch(SQLException sqle) {
	// packet = new Protocol(Protocol.TYPE6_VIEW_RES, Protocol.T6_FAIL);
	// sqle.printStackTrace();
	// output.write(packet.getPacket());
	// output.flush();
	// }
	//
	// packet = new Protocol(Protocol.TYPE6_VIEW_RES, Protocol.T6_CD8_JOIN);
	// try {
	// packet.setBody(sb.toString().trim().getBytes());
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	// output.write(packet.getPacket());
	// output.flush();
	// }
	//
	// public void CODE9(Protocol protocol) throws IOException{
	// PreparedStatement pstmt = null;
	// ResultSet rs = null;
	// StringBuilder sb = new StringBuilder();
	// Protocol packet = new Protocol(Protocol.UNDEFINED);
	//
	// String sql = "select b.Team_Name, c.Code_Name\r\n" +
	// "from match_application a, team b, code c\r\n" +
	// "where a.Team_ID = ? and b.ID = a.Team_ID\r\n" +
	// "and c.Code_ID = 001 and c.Code = a.Status\r\n" +
	// "and date_format(now(), \"%Y-%c-%d\") <= date_format(a.AvailableEnd,
	// \"%Y-%c-%d\")";
	//
	// try {
	// pstmt = conn.prepareStatement(sql);
	// pstmt.setString(1, new String(protocol.getBody()).trim());
	// rs = pstmt.executeQuery();
	//
	// if(rs.next()) {
	// while(true) {
	// sb.append(rs.getInt(1) + "/");
	// sb.append(rs.getString(2));
	//
	// if(rs.next())
	// sb.append("/");
	// else
	// break;
	// }
	// }
	// } catch(SQLException sqle) {
	// packet = new Protocol(Protocol.TYPE6_VIEW_RES, Protocol.T6_FAIL);
	// sqle.printStackTrace();
	// output.write(packet.getPacket());
	// output.flush();
	// }
	//
	// packet = new Protocol(Protocol.TYPE6_VIEW_RES, Protocol.T6_CD8_JOIN);
	// try {
	// packet.setBody(sb.toString().trim().getBytes());
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	// output.write(packet.getPacket());
	// output.flush();
	// }
	//
	// public void CODE10(Protocol protocol) throws IOException{
	// PreparedStatement pstmt = null;
	// ResultSet rs = null;
	// StringBuilder sb = new StringBuilder();
	// Protocol packet = new Protocol(Protocol.UNDEFINED);
	//
	// String sql = "select b.ID, c.Team_Name, d.Gym_Name, b.Match_Date,
	// e.Code_Name\r\n" +
	// "from match_application a, match_game b, team c, gym d, code e\r\n" +
	// "where a.Team_ID = ? and a.Status = 02 \r\n" +
	// "and (b.Team_1_Application_ID = a.ID OR b.Team_2_Application_ID = a.ID)
	// \r\n" +
	// "and a.Team_ID = c.ID and a.Match_Gym = d.ID\r\n" +
	// "and e.Code_ID = 002 and e.Code = b.Status\r\n" +
	// "and date_format(now(), \"%Y-%c-%d\") <= date_format(b.Match_Date,
	// \"%Y-%c-%d\")";
	//
	// try {
	// pstmt = conn.prepareStatement(sql);
	// pstmt.setString(1, new String(protocol.getBody()).trim());
	// rs = pstmt.executeQuery();
	//
	// if(rs.next()) {
	// while(true) {
	// sb.append(rs.getInt(1) + "/");
	// sb.append(rs.getString(2) + "/");
	// sb.append(rs.getString(3) + "/");
	// sb.append(rs.getTimestamp(4).toString().split(".")[0] + "/");
	// sb.append(rs.getString(5) + "/");
	//
	// if(rs.next())
	// sb.append("/");
	// else
	// break;
	// }
	// }
	// } catch(SQLException sqle) {
	// packet = new Protocol(Protocol.TYPE6_VIEW_RES, Protocol.T6_FAIL);
	// sqle.printStackTrace();
	// output.write(packet.getPacket());
	// output.flush();
	// }
	//
	// packet = new Protocol(Protocol.TYPE6_VIEW_RES, Protocol.T6_CD8_JOIN);
	// try {
	// packet.setBody(sb.toString().trim().getBytes());
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	// output.write(packet.getPacket());
	// output.flush();
	// }

	public void CODE11(Protocol protocol) throws Exception {

		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery("SELECT distinct Addr1 FROM address");

		String temp = "";
		if (rs.next()) {
			temp += rs.getString(1);
		}
		while (rs.next()) {
			temp += "/" + rs.getString(1);
		}

		System.out.println(temp);
		Protocol packet = new Protocol(Protocol.TYPE6_VIEW_RES, Protocol.T6_CD11_ADDRESS);
		packet.setString(temp);
		io.send(packet);
	}

	public void CODE12(Protocol protocol) throws Exception {
		PreparedStatement pstmt = conn.prepareStatement("select addr2 from address where addr1=?");
		pstmt.setString(1, protocol.getString());
		ResultSet rs = pstmt.executeQuery();

		String temp = "";
		if (rs.next()) {
			temp += rs.getString(1);
		}
		while (rs.next()) {
			temp += "/" + rs.getString(1);
		}

		Protocol packet = new Protocol(Protocol.TYPE6_VIEW_RES, Protocol.T6_CD12_ADDRESSSPECIFIC);
		packet.setString(temp);
		io.send(packet);
	}

	public void CODE13(Protocol protocol) throws Exception {
		String sb = "";
		Protocol packet;

		PreparedStatement pstmt = conn.prepareStatement("select ID, Gym_Name from gym where addr1=? and addr2=?");
		pstmt.setString(1, protocol.getString().split("/")[0]);
		pstmt.setString(1, protocol.getString().split("/")[1]);
		ResultSet rs = pstmt.executeQuery();

		if(rs.next()){
			sb += rs.getInt(1);
			sb += rs.getString(2);
		}while(rs.next()){
			sb += "/"+rs.getInt(1);
			sb += "/"+rs.getString(2);			
		}

		packet = new Protocol(Protocol.TYPE6_VIEW_RES, Protocol.T6_CD13_GYM);

		packet.setString(sb);
	}

	// public void CODE14(Protocol protocol) throws Exception{
	// PreparedStatement pstmt = null;
	// ResultSet rs = null;
	// StringBuilder sb = new StringBuilder();
	// Protocol packet = new Protocol(Protocol.UNDEFINED);
	//
	// try {
	// pstmt = conn.prepareStatement("select c.ID, b.Team_Name, c.Note,
	// date_format(c.Date_Req + interval c.Due_Date DAY, \"%Y-%c-%d\") as
	// DueDate\r\n" +
	// "from user a, team b, recruit c\r\n" +
	// "where a.ID = ? and a.ID = b.User_Captain and c.Team_ID = b.ID");
	// pstmt.setString(1, (new String(protocol.getBody()).trim()));
	// rs = pstmt.executeQuery();
	//
	// if(rs.next()) {
	// while(true) {
	// sb.append(rs.getInt(1) + "/");
	// sb.append(rs.getString(2) + "/");
	// sb.append(rs.getString(3) + "/");
	// sb.append(rs.getDate(4).toString());
	//
	// if(rs.next())
	// sb.append("/");
	// else
	// break;
	// }
	// }
	// } catch (SQLException sqle) {
	// packet = new Protocol(Protocol.TYPE6_VIEW_RES, Protocol.T6_FAIL);
	// sqle.printStackTrace();
	// output.write(packet.getPacket());
	// output.flush();
	// }
	//
	// packet = new Protocol(Protocol.TYPE6_VIEW_RES,
	// Protocol.T6_CD14_MYRECRUIT);
	// try {
	// packet.setBody(sb.toString().trim().getBytes());
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	// output.write(packet.getPacket());
	// output.flush();
	// }
}
