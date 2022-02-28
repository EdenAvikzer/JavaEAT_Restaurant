package controller;

import java.io.File;
import java.io.IOException;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

public class FinalScreenController {

	@FXML
    private AnchorPane mainScreen;
    @FXML
    private ImageView view;

    @FXML
    void continueShopping(ActionEvent event) throws IOException {
    	
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
    	
    	//go back to the customer view
    	FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/CustomerView.fxml"));
		AnchorPane pane = loader.load();
		mainScreen.getChildren().removeAll(mainScreen.getChildren());
		mainScreen.getChildren().add(pane);
    }

	    
    @FXML
	public void initialize() {
    	
    	//NEW ANIMATION
    	TranslateTransition transition = new TranslateTransition();
    	transition.setDuration(Duration.seconds(2));
    	transition.setNode(view);
    	transition.setToX(210);
    	transition.setToY(0);
    	transition.play();
    	
    	//NEW SOUND EFFECT
    	Media sound = new Media(new File("src\\truckSoundNew.mp4").toURI().toString());
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
