package Controller;

import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

import Model.Protocol;
import application.Network;
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
			//handleGymAction(event);
		});
		
		addr.setOnMouseClicked(event ->{
			handleAddrAction(event);
		});
		
		tr.setOnAction(event->{
			//handleTrAction(event);
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
			addr2.setItems(list);
			
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
	
//	public void handleTrAction(ActionEvent event){
//		try{
//			
//		}catch(Exception e){
//			e.printStackTrace();
//		}
//	}
}
