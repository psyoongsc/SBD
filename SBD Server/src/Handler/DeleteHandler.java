//package Handler;
//
//import java.io.IOException;
//import java.io.InputStream;
//import java.io.OutputStream;
//import java.sql.Connection;
//import java.sql.PreparedStatement;
//import java.sql.SQLException;
//
//import Model.Protocol;
//
//public class DeleteHandler {
//	InputStream input;
//	OutputStream output;
//	Connection conn;
//	
//	public DeleteHandler(OutputStream os, Connection conn) {
//		output = os;
//		this.conn = conn;
//	}
//	
//	public void CODE0(Protocol protocol) throws IOException {
//		PreparedStatement pstmt = null;
//		String[] bodyToken = null;
//		int affectedRow = 0;
//		Protocol packet = new Protocol(Protocol.UNDEFINED);
//		
//		String sql = "";
//		
//		try {
//			bodyToken = (new String(protocol.getBody())).trim().split("/");
//			pstmt = conn.prepareStatement(sql);
//			pstmt.setString(1, bodyToken[0]);
//			
//			affectedRow = pstmt.executeUpdate();
//
//		} catch(SQLException sqle) {
//			packet = new Protocol(Protocol.TYPE10_DELETE_RES, Protocol.T10_CD0_FAIL);
//			sqle.printStackTrace();
//			output.write(packet.getPacket());
//			output.flush(); 
//		}
//		
//		if(affectedRow == 1) {
//			packet = new Protocol(Protocol.TYPE10_DELETE_RES, Protocol.T10_CD1_SUCCESS);
//		} else {
//			packet = new Protocol(Protocol.TYPE10_DELETE_RES, Protocol.T10_CD0_FAIL);
//		}
//		output.write(packet.getPacket());
//		output.flush();
//	}
//}
