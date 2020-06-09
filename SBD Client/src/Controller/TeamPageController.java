package Controller;

import java.net.URL;
import java.util.ResourceBundle;

import application.Main;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

public class TeamPageController implements Initializable {

	@FXML private Circle my;
	@FXML private ImageView my1;
	@FXML private Circle match;
	@FXML private ImageView match1;
	@FXML private Button make;
	@FXML private Button recruit;
	@FXML private Button join;
	
	private String userid;
	private int[] teamid;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		
		
		my.setOnMouseClicked(event->{
			handleMyAction(event);
		});
		
		my1.setOnMouseClicked(event->{
			handleMyAction(event);
		});
		
		match.setOnMouseClicked(event->{
			handleMatchAction(event);
		});
		
		match1.setOnMouseClicked(event->{
			handleMatchAction(event);
		});
		
		make.setOnAction(event -> {
			handleMakeAction(event);
		});
		
		recruit.setOnAction(event -> {
			handleRecruitAction(event);
		});
		
		join.setOnAction(event -> {
			handleJoinAction(event);
		});
	}

	public void handleJoinAction(ActionEvent event){
		try {
			FXMLLoader another = new FXMLLoader(getClass().getResource( "../View/JoinTeam.fxml" ) );
			Parent pane = (Parent) another.load();
			Stage stage = new  Stage();
			stage.setScene(new Scene(pane));
			stage.setTitle("팀가입하기");
			stage.show();
		} catch (Exception e) {
		   // TODO Auto-generated catch block
		   e.printStackTrace();
		}
	}
	
	public void handleRecruitAction(ActionEvent event){
		try {
			FXMLLoader another = new FXMLLoader(getClass().getResource( "../View/RecruitTeam.fxml" ) );
			Parent pane = (Parent) another.load();
			Stage stage = new  Stage();
			stage.setScene(new Scene(pane));
			stage.setTitle("팀원모집하기");
			stage.show();
		} catch (Exception e) {
		   // TODO Auto-generated catch block
		   e.printStackTrace();
		}
	}
	
	public void handleMakeAction(ActionEvent event){
		try {
			FXMLLoader another = new FXMLLoader(getClass().getResource( "../View/MakeTeam.fxml" ) );
			Parent pane = (Parent) another.load();
			Stage stage = new  Stage();
			stage.setScene(new Scene(pane));
			stage.setTitle("팀만들기");
			stage.show();
		} catch (Exception e) {
		   // TODO Auto-generated catch block
		   e.printStackTrace();
		}
	}
	
	public void handleMyAction(MouseEvent event){
		 try {
				Parent UserRegister = FXMLLoader.load(getClass().getResource("../View/MyPage.fxml"));
				Scene scene = new Scene(UserRegister);
				Stage primaryStage = (Stage)my.getScene().getWindow();
				primaryStage.setScene(scene);
				primaryStage.setTitle("마이페이지");
			} catch (Exception e) {
				e.printStackTrace();
			}
	}
	
	public void handleMatchAction(MouseEvent event){
		 try {
				Parent UserRegister = FXMLLoader.load(getClass().getResource("../View/MatchingPage.fxml"));
				Scene scene = new Scene(UserRegister);
				Stage primaryStage = (Stage)my.getScene().getWindow();
				primaryStage.setScene(scene);
				primaryStage.setTitle("매칭페이지");
			} catch (Exception e) {
				e.printStackTrace();
			}
	}
}
