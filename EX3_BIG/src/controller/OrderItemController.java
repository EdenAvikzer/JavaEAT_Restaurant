package controller;

import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;

import application.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.Label;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import model.Component;
import model.Dish;

public class OrderItemController  {
	
	 @FXML
	    private Label name;
	    @FXML
	    private Label price;
	    @FXML
	    private Label components;
	    private Dish dish;
	    private MyListener myListener;
	    @FXML
	    private Button removeButton;
	    @FXML
	    private Button editButton;
	    
	    
	    @FXML
		public void setData(Dish dish, MyListener myListener) {
			this.dish = dish;
			name.setText("" + dish.getDishName());
			DecimalFormat df2 = new DecimalFormat("#.##");
			price.setText(df2.format(dish.getPrice()) + " $");
			this.myListener = myListener;
			components.setText(dish.getComponenets().toString());
	
		}
	    
	    @FXML
	    public void click(MouseEvent mouseEvent ) {
	    	myListener.onClickListener(dish);
	    }
	    
	    @FXML
	    private void editDish(ActionEvent event) throws IOException {
	    	
	    	playSound();
	    	Component emptyComponent = new Component("", false, false, 0);
	    	ChoiceDialog<Component> dialog = new ChoiceDialog<Component>(emptyComponent,dish.getComponenets());
	    	
	    	//Allows the customer to remove components from his dish
	    	dialog.setHeaderText("Removing Components");
	    	dialog.setContentText("please select the component you want to remove");
	    	dialog.showAndWait();
	    	Component comp = dialog.getSelectedItem();
	    	
	    	if (comp == emptyComponent) {
	    		//do nothing...
	    	}
	    	
	    	else {
		    	ArrayList<Component> compList = new ArrayList<>();
		    	
		    	for (Component c : this.dish.getComponenets()) {
		    		/*if the component is different then the chosen component
		    		we will add it to the new dish*/
		    		if (! c.equals(comp)) {
		    			compList.add(c);
		    		}
		    	}
		    	
		    	if (compList.size() > 0) {
		    		/*Create a new Dish object without the component the
		    		customer requested to remove*/
			    	Dish d = new Dish(this.dish.getDishName(), this.dish.getType(), compList, this.dish.getTimeToMake());
			    	//add the new dish without the component
			    	MainController.getC().getShoppingCart().add(d);
			    	//remove the other dish
			    	MainController.getC().getShoppingCart().remove(this.dish);
			    	save();
			    	
			    	//Update details on shopping Cart
					Parent p = FXMLLoader.load(getClass().getResource("/View/Cart.fxml"));
					Scene s = new Scene(p, 900,600);
					s.getStylesheets().add(getClass().getResource("controller.css").toExternalForm());
					Stage window =  (Stage) ((Node) event.getSource()).getScene().getWindow();
					window.setScene(s);
					window.show();	
					
		    	} else {
		    		playErrorSound();
		    		//New Alert
		    		Alert a = new Alert(AlertType.ERROR);
		    		a.setTitle("Error");
		    		a.setHeaderText("A dish must contain at least one component");
		    		a.showAndWait();
		    	}
	    	}
	    	
	    	
	    	
	    }
	    
	    @FXML
	    private void removeDish(ActionEvent event) throws IOException {
	    	
	    	playSound();
	    	
	    	if(MainController.getC().getShoppingCart().remove(dish)) {
	    		//New Alert
				Alert a = new Alert(AlertType.CONFIRMATION);
				a.setTitle("Remove Dish");
				a.setHeaderText("The dish was successfully removed");
				a.showAndWait();
				
				//Update details on shopping Cart
				Parent p = FXMLLoader.load(getClass().getResource("/View/Cart.fxml"));
				Scene s = new Scene(p, 900,600);
				s.getStylesheets().add(getClass().getResource("controller.css").toExternalForm());
				Stage window =  (Stage) ((Node) event.getSource()).getScene().getWindow();
				window.setScene(s);
				window.show();	
				
	    	} else {
	    		playErrorSound();
	    		//New Alert
				Alert a = new Alert(AlertType.ERROR);
				a.setTitle("Remove Dish");
				a.setHeaderText("Try Again");
				a.showAndWait();
	    	}
	    	save();
	    
	    	
	    }
	    
		@FXML
		public void save() {
			Main.getRestaurant().seralize();
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
