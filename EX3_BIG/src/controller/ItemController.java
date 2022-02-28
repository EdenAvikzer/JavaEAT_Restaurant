package controller;

import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;

import application.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import model.Order;

public class ItemController {
	
	
    @FXML
    private Label price;
    @FXML
    private Label dishes;
    @FXML
    private Label isDelivered;
    @FXML
    private Button cancel;
    
    private Order order;
   
    
    @FXML
	public void save() {
		Main.getRestaurant().seralize();
	}
    
    @FXML
	public void setData(Order o) {
    	
    	this.order = o;
    	
    	DecimalFormat df2 = new DecimalFormat("#.##");
    	this.price.setText(df2.format(order.calcOrderPrice()) + " $");
    	
    	this.dishes.setText(order.getDishes().toString());
    	
    	if(order.getDelivery() != null) {
    		
			//we will not aloud the customer to cancel an order that has been delivered 
    		cancel.setVisible(false);
    		
    		//If the delivery delivered already we update it's status
    		if (order.getDelivery().isDelivered()) {
    			this.isDelivered.setText("Order sent successfully");
    			this.isDelivered.setStyle("-fx-text-fill: GREEN");
	
    		} else  {
    			this.isDelivered.setText("Order is on its way to you");
    			this.isDelivered.setStyle("-fx-text-fill: RED");
    		} 
    		
    	} 
    	else {
    		this.isDelivered.setText("Order is in preparation");
			this.isDelivered.setStyle("-fx-text-fill: ORANGE");
    	}
	}
    
    @FXML
    private void cancelOrder(ActionEvent event) throws IOException {
    
    	//SOUND
	    Media sound = new Media(new File("src\\clickSound.mp4").toURI().toString());
		MediaPlayer mediaPlayer=new MediaPlayer(sound);
		mediaPlayer.play();
		
		
		mediaPlayer.setOnReady(new Runnable() {
	        @Override
	        public void run() {
	        	mediaPlayer.play();
	        }
	    });
		
		//remove
		Main.restaurant.removeOrder(order);
		Main.restaurant.getOrderByDeliveryArea().get(order.getDa()).remove(order);
		order.getCustomer().getAllOrders().remove(order);

		save();
		
		//New Alert
		Alert a = new Alert(AlertType.CONFIRMATION);
		a.setTitle("Cancel Order");
		a.setHeaderText("Your order has been canceled");
		a.showAndWait();
		
		//Update details on the list of orders 
		Parent p = FXMLLoader.load(getClass().getResource("/View/OrderByCust.fxml"));
		Scene s = new Scene(p, 900,600);
		s.getStylesheets().add(getClass().getResource("controller.css").toExternalForm());
		Stage window =  (Stage) ((Node) event.getSource()).getScene().getWindow();
		window.setScene(s);
		window.show();
		
		
    }
    


}
