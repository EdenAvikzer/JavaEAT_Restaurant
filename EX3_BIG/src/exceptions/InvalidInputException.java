package exceptions;

import java.io.File;
import java.lang.reflect.Field;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;


///*Exception that is thrown when the user enter wrong input to the text fields 
public class InvalidInputException extends Exception {


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public InvalidInputException() {
		playErrorSound();
	
		//New Alert
		Alert a = new Alert(AlertType.ERROR);
		a.setTitle("Input ERROR");
		a.setHeaderText("Invalid input, please try again");
		a.showAndWait();
		
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
