package controller;


import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.ResourceBundle;

import application.Main;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import model.Dish;

public class RestMenuController implements Initializable{
	
	@FXML
	private AnchorPane mainScreen;
	
   @FXML
    private Label itemName;

    @FXML
    private Label price;

    @FXML
    private ComboBox<Integer> qty;
    ArrayList<Integer> list = new ArrayList<Integer>();
    
    @FXML
    private GridPane grid;
    @FXML
    private Button addToCartButton;
    @FXML
    private Label status;
    
    ArrayList<Dish> dishes;
    private MyListener myListener;
   
    
	
	
	@FXML
	public void save() {
		Main.getRestaurant().seralize();
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		dishes = new ArrayList<>(Main.restaurant.getDishes().values());
		
		if(Main.restaurant.getDishes().values().size() > 0) {
			 myListener =  new MyListener() {

				@Override
				public void onClickListener(Dish dish) {
					setChosenDish(dish);
				}
				 
			 };
		}
		
		list.add(1);
		list.add(2);
		list.add(3);
		list.add(4);
		list.add(5);
		list.add(6);
		list.add(7);
		list.add(8);
		list.add(9);
		
		ObservableList<Integer> obList = FXCollections.observableList(list);
	    qty.getItems().clear();
	    qty.setItems(obList);
	    
	    int column = 0 , row =0;
	    
	    try {
		    for(Dish d : Main.restaurant.getDishes().values()) {
		    
			    FXMLLoader fxmlloader = new FXMLLoader();
			    fxmlloader.setLocation(getClass().getResource("/View/Dish.fxml"));
			    AnchorPane AP = fxmlloader.load();
			    
			    DishController dishController = fxmlloader.getController();
			    dishController.setData(d, myListener);
			    
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
	
	@FXML
	private void setChosenDish(Dish dish) {
		playSound();
		itemName.setText("" + dish.getDishName());
		DecimalFormat df2 = new DecimalFormat("#.##");
		price.setText(df2.format(dish.getPrice()) + "$");
		
		//set onAction for add to cart button
		addToCartButton.setOnAction(new EventHandler<ActionEvent>() {
			
		    @Override 
		    public void handle(ActionEvent e) {
		    	playSound();
		    	if (qty.getValue() != null) {
		    		MainController.getC().addToShopCart(dish, qty.getValue());
		    		status.setText(dish.getDishName() + " Added successfully");
		    		status.setStyle("-fx-background-color: black;-fx-background-radius: 30");
		    		save();
		    	}
		    	else {
		    		playErrorSound();
		    		status.setText("You must select a quantity");
		    		status.setStyle("-fx-background-color: white;-fx-background-radius: 30");
		    	}
		    }
		});
		
	}
	
	@FXML
	private void moveToCart(ActionEvent event) throws IOException {
		playSound();
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/Cart.fxml"));
		AnchorPane pane = loader.load();
		mainScreen.getChildren().removeAll(mainScreen.getChildren());
		mainScreen.getChildren().add(pane);
    }
	
	@FXML
	public void goBack (ActionEvent event) throws Exception {
		playSound();
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/CustomerView.fxml"));
		AnchorPane pane = loader.load();
		mainScreen.getChildren().removeAll(mainScreen.getChildren());
		mainScreen.getChildren().add(pane);
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
