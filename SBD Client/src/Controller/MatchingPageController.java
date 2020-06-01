package Controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

public class MatchingPageController implements Initializable {

	@FXML private Circle my;
	@FXML private ImageView my1;
	@FXML private Circle team;
	@FXML private ImageView team1;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		my.setOnMouseClicked(event ->{
			handleMyAction(event);
		});
		
		my1.setOnMouseClicked(event ->{
			handleMyAction(event);
		});
		
		team.setOnMouseClicked(event ->{
			handleTeamAction(event);
		});
		
		team1.setOnMouseClicked(event ->{
			handleTeamAction(event);
		});
		
		
	}

	public void handleMyAction(MouseEvent event){
		 try {
				Parent UserRegister = FXMLLoader.load(getClass().getResource("../View/MyPage.fxml"));
				Scene scene = new Scene(UserRegister);
				Stage primaryStage = (Stage)team.getScene().getWindow();
				primaryStage.setScene(scene);
				primaryStage.setTitle("마이페이지");
			} catch (Exception e) {
				e.printStackTrace();
			}
	}
	
	public void handleTeamAction(MouseEvent event){
		 try {
				Parent UserRegister = FXMLLoader.load(getClass().getResource("../View/TeamPage.fxml"));
				Scene scene = new Scene(UserRegister);
				Stage primaryStage = (Stage)team.getScene().getWindow();
				primaryStage.setScene(scene);
				primaryStage.setTitle("팀페이지");
			} catch (Exception e) {
				e.printStackTrace();
			}
	}
}
