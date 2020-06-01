package Handler;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import Model.Protocol;

public class ViewHandler {
	InputStream input;
	OutputStream output;
	Connection conn;
	
	public ViewHandler(OutputStream os, Connection conn) {
		output = os;
		this.conn = conn;
	}
	
	public void CODE0(Protocol protocol) throws IOException{
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuilder sb = new StringBuilder();
		Protocol packet = new Protocol(Protocol.UNDEFINED);
		
		String sql = "select * from user where id=?";
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, new String(protocol.getBody()).trim());
			rs = pstmt.executeQuery();
		
			if(rs.next()) {
				while(true) {
					sb.append(rs.getString(1));
					if(rs.next())
						sb.append("/");
					else
						break;
				}
			}
		} catch(SQLException sqle) {
			packet = new Protocol(Protocol.TYPE6_VIEW_RES, Protocol.T6_FAIL);
			sqle.printStackTrace();
			output.write(packet.getPacket());
			output.flush(); 
		}
		
		packet = new Protocol(Protocol.TYPE6_VIEW_RES, Protocol.T6_CD0_USERINFO);
		try {
			packet.setBody(sb.toString().trim().getBytes());
		} catch (Exception e) {
			e.printStackTrace();
		}
		output.write(packet.getPacket());
		output.flush();
	}
	
	public void CODE1(Protocol protocol) throws IOException{
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuilder sb = new StringBuilder();
		Protocol packet = new Protocol(Protocol.UNDEFINED);
		
		String sql = "select b.Team_Name, c.Team_Name, a.Match_Date\r\n" + 
				"from\r\n" + 
				"(select b.Team_1, b.Team_2, b.Match_Date\r\n" + 
				"from\r\n" + 
				"(select *\r\n" + 
				"from team_member\r\n" + 
				"where User_ID = ?) a,\r\n" + 
				"match_game b\r\n" + 
				"where (b.team_1 = a.Team_ID OR b.team_2 = a.team_ID) AND Status = 06\r\n" + 
				"group by b.ID) a,\r\n" + 
				"team b,\r\n" + 
				"team c\r\n" + 
				"where a.team_1 = b.ID AND a.team_2 = c.ID";
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, new String(protocol.getBody()).trim());
			rs = pstmt.executeQuery();
		
			if(rs.next()) {
				while(true) {
					sb.append(rs.getString(1));
					if(rs.next())
						sb.append("/");
					else
						break;
				}
			}
		} catch(SQLException sqle) {
			packet = new Protocol(Protocol.TYPE6_VIEW_RES, Protocol.T6_FAIL);
			sqle.printStackTrace();
			output.write(packet.getPacket());
			output.flush(); 
		}
		
		packet = new Protocol(Protocol.TYPE6_VIEW_RES, Protocol.T6_CD1_SCHEDULE);
		try {
			packet.setBody(sb.toString().trim().getBytes());
		} catch (Exception e) {
			e.printStackTrace();
		}
		output.write(packet.getPacket());
		output.flush();
	}
	
	public void CODE2(Protocol protocol) throws IOException{
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuilder sb = new StringBuilder();
		Protocol packet = new Protocol(Protocol.UNDEFINED);
		
		String sql = "select b.ID, b.Team_Name\r\n" + 
				"from team_member a, team b\r\n" + 
				"where User_ID = ? AND a.Team_ID = b.ID;";
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, new String(protocol.getBody()).trim());
			rs = pstmt.executeQuery();
		
			if(rs.next()) {
				while(true) {
					sb.append(rs.getString(1));
					if(rs.next())
						sb.append("/");
					else
						break;
				}
			}
		} catch(SQLException sqle) {
			packet = new Protocol(Protocol.TYPE6_VIEW_RES, Protocol.T6_FAIL);
			sqle.printStackTrace();
			output.write(packet.getPacket());
			output.flush(); 
		}
		
		packet = new Protocol(Protocol.TYPE6_VIEW_RES, Protocol.T6_CD2_TEAM);
		try {
			packet.setBody(sb.toString().trim().getBytes());
		} catch (Exception e) {
			e.printStackTrace();
		}
		output.write(packet.getPacket());
		output.flush();
	}
	
	public void CODE3(Protocol protocol) throws IOException{
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuilder sb = new StringBuilder();
		Protocol packet = new Protocol(Protocol.UNDEFINED);
		
		String sql = "select *\r\n" + 
				"from team\r\n" + 
				"where ID = ?;";
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, new String(protocol.getBody()).trim());
			rs = pstmt.executeQuery();
		
			if(rs.next()) {
				while(true) {
					sb.append(rs.getString(1));
					if(rs.next())
						sb.append("/");
					else
						break;
				}
			}
		} catch(SQLException sqle) {
			packet = new Protocol(Protocol.TYPE6_VIEW_RES, Protocol.T6_FAIL);
			sqle.printStackTrace();
			output.write(packet.getPacket());
			output.flush(); 
		}
		
		packet = new Protocol(Protocol.TYPE6_VIEW_RES, Protocol.T6_CD3_TEAMINFO);
		try {
			packet.setBody(sb.toString().trim().getBytes());
		} catch (Exception e) {
			e.printStackTrace();
		}
		output.write(packet.getPacket());
		output.flush();
	}
	
	public void CODE4(Protocol protocol) throws IOException{
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuilder sb = new StringBuilder();
		Protocol packet = new Protocol(Protocol.UNDEFINED);
		
		String sql = "select *\r\n" + 
				"from team_note\r\n" + 
				"where Team_ID = ?";
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, new String(protocol.getBody()).trim());
			rs = pstmt.executeQuery();
		
			if(rs.next()) {
				while(true) {
					sb.append(rs.getString(1));
					if(rs.next())
						sb.append("/");
					else
						break;
				}
			}
		} catch(SQLException sqle) {
			packet = new Protocol(Protocol.TYPE6_VIEW_RES, Protocol.T6_FAIL);
			sqle.printStackTrace();
			output.write(packet.getPacket());
			output.flush(); 
		}
		
		packet = new Protocol(Protocol.TYPE6_VIEW_RES, Protocol.T6_CD4_TEAMNOTICE);
		try {
			packet.setBody(sb.toString().trim().getBytes());
		} catch (Exception e) {
			e.printStackTrace();
		}
		output.write(packet.getPacket());
		output.flush();
	}
	
	public void CODE5(Protocol protocol) throws IOException{
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuilder sb = new StringBuilder();
		Protocol packet = new Protocol(Protocol.UNDEFINED);
		
		String sql = "select User_ID\r\n" + 
				"from team_member\r\n" + 
				"where Team_ID = ?";
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, new String(protocol.getBody()).trim());
			rs = pstmt.executeQuery();
		
			if(rs.next()) {
				while(true) {
					sb.append(rs.getString(1));
					if(rs.next())
						sb.append("/");
					else
						break;
				}
			}
		} catch(SQLException sqle) {
			packet = new Protocol(Protocol.TYPE6_VIEW_RES, Protocol.T6_FAIL);
			sqle.printStackTrace();
			output.write(packet.getPacket());
			output.flush(); 
		}
		
		packet = new Protocol(Protocol.TYPE6_VIEW_RES, Protocol.T6_CD5_TEAMMEMBER);
		try {
			packet.setBody(sb.toString().trim().getBytes());
		} catch (Exception e) {
			e.printStackTrace();
		}
		output.write(packet.getPacket());
		output.flush();
	}
	
	public void CODE6(Protocol protocol) throws IOException{
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuilder sb = new StringBuilder();
		Protocol packet = new Protocol(Protocol.UNDEFINED);
		
		String sql = "select b.*, a.*\r\n" + 
				"from recruit a, team b, user c\r\n" + 
				"where date_format(now(), \"%Y-%c-%d\") <= date_format(a.Date_req + interval a.Due_Date DAY, \"%Y-%c-%d\")\r\n" + 
				"AND c.ID = '345' AND c.Sex = b.Team_Sex \r\n" + 
				"AND c.Addr1 = b.Addr1 \r\n" + 
				"AND FLOOR( (CAST(REPLACE(CURRENT_DATE,'-','') AS UNSIGNED) - CAST(REPLACE(c.Birthday,'-','') AS UNSIGNED)) / 10000 ) >= b.Min_Age\r\n" + 
				"AND FLOOR( (CAST(REPLACE(CURRENT_DATE,'-','') AS UNSIGNED) - CAST(REPLACE(c.Birthday,'-','') AS UNSIGNED)) / 10000 ) <= b.Max_Age";
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, new String(protocol.getBody()).trim());
			rs = pstmt.executeQuery();
		
			if(rs.next()) {
				while(true) {
					sb.append(rs.getString(1));
					if(rs.next())
						sb.append("/");
					else
						break;
				}
			}
		} catch(SQLException sqle) {
			packet = new Protocol(Protocol.TYPE6_VIEW_RES, Protocol.T6_FAIL);
			sqle.printStackTrace();
			output.write(packet.getPacket());
			output.flush(); 
		}
		
		packet = new Protocol(Protocol.TYPE6_VIEW_RES, Protocol.T6_CD6_TEAMRECRUIT);
		try {
			packet.setBody(sb.toString().trim().getBytes());
		} catch (Exception e) {
			e.printStackTrace();
		}
		output.write(packet.getPacket());
		output.flush();
	}
	
	public void CODE7(Protocol protocol) throws IOException{ // 최근 2주일까지의 데이터
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuilder sb = new StringBuilder();
		Protocol packet = new Protocol(Protocol.UNDEFINED);
		
		String sql = "select *\r\n" + 
				"from application a, recruit b, team c\r\n" + 
				"where a.User_ID = '345'\r\n" + 
				"and date_format(now(), \"%Y-%c-%d\") <= date_format(a.datetime + interval 14 DAY, \"%Y-%c-%d\")\r\n" + 
				"and a.Recruit_ID = b.ID and b.Team_ID = c.ID";
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, new String(protocol.getBody()).trim());
			rs = pstmt.executeQuery();
		
			if(rs.next()) {
				while(true) {
					sb.append(rs.getString(1));
					if(rs.next())
						sb.append("/");
					else
						break;
				}
			}
		} catch(SQLException sqle) {
			packet = new Protocol(Protocol.TYPE6_VIEW_RES, Protocol.T6_FAIL);
			sqle.printStackTrace();
			output.write(packet.getPacket());
			output.flush(); 
		}
		
		packet = new Protocol(Protocol.TYPE6_VIEW_RES, Protocol.T6_CD7_TEAMAPPLICATION);
		try {
			packet.setBody(sb.toString().trim().getBytes());
		} catch (Exception e) {
			e.printStackTrace();
		}
		output.write(packet.getPacket());
		output.flush();
	}
	
	// CD8,9번 구현해야함
	
	public void CODE10(Protocol protocol) throws Exception{
		Statement stmt = null;
		ResultSet rs = null;
		
		stmt = conn.createStatement();
		rs = stmt.executeQuery("select distinct addr1 from address");
		
		String temp = "";
		if(rs.next()) {
			while(true) {
				temp += rs.getString(1).toString();
				if(rs.next())
					temp += "/";
				else
					break;
			}
		}
		
		Protocol packet = new Protocol(Protocol.TYPE6_VIEW_RES, Protocol.T6_CD10_ADDRESS);
		packet.setBody(temp.getBytes());
		output.write(packet.getPacket());
		output.flush();
	}
	
	public void CODE11(Protocol protocol) throws Exception{
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		pstmt = conn.prepareStatement("select addr2 from address where addr1=?");
		pstmt.setString(1, (new String(protocol.getBody()).trim()));
		rs = pstmt.executeQuery();
		
		String temp = "";
		if(rs.next()) {
			while(true) {
				temp += rs.getString(1).toString();
				if(rs.next())
					temp += "/";
				else
					break;
			}
		}
		
		Protocol packet = new Protocol(Protocol.TYPE6_VIEW_RES, Protocol.T6_CD11_ADDRESSSPECIFIC);
		packet.setBody(temp.getBytes());
		output.write(packet.getPacket());
		output.flush();
	}
	
	// CD12번 구현해야함
}
