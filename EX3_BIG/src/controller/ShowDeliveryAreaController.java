package controller;



import java.io.File;
import java.util.HashSet;

import application.Main;
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
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import model.DeliveryArea;
import utils.Neighberhood;

public class ShowDeliveryAreaController {
	
	@FXML
	private AnchorPane mainScreen;
	@FXML
	private AnchorPane replace;
	@FXML
	private AnchorPane addDelAreaAP;
	@FXML
	private ListView<DeliveryArea> deliveryAreaList;
	ObservableList<DeliveryArea> observableArrayList = FXCollections.observableArrayList(Main.restaurant.getAreas().values());
	@FXML
	private Label status;
	@FXML
	private DeliveryArea delAr;
	@FXML
	private Label name;
	@FXML
	private Label delPerField;
	@FXML
	private Label deliveries;
	@FXML
	private Label neighberhoods;

    @FXML
    private Label time;
	
    @FXML
	private TextField searchById;
	@FXML
	private TextField areaName;
	@FXML
	private TextField time2;
	@FXML
	private ListView<Neighberhood> neighberhoodsList;
	ObservableList<Neighberhood> observableArrayList4 = FXCollections.observableArrayList(Neighberhood.values());
	
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
	    			DeliveryArea delArea = Main.restaurant.getRealDeliveryArea(id);
	    			if (delArea == null ) {
	    				playErrorSound();
	    				//New Alert
	        			Alert a = new Alert(AlertType.ERROR);
	        			a.setTitle("Error");
	        			a.setHeaderText("There is no such ID, try again");
	        			a.showAndWait();
	    			} else {
	    				observableArrayList = FXCollections.observableArrayList(delArea); 
	    				deliveryAreaList.setItems(observableArrayList);
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
	    		observableArrayList = FXCollections.observableArrayList(Main.restaurant.getAreas().values()); 
	    		deliveryAreaList.setItems(observableArrayList);
	    	}
	    }
	
	
	@FXML
	public void initialize() {
		
		replace.setVisible(false);
		
		//Initialize the entries in the list
		deliveryAreaList.setItems(observableArrayList);
		neighberhoodsList.setItems(observableArrayList4);
		deliveryAreaList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<DeliveryArea>() {

			@Override
			public void changed(ObservableValue<? extends DeliveryArea> arg0, DeliveryArea arg1, DeliveryArea arg2) {
				playSound();
				showDetails();
			}
			
		});
		
		neighberhoodsList.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		deliveryAreaList.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		
		
		//Alerts in real time whether the value in the field is correct
		areaName.addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
	          public void handle(KeyEvent arg0) {

					//Input Check
	        	  	//Can contain letters and space only
	        	  	boolean allLetters = areaName.getText().chars().allMatch(Character::isLetter) || (areaName.getText().contains(" ") && areaName.getText().chars().allMatch(Character::isLetter));
	        	  	if (! allLetters) {
	        	  		
	        	  		boolean isValid = true;
	        	  		for (int i=0 ; i < areaName.getText().length(); i++) {
	        	  			if (! (Character.isLetter(areaName.getText().charAt(i))) && !(Character.isSpaceChar(areaName.getText().charAt(i)))) {
	        	  				isValid = false;
	        	  			}		
	        	  		}
	        	  		if (!isValid)
		        	  		areaName.setStyle("-fx-text-fill: red; -fx-border-radius: 30; -fx-background-radius: 30; -fx-border-color: red");
	        	  		else 
		        	  		areaName.setStyle("-fx-border-radius: 30; -fx-background-radius: 30; -fx-border-color: #71def9");
	        	  	} else {
	        	  		areaName.setStyle("-fx-border-radius: 30; -fx-background-radius: 30; -fx-border-color: #71def9");
	        	  	}
	          }
		});
		time2.addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
	          public void handle(KeyEvent arg0) {

				  //Input Check
	        	  if(time2.getText().matches("^[0-9]*$")){
	        		  time2.setStyle("-fx-border-radius: 30; -fx-background-radius: 30; -fx-border-color: #71def9");
	        	  } else {
	        		  time2.setStyle("-fx-text-fill: red; -fx-border-radius: 30; -fx-background-radius: 30; -fx-border-color: red");
	        	  }
	        	  		
	          }
		});
		
		
	}
	
	protected void showDetails() {
		playSound();
		delAr = deliveryAreaList.getSelectionModel().getSelectedItem();
		//Set labels
		name.setText(delAr.getAreaName());
		delPerField.setText(delAr.getDelPersons().toString());
		deliveries.setText(delAr.getDelivers().toString());
		neighberhoods.setText(delAr.getNeighberhoods().toString());
		time.setText("" + delAr.getDeliverTime());
		
		replace.setVisible(true);
	}



	@FXML
	public void goBack (ActionEvent event) throws Exception {
		playSound();
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/ManagerView.fxml"));
		AnchorPane pane = loader.load();
		mainScreen.getChildren().removeAll(mainScreen.getChildren());
		mainScreen.getChildren().add(pane);
	}
	
	@SuppressWarnings("null")
	@FXML
	private void addArea(ActionEvent event) throws Exception {
		playSound();
		addDelAreaAP.setStyle("-fx-border-color: white; -fx-border-width: 1px");
		
		boolean flag = true;
		if (flag) {
			if (areaName.getText().length() == 0) {
    	  		areaName.setStyle("-fx-text-fill: red; -fx-border-radius: 30; -fx-background-radius: 30; -fx-border-color: red");
				status.setText("All fields must be field currectly");
				playErrorSound();
				flag=false;
			}
			// force the field to be with letters only and to nut contain any other characters
			for (int i = 0; i<areaName.getText().length(); i++) {
				if (areaName.getText().charAt(i) < '9' && areaName.getText().charAt(i) > '0') {
					flag=false;
					throw new InvalidInputException(); 	
				}
			}
			if (time2.getText().length() == 0) {
				time2.setStyle("-fx-text-fill: red; -fx-border-radius: 30; -fx-background-radius: 30; -fx-border-color: red");
				status.setText("All fields must be field currectly");
				playErrorSound();
				flag=false;
			}
			
			// force the field to be numeric only and to nut contain string
			for (int i = 0; i<time2.getText().length(); i++) {
				if (time2.getText().charAt(i) < 'z' && time2.getText().charAt(i) > 'a' || 
						time2.getText().charAt(i) < 'Z' && time2.getText().charAt(i) > 'A') {
					flag=false;
					throw new InvalidInputException(); 	
				}
			}
		} 
		if (flag) {
			//if this id already exists 
			if (Main.restaurant.getAreas().get(DeliveryArea.getIdCounter()) != null) {
				DeliveryArea.fixID();
			}
			
			HashSet<Neighberhood>  neighberhoods = new HashSet<Neighberhood>(); 
			for (Neighberhood n : neighberhoodsList.getSelectionModel().getSelectedItems()) {
				neighberhoods.add(n);
			}
			
			DeliveryArea da = new DeliveryArea(areaName.getText(),neighberhoods,Integer.parseInt(time2.getText()));
			Main.restaurant.addDeliveryArea(da);
			save();

			deliveryAreaList.setItems(FXCollections.observableArrayList(Main.restaurant.getAreas().values()));
			status.setText("Delivery Area Added successfully");
		}	
	}
	
	@FXML
	public void removeArea (ActionEvent event) throws Exception {
		playSound();
		ObservableList<DeliveryArea> list = deliveryAreaList.getSelectionModel().getSelectedItems();
		if (list.size() != 2) {
			//New Alert
			Alert a = new Alert(AlertType.ERROR);
			a.setTitle("ERROR");
			a.setHeaderText("You need to choose 2 areas");
			a.showAndWait();
			
		} else {
			Main.restaurant.removeDeliveryArea(list.get(0), list.get(1));
			save();
			status.setText("Delivery Area removed successfully");
			observableArrayList = FXCollections.observableArrayList(Main.restaurant.getAreas().values());
			deliveryAreaList.setItems(observableArrayList);
		}

		if (Main.restaurant.getAreas().size() == 0) {
			replace.setVisible(false);
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
