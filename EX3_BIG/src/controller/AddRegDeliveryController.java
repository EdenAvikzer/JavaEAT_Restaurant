package controller;

import java.io.File;
import java.io.IOException;

import application.Main;
import exceptions.AllFieldsAreIncompleteException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import model.Delivery;
import model.DeliveryArea;
import model.DeliveryPerson; 
import model.Order;
import model.RegularDelivery;

public class AddRegDeliveryController {

	@FXML
	private AnchorPane mainScreen;
	@FXML
	private ChoiceBox<DeliveryPerson> delPerList;
	@FXML
	private ChoiceBox<DeliveryArea> areasList;
	@FXML
	private DatePicker deliveryDate;
	@FXML
	private TextField postage;
	@FXML
	private CheckBox isDelivered;
	@FXML
	private ListView<Order> allOrders;
	ObservableList<Order> observableArrayList = FXCollections.observableArrayList(Main.restaurant.getOrders().values()); 
	
	
	@FXML
	public void save() {
		Main.getRestaurant().seralize();
	}
	
	@FXML
	public void initialize() {
		
		allOrders.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		
		//Initialize the entries in the list
		delPerList.setItems(FXCollections.observableArrayList(Main.restaurant.getDeliveryPersons().values()));
		areasList.setItems(FXCollections.observableArrayList(Main.restaurant.getAreas().values()));
		allOrders.setItems(observableArrayList);
	}
	
	@FXML
	public void add() throws IOException, AllFieldsAreIncompleteException {
		
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
		
		Delivery d = null;
		
		//input check
		if (delPerList.getValue() == null || areasList.getValue() == null || deliveryDate.getValue() == null) {
			
			throw new AllFieldsAreIncompleteException();
		}
		if (!areasList.getValue().equals(delPerList.getValue().getArea())) {
			//New Alert
			Alert a = new Alert(AlertType.ERROR);
			a.setTitle("Error");
			a.setHeaderText("The delivery person must belong to the delivery area");
			a.showAndWait();
			
		} else {
			//It will add the delivery only if he contains more than one order
			 if (allOrders.getSelectionModel().getSelectedItems().size() > 1) {
				
				//if this id already exists 
				if (Main.restaurant.getDeliveries().get(Delivery.getIdCounter()) != null) {
					Delivery.fixID();
				}
				
				ObservableList<Order> orders = allOrders.getSelectionModel().getSelectedItems();
				
				d = new RegularDelivery(delPerList.getValue(),areasList.getValue(),isDelivered.isSelected(),deliveryDate.getValue());
				
				for (Order o : orders) {
					((RegularDelivery) d).addOrder(o);
				}
				if (Main.restaurant.addDelivery(d)) {
					delPerList.getValue().setAvailable(false);
					save();
					
					//New Alert
					Alert a = new Alert(AlertType.CONFIRMATION);
					a.setTitle("successfully added");
					a.setHeaderText("The delivery was successfully added");
					a.showAndWait();
					
					FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/ShowDelivery.fxml"));
					AnchorPane pane = loader.load();
					mainScreen.getChildren().removeAll(mainScreen.getChildren());
					mainScreen.getChildren().add(pane);
				}

			} else { 
				//There are not enough orders
				allOrders.setStyle("-fx-border-color: #f02491; -fx-border-width: 1px");
				playErrorSound();
				//New Alert
				Alert a = new Alert(AlertType.ERROR);
				a.setTitle("An error occurred");
				a.setHeaderText("Details are missing");
				a.showAndWait();
			}
		}
	}

		
		
	
	
	@FXML
	public void goBack (ActionEvent event) throws Exception {
		
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
		
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/ShowDelivery.fxml"));
		AnchorPane pane = loader.load();
		mainScreen.getChildren().removeAll(mainScreen.getChildren());
		mainScreen.getChildren().add(pane);
	}
	
	//NEW SOUND EFFECT
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
