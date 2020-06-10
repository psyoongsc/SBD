package Model;
import java.lang.Exception;
import java.nio.ByteBuffer;
import java.util.Arrays;

public class Protocol {
	final static public short LEN_PROTOCOL_MAX = 10012;
	
	final static public int UNDEFINED 				= -1;
	final static public int EXIT 					= 0;
	final static public int TYPE1_LOGIN_REQ 		= 1;
	final static public int TYPE2_LOGIN_RES 		= 2;
	final static public int TYPE3_REGISTER_REQ 		= 3;
	final static public int TYPE4_REGISTER_RES 		= 4;
	final static public int TYPE5_VIEW_REQ 			= 5;
	final static public int TYPE6_VIEW_RES 			= 6;
	final static public int TYPE7_UPDATE_REQ 		= 7;
	final static public int TYPE8_UPDATE_RES 		= 8;
	final static public int TYPE9_DELETE_REQ 		= 9;
	final static public int TYPE10_DELETE_RES 		= 10;
	final static public int TYPE11_CHECK_REQ 		= 11;
	final static public int TYPE12_CHECK_RES 		= 12;
	
	final static public int T2_CD0_FAIL 			= -1;
	final static public int T2_CD1_SUCCESS 			= 1;
	
	final static public int T3_CD0_USER 			= 0;
	final static public int T3_CD1_TEAM 			= 1;
	final static public int T3_CD2_TEAMNOTICE 		= 2;
	final static public int T3_CD3_TEAMRECRUIT 		= 3;
	final static public int T3_CD4_TEAMAPPLICATION 	= 4;
	final static public int T3_CD5_JOIN 			= 5;
	final static public int T3_CD6_MATCH 			= 6;
	final static public int T3_CD7_MATCHANSWER 		= 7;
	
	final static public int T4_CD0_FAIL 			= -1;
	final static public int T4_CD1_SUCCESS 			= 1;
	
	final static public int T5_CD0_USERINFO 		= 0;
	final static public int T5_CD1_SCHEDULE 		= 1;
	final static public int T5_CD2_TEAM 			= 2;
	final static public int T5_CD3_TEAMINFO 		= 3;
	final static public int T5_CD4_TEAMNOTICE 		= 4;
	final static public int T5_CD5_TEAMMEMBER 		= 5;
	final static public int T5_CD6_TEAMRECRUIT 		= 6;
	final static public int T5_CD7_TEAMAPPLICATION 	= 7;
	final static public int T5_CD8_JOIN 			= 8;
	final static public int T5_CD9_MATCHAPPLICATION = 9;
	final static public int T5_CD10_MATCH 			= 10;
	final static public int T5_CD11_ADDRESS 		= 11;
	final static public int T5_CD12_ADDRESSSPECIFIC = 12;
	final static public int T5_CD13_GYM 			= 13;
	final static public int T5_CD14_MYRECRUIT 		= 14;
	
	final static public int T6_FAIL 				= -1;
	final static public int T6_CD0_USERINFO 		= 0;
	final static public int T6_CD1_SCHEDULE 		= 1;
	final static public int T6_CD2_TEAM 			= 2;
	final static public int T6_CD3_TEAMINFO 		= 3;
	final static public int T6_CD4_TEAMNOTICE 		= 4;
	final static public int T6_CD5_TEAMMEMBER 		= 5;
	final static public int T6_CD6_TEAMRECRUIT 		= 6;
	final static public int T6_CD7_TEAMAPPLICATION 	= 7;
	final static public int T6_CD8_JOIN 			= 8;
	final static public int T6_CD9_MATCHAPPLICATION = 9;
	final static public int T6_CD10_MATCH 			= 10;
	final static public int T6_CD11_ADDRESS 		= 11;
	final static public int T6_CD12_ADDRESSSPECIFIC = 12;
	final static public int T6_CD13_GYM 			= 13;
	final static public int T6_CD14_MYRECRUIT 		= 14;
	
	final static public int T7_CD0_USERINFO 		= 0;
	final static public int T7_CD1_TEAMINFO 		= 1;
	final static public int T7_CD2_CAPTAIN 			= 2;
	
	final static public int T8_CD0_FAIL 			= -1;
	final static public int T8_CD1_SUCCESS 			= 1;
	
	final static public int T9_CD0_USER 			= 0;
	final static public int T9_CD1_TEAM 			= 1;
	final static public int T9_CD2_TEAMNOTICE 		= 2;
	final static public int T9_CD3_TEAMMEMBER 		= 3;
	final static public int T9_CD4_TEAMRECRUIT 		= 4;
	
	final static public int T10_CD0_FAIL 			= -1;
	final static public int T10_CD1_SUCCESS 		= 1;
	
	final static public int T11_CD0_USERID 			= 0;
	final static public int T11_CD1_TEAMNAME 		= 1;
	
	final static public int T12_CD0_FAIL 			= -1;
	final static public int T12_CD1_SUCCESS 		= 1;
	
	// 랭스
	public static final int LEN_HEADER = 12;
	public static final int LEN_BODY = 10000;
	
	public byte[] header;
	public byte[] body;

	// 생성자
	// 타입(1)-0, 코드(1)-1, 바디랭스(4)-2,3,4,5, flag(1)-6, last(1)-7, seq_num(4)-8,9,10,11
	public Protocol() {
		header = new byte[LEN_HEADER];
	}

	public Protocol(int protocolType) {
		this();
		header[0] = (byte) protocolType;
	}

	public Protocol(int protocolType, int protocolCode) {
		this(protocolType);
		header[1] = (byte) protocolCode;
	}

	public Protocol(int protocolType, int protocolCode, String data) {
		this(protocolType, protocolCode);
		setString(data);
	}

	public Protocol(int protocolType, int protocolCode, byte[] data) {
		this(protocolType, protocolCode);
		setBody(data);
	}

	public Protocol(int protocolType, int protocolCode, int flag, int last, int seqnum, String data) {
		this(protocolType, protocolCode, data);
		header[6] = (byte)flag;
		header[7] = (byte)last;
		setSeqNum(seqnum);
	}

	public Protocol(int protocolType, int protocolCode, int flag, int last, int seqnum, byte[] data) {
		this(protocolType, protocolCode, data);
		header[4] = (byte) flag;
		header[5] = (byte) last;
		setSeqNum(seqnum);
	}
	
	//inttobyte
	public static byte[] intTobyte(int value) {
        byte[] bytes=new byte[4];
        bytes[0]=(byte)((value&0xFF000000)>>24);
        bytes[1]=(byte)((value&0x00FF0000)>>16);
        bytes[2]=(byte)((value&0x0000FF00)>>8);
        bytes[3]=(byte) (value&0x000000FF);
        return bytes;
	}
	public static int byteToInt(byte[] src) {

        int newValue = 0;

        newValue |= (((int)src[0])<<24)&0xFF000000;
        newValue |= (((int)src[1])<<16)&0xFF0000;
        newValue |= (((int)src[2])<<8)&0xFF00;
        newValue |= (((int)src[3]))&0xFF;
        return newValue;
	}


	// Packet
	public byte[] getPacket() {
		if (body != null) {
			byte[] sum = new byte[header.length + body.length];
			System.arraycopy(header, 0, sum, 0, LEN_HEADER);
			System.arraycopy(body, 0, sum, LEN_HEADER, body.length);
			return sum;
		} else {
			return header;
		}
	}

	public void setPacket(byte[] header, byte[] body) {
		this.header = header;
		this.body = body;
		setBodyLength(body.length);
	}
	
	public void setPacket(byte[] sum){
		System.arraycopy(sum, 0, header, 0, LEN_HEADER);
		System.arraycopy(sum, LEN_HEADER, body, 0, getBodyLength());
	}

	// header
	public byte[] getHeader() {
		return header;
	}

	public void setHeader(byte[] header) {
		this.header = header;
	}

	// string
	public String getString() {
		if(body!=null){
			return new String(body);
		}
		return "";
	}

	public void setString(String data) {
		body = new byte[data.getBytes().length];
		System.arraycopy(data.getBytes(), 0, body, 0, data.getBytes().length);
		setBodyLength(data.getBytes().length);
	}

	// Body
	public byte[] getBody() {
		if(body!=null){
			return body;
		}
		return new byte[1];
	}

	public void setBody(byte[] bd) {
		body = bd;
		setBodyLength(bd.length);
	}
	
	//getset
	public void setType(int type) { header[0] = (byte) type; }
	public void setCode(int code) { header[1] = (byte) code; }
	public void setBodyLength(int bodyLength) { 
		byte[] bytebodylen = intTobyte(bodyLength);
		System.arraycopy(bytebodylen,0, header, 2, 4);
	}
	public void setFrag(int frag) { header[6] = (byte) frag; }
	public void setIsLast(int isLast) { header[7] = (byte) isLast; }
	public void setSeqNum(int seqNum) {
		byte[] byteseqnum = intTobyte(seqNum);
		System.arraycopy(byteseqnum,0, header, 8, 4);
	}
	
	public int getType() { return (int) header[0]; }
	public int getCode() { return (int) header[1]; }
	public int getBodyLength() { 
		byte[] bdl = new byte[4];
		System.arraycopy(header,2, bdl, 0, 4);
		return byteToInt(bdl);
	}
	public int getFrag() { return (int) header[6]; }
	public int getIsLast() { return (int) header[7]; }
	public int getSeqNum() { 
		byte[] sqn = new byte[4];
		System.arraycopy(header,8, sqn, 0, 4);
		return byteToInt(sqn);
	}
}
