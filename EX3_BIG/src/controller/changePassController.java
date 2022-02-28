package controller;

import java.io.File;

import application.Main;
import javafx.animation.RotateTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label; 
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.util.Duration;

public class changePassController {
	
	@FXML
	private AnchorPane mainScreen;	
	@FXML
	private TextField password;
	@FXML
	private TextField currPassword;
	@FXML
	private Label bad;
	@FXML
	private Label good;
	@FXML
	private Label best;
	@FXML
	private Tooltip ttPass;
    @FXML
    private ImageView view;
	
	
	@FXML
	public void save() {
		Main.getRestaurant().seralize();
	}
	
	@FXML
	public void initialize() {
		
		bad.setVisible(false);
		good.setVisible(false);
		best.setVisible(false);
		
		//Alerts in real time whether the password is correct
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
		
		
		//Animation
		RotateTransition RotTransition = new RotateTransition(Duration.seconds(4),view);
		RotTransition.setByAngle(360);
		RotTransition.play();
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
	
	public void removeAllStyle(TextField f){
		f.getStyleClass().removeAll("bad","good","best"); 
	}
	
	
	
	@FXML
	public void saveChanges (ActionEvent event) throws Exception {
		
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
		
		if (currPassword.getText().equals(MainController.getC().getPassword()) && currPassword.getText().length() != 0) {
				if (password.getText().length() != 0 && password.getText().length() > 8) {
					MainController.getC().setPassword(password.getText());
				}
			save();

			//New Alert
			Alert a = new Alert(AlertType.CONFIRMATION);
			a.setTitle("Changes saved");
			a.setHeaderText("Thanks " + MainController.getC().getFirstName() + "!\nThe changes saved successfully");
			a.showAndWait();
			
		} else {
			currPassword.setStyle("-fx-border-color: red; -fx-border-width: 2px;"
					+ " -fx-background-color: black; -fx-background-radius: 2cm; -fx-border-radius: 2cm");
			
			playErrorSound();
			//New Alert
			Alert a = new Alert(AlertType.ERROR);
			a.setTitle("ERROR");
			a.setHeaderText("There is no such password !");
			a.showAndWait();
			
		}
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
