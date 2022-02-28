package controller;

import java.io.File;
import java.util.TreeSet;

import application.Main;
import exceptions.AllFieldsAreIncompleteException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import model.Delivery;
import model.DeliveryArea;
import model.DeliveryPerson;
import model.Order;

public class AIMacineController {
	
	@FXML
	private AnchorPane mainScreen;
	@FXML
	private AnchorPane showResult;
	@FXML
	private AnchorPane enterData;
	@FXML
	private ChoiceBox<DeliveryPerson> delPerBox;
	@FXML
	private ChoiceBox<DeliveryArea> delArBox;
	
	@FXML
	private ListView<Order> ordersList;
	ObservableList<Order> observableArrayList = FXCollections.observableArrayList(Main.restaurant.getOrders().values());
	@FXML
	private ListView<Delivery> DeliveryList;
	
	
	

	@FXML
	public void initialize() {
		
		showResult.setVisible(false);
		//Initialize the entries in the list
		delPerBox.setItems(FXCollections.observableArrayList(Main.restaurant.getDeliveryPersons().values()));
		delArBox.setItems(FXCollections.observableArrayList(Main.restaurant.getAreas().values()));
		ordersList.setItems(observableArrayList);
	}
	
	@FXML
	public void createAIMacine (ActionEvent event) throws Exception {
		
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
		
		DeliveryPerson dp;
		DeliveryArea da;
		TreeSet<Order> orders = new TreeSet<>();
		boolean isRight = false;
		
		if (isRight == false) {
			
			//input check
			if (delPerBox.getValue() == null || delArBox.getValue() == null || 
					ordersList.getSelectionModel().getSelectedItem() == null) {
				enterData.setStyle("-fx-border-color: red; -fx-border-width: 2px");
				throw new AllFieldsAreIncompleteException();
				
			}
			else {
				isRight =  true;
				enterData.setStyle(null);
			}
		}
		//Will be performed only with the values filled in as required
		if (isRight) {
			
			dp = delPerBox.getValue();
			da = delArBox.getValue();
			for (Order o: ordersList.getItems()) {
				orders.add(o);
			}
			
			ObservableList<Delivery> observableArrayList = FXCollections.observableArrayList(Main.restaurant.createAIMacine(dp, da, orders));
			DeliveryList.setItems(observableArrayList);
			
			showResult.setVisible(true);
		}
		
		
	}

}
