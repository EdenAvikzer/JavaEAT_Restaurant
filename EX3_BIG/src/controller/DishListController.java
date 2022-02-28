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
import model.Customer;

public class DishListController {
	
	
	@FXML
	private AnchorPane mainScreen;
	@FXML
	private AnchorPane resultScreen;
	@FXML
	private AnchorPane data;
	@FXML
	private Label result;
	@FXML
	private ChoiceBox<Customer> customersList;
	@FXML
	private Customer c;
	
	
	
	@FXML
	public void initialize() {
		//Initialize the entries in the list
		customersList.setItems(FXCollections.observableArrayList(Main.restaurant.getCustomers().values()));
		
		resultScreen.setVisible(false);
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
			if (customersList.getValue() == null) {
				data.setStyle("-fx-border-color: red; -fx-border-width: 2px");
			}
			else {
				isRight = true;
				data.setStyle("-fx-border-color: white; -fx-border-width: 1px");
			}
		}
		if (isRight) {
			c = customersList.getSelectionModel().getSelectedItem();
			result.setText(Main.restaurant.getReleventDishList(c).toString());
			resultScreen.setVisible(true);
		}
	}

}
