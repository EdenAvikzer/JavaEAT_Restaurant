package exceptions;

import java.io.File;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

/*Exception that is thrown when the value in the DatePicher
is in the future or the employee is under 16 */
public class InvalidAgeException extends Exception {

		/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

		public InvalidAgeException() {
			
			playErrorSound();
			
			//New Alert
			Alert a = new Alert(AlertType.ERROR);
			a.setTitle("Date ERROR");
			a.setHeaderText("The date is invalid, try again");
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
