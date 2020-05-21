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
	
	public void CODE11(Protocol protocol) throws Exception{
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
		
		Protocol packet = new Protocol(Protocol.TYPE6_VIEW_RES, Protocol.T6_CD11_ADDRESS);
		packet.setBody(temp.getBytes());
		output.write(packet.getPacket());
		output.flush();
	}
	public void CODE12(Protocol protocol) throws Exception{
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
		
		Protocol packet = new Protocol(Protocol.TYPE6_VIEW_RES, Protocol.T6_CD12_ADDRESSSPECIFIC);
		packet.setBody(temp.getBytes());
		output.write(packet.getPacket());
		output.flush();
	}
}
