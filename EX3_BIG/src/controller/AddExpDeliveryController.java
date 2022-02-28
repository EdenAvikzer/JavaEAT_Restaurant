package controller;

import java.io.File;
import java.io.IOException;

import application.Main;
import exceptions.AllFieldsAreIncompleteException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import model.Delivery;
import model.DeliveryArea;
import model.DeliveryPerson;
import model.ExpressDelivery;
import model.Order;

public class AddExpDeliveryController {
	
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
		
		//Initialize the entries in the list
		delPerList.setItems(FXCollections.observableArrayList(Main.restaurant.getDeliveryPersons().values()));
		areasList.setItems(FXCollections.observableArrayList(Main.restaurant.getAreas().values()));
		allOrders.setItems(observableArrayList);
		
		
		//Alerts in real time whether the value in the field is correct
		postage.addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
	          public void handle(KeyEvent arg0) {

					//Input Check
	        	  if(postage.getText().matches("[0-9]+\\.[0-9]*$") || postage.getText().matches("^[0-9]*.$")){
	        		  postage.setStyle(null);
	        	  } else {
	        		  postage.setStyle("-fx-text-fill: red; -fx-border-color: red");
	        	  }
	        	  		
	          }
		});
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
		
		if (delPerList.getValue() == null || areasList.getValue() == null || deliveryDate.getValue() == null
				||postage.getText().length() == 0) {
			
			throw new AllFieldsAreIncompleteException();
			
		}

		
		else if (allOrders.getSelectionModel().getSelectedItems().size() == 1) {
			
			//if this id already exists 
			if (Main.restaurant.getDeliveries().get(Delivery.getIdCounter()) != null) {
				Delivery.fixID();
			}
			
			d = new ExpressDelivery(delPerList.getValue(),areasList.getValue(),isDelivered.isSelected(),allOrders.getSelectionModel().getSelectedItem()
					,Double.parseDouble(postage.getText()),deliveryDate.getValue());
			if (Main.restaurant.addDelivery(d)) {
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
			//The addition is not successful
			allOrders.setStyle("-fx-border-color: #f02491; -fx-border-width: 1px");
			playErrorSound();
			//New Alert			
			Alert a = new Alert(AlertType.ERROR);
			a.setTitle("An error occurred");
			a.setHeaderText("You must select one order exactly");
			a.showAndWait();
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
