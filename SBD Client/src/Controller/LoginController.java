package Controller;

import application.Network;
import application.UserData;

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
			String ID = txtID.getText();
			String PW = txtPW.getText();

			Check.isFill(ID, PW);

			Protocol p = new Protocol();
			// message passing - send
			p.setType(Protocol.TYPE1_LOGIN_REQ);
			p.setString(ID+"/"+PW);
			Network.send(p);
			Check.printPacket(p);

			// message passing - receive
			p = Network.read();
			Check.printPacket(p);

			switch (p.getCode()) {
			case Protocol.T2_CD0_FAIL:
				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("Warning");
				alert.setHeaderText("Warning!!");
				alert.setContentText("Please fill Login Info Correctly.");
				alert.showAndWait();
				break;
			case Protocol.T2_CD1_SUCCESS:
				UserData.setUserid(ID);
				setUserData(ID);
				Parent UserRegister = FXMLLoader.load(getClass().getResource("../View/MyPage.fxml"));
				Scene scene = new Scene(UserRegister);
				Stage primaryStage = (Stage)btnLogin.getScene().getWindow();
				primaryStage.setScene(scene);
				primaryStage.setTitle("마이페이지");
				break;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	private void setUserData(String uid) throws IOException{
		Protocol p = new Protocol();
		// message passing - send
		p.setType(Protocol.TYPE5_VIEW_REQ);
		p.setCode(Protocol.T5_CD2_TEAM);
		p.setString(uid);
		Network.send(p);
		Check.printPacket(p);

		// message passing - receive
		p = Network.read();
		String[] str = p.getString().split("/");
		if(str.length>=2){
			for(int i=0;i<str.length; i+=2){
				UserData.setTeam(Integer.parseInt(str[i]), str[i+1]);
			}
			UserData.printTeam();
		}
	}
	
}
