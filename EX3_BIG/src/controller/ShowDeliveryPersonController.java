package controller;


import java.io.File;
import java.time.LocalDate;
import java.time.Period;

import application.Main;
import exceptions.AllFieldsAreIncompleteException;
import exceptions.InvalidAgeException;
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
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.KeyEvent;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import model.DeliveryArea;
import model.DeliveryPerson;
import utils.Gender;
import utils.Vehicle;

public class ShowDeliveryPersonController {
	
	@FXML
	private AnchorPane mainScreen;
	
	@FXML
	private ListView<DeliveryPerson> deliveryPersonsList;
	ObservableList<DeliveryPerson> observableArrayList = FXCollections.observableArrayList(Main.restaurant.getDeliveryPersons().values());
	@FXML
	private AnchorPane replace;
	@FXML
	private AnchorPane addDelPerAP;
	
	@FXML
	private Label fname;
	@FXML
	private Label lname;
	@FXML
	private Label date;
   
	@FXML
	private Label gender;
	@FXML
	private DeliveryPerson dp;
	@FXML
	private Label status;

    @FXML
    private CheckBox available;
	
	@FXML
	private TextField firstName;
	@FXML
	private TextField lastName;
	@FXML
	private DatePicker birthday;
	@FXML
	private ToggleGroup gender1;
	@FXML
	private ChoiceBox<Vehicle> vehicleList;
	@FXML
    private ChoiceBox<Vehicle> vehicle; 
	private ObservableList<Vehicle> listOfVehicles = FXCollections.observableArrayList(Vehicle.values());
	
	@FXML
	private ChoiceBox<DeliveryArea> areasList;
    @FXML
    private ChoiceBox<DeliveryArea> area;
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
	    			DeliveryPerson dp = Main.restaurant.getRealDeliveryPerson(id);
	    			if (dp == null ) {
	    				playErrorSound();
	    				//New Alert
	        			Alert a = new Alert(AlertType.ERROR);
	        			a.setTitle("Error");
	        			a.setHeaderText("There is no such ID, try again");
	        			a.showAndWait();
	    			} else {
	    				observableArrayList = FXCollections.observableArrayList(dp); 
	    				deliveryPersonsList.setItems(observableArrayList);
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
	    		observableArrayList = FXCollections.observableArrayList(Main.restaurant.getDeliveryPersons().values()); 
	    		deliveryPersonsList.setItems(observableArrayList);
	    	}
	    }
	
	@FXML
	public void initialize() {
		
		replace.setVisible(false);
		
		//Initialize the entries in the list
		vehicleList.setItems(listOfVehicles);
		vehicle.setItems(listOfVehicles);
		deliveryPersonsList.setItems(observableArrayList);
		areasList.setItems(FXCollections.observableArrayList(Main.restaurant.getAreas().values()));
		area.setItems(FXCollections.observableArrayList(Main.restaurant.getAreas().values()));
		
		deliveryPersonsList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<DeliveryPerson>() {

			@Override
			public void changed(ObservableValue<? extends DeliveryPerson> arg0, DeliveryPerson arg1, DeliveryPerson arg2) {
				playSound();
				showDetails();
			}
			
		});
		
		//Alerts in real time whether the value in the field is correct
		firstName.addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
	          public void handle(KeyEvent arg0) {

	        	  	//Input Check
	        	    //Can contain letters and space only
	        	  	boolean allLetters = firstName.getText().chars().allMatch(Character::isLetter);
	        	  	if (! allLetters) {
	        	  		
	        	  		boolean isValid = true;
	        	  		for (int i=0 ; i < firstName.getText().length(); i++) {
	        	  			if (! (Character.isLetter(firstName.getText().charAt(i))) && !(Character.isSpaceChar(firstName.getText().charAt(i)))) {
	        	  				isValid = false;
	        	  			}		
	        	  		}
	        	  		if (!isValid)
		        	  		firstName.setStyle("-fx-text-fill: red; -fx-border-radius: 30; -fx-background-radius: 30; -fx-border-color: red");
	        	  		else 
		        	  		firstName.setStyle("-fx-border-radius: 30; -fx-background-radius: 30; -fx-border-color: #71def9");
	        	  	} else {
	        	  		firstName.setStyle("-fx-border-radius: 30; -fx-background-radius: 30; -fx-border-color: #71def9");
	        	  	}
	          }
		});
		lastName.addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
	          public void handle(KeyEvent arg0) {

					//Input Check
	        	  	boolean allLetters = lastName.getText().chars().allMatch(Character::isLetter);
	        	  	if (! allLetters) {
	        	  		
	        	  		boolean isValid = true;
	        	  		for (int i=0 ; i < lastName.getText().length(); i++) {
	        	  			if (! (Character.isLetter(lastName.getText().charAt(i))) && !(Character.isSpaceChar(lastName.getText().charAt(i)))) {
	        	  				isValid = false;
	        	  			}		
	        	  		}
	        	  		if (!isValid)
		        	  		lastName.setStyle("-fx-text-fill: red; -fx-border-radius: 30; -fx-background-radius: 30; -fx-border-color: red");
	        	  		else 
		        	  		lastName.setStyle("-fx-border-radius: 30; -fx-background-radius: 30; -fx-border-color: #71def9");
	        	  	} else {
	        	  		lastName.setStyle("-fx-border-radius: 30; -fx-background-radius: 30; -fx-border-color: #71def9");
	        	  	}
	          }
		});
		
		
		
	}
	
	protected void showDetails() {
		
		dp = deliveryPersonsList.getSelectionModel().getSelectedItem();
		
		fname.setText(dp.getFirstName());
		lname.setText(dp.getLastName());
		date.setText(dp.getBirthDay().toString());
		vehicle.setValue(dp.getVehicle());
		gender.setText(dp.getGender().name());
		area.setValue(dp.getArea());
	
		if (dp.getAvailable())
			available.setSelected(true);
		else {
			available.setSelected(false);
		}
		
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
	

    @FXML
    void edit(ActionEvent event) throws AllFieldsAreIncompleteException {
    	playSound();

    	dp = deliveryPersonsList.getSelectionModel().getSelectedItem();
    	boolean isValid = true;
    	if (area.getValue() == null || vehicle.getValue() == null) {
   		   isValid = false;
   		   throw new AllFieldsAreIncompleteException();
   	   	}
    	if (isValid) {
    		dp.setArea(area.getValue());
        	dp.setVehicle(vehicle.getValue());
        	dp.setAvailable(available.isSelected());
        	
        	 //New Alert
    		Alert a = new Alert(AlertType.CONFIRMATION);
    		a.setTitle("CONFIRMATION");
    		a.setHeaderText("Changes saved successfully");
    		a.showAndWait();
    		
    		save();
    	}	
    	
    }
	
	
	@FXML
	public void removeDelPer (ActionEvent event) throws Exception {
		playSound();
		dp = deliveryPersonsList.getSelectionModel().getSelectedItem();
		Main.restaurant.removeDeliveryPerson(dp);
		observableArrayList = FXCollections.observableArrayList(Main.restaurant.getDeliveryPersons().values());
		deliveryPersonsList.setItems(observableArrayList);
		
		status.setText("Delivery Person /n removed successfully");
		
		if (Main.restaurant.getDeliveryPersons().size() == 0) {
			replace.setVisible(false);
		}
		save();
		
		
	}
	
	@FXML
	public void addDelPer (ActionEvent event) throws Exception {
		playSound();
		boolean flag = true;
		
		if (flag) {
			if (firstName.getText().length() == 0) {
    	  		firstName.setStyle("-fx-border-radius: 30; -fx-background-radius: 30; -fx-border-color: red");
				status.setText("All fields must be field currectly");
				playErrorSound();
				flag = false;
			}
			// force the field to be with letters only and to nut contain any other characters
			for (int i = 0; i<firstName.getText().length(); i++) {
				if (firstName.getText().charAt(i) < '9' && firstName.getText().charAt(i) > '0') {
					flag = false;
					throw new InvalidInputException(); 	
				}
			}
			if (lastName.getText().length() == 0) {
				lastName.setStyle("-fx-border-radius: 30; -fx-background-radius: 30; -fx-border-color: red");
				status.setText("All fields must be field currectly");
				flag = false;
				playErrorSound();
			}
			// force the field to be with letters only and to nut contain any other characters
			for (int i = 0; i<lastName.getText().length(); i++) {
				if (lastName.getText().charAt(i) < '9' && lastName.getText().charAt(i) > '0') {
					flag = false;
					throw new InvalidInputException(); 	
				}
			}
			
			if (birthday.isPressed()) {
				birthday.setStyle("-fx-border-color: red; -fx-border-width: 2px");
				status.setText("All fields must be field currectly");
				flag = false;
				playErrorSound();
			}
			//check that the employee is at least 16 yo
			Period period = Period.between(LocalDate.now().minusYears(16), birthday.getValue());
		    if (period.getYears() > 0 || birthday.getValue().isAfter(LocalDate.now())) {
		    	flag = false;
		    	throw new InvalidAgeException();
		    }
			if (vehicleList.isPressed()) {
				vehicleList.setStyle("-fx-border-color: #f02491; -fx-border-width: 2px");
				status.setText("All fields must be field currectly");
				flag = false;
				playErrorSound();
			}
			
		} 
		if (flag) {
			Gender g = null;
			RadioButton selectedRadioButton = (RadioButton) gender1.getSelectedToggle();
			if (selectedRadioButton.getText().equals( "Female")) {
				g = Gender.Female;
			}
			if (selectedRadioButton.getText().equals("Male")) {
				g = Gender.Male;
			}
			if (selectedRadioButton.getText().equals("Unknowm")) {
				g = Gender.Unknown;
			}
			
			//if this id already exists 
			if (Main.restaurant.getDeliveryPersons().get(DeliveryPerson.getIdCounter()) != null) {
				DeliveryPerson.fixID();
			}
			
			DeliveryPerson delPer = new DeliveryPerson(firstName.getText(),lastName.getText(),
					birthday.getValue() , g ,vehicleList.getSelectionModel().getSelectedItem(),
					areasList.getValue(), true);
			
			Main.restaurant.addDeliveryPerson(delPer,areasList.getValue());
			save();
			deliveryPersonsList.setItems(FXCollections.observableArrayList(Main.restaurant.getDeliveryPersons().values()));
			status.setText("Delivery Person Added successfully");		
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
