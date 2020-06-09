package Controller;

import Model.Protocol;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class Check {

	//printPacket
	public static void printPacket(Protocol p) {
		System.out.printf("%d %d %d %d %d %d\n", p.getType(), p.getCode(), p.getBodyLength(), p.getFrag(),
				p.getIsLast(), p.getSeqNum());
		if(p.getBodyLength()!=0){
			System.out.println(p.getString());			
		}
	}
	
	//isFill
	public static void isFill(String ID) {
		if (ID.contentEquals("")) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Warning");
			alert.setHeaderText("Warning!");
			alert.setContentText( "ID를 입력하세요!");
			alert.showAndWait();
		}
	}
	
	public static void isFill(String ID, String PW) {
		String temp = "";
		if (ID.contentEquals("")) {
			temp = "ID";
		} else if (PW.contentEquals("")) {
			temp = "PW";
		}
		if (!temp.contentEquals("")) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Warning");
			alert.setHeaderText("Warning!");
			alert.setContentText( temp + "를 입력하세요!");
			alert.showAndWait();
		}
	}
	
	public static void isFill(String id, String pw, String name, String sex, String birth, String phone, String addr1, String addr2 ){
		String temp = "";
		if(id.contentEquals("")){ temp = "ID";}
		else if(pw.contentEquals("")){temp ="PW";}
		else if(name.contentEquals("")){temp ="NAME";}
		else if(sex.contentEquals("")){temp ="SEX";}
		else if(birth.contentEquals("")){temp ="BIRTHDAY";}
		else if(phone.contentEquals("")){temp ="PHONE";}
		else if(addr1.contentEquals("")){temp ="ADDR1";}
		else if(addr2.contentEquals("")){temp ="ADDR2";}
		
		if (!temp.contentEquals("")) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Warning");
			alert.setHeaderText("Warning!");
			alert.setContentText( temp + "를 입력하세요!");
			alert.showAndWait();
		}
	}
	
}
