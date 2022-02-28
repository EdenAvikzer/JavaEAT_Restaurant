package controller;

import java.io.File;
import java.time.LocalDate;
import java.time.Period;

import application.Main;
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
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import model.Cook;
import model.Restaurant;
import utils.Expertise;
import utils.Gender;

public class ShowCookController {
	
	@FXML
	private AnchorPane mainScreen;
	@FXML
	private AnchorPane replace;
	@FXML
	private AnchorPane addCookAP;
	@FXML
	private ListView<Cook> cooksList;
	ObservableList<Cook> observableArrayList = FXCollections.observableArrayList(Main.restaurant.getCooks().values());

	@FXML
	private Label status;
	@FXML
	private Label firstName;
	@FXML
	private Label lastName;
	@FXML
	private Label birthday;
	@FXML
	private Label gender;
	@FXML
	private Label expert;
    @FXML
    private CheckBox chef;
	@FXML
	private Cook cook;
	@FXML
	private TextField searchById;
	@FXML
	private TextField fName;
	@FXML
	private TextField lName;
	@FXML
	private DatePicker date;
	@FXML
	private ToggleGroup gender1;
	@FXML
	private ChoiceBox<Expertise> newExpertise;
	@FXML
	private CheckBox isChef;
	
	
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
	    			Cook cook = Main.restaurant.getRealCook(id);
	    			if (cook == null ) {
	    				playErrorSound();
	    				//New Alert
	        			Alert a = new Alert(AlertType.ERROR);
	        			a.setTitle("Error");
	        			a.setHeaderText("There is no such ID, try again");
	        			a.showAndWait();
	    			} else {
	    				observableArrayList = FXCollections.observableArrayList(cook); 
	    				cooksList.setItems(observableArrayList);
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
	    		observableArrayList = FXCollections.observableArrayList(Main.restaurant.getCooks().values()); 
	    		cooksList.setItems(observableArrayList);
	    	}
	    }
	
	@FXML
	public void initialize() {
	
		cooksList.setItems(FXCollections.observableArrayList(Main.restaurant.getCooks().values()));
		replace.setVisible(false);
		newExpertise.setItems(FXCollections.observableArrayList(Expertise.values()));
		
		//Will Will turn to the method 'showDetails' according to the selection in ListView
		cooksList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Cook>() {

			@Override
			public void changed(ObservableValue<? extends Cook> arg0, Cook arg1, Cook arg2) {
				playSound();
				showDetails();
			}
			
		});
		
		//Alerts in real time whether the value in the field is correct
		fName.addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
	          public void handle(KeyEvent arg0) {

					//Input Check
	        	  	//Can contain letters and space only
	        	  	boolean allLetters = fName.getText().chars().allMatch(Character::isLetter);
	        	  	if (! allLetters) {
	        	  		boolean isValid = true;
	        	  		for (int i=0 ; i < fName.getText().length(); i++) {
	        	  			if (! (Character.isLetter(fName.getText().charAt(i))) && !(Character.isSpaceChar(fName.getText().charAt(i)))) {
	        	  				isValid = false;
	        	  			}		
	        	  		}
	        	  		if (!isValid)
		        	  		fName.setStyle("-fx-text-fill: red; -fx-border-radius: 30; -fx-background-radius: 30; -fx-border-color: red");
	        	  		else 
		        	  		fName.setStyle("-fx-border-radius: 30; -fx-background-radius: 30; -fx-border-color: #71def9");
	        	  	} else {
	        	  		fName.setStyle("-fx-border-radius: 30; -fx-background-radius: 30; -fx-border-color: #71def9");
	        	  	}
	          }
		});
		lName.addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
	          public void handle(KeyEvent arg0) {

					//Input Check
	        	    //Can contain letters and space only
	        	  	boolean allLetters = lName.getText().chars().allMatch(Character::isLetter);
	        	  	if (! allLetters) {
	        	  		
	        	  		boolean isValid = true;
	        	  		for (int i=0 ; i < lName.getText().length(); i++) {
	        	  			if (! (Character.isLetter(lName.getText().charAt(i))) && !(Character.isSpaceChar(lName.getText().charAt(i)))) {
	        	  				isValid = false;
	        	  			}		
	        	  		}
	        	  		if (!isValid)
		        	  		lName.setStyle("-fx-text-fill: red; -fx-border-radius: 30; -fx-background-radius: 30; -fx-border-color: red");
	        	  		else 
		        	  		lName.setStyle("-fx-border-radius: 30; -fx-background-radius: 30; -fx-border-color: #71def9");
	        	  	} else {
	        	  		lName.setStyle("-fx-border-radius: 30; -fx-background-radius: 30; -fx-border-color: #71def9");
	        	  	}
	          }
		});	
	}
	
	@FXML
	public void showDetails() {

		cook = cooksList.getSelectionModel().getSelectedItem();
		firstName.setText(cook.getFirstName());
		lastName.setText(cook.getLastName());
		birthday.setText(cook.getBirthDay().toString());
		gender.setText(cook.getGender().name());
		expert.setText(cook.getExpert().name());
		
		if (cook.getIsChef()) {
			chef.setSelected(true);
		}
		else {
			chef.setSelected(false);
		}

		replace.setVisible(true);
		
	}
	
	@FXML
    void edit(ActionEvent event) {
		playSound();
		cook = cooksList.getSelectionModel().getSelectedItem();
		
		//set new data
		cook.setChef(chef.isSelected());
		
		//New Alert
		Alert a = new Alert(AlertType.CONFIRMATION);
		a.setTitle("CONFIRMATION");
		a.setHeaderText("Changes saved successfully");
		a.showAndWait();
		
		save();
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
	public void addCook (ActionEvent event) throws Exception {
		playSound();
		boolean flag = true;
		if (flag) {
			if (fName.getText().length() == 0) {
    	  		fName.setStyle("-fx-text-fill: red; -fx-border-radius: 30; -fx-background-radius: 30; -fx-border-color: red");
				status.setText("All fields must be field currectly");
				playErrorSound();
				flag = false;
			}
			// force the field to be with letters only and to nut contain any other characters
			for (int i = 0; i<fName.getText().length(); i++) {
				if (fName.getText().charAt(i) < '9' && fName.getText().charAt(i) > '0') {
					flag = false;
					throw new InvalidInputException(); 	
				}
			}
			if (lName.getText().length() == 0) {
				lName.setStyle("-fx-text-fill: red; -fx-border-radius: 30; -fx-background-radius: 30; -fx-border-color: red");
				status.setText("All fields must be field currectly");
				flag = false;
				playErrorSound();
			}
			// force the field to be with letters only and to nut contain any other characters
			for (int i = 0; i<lName.getText().length(); i++) {
				if (lName.getText().charAt(i) < '9' && lName.getText().charAt(i) > '0') {
					flag = false;
					throw new InvalidInputException(); 	
				}
			}
			if (!date.isPressed()) {
				status.setText("All fields must be field currectly");
				flag = false;
				playErrorSound();
			}
			//check that the employee is at least 16 yo
			Period period = Period.between(LocalDate.now().minusYears(16), date.getValue());
		    if (period.getYears() > 0 || date.getValue().isAfter(LocalDate.now())) {
		    	flag = false;
		    	throw new InvalidAgeException();
		    }
			
		
			if (gender1.getSelectedToggle() == null) {
				status.setText("Gender must be fill out");
				flag = false;
				playErrorSound();
			}
			
			if (!newExpertise.isPressed()) {
				status.setText("Select Expertise");
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
			if (Main.restaurant.getCooks().get(Cook.getIdCounter()) != null) {
				Cook.fixID();
			}
			
			Cook c = new Cook(fName.getText(), lName.getText(),date.getValue(), g ,newExpertise.getValue(),isChef.isSelected());
			Main.restaurant.addCook(c);
			save();
			System.out.println(Main.restaurant.getCooks());
			cooksList.setItems(FXCollections.observableArrayList(Main.restaurant.getCooks().values()));
			status.setText("Cook Added successfully");
		} 
	}
	
	@FXML
	public void removeCook (ActionEvent event) throws Exception {
		playSound();
		cook = cooksList.getSelectionModel().getSelectedItem();
		Main.restaurant.removeCook(cook);
		observableArrayList = FXCollections.observableArrayList(Restaurant.getInstance().getCooks().values());
		cooksList.setItems(observableArrayList);

		
		status.setText("Cook removed successfully");
		
		if (Main.restaurant.getCooks().size() == 0) {
			replace.setVisible(false);
		}
		save();
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
