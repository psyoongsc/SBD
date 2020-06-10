package Handler;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import Main.IO;
import Model.Protocol;

public class CheckHandler {
	IO io;
	Connection conn;

	public CheckHandler(IO io, Connection conn) {
		this.io = io;
		this.conn = conn;
	}

	public void CODE0(Protocol protocol) throws Exception {
		Protocol packet;
		String sql = "select id from sbd.user where sbd.user.id = ?";

		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, protocol.getString());
		ResultSet rs = pstmt.executeQuery();

		if (rs.next()) {
			packet = new Protocol(Protocol.TYPE12_CHECK_RES, Protocol.T12_CD0_FAIL);

		} else {
			packet = new Protocol(Protocol.TYPE12_CHECK_RES, Protocol.T12_CD1_SUCCESS);
		}
		io.send(packet);
	}

	public void CODE1(Protocol protocol) throws Exception {

		String sql = "SELECT * FROM team WHERE Team_Name=?";

		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, protocol.getString());
		ResultSet rs = pstmt.executeQuery();
		Protocol packet;

		System.out.println("ok");
		if (rs.next()) {
			packet = new Protocol(Protocol.TYPE12_CHECK_RES, Protocol.T12_CD0_FAIL);
		} else {
			packet = new Protocol(Protocol.TYPE12_CHECK_RES, Protocol.T12_CD1_SUCCESS);
		}
		io.send(packet);
	}

}
