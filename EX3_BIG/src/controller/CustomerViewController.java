package controller;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import application.Main;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import model.Customer;

public class CustomerViewController implements Initializable {

	@FXML
	private Label customerName;
	@FXML
	private Customer c = MainController.c;
	@FXML
	private AnchorPane mainScreen;
	@FXML
	private AnchorPane secondScreen;
	@FXML
    private Hyperlink menuLink;
    @FXML
    private Hyperlink myAccount;
    @FXML
    private Hyperlink Cart;
    @FXML
    private Hyperlink Orders;
    @FXML
    private Hyperlink QUERIES;
	
	private File file;
	private Media media;
	private MediaPlayer mediaPlayer;
	
	@FXML
	private MediaView video;
	
	@FXML
	public void save() {
		Main.getRestaurant().seralize();
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		//Add background video #2
		file = new File("src\\\\customerViewVideo.mp4");
		media = new Media(file.toURI().toString());
		mediaPlayer = new MediaPlayer(media);
		video.setMediaPlayer(mediaPlayer);
		mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
		video.setFitHeight(600);
		video.setFitWidth(900);
		mediaPlayer.setMute(true);
		mediaPlayer.play();
		
		//set label
		customerName.setText("Hello " + MainController.getC().getFirstName() + " " + MainController.getC().getLastName());
		
		secondScreen.setVisible(false);

		
		//Initialize the links in the customer MENU
		
		menuLink.setOnAction(new EventHandler<ActionEvent>() {
		    @Override
		    public void handle(ActionEvent e) {
		    	//NEW SOUND EFFECT
		    	playSound(); 
		    	
		    	FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/RestMenu.fxml"));
				AnchorPane pane = null;
				try {
					pane = loader.load();
				} catch (IOException e1) {
					e1.printStackTrace();
				}	
				mainScreen.getChildren().removeAll(mainScreen.getChildren());
				mainScreen.getChildren().add(pane);
		    }
		});
		
		myAccount.setOnAction(new EventHandler<ActionEvent>() {
		    @Override
		    public void handle(ActionEvent e) {
		    	//NEW SOUND EFFECT
		    	playSound();
		    	
		    	secondScreen.setVisible(true);
		    	
		    	FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/UpdatePersonalDetails.fxml"));
				AnchorPane pane = null;
				try {
					pane = loader.load();
				} catch (IOException e1) {
					e1.printStackTrace();
				}	
				secondScreen.getChildren().removeAll(secondScreen.getChildren());
				secondScreen.getChildren().add(pane);
		    }
		});
		
		
		Cart.setOnAction(new EventHandler<ActionEvent>() {
		    @Override
		    public void handle(ActionEvent e) {
		    	//NEW SOUND EFFECT
		    	playSound();
		    	
		    	FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/Cart.fxml"));
				AnchorPane pane = null;
				try {
					pane = loader.load();
				} catch (IOException e1) {
					e1.printStackTrace();
				}	
				mainScreen.getChildren().removeAll(mainScreen.getChildren());
				mainScreen.getChildren().add(pane);
		    }
		});
		
		Orders.setOnAction(new EventHandler<ActionEvent>() {
		    @Override
		    public void handle(ActionEvent e) {
		    	//NEW SOUND EFFECT
		    	playSound();
		    	
		    	FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/OrderByCust.fxml"));
		    	AnchorPane pane = null;
				try {
					pane = loader.load();
				} catch (IOException e1) {
					e1.printStackTrace();
				}	
				mainScreen.getChildren().removeAll(mainScreen.getChildren());
				mainScreen.getChildren().add(pane);
		    }
		});

		QUERIES.setOnAction(new EventHandler<ActionEvent>() {
	    @Override
	    public void handle(ActionEvent e) {
	    	//NEW SOUND EFFECT
	    	playSound();
	    	
	    	FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/CustomerQueries.fxml"));
	    	AnchorPane pane = null;
			try {
				pane = loader.load();
			} catch (IOException e1) {
				e1.printStackTrace();
			}	
			mainScreen.getChildren().removeAll(mainScreen.getChildren());
			mainScreen.getChildren().add(pane);
	    }
	});
		
	}
	
	public void Logout (ActionEvent event) throws Exception {
		
		//NEW SOUND EFFECT
		playSound();
		
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/LoginFX.fxml"));
		AnchorPane pane = loader.load();
		mainScreen.getChildren().removeAll(mainScreen.getChildren());
		mainScreen.getChildren().add(pane);
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
	

}
