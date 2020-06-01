package Controller;

import application.Network;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ResourceBundle;

import Model.Protocol;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class LoginController implements Initializable {

	@FXML private TextField txtID;
	@FXML private TextField txtPW;
	@FXML private Button btnLogin;
	@FXML private Button btnRegister;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		// 로그인 버튼 핸들러
		btnLogin.setOnAction(event -> {
			handleBtnLoginAction(event);
		});

		// 회원가입 버튼 핸들러
		btnRegister.setOnAction(event -> {
			handleBtnRegisterAction(event);
		});

	}

	public void handleBtnRegisterAction(ActionEvent event){
		try {
			Parent UserRegister = FXMLLoader.load(getClass().getResource("../View/UserRegister.fxml"));
			Scene scene = new Scene(UserRegister);
			Stage primaryStage = (Stage)btnLogin.getScene().getWindow();
			primaryStage.setScene(scene);
			primaryStage.setTitle("회원 가입");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void handleBtnLoginAction(ActionEvent event){
		try {
//			String ID = txtID.getText().trim();
//			String PW = txtPW.getText().trim();
//
//			Check.isFill(ID, PW);
//
//			Protocol p = new Protocol();
//			// message passing - send
//			// set head
//			p.setType(Protocol.TYPE1_LOGIN_REQ);
//			// set body
//			p.setBody((ID + "/" + PW).getBytes());
//			// send
//			Network.os.write(p.getPacket());
//			Network.os.flush();
//			// printPakcet
//			Check.printPacket(p);
//
//			// message passing - receive
//			// read buf
//			byte[] buf = new byte[Protocol.LEN_PROTOCOL_MAX];
//			Network.is.read(buf);
//			// set Protocol
//			p.setPakcet(buf);
//			// get Type
//			int packetType = p.getType();
//			Check.printPacket(p);
//
//			switch (packetType) {
//			case Protocol.T2_CD0_FAIL:
//				Alert alert = new Alert(AlertType.WARNING);
//				alert.setTitle("Warning");
//				alert.setHeaderText("Warning!!");
//				alert.setContentText("Please fill Login Info Correctly.");
//				alert.showAndWait();
//				break;
//			case Protocol.T2_CD1_SUCCESS:
//				alert = new Alert(AlertType.INFORMATION);
//				alert.setTitle("Information");
//				alert.setHeaderText("Login Success");
//				alert.setContentText("Welcome");
//				alert.showAndWait();
//				break;
//			}
			
			Parent UserRegister = FXMLLoader.load(getClass().getResource("../View/MyPage.fxml"));
			Scene scene = new Scene(UserRegister);
			Stage primaryStage = (Stage)btnLogin.getScene().getWindow();
			primaryStage.setScene(scene);
			primaryStage.setTitle("마이페이지");

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
}
