package controller;



import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

import java.io.File;

import application.Main;
import exceptions.IllegalUserName;
import javafx.animation.RotateTransition;
import model.Customer;


public class MainController {
	
	@FXML
	private Label title;
	@FXML
	private TextField txtUserName;
	@FXML
	private PasswordField txtPassword;
	@FXML
	private Button newAcountButton;
	@FXML
	private Button Login;
	@FXML
	private AnchorPane mainScreen;
	@FXML
	private ImageView showPass;
	@FXML
	private ImageView hide;
	@FXML
	private TextField showPassword;
	@FXML
	static Customer c;
	@FXML
	private ImageView view;

	//Save the current customer that online
	public static Customer getC() {
		return c;
	}
	public static void setC(Customer c) {
		MainController.c = c;
	}
	
	@FXML
	public void save() {
		Main.getRestaurant().seralize();
	}
	
	@FXML
	public void initialize() {
		
		showPassword.setVisible(false);
		hide.setVisible(false);
		
		//in case the user want to see the password in the passwordField
		showPass.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {

		     @Override
		     public void handle(MouseEvent event) {
		    	 
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
		    	 
		    	 showPassword.setText(txtPassword.getText());
		    	 showPassword.setVisible(true);
		    	 txtPassword.setVisible(false);
		    	 hide.setVisible(true);
		    	 showPass.setVisible(false);
		         event.consume();
		     }
		});
		//in case the user want to hide the password in the passwordField
		hide.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {

		     @Override
		     public void handle(MouseEvent event) {
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
		    	 showPassword.setVisible(false);
		    	 txtPassword.setVisible(true);
		    	 hide.setVisible(false);
		    	 showPass.setVisible(true);
		         event.consume();
		     }
		});
		
		//Animation
		RotateTransition RotTransition = new RotateTransition(Duration.seconds(4),view);
		RotTransition.setByAngle(360);
		RotTransition.play();
	}


	@FXML
	public void Login (ActionEvent event) throws Exception {
		
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
		
		boolean isValid = false;
		
		//input check
		if (txtUserName.getText().length() == 0) {
			txtUserName.setStyle("-fx-border-color: RED ;-fx-border-width:0px 0px 2px 0px; -fx-background-color:transparent;");
			title.setText("All fields must be field");
			playErrorSound();
		}
		else {
			txtUserName.setStyle(null);
		}
		if (txtPassword.getText().length() == 0) {
			txtPassword.setStyle("-fx-border-color: RED ;-fx-border-width:0px 0px 2px 0px; -fx-background-color:transparent;");
			title.setText("All fields must be field");
			playErrorSound();
		}
		//Login manager
		if (txtUserName.getText().equals("manager") && txtPassword.getText().equals("manager") || 
				(txtUserName.getText().equals("manager") && showPassword.getText().equals("manager"))) {
			
			//New Alert
			Alert a = new Alert(AlertType.CONFIRMATION);
			a.setTitle("Login");
			a.setHeaderText("Welcome Back, Manager! \nLogin successfully");
			a.showAndWait();

			FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/ManagerView.fxml"));
			AnchorPane pane = loader.load();
			mainScreen.getChildren().removeAll(mainScreen.getChildren());
			mainScreen.getChildren().add(pane);
			isValid = true;
		}	
		else if (!isValid) { 
			//If it's not the manager we need to check the customers
			
			try {
				isValid = false;
				String userName = txtUserName.getText();
				String pass = txtPassword.getText();
				
				//find the customer with the same userName & Password
				for (Customer cust : Main.restaurant.getCustomers().values()) {
					if (cust.getUserName().equals(userName) && cust.getPassword().equals(pass)) {
						c = cust;
						isValid = true;
						
						//New Alert
						Alert a = new Alert(AlertType.CONFIRMATION);
						a.setTitle("Login");
						a.setHeaderText("Hello " + c.getFirstName() + "!\nLogin successfully");
						a.showAndWait();
						
						FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/CustomerView.fxml"));
						AnchorPane pane = loader.load();
						mainScreen.getChildren().removeAll(mainScreen.getChildren());
						mainScreen.getChildren().add(pane);
					}
				}
				
				if (! isValid) {
					//if there is no customer with that userName and Password
					title.setText("Login Failed");
					txtUserName.setText("");
					txtPassword.setText("");
					showPassword.setText("");
					
					//throws NEW exception
					throw new IllegalUserName();
				}
					
			} catch (IllegalUserName e) {
				e.printStackTrace();
			}
		}

	}
	
	//when clicked on button "create new account"
	@FXML
	public void addNewCustomerView (ActionEvent event) throws Exception {
		
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
		
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/addCustomer.fxml"));
		AnchorPane pane = loader.load();
		mainScreen.getChildren().removeAll(mainScreen.getChildren());
		mainScreen.getChildren().add(pane);
		
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