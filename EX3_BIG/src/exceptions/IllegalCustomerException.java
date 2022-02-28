package exceptions;

import java.io.File;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

//Exception that is thrown when this customer try to order and he is in the black list
public class IllegalCustomerException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public IllegalCustomerException() {
		playErrorSound();
		
		//New Alert
		Alert a = new Alert(AlertType.ERROR);
		a.setTitle("Error");
		a.setHeaderText("We are unable to let you do this, please contact support for more information");
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
