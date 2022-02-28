package controller;

import java.io.File;

import application.Main;
import exceptions.InvalidInputException;
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
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import utils.Gender;
import utils.Neighberhood;

public class UpdatePersonalDetailsController {
	
	@FXML
	private AnchorPane mainScreen;
	@FXML
	private TextField fName;
	@FXML
	private TextField lName;
	@FXML
	private DatePicker date;
	@FXML
	private ToggleGroup gender;
	@FXML
	private RadioButton radio1, radio2, radio3;
	@FXML
	private ChoiceBox<Neighberhood> neighberhoodBox;
	private ObservableList<Neighberhood> listOfNeighberhoods = FXCollections.observableArrayList(Neighberhood.values());
	@FXML
	private CheckBox lactose;
	@FXML
	private CheckBox gluten;
	
	@FXML
	public void save() {
		Main.getRestaurant().seralize();
	}
	
	@FXML
	public void initialize() {
		
		fName.setText(MainController.getC().getFirstName());
		lName.setText(MainController.getC().getLastName());
		date.setValue(MainController.getC().getBirthDay());
		
		if(MainController.getC().getGender() == Gender.Female) {
			radio1.setSelected(true);
		}
		if(MainController.getC().getGender() == Gender.Male) {
			radio2.setSelected(true);
		}
		if(MainController.getC().getGender() == Gender.Unknown) {
			radio3.setSelected(true);
		}
		
		neighberhoodBox.setValue(Neighberhood.DownTown);
		neighberhoodBox.setItems(listOfNeighberhoods);
		neighberhoodBox.setValue(MainController.getC().getNeighberhood());
		
		if (MainController.getC().isSensitiveToGluten()) {
			gluten.setSelected(true);
		}
			
		if (MainController.getC().isSensitiveToLactose()) {
			lactose.setSelected(true);
		}
		
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
		        	  		fName.setStyle("-fx-text-fill: red; -fx-border-radius: 30; -fx-background-radius: 30; -fx-border-color: red; -fx-background-color: black");
	        	  		else 
		        	  		fName.setStyle("-fx-border-radius: 30; -fx-background-radius: 30; -fx-border-color: white; -fx-background-color: black; -fx-text-fill: white");
	        	  	} else {
	        	  		fName.setStyle("-fx-border-radius: 30; -fx-background-radius: 30; -fx-border-color: white; -fx-background-color: black; -fx-text-fill: white");
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
	        	  			lName.setStyle("-fx-text-fill: red; -fx-border-radius: 30; -fx-background-radius: 30; -fx-border-color: red; -fx-background-color: black");
	        	  		else 
	        	  			lName.setStyle("-fx-border-radius: 30; -fx-background-radius: 30; -fx-border-color: white; -fx-background-color: black; -fx-text-fill: white");
	        	  	} else {
        	  			lName.setStyle("-fx-border-radius: 30; -fx-background-radius: 30; -fx-border-color: white; -fx-background-color: black; -fx-text-fill: white");

	        	  	}
	          }
		});
		
		
		
		
		
	}
	
	@FXML
	public void saveChanges (ActionEvent event) throws Exception {
		playSound();
		
		boolean flag = true;
		if (flag) {
			// force the field to be with letters only and to nut contain any other characters
	  		for (int i=0 ; i < fName.getText().length(); i++) {
	  			if (! (Character.isLetter(fName.getText().charAt(i))) && !(Character.isSpaceChar(fName.getText().charAt(i)))) {
	  				flag = false;
	  				throw new InvalidInputException();
	  			}		
	  		}	
	  		for (int i=0 ; i < lName.getText().length(); i++) {
	  			if (! (Character.isLetter(lName.getText().charAt(i))) && !(Character.isSpaceChar(lName.getText().charAt(i)))) {
	  				flag = false;
	  				throw new InvalidInputException();
	  			}		
	  		}
		}
		
		if (flag) {
			Gender g = null;
			RadioButton selectedRadioButton = (RadioButton) gender.getSelectedToggle();
			if (selectedRadioButton.getText().equals( "Female")) {
				g = Gender.Female;
			}
			if (selectedRadioButton.getText().equals("Male")) {
				g = Gender.Male;
			}
			if (selectedRadioButton.getText().equals("Unknowm")) {
				g = Gender.Unknown;
			}
			
		
		  	
			MainController.getC().setFirstName(fName.getText());
			MainController.getC().setLastName(lName.getText());
			MainController.getC().setBirthDay(date.getValue());
			MainController.getC().setGender(g);
			MainController.getC().setNeighberhood(neighberhoodBox.getValue());
			MainController.getC().setSensitiveToGluten(gluten.isSelected());
			MainController.getC().setSensitiveToLactose(lactose.isSelected());
			save();

			//New Alert
			Alert a = new Alert(AlertType.CONFIRMATION);
			a.setTitle("Changes saved");
			a.setHeaderText("Thanks " + fName.getText() + "!\nThe changes saved successfully");
			a.showAndWait();
		}
		
		

	}
	
	@FXML
	public void changePassword (ActionEvent event) throws Exception {
		playSound();
		mainScreen.setVisible(true);
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/changePass.fxml"));
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
