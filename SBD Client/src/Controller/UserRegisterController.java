package Controller;

import java.net.URL;
import java.util.ResourceBundle;

import Model.Protocol;
import application.Network;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

public class UserRegisterController implements Initializable {
	
	@FXML private Button login;
	@FXML private TextField id;
	@FXML private Button check;
	@FXML private TextField pw;
	@FXML private TextField name;
	@FXML private ToggleGroup sex;
	@FXML private DatePicker birthday;
	@FXML private TextField phone;
	@FXML private ComboBox addr1;
	@FXML private ComboBox addr2;
	@FXML private Button register;
    
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		login.setOnAction(event->{
			handleLoginAction(event);
		});
		
		check.setOnAction(event ->{
			handleCheckAction(event);
		});
		
		register.setOnAction(event ->{
			handleRegisterAction(event);
		});
		
		addr1.setOnMouseClicked(event ->{
			handleAddr1Action(event);
		});
		
		addr2.setOnMouseClicked(event ->{
			handleAddr2Action(event);
		});
	}
	
	
	
	public void handleLoginAction(ActionEvent event){
		try{
			Parent Login = FXMLLoader.load(getClass().getResource("../View/Login.fxml"));
			Scene scene = new Scene(Login);
			Stage primaryStage = (Stage)login.getScene().getWindow();
			primaryStage.setScene(scene);
			primaryStage.setTitle("로그인");
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void handleCheckAction(ActionEvent event){
		try{
			String ID = id.getText().trim();
			
			Check.isFill(ID);

			Protocol p = new Protocol();
			// message passing - send
			//p.setType();
			p.setBody((ID).getBytes());
			Network.os.write(p.getPacket());
			Network.os.flush();

			// message passing - receive
			byte[] buf = new byte[Protocol.LEN_PROTOCOL_MAX];
			Network.is.read(buf);
			p.setPakcet(buf);
			int packetType = p.getType();

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
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void handleRegisterAction(ActionEvent event){
		try{
			String ID = id.getText().trim();
			String PW = pw.getText().trim();
			String NAME = name.getText().trim();
			String SEX = (String) sex.getSelectedToggle().getUserData();
			String BIRTHDAY = birthday.getValue().toString();
			String PHONE = phone.getText().trim();
			String ADDR1 = (String) addr1.getValue();
			String ADDR2 = (String) addr2.getValue();
			
			Check.isFill(ID,PW,NAME,SEX,BIRTHDAY, PHONE, ADDR1, ADDR2);
			
			String body = ID + PW + NAME + SEX + BIRTHDAY + PHONE + ADDR1 + ADDR2;
			
			Protocol p = new Protocol();
			// message passing - send
			//p.setType();
			p.setBody(body.getBytes());
			Network.os.write(p.getPacket());
			Network.os.flush();

			// message passing - receive
			byte[] buf = new byte[Protocol.LEN_PROTOCOL_MAX];
			Network.is.read(buf);
			p.setPakcet(buf);
			int packetType = p.getType();

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
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void handleAddr1Action(MouseEvent event){
		try{
			
			Protocol p = new Protocol();
			// message passing - send
			//p.setType();
			//p.setBody();
			Network.os.write(p.getPacket());
			Network.os.flush();

			// message passing - receive
			byte[] buf = new byte[Protocol.LEN_PROTOCOL_MAX];
			Network.is.read(buf);
			p.setPakcet(buf);
			int packetType = p.getType();

			//combobox에 추가하기
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void handleAddr2Action(MouseEvent event){
		try{
			
			Protocol p = new Protocol();
			// message passing - send
			//p.setType();
			//p.setBody();
			Network.os.write(p.getPacket());
			Network.os.flush();

			// message passing - receive
			byte[] buf = new byte[Protocol.LEN_PROTOCOL_MAX];
			Network.is.read(buf);
			p.setPakcet(buf);
			int packetType = p.getType();

			//combobox에 추가하기
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
