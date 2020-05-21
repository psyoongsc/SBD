package Model;
import java.lang.Exception;
import java.util.Arrays;

public class Protocol {
	final static public short LEN_PROTOCOL_MAX = 10012;
	
	final static public int UNDEFINED = -1;
	final static public int EXIT = 0;
	final static public int TYPE1_LOGIN_REQ = 1;
	final static public int TYPE2_LOGIN_RES = 2;
	final static public int TYPE3_REGISTER_REQ = 3;
	final static public int TYPE4_REGISTER_RES = 4;
	final static public int TYPE5_VIEW_REQ = 5;
	final static public int TYPE6_VIEW_RES = 6;
	final static public int TYPE7_RENEW_REQ = 7;
	final static public int TYPE8_RENEW_RES = 8;
	final static public int TYPE9_DELETE_REQ = 9;
	final static public int TYPE10_DELETE_RES = 10;
	
	final static public int T2_CD0_FAIL = -1;
	final static public int T2_CD1_SUCCESS = 1;
	
	final static public int T3_CD0_USER = 0;
	final static public int T3_CD1_TEAM = 1;
	final static public int T3_CD2_TEAMNOTICE = 2;
	final static public int T3_CD3_TEAMRECRUIT = 3;
	final static public int T3_CD4_TEAMAPPLICATION = 4;
	final static public int T3_CD5_JOIN = 5;
	final static public int T3_CD6_MATCH = 6;
	final static public int T3_CD7_DIALOGUE = 7;
	
	final static public int T4_CD0_FAIL = -1;
	final static public int T4_CD1_SUCCESS = 1;
	
	final static public int T5_CD0_USERINFO = 0;
	final static public int T5_CD1_RECORD = 1;
	final static public int T5_CD2_SCHEDULE = 2;
	final static public int T5_CD3_TEAM = 3;
	final static public int T5_CD4_TEAMINFO = 4;
	final static public int T5_CD5_TEAMNOTICE = 5;
	final static public int T5_CD6_TEAMRECRUIT = 6;
	final static public int T5_CD7_TEAMAPPLICATION = 7;
	final static public int T5_CD8_JOIN = 8;
	final static public int T5_CD9_MATCH = 9;
	final static public int T5_CD10_DIALOGUE = 10;
	final static public int T5_CD11_ADDRESS = 11;
	final static public int T5_CD12_ADDRESSSPECIFIC = 12;
	
	final static public int T6_FAIL = -1;
	final static public int T6_CD0_USERINFO = 0;
	final static public int T6_CD1_RECORD = 1;
	final static public int T6_CD2_SCHEDULE = 2;
	final static public int T6_CD3_TEAM = 3;
	final static public int T6_CD4_TEAMINFO = 4;
	final static public int T6_CD5_TEAMNOTICE = 5;
	final static public int T6_CD6_TEAMRECRUIT = 6;
	final static public int T6_CD7_TEAMAPPLICATION = 7;
	final static public int T6_CD8_JOIN = 8;
	final static public int T6_CD9_MATCH = 9;
	final static public int T6_CD10_DIALOGUE = 10;
	final static public int T6_CD11_ADDRESS = 11;
	final static public int T6_CD12_ADDRESSSPECIFIC = 12;
	
	// Type 7~10 Code 추가되어야 함
	
	public short[] head = new short[6];
	public byte[] body = null;
	
	final static public int LEN_HEAD = 12; // 12bytes
	final static public int LEN_DATA_MAX = 10000; // 10000bytes
	
	public Protocol() { 
		head[0] = UNDEFINED; head[1] = UNDEFINED; head[2] = UNDEFINED;
	 	head[3] = UNDEFINED; head[4] = UNDEFINED; head[5] = UNDEFINED; 
	}
	public Protocol(int type) {
		head[0] = (short) type; head[1] = UNDEFINED; head[2] = UNDEFINED;
	 	head[3] = UNDEFINED; head[4] = UNDEFINED; head[5] = UNDEFINED; 
	}
	public Protocol(int type, int code) {
		head[0] = (short) type; head[1] = (short) code; head[2] = UNDEFINED;
	 	head[3] = UNDEFINED; head[4] = UNDEFINED; head[5] = UNDEFINED; 
	}
	public Protocol(int type, int code, int bodyLength, int frag, int isLast, int seqNum) {
		head[0] = (short) type; head[1] = (short) code; head[2] = (short) bodyLength;
		head[3] = (short) frag; head[4] = (short) isLast; head[5] = (short) seqNum; 
	}
	
	static public byte[] stringToBytes(String str, int srcPos, int length) {
		byte[] bytes = str.getBytes();
		byte[] res = new byte[length];
		System.arraycopy(bytes, srcPos, res, 0, res.length);
		
		return res;
	}
	
	static public byte[] shortToBytes(short src) { 
		// Warning! Big Endian
		
		byte[] bytes = new byte[2];
		bytes[0] = (byte)((src >> 8) & 0xFF);
		bytes[1] = (byte)(src & 0xFF);
		
		return bytes;
	}
	
	public void setType(int type) { head[0] = (short) type; }
	public void setCode(int code) { head[1] = (short) code; }
	public void setBodyLength(int bodyLength) { head[2] = (short) bodyLength; }
	public void setFrag(int frag) { head[3] = (short) frag; }
	public void setIsLast(int isLast) { head[4] = (short) isLast; }
	public void setSeqNum(int seqNum) { head[5] = (short) seqNum; }
	
	public int getType() { return (int) head[0]; }
	public int getCode() { return (int) head[1]; }
	public int getBodyLength() { return (int) head[2]; }
	public int getFrag() { return (int) head[3]; }
	public int getIsLast() { return (int) head[4]; }
	public int getSeqNum() { return (int) head[5]; }
	
	public byte[] getPacket() {
		byte[] packet;
		if(body != null)
			packet = new byte[LEN_HEAD + body.length];
		else
			packet = new byte[LEN_HEAD];
				
		
		for(int i=0; i<head.length; i++) {
			System.arraycopy(shortToBytes(head[i]), 0, packet, i*2, 2);
		}
		
		if(body != null)
			System.arraycopy(body, 0, packet, LEN_HEAD, body.length);
		
		return packet;
	}
	
	public void setPakcet(byte[] packet) {
		for(int i=0; i<LEN_HEAD; i += 2) {
			short tmp = 0;
			// Warning! Big Endian
			tmp |= ((0xFF & packet[i]) << 8);
			tmp |= 0xFF & packet[i+1];
			
			head[i/2] = tmp;
		}
		
		body = null;
		body = new byte[packet.length-LEN_HEAD];
		System.arraycopy(packet, LEN_HEAD, body, 0, packet.length-LEN_HEAD);
	}
	
	public void setBody(byte[] bytes) throws Exception{
		if(bytes.length <= 10000) {
			body = bytes;
		} else {
			throw new Exception("bytes length is over 10000byte!! : Overflow");
		}
	}
	
	public byte[] getBody() { return body; }
}
