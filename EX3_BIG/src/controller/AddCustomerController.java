package controller;

import java.io.File;
import java.time.LocalDate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import application.Main;
import exceptions.AllFieldsAreIncompleteException;
import exceptions.InvalidAgeException;
import exceptions.InvalidInputException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Tooltip;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import model.Customer;
import utils.Gender;
import utils.Neighberhood;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class AddCustomerController  {
	
	@FXML
	private AnchorPane mainScreen;
	
	@FXML
	private Label accountLabel;
	@FXML
	private TextField firstNametxt;
	@FXML
	private TextField lastNametxt;
	@FXML
	private DatePicker dateOfBirth;
	@FXML
	private ToggleGroup gender;
	@FXML
	private HBox genderHbox;
	@FXML
	private CheckBox lactose;
	@FXML
	private CheckBox gluten;
	@FXML
	private TextField userNametxt;
	@FXML
	private PasswordField password;	
	@FXML
	private ObservableList<Neighberhood> listOfNeighberhoods = FXCollections.observableArrayList(Neighberhood.Neve_Shanan, Neighberhood.Kiriat_Haim,
			Neighberhood.DownTown, Neighberhood.Vardia, Neighberhood.Bat_Galim, Neighberhood.Merkaz_Karmel, Neighberhood.Denya, Neighberhood.Kiriat_Eliezer,
			Neighberhood.Hadar, Neighberhood.Romema, Neighberhood.German_Colony, Neighberhood.Vadi_Nisnas, Neighberhood.Vadi_Saliv,
			Neighberhood.Neot_Peres, Neighberhood.Kababir, Neighberhood.Neve_David,Neighberhood.Karmelia, Neighberhood.Halisa,
			Neighberhood.French_Karmel, Neighberhood.Ramat_Hanasi, Neighberhood.Neve_Yosef, Neighberhood.Ramat_Almogi);
	@FXML
	private ChoiceBox<Neighberhood> neighberhoodBox;
	
	@FXML
	private Customer c = MainController.c;
	@FXML
	private Label bad;
	@FXML
	private Label good;
	@FXML
	private Label best;
	@FXML
	private Tooltip ttPass;

	
	
	@FXML
	public void initialize() {
		
		//Initialize the entries in the list
		neighberhoodBox.setValue(Neighberhood.DownTown);
		neighberhoodBox.setItems(listOfNeighberhoods);
		
		//Alerts in real time whether the value in the field is correct
		firstNametxt.addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
	          public void handle(KeyEvent arg0) {
  
					//Input Check
	        	    //Can contain letters and space only
	        	  	boolean allLetters = firstNametxt.getText().chars().allMatch(Character::isLetter);
	        	  	if (! allLetters) {
	        	  		boolean isValid = true;
	        	  		for (int i=0 ; i < firstNametxt.getText().length(); i++) {
	        	  			if (! (Character.isLetter(firstNametxt.getText().charAt(i))) && !(Character.isSpaceChar(firstNametxt.getText().charAt(i)))) {
	        	  				isValid = false;
	        	  			}		
	        	  		}
	        	  		if (!isValid) {
	        	  			firstNametxt.setStyle("-fx-text-fill: red; -fx-border-radius: 30; -fx-background-radius: 30; -fx-border-color: red");
	        	  		} else 
	        	  			firstNametxt.setStyle("-fx-border-color: grey; -fx-border-radius: 30; -fx-background-radius: 30");	
	        	  	}
	        	  	else {
        	  			firstNametxt.setStyle("-fx-border-color: grey; -fx-border-radius: 30; -fx-background-radius: 30");	
	        	  	}
	          }
		});
		//Alerts in real time whether the value in the field is correct
		lastNametxt.addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
	          public void handle(KeyEvent arg0) {

					//Input Check
	        	  	boolean allLetters = lastNametxt.getText().chars().allMatch(Character::isLetter);
	        	  	if (! allLetters) {
	        	  		boolean isValid = true;
	        	  		for (int i=0 ; i < lastNametxt.getText().length(); i++) {
	        	  			if (! (Character.isLetter(lastNametxt.getText().charAt(i))) && !(Character.isSpaceChar(lastNametxt.getText().charAt(i)))) {
	        	  				isValid = false;
	        	  			}		
	        	  		}
	        	  		if (!isValid) {
	        	  			lastNametxt.setStyle("-fx-text-fill: red; -fx-border-radius: 30; -fx-background-radius: 30; -fx-border-color: red");
	        	  		} else
		        	  		lastNametxt.setStyle("-fx-border-color: grey; -fx-border-radius: 30; -fx-background-radius: 30");	
	        	  	
	        	  	} else 
	        	  		lastNametxt.setStyle("-fx-border-color: grey; -fx-border-radius: 30; -fx-background-radius: 30");	
	          }
		});
		
		bad.setVisible(false);
		good.setVisible(false);
		best.setVisible(false);

		//Alerts in real time whether the new password is correct
		password.addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
	          public void handle(KeyEvent arg0) {
	        	  
					int length = password.getText().length();
					
					if(length > 0 && length <= 4){
						setMessage(bad,Color.RED);
						removeAllStyle(password);
						password.getStyleClass().add("bad");
						good.setVisible(false);
						best.setVisible(false);
					
					}else if(length <= 8 && length > 4){
						setMessage(good ,Color.ORANGE);
						removeAllStyle(password);
						password.getStyleClass().add("good"); 
						best.setVisible(false);
						
					}else if(length > 8 ){
						setMessage(best ,Color.GREEN);
						removeAllStyle(password);
						password.getStyleClass().add("best");
						
					} else{ 
						//length == 0
						removeAllStyle(password);
						bad.setVisible(false);
						good.setVisible(false);
						best.setVisible(false);
					}
	          };
		});
	}
		
	public void setMessage(Label l, Color color){
		if (color == Color.RED) {
			l.setStyle("-fx-background-color: red");
		}
		if (color == Color.ORANGE) {
			l.setStyle("-fx-background-color:orange");
		} 
		if (color == Color.GREEN){
			l.setStyle("-fx-background-color:green ");
		}
		
		l.setVisible(true); 
	}
	
	public void removeAllStyle(PasswordField f){
		f.getStyleClass().removeAll("bad","good","best"); 
		
	}
	@FXML
	public void save() {
		Main.getRestaurant().seralize();
	}

	//After clicking 'Create New User' button and fill in all the details
	@FXML
	public void createAccount (ActionEvent event) throws Exception {
	
		playSound();
		
		boolean flag = true;

		if (flag) {
			/*Tests to make sure that the fields that the user needs to fill are
			not left blank or wrong*/
	  		for (int i=0 ; i < firstNametxt.getText().length(); i++) {
	  			if (! (Character.isLetter(firstNametxt.getText().charAt(i))) && !(Character.isSpaceChar(firstNametxt.getText().charAt(i)))) {		
	  				flag = false;
	  				firstNametxt.setStyle("-fx-border-color: red; -fx-border-width: 1px; -fx-border-radius: 30; -fx-background-radius: 30");
	  				throw new InvalidInputException();
	  			}		
	  		}
	  		
	  		for (int i=0 ; i < lastNametxt.getText().length(); i++) {
	  			if (! (Character.isLetter(lastNametxt.getText().charAt(i))) && !(Character.isSpaceChar(lastNametxt.getText().charAt(i)))) {
	  				lastNametxt.setStyle("-fx-border-color: red; -fx-border-width: 1px; -fx-border-radius: 30; -fx-background-radius: 30");
	  				flag = false;
	  				throw new InvalidInputException();
	  			}		
	  		}
			
			if (firstNametxt.getText().length() == 0) {
				firstNametxt.setStyle("-fx-border-color: red; -fx-border-width: 1px; -fx-border-radius: 30; -fx-background-radius: 30");
				accountLabel.setText("All fields must be field currectly");
				flag = false;
				throw new AllFieldsAreIncompleteException();
			}
			if (lastNametxt.getText().length() == 0) {
				lastNametxt.setStyle("-fx-border-color: red; -fx-border-width: 1px; -fx-border-radius: 30; -fx-background-radius: 30");
				accountLabel.setText("All fields must be field currectly");
				flag = false;
				throw new AllFieldsAreIncompleteException();
			}
			if (dateOfBirth.getValue() == null) {
				flag = false;
				throw new InvalidAgeException();
			}
			//Date value can not be in the future
			if (dateOfBirth.getValue() != null) {
				if (dateOfBirth.getValue().isAfter(LocalDate.now())) {
					flag = false;
					throw new InvalidAgeException();
				}
			}
			if (gender.getSelectedToggle() == null) {
				accountLabel.setText("Gender must be fill out");
				flag = false;
				throw new AllFieldsAreIncompleteException();
			}
			if (userNametxt.getText().length() == 0) {
				userNametxt.setStyle("-fx-border-color: red; -fx-border-width: 1px; -fx-border-radius: 30; -fx-background-radius: 30");
				accountLabel.setText("All fields must be field currectly");
				flag = false;
				throw new AllFieldsAreIncompleteException();
			}
			if (password.getText().length() == 0 || password.getText().length() < 8) {
				password.setStyle("-fx-border-color: red; -fx-border-width: 1px; -fx-border-radius: 30; -fx-background-radius: 30");
				playErrorSound();
				accountLabel.setText("All fields must be field currectly");
				flag = false;
			
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
			
			//if this id already exists 
			if (Main.restaurant.getCustomers().get(Customer.getIdCounter()) != null) {
				Customer.fixID();
			}
			//create new customer
			Customer c = new Customer(firstNametxt.getText(),lastNametxt.getText(), dateOfBirth.getValue(), g, neighberhoodBox.getValue(), lactose.isSelected(), gluten.isSelected(),
					userNametxt.getText(), password.getText());
			
			
			if (Main.restaurant.addCustomer(c)) {
				MainController.setC(c); //set the customer that is online right now
				
				//New Alert
				Alert a = new Alert(AlertType.CONFIRMATION);
				a.setTitle("New Account");
				a.setHeaderText("Hello " + c.getFirstName() + "!\nThe account was created successfully");
				a.showAndWait();
				save();
				
				Parent p = FXMLLoader.load(getClass().getResource("/View/CustomerView.fxml"));
				Scene s = new Scene(p, 900,600);
				s.getStylesheets().add(getClass().getResource("controller.css").toExternalForm());
				Stage window =  (Stage) ((Node) event.getSource()).getScene().getWindow();
				window.setScene(s);
				window.show();	
				
			}
			
			else {
				playErrorSound();
				accountLabel.setText("An error occurred while creating the account");
			}
		}		
	}
	
	@FXML
	public void goBack (ActionEvent event) throws Exception {
		playSound();
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/LoginFX.fxml"));
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
	
	//NEW SOUND EFFECT
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
