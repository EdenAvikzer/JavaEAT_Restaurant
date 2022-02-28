package controller;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import model.Customer;
import model.Order;

public class OrderByCustController implements Initializable {
	
	 	@FXML
	    private AnchorPane mainScreen;
	    @FXML
	    private Label customer;
	    
	    Customer cust;

	    @FXML
	    private GridPane grid;
	    @FXML
	    private Label numberOfOrders;
	    

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		//Update of customer information logged in to the system now
		cust = MainController.getC();
		customer.setText("Hello " + MainController.getC().getFirstName() + " " + MainController.getC().getLastName());
		
		if (cust.getAllOrders() == null) {
			numberOfOrders.setText("0");
		} else {
			numberOfOrders.setText(cust.getAllOrders().size() + "");
			int column = 0 , row = 0;
			
			try {
			    for(Order o : cust.getAllOrders()) {
			    
				    FXMLLoader fxmlloader = new FXMLLoader();
				    fxmlloader.setLocation(getClass().getResource("/View/Item.fxml"));
				    AnchorPane AP = fxmlloader.load();
				    
				    ItemController itemController = fxmlloader.getController();
				    itemController.setData(o);
				    
				    if(column == 1) {
				    	column = 0;
				    	row++;
				    }
				    
				    grid.add(AP,column++, row);
				    
				    //set grid Width
				    grid.setMinWidth(Region.USE_COMPUTED_SIZE);
				    grid.setPrefWidth(Region.USE_COMPUTED_SIZE);
				    grid.setMaxWidth(Region.USE_PREF_SIZE);
				    
				    //set grid Height
				    grid.setMinHeight(Region.USE_COMPUTED_SIZE);
				    grid.setPrefHeight(Region.USE_COMPUTED_SIZE);
				    grid.setMaxHeight(Region.USE_PREF_SIZE);
				    
				    GridPane.setMargin(AP, new Insets(10));
			    }
		    } catch (IOException i) {
		    	i.printStackTrace();
		    }
		}
		
		
	}
	
	@FXML
	public void goBack (ActionEvent event) throws Exception {
		Media sound = new Media(new File("src\\clickSound.mp4").toURI().toString());
		MediaPlayer mediaPlayer=new MediaPlayer(sound);
		mediaPlayer.play();
		
		
		mediaPlayer.setOnReady(new Runnable() {
	        @Override
	        public void run() {
	        	mediaPlayer.play();
	        }
	    });
		
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/CustomerView.fxml"));
		AnchorPane pane = loader.load();
		mainScreen.getChildren().removeAll(mainScreen.getChildren());
		mainScreen.getChildren().add(pane);
	}

}
