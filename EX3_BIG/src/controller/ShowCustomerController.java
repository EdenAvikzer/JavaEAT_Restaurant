package controller;


import java.io.File;

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
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import model.Customer;
import utils.Neighberhood;

public class ShowCustomerController {
	
	@FXML
	private AnchorPane mainScreen;
	@FXML
	private AnchorPane replace;
	
	@FXML
	private ListView<Customer> customersList;
	
	ObservableList<Customer> observableArrayList = FXCollections.observableArrayList(Main.restaurant.getCustomers().values());

	@FXML
	private ListView<Customer> customersBlackList;
	
	ObservableList<Customer> observableArrayListB = FXCollections.observableArrayList(Main.restaurant.getBlackList());
	
	@FXML
	private Label status;
	@FXML
	private TextField firstName;
	@FXML
	private TextField lastName;
	@FXML
	private Label birthday;
	@FXML
	private ChoiceBox<Neighberhood> neighberhood;
	@FXML
	private Label gender;
	@FXML
	private CheckBox gluten;
	@FXML
	private CheckBox lactose;
    @FXML
    private Label userName;
    @FXML
    private Label password;
	@FXML
	private Button add;
	@FXML
	private TextField searchById;
	
	@FXML
	private static Customer cust;
	
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
	    			Customer cust = Main.restaurant.getRealCustomer(id);
	    			if (cust == null ) {
	    				playErrorSound();
	    				//New Alert
	        			Alert a = new Alert(AlertType.ERROR);
	        			a.setTitle("Error");
	        			a.setHeaderText("There is no such ID, try again");
	        			a.showAndWait();
	    			} else {
	    				observableArrayList = FXCollections.observableArrayList(cust); 
	    				customersList.setItems(observableArrayList);
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
	    		observableArrayList = FXCollections.observableArrayList(Main.restaurant.getCustomers().values()); 
	    		customersList.setItems(observableArrayList);
	    	}
	    }
	
	@FXML
	public void initialize() {
		customersList.setItems(observableArrayList);
		customersBlackList.setItems(observableArrayListB);
		neighberhood.setItems(FXCollections.observableArrayList(Neighberhood.values()));

		replace.setVisible(false);
		
		//Call to the method 'showDetails' according to the selection in ListView
		customersList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Customer>() {

			@Override
			public void changed(ObservableValue<? extends Customer> arg0, Customer arg1, Customer arg2) {
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
	        	  	//Can contain letters and space only
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
	
	//"onAction" - Edit button
		@FXML
		public void edit (ActionEvent event) throws Exception {
			playSound();
			cust = customersList.getSelectionModel().getSelectedItem();
			
			boolean flag = true;
			
			if (firstName.getText().length() == 0) {
				flag =  false;
				throw new AllFieldsAreIncompleteException();
			}
			
			//Input Check
			for (int i = 0; i < firstName.getText().length(); i++) {
				if (!Character.isLetter(firstName.getText().charAt(i))) {
					flag =  false;
					throw new InvalidInputException();
				}
			}
			if (flag) {
				cust.setFirstName(firstName.getText());
			}
			
			if (lastName.getText().length() == 0) {
				flag =  false;
				throw new AllFieldsAreIncompleteException();
			}
		
			//Input Check
			flag = true;
			for (int i = 0; i < lastName.getText().length(); i++) {
				if (!Character.isLetter(lastName.getText().charAt(i))) {
					flag =  false;
					throw new InvalidInputException();
				}
			}
			if (flag) {
				cust.setLastName(lastName.getText());
			}
			cust.setNeighberhood(neighberhood.getValue());
			cust.setSensitiveToGluten(gluten.isSelected());
			cust.setSensitiveToLactose(lactose.isSelected());
			
			customersList.setItems(FXCollections.observableArrayList(Main.restaurant.getCustomers().values()));
			
			//New Alert
			Alert a = new Alert(AlertType.CONFIRMATION);
			a.setTitle("CONFIRMATION");
			a.setHeaderText("Changes saved successfully");
			a.showAndWait();
			
			save();
		}
	
	
	@FXML
	public void showDetails() {

		cust = customersList.getSelectionModel().getSelectedItem();
		userName.setText(cust.getUserName());
		password.setText(cust.getPassword());
		firstName.setText(cust.getFirstName());
		lastName.setText(cust.getLastName());
		birthday.setText(cust.getBirthDay().toString());
		gender.setText(cust.getGender().name());
		neighberhood.setValue(cust.getNeighberhood());
		
		if (cust.isSensitiveToGluten()) {
			gluten.setSelected(true);
		}
		else {
			gluten.setSelected(false);
		}
		
		if (cust.isSensitiveToLactose()) {
			lactose.setSelected(true);
		}
		else {
			lactose.setSelected(false);
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
	public void addCustToBlackList (ActionEvent event) throws Exception {
		playSound();
		cust = customersList.getSelectionModel().getSelectedItem();
		Main.restaurant.addCustomerToBlackList(cust);
		save();
		observableArrayListB = FXCollections.observableArrayList(Main.restaurant.getBlackList());
		customersBlackList.setItems(observableArrayListB);
		
		status.setText("Added to blacklist successfully");
		
	}
	
	@FXML
	public void addCust (ActionEvent event) throws Exception {
		playSound();
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/addCustomer.fxml"));
		AnchorPane pane = loader.load();
		mainScreen.getChildren().removeAll(mainScreen.getChildren());
		mainScreen.getChildren().add(pane);
		
		status.setText("Customer Added successfully");
		save();
	}
	
	@FXML
	public void removeCust (ActionEvent event) throws Exception {
		
		playSound();
		cust = customersList.getSelectionModel().getSelectedItem();
		Main.restaurant.removeCustomer(cust);
		observableArrayList = FXCollections.observableArrayList(Main.restaurant.getCustomers().values());
		customersList.setItems(observableArrayList);
		observableArrayListB = FXCollections.observableArrayList(Main.restaurant.getBlackList());
		customersBlackList.setItems(observableArrayListB);
		
		status.setText("Customer removed successfully");
		
		if (Main.restaurant.getCustomers().size() == 0) {
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
