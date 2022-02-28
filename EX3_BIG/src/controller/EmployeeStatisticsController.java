package controller;


import java.io.File;

import application.Main;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import model.DeliveryPerson;
import utils.Expertise;

public class EmployeeStatisticsController {
	//GetCooksByExpertise
	//getDeliveriesByPerson

	
	@FXML
	private AnchorPane mainScreen;
	@FXML
	private AnchorPane resultScreen;
	@FXML
	private AnchorPane data;
	@FXML
	private AnchorPane data2;
	@FXML
	private AnchorPane resultScreen2;
	@FXML
	private ChoiceBox<Expertise> expertise;
	@FXML
	private Label result;
	@FXML
	private Label result2;
	
	@FXML
	private ChoiceBox<DeliveryPerson> delPerBox;
	@FXML
	private ChoiceBox<Integer> MonthBox;
	
	
	
	@FXML
	public void initialize() {
		
		//Initialize the entries in the list
		expertise.setItems(FXCollections.observableArrayList(Expertise.values()));
		MonthBox.setItems(FXCollections.observableArrayList(1,2,3,4,5,6,7,8,9,10,11,12));
		delPerBox.setItems(FXCollections.observableArrayList(Main.restaurant.getDeliveryPersons().values()));

		resultScreen.setVisible(false);
		resultScreen2.setVisible(false);
		
	}
	
	@FXML
	public void showResult (ActionEvent event) throws Exception {
		
		//NEW SOUND EFFECT
    	Media sound = new Media(new File("src\\clickSound.mp4").toURI().toString());
    	MediaPlayer mediaPlayer=new MediaPlayer(sound);
    	mediaPlayer.play();
    	
    	
    	mediaPlayer.setOnReady(new Runnable() {
            @Override
            public void run() {
            	mediaPlayer.play();
            }
        });
		
		boolean isRight = false;
		
		if (isRight == false) {
			if (expertise.getValue() == null) {
				data.setStyle("-fx-border-color: red; -fx-border-width: 2px");
				playErrorSound();
			}
			else {
				isRight = true;
				data.setStyle("-fx-border-color: white; -fx-border-width: 1px");
			}
		}
		if (isRight) {
			data.setStyle("-fx-border-color: white; -fx-border-width: 1px");
			Expertise e = expertise.getSelectionModel().getSelectedItem();
			result.setText(Main.restaurant.GetCooksByExpertise(e).toString());
			resultScreen.setVisible(true);
			resultScreen2.setVisible(false);
		}
	}
	
	@FXML
	public void showResult2 (ActionEvent event) throws Exception {
		
		//NEW SOUND EFFECT
    	Media sound = new Media(new File("src\\clickSound.mp4").toURI().toString());
    	MediaPlayer mediaPlayer=new MediaPlayer(sound);
    	mediaPlayer.play();
    	
    	
    	mediaPlayer.setOnReady(new Runnable() {
            @Override
            public void run() {
            	mediaPlayer.play();
            }
        });
		
		boolean isRight = false;
		
		if (isRight == false) {
			if (delPerBox.getValue() == null || MonthBox.getValue() == null ) {
				data.setStyle("-fx-border-color: red; -fx-border-width: 2px");
				playErrorSound();
			}
			else {
				isRight = true;
				data.setStyle("-fx-border-color: white; -fx-border-width: 1px");
			}
		}
		
		result2.setText(Main.restaurant.getDeliveriesByPerson(delPerBox.getValue(), MonthBox.getValue()).toString());
		resultScreen.setVisible(false);
		resultScreen2.setVisible(true);
	}
	
	@FXML
	public void playErrorSound() {
		
		Media sound = new Media(new File("src\\ErrorSound.mp4").toURI().toString());
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
