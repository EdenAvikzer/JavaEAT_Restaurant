package controller;

import java.io.File;

import application.Main;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import model.Delivery;
import model.ExpressDelivery;
import model.Order;
import model.RegularDelivery;
import model.Restaurant;
public class ShowDeliveryController {
	
	@FXML 
	private Delivery delivery;
	@FXML
	private AnchorPane mainScreen;
	@FXML
	private AnchorPane replace;
	@FXML
	private ListView<Delivery> deliveriesList;
	ObservableList<Delivery> observableArrayList = FXCollections.observableArrayList(Main.restaurant.getDeliveries().values()); 
	@FXML
	private Label delPer;
	@FXML
	private Label area;
	@FXML
	private Label isDelivered;
	@FXML
	private Label date;
	@FXML
	private ListView<Order> ordersList;
	@FXML
	private Label status;
	@FXML
	private CheckBox deliver;
	@FXML
	private TextField searchById;
	
	

	
	@FXML
	public void save() {
		Main.getRestaurant().seralize();
	}
	
	 @FXML
	    void search(ActionEvent event) {	
		 
		 	playSound();
	    	if (searchById.getText().length() != 0) {
	    		try {
	    			int id = Integer.parseInt(searchById.getText());
	    			Delivery del = Main.restaurant.getRealDelivery(id);
	    			if (del == null ) {
	    				playErrorSound();
	    				//New Alert
	        			Alert a = new Alert(AlertType.ERROR);
	        			a.setTitle("Error");
	        			a.setHeaderText("There is no such ID, try again");
	        			a.showAndWait();
	    			} else {
	    				observableArrayList = FXCollections.observableArrayList(del); 
	    				deliveriesList.setItems(observableArrayList);
	    			}
	    			
	    		} catch (Exception e) {
	    			playErrorSound();
	    			//New Alert
	    			Alert a = new Alert(AlertType.ERROR);
	    			a.setTitle("Error");
	    			a.setHeaderText("There is no such ID, try again");
	    			a.showAndWait();
	    		}	
	    	} else {
	    		observableArrayList = FXCollections.observableArrayList(Main.restaurant.getDeliveries().values()); 
	    		deliveriesList.setItems(observableArrayList);
	    	}
	    }
	
	@FXML
	public void initialize() {
		
		deliveriesList.setItems(observableArrayList);
		replace.setVisible(false);
	
		if (getDeliveriesList().getSelectionModel().selectedItemProperty() != null) {
			getDeliveriesList().getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Delivery>() {
	
				@Override
				public void changed(ObservableValue<? extends Delivery> arg0, Delivery arg1, Delivery arg2) {
					playSound();
					showDetails();
				}
				
			});
		}
		
	}
	
	protected void showDetails() {
		delivery = getDeliveriesList().getSelectionModel().getSelectedItem();
		
		if(delivery != null) {
			
			delPer.setText(delivery.getDeliveryPerson().getFirstName());
			area.setText(delivery.getArea().getAreaName());
			if (delivery.isDelivered() == true) {
				isDelivered.setText("Yes");
			}else {
				isDelivered.setText("No");
			}
			date.setText(delivery.getDeliveredDate().toString());
			
			if (delivery instanceof ExpressDelivery) {
				delivery = (ExpressDelivery)delivery;
				ordersList.setItems(FXCollections.observableArrayList(((ExpressDelivery) delivery).getOrder()));
				
			}else { // RegularDelivery
				delivery = (RegularDelivery)delivery;
				ordersList.setItems(FXCollections.observableArrayList(((RegularDelivery) delivery).getOrders()));
			}

			replace.setVisible(true);
		}
		
	}
	
	@FXML
	public void removeDelivery (ActionEvent event) throws Exception {
		playSound();
		delivery = getDeliveriesList().getSelectionModel().getSelectedItem();
		Main.restaurant.removeDelivery(delivery);
		save();
		
		getDeliveriesList().setItems(FXCollections.observableArrayList(Main.restaurant.getDeliveries().values()));
		status.setText("Delivery removed successfully");
		
		if (Main.restaurant.getDeliveries().size() == 0) {
			replace.setVisible(false);
		}
		
	}
	
	@FXML
	public void addRegDelivery (ActionEvent event) throws Exception {
		playSound();
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/AddRegDelivery.fxml"));
		AnchorPane pane = loader.load();
		mainScreen.getChildren().removeAll(mainScreen.getChildren());
		mainScreen.getChildren().add(pane);
	}
	
	@FXML
	public void addExpDelivery (ActionEvent event) throws Exception {
		playSound();
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/AddExpDelivery.fxml"));
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
	public void delivered (ActionEvent event) throws Exception {
		playSound();
		delivery = getDeliveriesList().getSelectionModel().getSelectedItem();
		if (delivery != null) {
			Main.restaurant.deliver(delivery);
			deliveriesList.setItems(FXCollections.observableArrayList(Main.restaurant.getDeliveries().values()));
			save();
			status.setText("Updated successfully");
		}
		
	}
	
	@FXML
	public void createDelveries (ActionEvent event) throws Exception {
		playSound();
		Restaurant.createNewDeliveries();
		deliveriesList.setItems(FXCollections.observableArrayList(Main.restaurant.getDeliveries().values())); 
		
	}

	public  ListView<Delivery> getDeliveriesList() {
		return deliveriesList;
	}

	public  void setDeliveriesList(ListView<Delivery> deliveriesList) {
		this.deliveriesList = deliveriesList;
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
