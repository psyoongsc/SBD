package Main;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import Model.Protocol;

public class IO {

	private Socket socket;
	private InputStream is;
	private OutputStream os;

	public IO(Socket socket) throws IOException {
		this.socket = socket;
		is = socket.getInputStream();
		os = socket.getOutputStream();
	}

	public void close() throws IOException {
		if (socket != null)
			socket.close();
		if (is != null)
			is.close();
		if (os != null)
			os.close();
	}

	public void send(Protocol p) throws IOException {
		os.write(p.getPacket());
		os.flush();
	}

	public Protocol read() throws IOException {
		Protocol p = new Protocol();
		byte[] header = new byte[Protocol.LEN_HEADER];
		is.read(header);
		p.setHeader(header);
		if (p.getBodyLength() != 0) {
			byte[] body = new byte[p.getBodyLength()];
			is.read(body);
			p.setBody(body);
		}
		return p;
	}
}
