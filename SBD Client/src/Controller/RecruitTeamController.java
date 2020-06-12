package Controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

import application.UserData;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Circle;

public class RecruitTeamController implements Initializable  {

	@FXML private ComboBox selectteam;
	@FXML private ComboBox term;
	@FXML private TextArea text;
	@FXML private Button post;
	
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		selectteam.setOnMouseClicked(event ->{
			handleSelectTeamAction(event);
		});
		
		term.setOnMouseClicked(event ->{
			handleTermAction(event);
		});
		
		post.setOnAction(event->{
			handlePostAction(event);
		});
	}

	public void handleSelectTeamAction(MouseEvent event){
		List<String> addr = Arrays.asList(UserData.getTeamname());
		ObservableList<String> list = FXCollections.observableArrayList(addr);
		selectteam.setItems(list);
	}
	
	public void handleTermAction(MouseEvent event){
		List<String> addr = new ArrayList<String>(){{
			add("1老");			add("2老");
			add("3老");			add("4老");
			add("5老");			add("6老");
			add("7老");
		}};
		ObservableList<String> list = FXCollections.observableArrayList(addr);
		term.setItems(list);
	}
	
	public void handlePostAction(ActionEvent event){
		
	}
}
