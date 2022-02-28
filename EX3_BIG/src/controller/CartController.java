package controller;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.TreeSet;

import application.Main;
import exceptions.NoDishException;
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
import model.Dish;
import model.Order;

public class CartController implements Initializable {
	
    @FXML
    private AnchorPane mainScreen;
    @FXML
    private Label totalPrice;
	@FXML GridPane grid;
    @FXML
    private Label numberOfItems;
    @FXML
    private Label customer;
    
    private Double price = 0.0;
    private MyListener myListener;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		customer.setText("Hello " + MainController.getC().getFirstName() + " " + MainController.getC().getLastName());
		
		//That means there are no dishes on order
		if (MainController.getC().getShoppingCart() == null ) {
			numberOfItems.setText("0");
			
		} else { //The Cart has dishes			
			numberOfItems.setText(MainController.getC().getShoppingCart().size() + "");
			for (Dish d : MainController.getC().getShoppingCart()) {
				//calculates total price of the order
				price += d.getPrice();
			}

			DecimalFormat df2 = new DecimalFormat("#.##");
			totalPrice.setText( df2.format(price) + " $" );
	
			int column = 0 , row =0;
			
			//create dish list
			try {
			    for(Dish d : MainController.getC().getShoppingCart()) {
			    
				    FXMLLoader fxmlloader = new FXMLLoader();
				    fxmlloader.setLocation(getClass().getResource("/View/OrderItem.fxml"));
				    AnchorPane AP = fxmlloader.load();
				    
				    OrderItemController OrderitemController = fxmlloader.getController();
				    OrderitemController.setData(d, myListener);
				    
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
	
	//When the customer wants to place an order
    @FXML
    void checkout(ActionEvent event) throws Exception {
    	
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
    	
    	ArrayList<Dish> al = new ArrayList<>();
    	
		//Add the dishes in the cart
		for(Dish d : MainController.getC().getShoppingCart()){
            al.add(d);
        }
		
		if (al.size() > 0) {
	
			//if this id already exists 
			if (Main.restaurant.getOrders().get(Order.getIdCounter()) != null) {
				Order.fixID();
			}
			
			Order o = new Order (MainController.getC(), al,null);
			if (Main.restaurant.addOrder(o)) {
				if(o.getCustomer().getAllOrders() == null) {
					TreeSet<Order> Allorders = new TreeSet<Order>();
					o.getCustomer().setAllOrders(Allorders);
				} else {
					o.getCustomer().getAllOrders().add(o);
				}
				System.out.println("order added");
				
				//After the order is completed successfully the shopping cart should be reset
				MainController.getC().getShoppingCart().removeAll(MainController.getC().getShoppingCart());
				
				save();
		    	
		    	FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/FinalScreen.fxml"));
				AnchorPane pane = loader.load();
				mainScreen.getChildren().removeAll(mainScreen.getChildren());
				mainScreen.getChildren().add(pane);
			}
		}
		else {
			throw new NoDishException();
		}
		
		
    }
		
	
	@FXML
	public void save() {
		Main.getRestaurant().seralize();
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
		
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/CustomerView.fxml"));
		AnchorPane pane = loader.load();
		mainScreen.getChildren().removeAll(mainScreen.getChildren());
		mainScreen.getChildren().add(pane);
	}
	
	
	
	

	
	


}
