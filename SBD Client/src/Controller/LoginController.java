package Controller;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import Model.Protocol;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class LoginController {
	Protocol protocol;
	OutputStream os;
	InputStream is;
	Socket socket = null;
	
    @FXML
    private TextField txtID;

    @FXML
    private Button btnLogin;
    
    @FXML
    private Hyperlink btnRegister;

    @FXML
    private TextField txtPW;
    
    @FXML
    void onBtnRegisterClicked(ActionEvent event) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("../View/UserRegister.fxml"));
	    	Parent root = loader.load();
	        Stage stage = new Stage();
	        
	        UserRegisterController controller = loader.<UserRegisterController>getController();
	        controller.initializer(protocol, os, is);
	        
	        stage.setTitle("회원 가입");
	        stage.initModality(Modality.APPLICATION_MODAL); //모달 팝업 처리
	        stage.setResizable(false); // 크기 조절 막기
	        stage.setScene(new Scene(root));
	        stage.show();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

    @FXML
    void onBtnLoginClicked(ActionEvent event) {
    	String ID = txtID.getText().trim();
    	String PW = txtPW.getText().trim();
    	
    	if(ID.contentEquals("")) {
    		Alert alert = new Alert(AlertType.WARNING);
    		alert.setTitle("Warning");
    		alert.setHeaderText("Warning!!");
    		alert.setContentText("Please fill ID context.");
    		
    		alert.showAndWait();
    		return;
    	}
    	
    	if(PW.contentEquals("")) {
    		Alert alert = new Alert(AlertType.WARNING);
    		alert.setTitle("Warning");
    		alert.setHeaderText("Warning!!");
    		alert.setContentText("Please fill Password context.");
    		
    		alert.showAndWait();
    		return;
    	}
    	
    	Protocol p = new Protocol();
    	p.setType(Protocol.TYPE1_LOGIN_REQ);
    	try {
			p.setBody((ID + "/" + PW).getBytes());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	System.out.printf("%d %d %d %d %d %d\n", p.getType(), p.getCode(), p.getBodyLength(), p.getFrag(), p.getIsLast(), p.getSeqNum());
		System.out.println(new String(p.getBody()));
    	
    	try {
			os.write(p.getPacket());
			os.flush();
			
			protocol = new Protocol();
			byte[] buf = new byte[Protocol.LEN_PROTOCOL_MAX];
			
			is.read(buf);
			
			System.out.println("packet arrived");
			
			protocol.setPakcet(buf);
			int packetType = protocol.getType();
			
			switch(packetType) {
			case Protocol.TYPE2_LOGIN_RES:
				if(protocol.getCode() == Protocol.T2_CD0_FAIL) {
					Alert alert = new Alert(AlertType.WARNING);
		    		alert.setTitle("Warning");
		    		alert.setHeaderText("Warning!!");
		    		alert.setContentText("Please fill Login Info Correctly.");
		    		
		    		alert.showAndWait();
				} else {
					Alert alert = new Alert(AlertType.INFORMATION);
		    		alert.setTitle("Information");
		    		alert.setHeaderText("Login Success");
		    		alert.setContentText("Welcome");
		    		
		    		alert.showAndWait();
				}
				break;
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    public LoginController() {
    	System.out.println("Establishing connection. Please wait ...");
		try {
			socket = new Socket("127.0.0.1", 3333);
			System.out.println("Connected: " + socket);
			t_start();
		} catch (UnknownHostException uhe) {
			System.out.println("Host unknown: " + uhe.getMessage());
			System.exit(0);
		} catch (IOException ioe) {
			System.out.println("Unexpected exception: " + ioe.getMessage());
			System.exit(0);
		}
    }
    
    public void t_start() throws IOException {
    	is = socket.getInputStream();
    	os = socket.getOutputStream();
    	System.out.println("This client binding to server using port: " + socket.getPort());
    }
    
	public void t_stop() {
		try {
			if (is != null)
				is.close();
			if (os != null)
				os.close();
			if (socket != null)
				socket.close();
		} catch (IOException ioe) {
			System.out.println("Error closing ...");
		}
	}
}
