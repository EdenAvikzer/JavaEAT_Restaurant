package controller;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

import application.Main;
import exceptions.AllFieldsAreIncompleteException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import model.Component;
import model.Cook;
import model.Customer;
import model.Dish;
import utils.Expertise;

public class CustomerQueriesController implements Initializable {
	
    @FXML
    private AnchorPane mainScreen;
    @FXML
    private Label customerName;
    @FXML
    private ListView<Component> popularComp;
    ObservableList<Component> observableArrayList1;


    
    @FXML
    private ListView<Dish> releventDishList;
    ObservableList<Dish> observableArrayList2; 

    @FXML
    private ChoiceBox<Expertise> expertise;
    
    @FXML
    private ListView<Cook> listOfChefs;
    ObservableList<Dish> observableArrayList3; 


    Customer cust;


	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		//Updates the customer who is currently connected to the system
		cust = MainController.getC();
		customerName.setText("Hello " + MainController.getC().getFirstName() + " " + MainController.getC().getLastName());
		
		//Initialize the entries in the list
		observableArrayList1 = FXCollections.observableArrayList(Main.restaurant.getPopularComponents());
		popularComp.setItems(observableArrayList1);
	
		observableArrayList2 =  FXCollections.observableArrayList(Main.restaurant.getReleventDishList(cust));
		releventDishList.setItems(observableArrayList2);
		
		expertise.setItems(FXCollections.observableArrayList(Expertise.values()));
	}
	
	

    @FXML
    private void seeChefs(ActionEvent event) throws AllFieldsAreIncompleteException {
    	
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
    	//input check
    	if (expertise.getValue() != null) {
	    	 ObservableList<Cook> observableArrayList = FXCollections.observableArrayList(Main.restaurant.GetCooksByExpertise(expertise.getValue()));
	    	 listOfChefs.setItems(observableArrayList);
    	}
    	else { //if the field is empty
    		throw new AllFieldsAreIncompleteException();
    	}
    }
    
    @FXML
	public void goBack (ActionEvent event) throws Exception {
    	
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
    	
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/CustomerView.fxml"));
		AnchorPane pane = loader.load();
		mainScreen.getChildren().removeAll(mainScreen.getChildren());
		mainScreen.getChildren().add(pane);
	}
	
	
	
	

}
