package Controller;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

import Model.Protocol;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;

public class UserRegisterController implements Initializable {
	Protocol protocol;
	OutputStream os;
	InputStream is;
	ObservableList<String> Addr1List = FXCollections.observableArrayList();
	ObservableList<String> Addr2List = FXCollections.observableArrayList();
	
    @FXML
    private TextField txtPhone;

    @FXML
    private ComboBox<String> comboAddr1;

    @FXML
    private DatePicker dateBirthday;

    @FXML
    private TextField txtID;

    @FXML
    private ComboBox<String> comboAddr2;

    @FXML
    private TextField txtName;

    @FXML
    private ComboBox<String> comboSex;

    @FXML
    private TextField txtPW;

    @FXML
    private Button btnRegister;
	
	public void initializer(Protocol _protocol, OutputStream _os, InputStream _is) {
		protocol = _protocol;
		os = _os;
		is = _is;
		
        Protocol reqPacket = new Protocol(Protocol.TYPE5_VIEW_REQ, Protocol.T5_CD11_ADDRESS);
        try {
			os.write(reqPacket.getPacket());
			os.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        byte[] packet = new byte[Protocol.LEN_PROTOCOL_MAX];
        try {
			is.read(packet);
			Protocol resPacket = new Protocol();
			resPacket.setPakcet(packet);
			
			String[] temp = (new String(resPacket.getBody())).trim().split("/");
			
			for(int i=0; i<temp.length; i++) {
				Addr1List.add(temp[i]);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
    public void initialize(URL location, ResourceBundle resources) {
		comboAddr1.setItems(Addr1List);
		comboSex.getItems().addAll("³²", "¿©");
    }
	
    @FXML
    void onComboAddr1Change(ActionEvent event) {
    	Protocol reqPacket = new Protocol(Protocol.TYPE5_VIEW_REQ, Protocol.T5_CD12_ADDRESSSPECIFIC);
        try {
        	reqPacket.setBody(comboAddr1.getValue().trim().getBytes());
			os.write(reqPacket.getPacket());
			os.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        byte[] packet = new byte[Protocol.LEN_PROTOCOL_MAX];
        try {
			is.read(packet);
			Protocol resPacket = new Protocol();
			resPacket.setPakcet(packet);
			
			String[] temp = (new String(resPacket.getBody())).trim().split("/");
			Addr2List = FXCollections.observableArrayList();
			
			for(int i=0; i<temp.length; i++) {
				Addr2List.add(temp[i]);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        comboAddr2.getItems().clear();
        comboAddr2.getItems().addAll(Addr2List);
    }
	
    @FXML
    void onBtnRegisterClicked(ActionEvent event) {
    	String userID = txtID.getText().trim();
    	String userPW = txtPW.getText().trim();
    	String userName = txtName.getText().trim();
    	String userSex = comboSex.getValue().trim();
    	LocalDate userBirthday = dateBirthday.getValue();
    	String userPhone = txtPhone.getText().trim();
    	String userAddr1 = comboAddr1.getValue();
    	String userAddr2 = comboAddr2.getValue();
    	
    	System.out.printf("%s\n%s\n%s\n%s\n%s\n%s\n%s\n%s\n"
    			, userID, userPW, userName, userSex, userBirthday, userPhone, userAddr1, userAddr2);
    	
        Protocol reqPacket = new Protocol(Protocol.TYPE3_REGISTER_REQ, Protocol.T3_CD0_USER);
        try {
        	reqPacket.setBody((userID + "/" + userPW + "/" + userName + "/" + userSex + "/" + userBirthday
        			 + "/" + userPhone + "/" + userAddr1 + "/" + userAddr2).getBytes());
			os.write(reqPacket.getPacket());
			os.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        byte[] packet = new byte[Protocol.LEN_PROTOCOL_MAX];
        try {
			is.read(packet);
			Protocol resPacket = new Protocol();
			resPacket.setPakcet(packet);
			
			if(resPacket.getCode() == Protocol.T4_CD1_SUCCESS) {
				Alert alert = new Alert(AlertType.INFORMATION);
	    		alert.setTitle("Information");
	    		alert.setHeaderText("Register Success");
	    		alert.setContentText("Please Login");
	    		
	    		alert.showAndWait();
			} else {
				Alert alert = new Alert(AlertType.WARNING);
	    		alert.setTitle("Warning");
	    		alert.setHeaderText("Warning!!");
	    		alert.setContentText("Please fill Register Info Correctly.");
	    		
	    		alert.showAndWait();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
}
