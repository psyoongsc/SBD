package Controller;

import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

import Model.Protocol;
import application.Network;
import application.UserData;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseEvent;

public class MakeTeamController implements Initializable {

	@FXML private TextField tid;
	@FXML private Button check;
	@FXML private ComboBox addr1;
	@FXML private ComboBox addr2;
	@FXML private ComboBox gym;
	@FXML private TextField minage;
	@FXML private TextField maxage;
	@FXML private ComboBox addr;
	@FXML private ToggleGroup sex;
	@FXML private TextArea td;
	@FXML private Button tr;
	
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		check.setOnAction(event->{
			handleCheckAction(event);
		});
		
		addr1.setOnMouseClicked(event ->{
			handleAddr1Action(event);
		});
		
		addr2.setOnMouseClicked(event ->{
			handleAddr2Action(event);
		});
		
		gym.setOnMouseClicked(event ->{
			handleGymAction(event);
		});
		
		addr.setOnMouseClicked(event ->{
			handleAddrAction(event);
		});
		
		tr.setOnAction(event->{
			handleTrAction(event);
		});
	}
	
	public void handleCheckAction(ActionEvent event){
		try{
			String ID = tid.getText();
			
			Check.isFill(ID);

			Protocol p = new Protocol();
			// message passing - send
			p.setType(Protocol.TYPE11_CHECK_REQ);
			p.setCode(Protocol.T11_CD1_TEAMNAME);
			p.setString(ID);
			Network.send(p);
			Check.printPacket(p);

			// message passing - receive
			p = Network.read();
			Check.printPacket(p);
			
			switch (p.getCode()) {
			case Protocol.T12_CD0_FAIL:
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("중복된 ID입니다");
				alert.setHeaderText("중복된 ID입니다");
				alert.setContentText("중복된 ID입니다");
				alert.showAndWait();
				break;
			case Protocol.T12_CD1_SUCCESS:
				Alert alert1 = new Alert(AlertType.WARNING);
				alert1.setTitle("사용가능한 ID입니다");
				alert1.setHeaderText("사용가능한 ID입니다");
				alert1.setContentText("사용가능한 ID입니다");
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
	
	public void handleGymAction(MouseEvent event){
		try{
			Protocol p = new Protocol();
			// message passing - send
			p.setType(Protocol.TYPE5_VIEW_REQ);
			p.setCode(Protocol.T5_CD13_GYM);
			p.setString((String)addr1.getValue()+"/"+(String)addr2.getValue());
			Network.send(p);
			Check.printPacket(p);

			// message passing - receive
			p = Network.read();
			Check.printPacket(p);
			
			//값넣기
			String str = p.getString();
			List<String> addr = Arrays.asList(str.split("/"));
			ObservableList<String> list = FXCollections.observableArrayList(addr);
			gym.setItems(list);
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void handleAddrAction(MouseEvent event){
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
			List<String> li = Arrays.asList(str.split("/"));
			ObservableList<String> list = FXCollections.observableArrayList(li);
			addr.setItems(list);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void handleTrAction(ActionEvent event){
		try{
			
			String TID = tid.getText();
			String GYM = (String) gym.getValue();
			String ID = UserData.getUserid();
			String MINAGE = minage.getText();
			String MAXAGE = maxage.getText();
			String ADDR = (String) addr.getValue();
			String SEX = (String) sex.getSelectedToggle().getUserData();
			String TD = td.getText();
			
			Check.isFill(TID,GYM,ID,MINAGE,MAXAGE,ADDR,SEX,TD);
			
			String body= TID +"/"+ GYM +"/"+ ID +"/"+ MINAGE +"/"+ MAXAGE +"/"+ ADDR +"/"+ SEX +"/"+ TD;
			
			Protocol p = new Protocol();
			// message passing - send
			p.setType(Protocol.TYPE3_REGISTER_REQ);
			p.setCode(Protocol.T3_CD1_TEAM);
			p.setString(body);
			Network.send(p);
			
			// message passing - receive
			p = Network.read();
			switch (p.getCode()) {
			case Protocol.T2_CD0_FAIL:
				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("팀등록 실패");
				alert.setHeaderText("팀등록 실패");
				alert.setContentText("팀등록 실패");
				alert.showAndWait();
				break;
			case Protocol.T2_CD1_SUCCESS:
				Alert alert1 = new Alert(AlertType.WARNING);
				alert1.setTitle("팀등록 성공");
				alert1.setHeaderText("팀등록 성공");
				alert1.setContentText("팀등록 성공");
				alert1.showAndWait();
				
				//UserData갱신하기
				break;
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
