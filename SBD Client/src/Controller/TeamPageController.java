package Controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import Model.Protocol;
import Model.TeamNoticeDataModel;
import application.Network;
import application.UserData;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
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
	
    @FXML private TextField t1_name;
    @FXML private TextField t1_gym;
    @FXML private TextField t1_maxAge;
    @FXML private TextField t1_minAge;
    @FXML private TextField t1_sex;
    @FXML private TextField t1_note;

    @FXML private TextField t2_name;
    @FXML private TextField t2_gym;
    @FXML private TextField t2_maxAge;
    @FXML private TextField t2_minAge;
    @FXML private TextField t2_sex;
    @FXML private TextField t2_note;

    @FXML private TextField t3_name;
    @FXML private TextField t3_gym;
    @FXML private TextField t3_maxAge;
    @FXML private TextField t3_minAge;
    @FXML private TextField t3_sex;
    @FXML private TextField t3_note;
    
    @FXML private TableView<TeamNoticeDataModel> t1_notice;
    @FXML private TableColumn<TeamNoticeDataModel, String> t1_notice_date;
    @FXML private TableColumn<TeamNoticeDataModel, String> t1_notice_author;
    @FXML private TableColumn<TeamNoticeDataModel, String> t1_notice_note;
    
    @FXML private TableView<TeamNoticeDataModel> t2_notice;
    @FXML private TableColumn<TeamNoticeDataModel, String> t2_notice_date;
    @FXML private TableColumn<TeamNoticeDataModel, String> t2_notice_author;
    @FXML private TableColumn<TeamNoticeDataModel, String> t2_notice_note;
    
    @FXML private TableView<TeamNoticeDataModel> t3_notice;
    @FXML private TableColumn<TeamNoticeDataModel, String> t3_notice_date;
    @FXML private TableColumn<TeamNoticeDataModel, String> t3_notice_author;
    @FXML private TableColumn<TeamNoticeDataModel, String> t3_notice_note;
	
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
		
		t1_notice_date.setCellValueFactory(cellData -> cellData.getValue().dateProperty());
		t1_notice_author.setCellValueFactory(cellData -> cellData.getValue().authorProperty());
		t1_notice_note.setCellValueFactory(cellData -> cellData.getValue().noteProperty());
		
		t2_notice_date.setCellValueFactory(cellData -> cellData.getValue().dateProperty());
		t2_notice_author.setCellValueFactory(cellData -> cellData.getValue().authorProperty());
		t2_notice_note.setCellValueFactory(cellData -> cellData.getValue().noteProperty());
		
		t3_notice_date.setCellValueFactory(cellData -> cellData.getValue().dateProperty());
		t3_notice_author.setCellValueFactory(cellData -> cellData.getValue().authorProperty());
		t3_notice_note.setCellValueFactory(cellData -> cellData.getValue().noteProperty());
		
		
		TextField[][] tfArray = { { t1_gym, t1_maxAge, t1_minAge, t1_sex, t1_name, t1_note },
				{ t2_gym, t2_maxAge, t2_minAge, t2_sex, t2_name, t2_note },
				{ t3_gym, t3_maxAge, t3_minAge, t3_sex, t3_name, t3_note } };


		String[] tCaptain = new String[3];

		Protocol protocol = new Protocol(Protocol.UNDEFINED);
		protocol.setType(Protocol.TYPE5_VIEW_REQ);
		protocol.setCode(Protocol.T5_CD3_TEAMINFO);

		for (int i = 0; i < UserData.getTeamcnt(); i++) {
			try {
				protocol.setString(UserData.getTeamid()[i] + "");
				Network.send(protocol);

				Protocol packet = Network.read();

				String[] body = packet.getString().split("/");

				for (int j = 1; j < body.length - 2; j++) {
					tfArray[i][j-1].setText(body[j] + "");
				}
				tCaptain[i] = body[7];
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		ObservableList<TeamNoticeDataModel> myList = FXCollections.observableArrayList();
		
		protocol = new Protocol(Protocol.UNDEFINED);
		protocol.setType(Protocol.TYPE5_VIEW_REQ);
		protocol.setCode(Protocol.T5_CD4_TEAMNOTICE);
		
		for(int i=0; i<UserData.getTeamcnt(); i++) {
			try {
				protocol.setString(UserData.getTeamid()[i] + "");
				Network.send(protocol);
				
				Protocol packet = Network.read();
				
				String[] body = packet.getString().split("/");
				
				for(int j=0; j<body.length; j += 3) {
					myList.add(new TeamNoticeDataModel(new SimpleStringProperty(body[j]), new SimpleStringProperty(body[j+1]), new SimpleStringProperty(body[j+2])));
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		t1_notice.setItems(myList);
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
