package controller;

import java.io.File;

import application.Main;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import model.Dish;

public class FinanceController {
	
	
	@FXML
	private AnchorPane mainScreen;
	@FXML
	private ListView<Dish> profitRelationList;
	ObservableList<Dish> observableArrayList = FXCollections.observableArrayList(Main.restaurant.getProfitRelation());
	@FXML
	private Label revenue;
	
	
	@FXML
	public void initialize() {		
		profitRelationList.setItems(observableArrayList);
		revenue.setText("" + Main.restaurant.revenueFromExpressDeliveries() + "$");		
	}
	
	@FXML
	public void exportToWordDoc (ActionEvent event) throws Exception {
		Media sound = new Media(new File("src\\clickSound.mp4").toURI().toString());
		MediaPlayer mediaPlayer=new MediaPlayer(sound);
		mediaPlayer.play();
		
		
		mediaPlayer.setOnReady(new Runnable() {
	        @Override
	        public void run() {
	        	mediaPlayer.play();
	        }
	    });
		
		Main.restaurant.exportToWordDoc();
		
		//New Alert
		Alert a = new Alert(AlertType.CONFIRMATION);
		a.setTitle("CONFIRMATION");
		a.setHeaderText("The file was extracted successfully");
		a.showAndWait();
	}

	

	
	
	
	

}
