package exceptions;

import java.io.File;

import javafx.fxml.FXML;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

/*Exception that is thrown when the customer try to add Dish that he is
sensitive to one of the components in this dish */
public class SensitiveException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public SensitiveException(String dishName) {
	
		super("There is a sensitivity to one of the components in the dish " + dishName);
		playErrorSound();
		
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
