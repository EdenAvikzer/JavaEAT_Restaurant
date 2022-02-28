package controller;

import java.io.File;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class QuereiesMenuController {
	
	
	@FXML
	private AnchorPane mainScreen;
	@FXML
	private AnchorPane replaceScreen;
	@FXML
	private Label status;
	@FXML
	private Button a;
	@FXML
	private Button b;
	@FXML
	private Button c;
	@FXML
	private Button d;
	@FXML
	private Button e;
	@FXML
	private Button f;
	
	
	
	
	@FXML
	public void Logout (ActionEvent event) throws Exception {
		playSound();
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/LoginFX.fxml"));
		AnchorPane pane = loader.load();
		mainScreen.getChildren().removeAll(mainScreen.getChildren());
		mainScreen.getChildren().add(pane);
	}
	
	@FXML
	public void goBack (ActionEvent event) throws Exception {
		playSound();
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/ManagerView.fxml"));
		AnchorPane pane = loader.load();
		mainScreen.getChildren().removeAll(mainScreen.getChildren());
		mainScreen.getChildren().add(pane);
	}
	
	@FXML
	public void Finance (ActionEvent event) throws Exception {
		playSound();
		a.setStyle("-fx-background-color: #ffef8a" );
		b.setStyle("-fx-background-color: #d1d1c2" );
		c.setStyle("-fx-background-color: #d1d1c2" );
		d.setStyle("-fx-background-color: #d1d1c2" );
		e.setStyle("-fx-background-color: #d1d1c2" );
		f.setStyle("-fx-background-color: #d1d1c2" );
		 
		status.setText("Finance");
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/Finance.fxml"));
		AnchorPane pane = loader.load();
		replaceScreen.getChildren().removeAll(replaceScreen.getChildren());
		replaceScreen.getChildren().add(pane);
		
	}
	@FXML
	public void DeliveriesPerType (ActionEvent event) throws Exception {
		playSound();
		
		b.setStyle("-fx-background-color: #ffef8a" );
		
		a.setStyle("-fx-background-color: #d1d1c2" );
		c.setStyle("-fx-background-color: #d1d1c2" );
		d.setStyle("-fx-background-color: #d1d1c2" );
		e.setStyle("-fx-background-color: #d1d1c2" );
		f.setStyle("-fx-background-color: #d1d1c2" );
		
		status.setText("Deliveries Per Type");
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/StatusticsQueries.fxml"));
		AnchorPane pane = loader.load();
		replaceScreen.getChildren().removeAll(replaceScreen.getChildren());
		replaceScreen.getChildren().add(pane);
	}
	
	@FXML
	public void popularComponents (ActionEvent event) throws Exception {
		playSound();
		c.setStyle("-fx-background-color: #ffef8a" );
		a.setStyle("-fx-background-color: #d1d1c2" );
		b.setStyle("-fx-background-color: #d1d1c2" );
		d.setStyle("-fx-background-color: #d1d1c2" );
		e.setStyle("-fx-background-color: #d1d1c2" );
		f.setStyle("-fx-background-color: #d1d1c2" );
		
		status.setText("Get Popular Components");
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/StatusticsQueries.fxml"));
		AnchorPane pane = loader.load();
		replaceScreen.getChildren().removeAll(replaceScreen.getChildren());
		replaceScreen.getChildren().add(pane);
	}
	
	
	@FXML
	public void createAIMacine (ActionEvent event) throws Exception {
		playSound();
		d.setStyle("-fx-background-color: #ffef8a" );
		a.setStyle("-fx-background-color: #d1d1c2" );
		b.setStyle("-fx-background-color: #d1d1c2" );
		c.setStyle("-fx-background-color: #d1d1c2" );
		e.setStyle("-fx-background-color: #d1d1c2" );
		f.setStyle("-fx-background-color: #d1d1c2" );
		
		status.setText("");
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/AIMacine.fxml"));
		AnchorPane pane = loader.load();
		replaceScreen.getChildren().removeAll(replaceScreen.getChildren());
		replaceScreen.getChildren().add(pane);
	}
		
	
	@FXML
	public void employees (ActionEvent event) throws Exception {
		playSound();
		e.setStyle("-fx-background-color: #ffef8a" );
		a.setStyle("-fx-background-color: #d1d1c2" );
		b.setStyle("-fx-background-color: #d1d1c2" );
		c.setStyle("-fx-background-color: #d1d1c2" );
		d.setStyle("-fx-background-color: #d1d1c2" );
		f.setStyle("-fx-background-color: #d1d1c2" );
		
		status.setText("Employee's Statistics");
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/EmployeeStatistics.fxml"));
		AnchorPane pane = loader.load();
		replaceScreen.getChildren().removeAll(replaceScreen.getChildren());
		replaceScreen.getChildren().add(pane);
	}
	
	@FXML
	public void releventDishList (ActionEvent event) throws Exception {
		playSound();
		f.setStyle("-fx-background-color: #ffef8a" );
		a.setStyle("-fx-background-color: #d1d1c2" );
		b.setStyle("-fx-background-color: #d1d1c2" );
		c.setStyle("-fx-background-color: #d1d1c2" );
		d.setStyle("-fx-background-color: #d1d1c2" );
		e.setStyle("-fx-background-color: #d1d1c2" );
		
		status.setText("");
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/DishList.fxml"));
		AnchorPane pane = loader.load();
		replaceScreen.getChildren().removeAll(replaceScreen.getChildren());
		replaceScreen.getChildren().add(pane);
	}
	
	
	//NEW SOUND EFFECT
	@FXML
	public void playSound() {
		
		Media sound = new Media(new File("src\\clickSound.mp4").toURI().toString());
		MediaPlayer mediaPlayer=new MediaPlayer(sound);
		mediaPlayer.play();
		
		
		mediaPlayer.setOnReady(new Runnable() {
	        @Override
	        public void run() {
	        	mediaPlayer.play();
	        }
	    });
	}
	
}
