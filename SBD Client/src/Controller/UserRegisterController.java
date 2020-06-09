package Controller;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

import javax.xml.crypto.dsig.keyinfo.PGPData;

import Model.Protocol;
import application.Network;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
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
			primaryStage.setTitle("�α���");
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
			p.setType(Protocol.TYPE11_CHECK_REQ);
			p.setCode(Protocol.T11_CD0_USERID);
			p.setString(ID);
			Network.send(p);
			Check.printPacket(p);

			// message passing - receive
			p = Network.read();
			Check.printPacket(p);
			
			switch (p.getCode()) {
			case Protocol.T2_CD0_FAIL:
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("�ߺ��� ID�Դϴ�");
				alert.setHeaderText("�ߺ��� ID�Դϴ�");
				alert.setContentText("�ߺ��� ID�Դϴ�");
				alert.showAndWait();
				break;
			case Protocol.T2_CD1_SUCCESS:
				Alert alert1 = new Alert(AlertType.WARNING);
				alert1.setTitle("��밡���� ID�Դϴ�");
				alert1.setHeaderText("��밡���� ID�Դϴ�");
				alert1.setContentText("��밡���� ID�Դϴ�");
				alert1.showAndWait();
				break;
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void handleRegisterAction(ActionEvent event){
		try{
			String ID = id.getText();
			String PW = pw.getText();
			String NAME = name.getText();
			String SEX = (String) sex.getSelectedToggle().getUserData();
			String BIRTHDAY = birthday.getValue().toString();
			String PHONE = phone.getText();
			String ADDR1 = (String) addr1.getValue();
			String ADDR2 = (String) addr2.getValue();
			
			Check.isFill(ID,PW,NAME,SEX,BIRTHDAY, PHONE, ADDR1, ADDR2);
			
			String body = ID +"/"+ PW +"/"+ NAME +"/"+ SEX +"/"+ BIRTHDAY +"/"+ PHONE +"/"+ ADDR1 +"/"+ ADDR2;
			
			Protocol p = new Protocol();
			// message passing - send
			p.setType(Protocol.TYPE3_REGISTER_REQ);
			p.setCode(Protocol.T3_CD0_USER);
			p.setString(body);
			Network.send(p);
			
			// message passing - receive
			p = Network.read();

			switch (p.getCode()) {
			case Protocol.T2_CD0_FAIL:
				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("ȸ����� ����");
				alert.setHeaderText("ȸ����� ����");
				alert.setContentText("ȸ����� ����");
				alert.showAndWait();
				break;
			case Protocol.T2_CD1_SUCCESS:
				Alert alert1 = new Alert(AlertType.WARNING);
				alert1.setTitle("ȸ����� ����");
				alert1.setHeaderText("ȸ����� ����");
				alert1.setContentText("ȸ����� ����");
				alert1.showAndWait();
				break;
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void handleAddr1Action(MouseEvent event){
		try{
			Protocol p = new Protocol();
			// message passing - send
			p.setType(Protocol.TYPE5_VIEW_REQ);
			p.setCode(Protocol.T5_CD11_ADDRESS);
			Network.send(p);
			Check.printPacket(p);
			
			// message passing - receive
			p = Network.read();
			Check.printPacket(p);
			
			String str = p.getString();
			List<String> addr = Arrays.asList(str.split("/"));
			ObservableList<String> list = FXCollections.observableArrayList(addr);
			addr1.setItems(list);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void handleAddr2Action(MouseEvent event){
		try{
			
			Protocol p = new Protocol();
			// message passing - send
			p.setType(Protocol.TYPE5_VIEW_REQ);
			p.setCode(Protocol.T5_CD12_ADDRESSSPECIFIC);
			p.setString((String)addr1.getValue());
			Network.send(p);
			Check.printPacket(p);

			// message passing - receive
			p = Network.read();
			Check.printPacket(p);
			
			String str = p.getString();
			List<String> addr = Arrays.asList(str.split("/"));
			ObservableList<String> list = FXCollections.observableArrayList(addr);
			addr2.setItems(list);
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
