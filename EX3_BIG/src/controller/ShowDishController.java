package controller;

import java.io.File;
import java.util.ArrayList;

import application.Main;
import exceptions.AllFieldsAreIncompleteException;
import exceptions.InvalidInputException;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import model.Component;
import model.Dish;
import utils.DishType;


public class ShowDishController {
	
	@FXML
	private Dish dish;
	
	@FXML
	private AnchorPane mainScreen;
	
	@FXML
	private ListView<Dish> dishesList;
	ObservableList<Dish> observableArrayList = FXCollections.observableArrayList(Main.restaurant.getDishes().values());
	@FXML
	private AnchorPane replace;
	@FXML
	private AnchorPane addDishAP;
	@FXML
	private Label name;

    @FXML
    private Label price;
	@FXML
	private Label dishType;

    @FXML
    private TextField time;
	@FXML
	private ListView<Component> listOfComponents;
	@FXML
	private Label status;
	@FXML
	private ChoiceBox<DishType> newType;
	@FXML
	private TextField dishName;
	@FXML
	private TextField timeToMake;
	@FXML
	private TextField searchById;
	@FXML
	private ListView<Component> listOfComponents1;
	ObservableList<Component> observableArrayList2 = FXCollections.observableArrayList(Main.restaurant.getComponenets().values());
	
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
	    			Dish dish = Main.restaurant.getRealDish(id);
	    			if (dish == null ) {
	    				playErrorSound();
	    				//New Alert
	        			Alert a = new Alert(AlertType.ERROR);
	        			a.setTitle("Error");
	        			a.setHeaderText("There is no such ID, try again");
	        			a.showAndWait();
	    			} else {
	    				observableArrayList = FXCollections.observableArrayList(dish); 
	    				dishesList.setItems(observableArrayList);
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
	    		observableArrayList = FXCollections.observableArrayList(Main.restaurant.getDishes().values()); 
	    		dishesList.setItems(observableArrayList);
	    	}
	    }
	
	@FXML
	public void initialize() {
		
		//Initialize the entries in the list
		dishesList.setItems(observableArrayList);
		listOfComponents1.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		listOfComponents1.setItems(observableArrayList2);
		newType.setItems(FXCollections.observableArrayList(DishType.values()));
		
		replace.setVisible(false);
		
		dishesList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Dish>() {

			@Override
			public void changed(ObservableValue<? extends Dish> arg0, Dish arg1, Dish arg2) {
				playSound();
				showDetails();
			}
			
		});
		
		
		
		//Alerts in real time whether the value in the field is correct
		dishName.addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
	          public void handle(KeyEvent arg0) {

					//Input Check
	        	    //Can contain letters and space only
	        	  	boolean allLetters = dishName.getText().chars().allMatch(Character::isLetter);
	        	  	if (! allLetters) {
	        	  		
	        	  		boolean isValid = true;
	        	  		for (int i=0 ; i < dishName.getText().length(); i++) {
	        	  			if (! (Character.isLetter(dishName.getText().charAt(i))) && !(Character.isSpaceChar(dishName.getText().charAt(i)))) {
	        	  				isValid = false;
	        	  			}		
	        	  		}
	        	  		if (!isValid)
		        	  		dishName.setStyle("-fx-text-fill: red; -fx-border-radius: 30; -fx-background-radius: 30; -fx-border-color: red");
	        	  		else 
		        	  		dishName.setStyle("-fx-border-radius: 30; -fx-background-radius: 30; -fx-border-color: #71def9");
	        	  	} else {
	        	  		dishName.setStyle("-fx-border-radius: 30; -fx-background-radius: 30; -fx-border-color: #71def9");
	        	  	}
	          }
		});
		timeToMake.addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
	          public void handle(KeyEvent arg0) {

					//Input Check
	        	  if(timeToMake.getText().matches("^[0-9]*$")){
	        		  timeToMake.setStyle("-fx-border-radius: 30; -fx-background-radius: 30; -fx-border-color: #71def9");
	        	  } else {
	        		  timeToMake.setStyle("-fx-text-fill: red; -fx-border-radius: 30; -fx-background-radius: 30; -fx-border-color: red");
	        	  }
	        	  		
	          }
		});
		time.addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
	          public void handle(KeyEvent arg0) {

					//Input Check
	        	  if(time.getText().matches("^[0-9]*$")){
	        		  time.setStyle("-fx-border-radius: 30; -fx-background-radius: 30; -fx-border-color: #71def9");
	        	  } else {
	        		  time.setStyle("-fx-text-fill: red; -fx-border-radius: 30; -fx-background-radius: 30; -fx-border-color: red");
	        	  }
	        	  		
	          }
		});
		
		
		
		
		
	}
	
   @FXML
    void edit(ActionEvent event) throws InvalidInputException, AllFieldsAreIncompleteException {
	   playSound();
	   Dish d = dishesList.getSelectionModel().getSelectedItem();
	   boolean isValid = true;
	   
	   if (time.getText().length() == 0) {
		   isValid = false;
		   throw new AllFieldsAreIncompleteException();
	   }
	   
	   for (int i = 0; i<time.getText().length(); i++) {
			if (time.getText().charAt(i) > '9' || time.getText().charAt(i) < '0') {
				isValid = false;
				throw new InvalidInputException();
			} 	
	   }
	   if (isValid) {
		   d.setTimeToMake(Integer.parseInt(time.getText()));
			//New Alert
			Alert a = new Alert(AlertType.CONFIRMATION);
			a.setTitle("CONFIRMATION");
			a.setHeaderText("Changes saved successfully");
			a.showAndWait();
			
			save();
	   }
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
	public void showDetails() {
		
		
		dish = dishesList.getSelectionModel().getSelectedItem();
		name.setText(dish.getDishName());
		price.setText("" + dish.getPrice() + " $");
		dishType.setText(dish.getType().name());
		time.setText("" + dish.getTimeToMake());
		
		listOfComponents.setItems(FXCollections.observableArrayList(dish.getComponenets()));
		replace.setVisible(true);
		
	}
	
	@FXML
	public void removeDish (ActionEvent event) throws Exception {
		playSound();
		dish = dishesList.getSelectionModel().getSelectedItem();
		Main.restaurant.removeDish(dish);
		save();
		observableArrayList = FXCollections.observableArrayList(Main.restaurant.getDishes().values());
		dishesList.setItems(observableArrayList);
		listOfComponents1.setItems(FXCollections.observableArrayList(Main.restaurant.getComponenets().values()));
		
		status.setText("Dish removed successfully");
		
		if (Main.restaurant.getDishes().size() == 0) {
			replace.setVisible(false);
		}
		
	}
	
	@FXML
	public void addDish (ActionEvent event) throws Exception {
		playSound();
		
		boolean flag = true;
		
		if (flag) {
			if (dishName.getText().length() == 0) {
    	  		dishName.setStyle("-fx-text-fill: red; -fx-border-radius: 30; -fx-background-radius: 30; -fx-border-color: red");
				status.setText("All fields must be field currectly");
				playErrorSound();
				flag = false;
			}
			// force the field to be with letters only and to nut contain any other characters
			for (int i = 0; i<dishName.getText().length(); i++) {
				if (dishName.getText().charAt(i) < '9' && dishName.getText().charAt(i) > '0') {
					flag = false;
					throw new InvalidInputException(); 	
				}
			}
			if (timeToMake.getText().length() == 0) {
				timeToMake.setStyle("-fx-text-fill: red; -fx-border-radius: 30; -fx-background-radius: 30; -fx-border-color: red");
				status.setText("All fields must be field currectly");
				playErrorSound();
				flag = false;
			}
			
			// force the field to be numeric only and to nut contain string
			for (int i = 0; i<timeToMake.getText().length(); i++) {
				if (timeToMake.getText().charAt(i) < 'z' && timeToMake.getText().charAt(i) > 'a' || 
						timeToMake.getText().charAt(i) < 'Z' && timeToMake.getText().charAt(i) > 'A') {
					
					flag = false;
					throw new InvalidInputException(); 	
				}
			}
			
			if (newType.isPressed()) {
				newType.setStyle("-fx-border-color: #f02491; -fx-border-width: 1px");
				status.setText("All fields must be field currectly");
				playErrorSound();
				flag = false;
			}
		}
		
		if (flag) {
			//if this id already exists 
			if (Main.restaurant.getDishes().get(Dish.getIdCounter()) != null) {
				Dish.fixID();
			}
			
			ArrayList<Component> al = new ArrayList<>();
			ObservableList<Component> selectedItems =  listOfComponents1.getSelectionModel().getSelectedItems();
			 
			for(Component comp : selectedItems){
	           al.add(comp);
	           save();
	        }
					
			Dish d = new Dish(dishName.getText(),newType.getValue(), al, Integer.parseInt(timeToMake.getText()));
			Main.restaurant.addDish(d);
			save();
			
			dishesList.setItems(FXCollections.observableArrayList(Main.restaurant.getDishes().values()));
			status.setText("Dish Added successfully");
			
			listOfComponents1.setItems(FXCollections.observableArrayList(Main.restaurant.getComponenets().values()));
		}
	
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
