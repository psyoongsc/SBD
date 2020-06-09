package Controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

public class MyPageController implements Initializable {

	@FXML private Circle team;
	@FXML private ImageView team1;
	@FXML private Circle match;
	@FXML private ImageView match1;
	@FXML private Button modify;
	@FXML private Button drop;
	@FXML private TableView calendar;
	@FXML private TableView info;
	@FXML private TableColumn teamname;
	@FXML private TableColumn gym;
	@FXML private TableColumn datetime;
	@FXML private TableColumn attribute;
	@FXML private TableColumn value;

	private String userid;
	private int[] teamid;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		HandleCalenderAction();
		HandleInfoAction();
		
		// TODO Auto-generated method stub
		team.setOnMouseClicked(event->{
			handleTeamAction(event);
		});
		 
		team1.setOnMouseClicked(event->{
			handleTeamAction(event);
		});
		
		match.setOnMouseClicked(event->{
			handleMatchAction(event);
		});
		
		match1.setOnMouseClicked(event->{
			handleMatchAction(event);
		});
		
		modify.setOnAction(event ->{
			handleModifyAction(event);
		});
		
		drop.setOnAction(event ->{
			handleModifyAction(event);
		});
	}
	
	public void HandleCalenderAction(){
		
	};
	
	public void HandleInfoAction(){
		
	};
	
	public void handleDropAction(ActionEvent event){
		
	}
	
	public void handleModifyAction(ActionEvent event){
		
	}
	
	public void handleTeamAction(MouseEvent event){
		 try {
				Parent UserRegister = FXMLLoader.load(getClass().getResource("../View/TeamPage.fxml"));
				Scene scene = new Scene(UserRegister);
				Stage primaryStage = (Stage)team.getScene().getWindow();
				primaryStage.setScene(scene);
				primaryStage.setTitle("∆¿∆‰¿Ã¡ˆ");
			} catch (Exception e) {
				e.printStackTrace();
			}
	}
	
	public void handleMatchAction(MouseEvent event){
		 try {
				Parent UserRegister = FXMLLoader.load(getClass().getResource("../View/MatchingPage.fxml"));
				Scene scene = new Scene(UserRegister);
				Stage primaryStage = (Stage)team.getScene().getWindow();
				primaryStage.setScene(scene);
				primaryStage.setTitle("∏≈ƒ™∆‰¿Ã¡ˆ");
			} catch (Exception e) {
				e.printStackTrace();
			}
	}
}
