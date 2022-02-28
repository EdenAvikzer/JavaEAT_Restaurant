package exceptions;

import java.io.File;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

//Exception that is thrown when the customer try to order without dishes in the order
public class NoDishException extends Exception {

	/**
 * 
 */
private static final long serialVersionUID = 1L;

	public NoDishException() {
		playErrorSound();
		//New Alert
		Alert a = new Alert(AlertType.ERROR);
		a.setTitle("ERROR");
		a.setHeaderText("Your order must contain at least one dish");
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
