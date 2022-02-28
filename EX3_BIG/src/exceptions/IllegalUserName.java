package exceptions;

import java.io.File;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

//Exception that is thrown when the user enter wrong password or user name
public class IllegalUserName extends Exception {

		/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

		public IllegalUserName() {
			playErrorSound();
			//New Alert
			Alert a = new Alert(AlertType.ERROR);
			a.setTitle("Login");
			a.setHeaderText("No such user exists, please try again");
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
