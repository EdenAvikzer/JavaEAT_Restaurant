package controller;

import java.io.File;

import application.Main;
import exceptions.AllFieldsAreIncompleteException;
import exceptions.InvalidInputException;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import model.Component;

public class ShowComponentController {
	
	@FXML
	private Component comp;
	@FXML
	private AnchorPane mainScreen;
	@FXML
	private AnchorPane replace;
	@FXML
	private AnchorPane addComponentAP;
	@FXML
	private ListView<Component> componentsList;
	ObservableList<Component> observableArrayList = FXCollections.observableArrayList(Main.restaurant.getComponenets().values()); 
	@FXML
	private Label name;
    @FXML
    private TextField price;
	@FXML
	private Label gluten;
	@FXML
	private Label lactose;
	
	@FXML
	private TextField compName;
	@FXML
	private TextField compPrice;
	@FXML
	private CheckBox containGluten;
	@FXML
	private CheckBox containLactose;
	@FXML
	private Label status;
	@FXML
	private TextField searchById;
	
	
	
	@FXML
	public void save() {
		Main.getRestaurant().seralize();
	}
	
	//search object by ID
    @FXML
    void search(ActionEvent event) {
    	playSound();
    	if (searchById.getText().length() != 0) {
    		try {
    			int id = Integer.parseInt(searchById.getText());
    			Component comp = Main.restaurant.getRealComponent(id);
    			
    			if (comp == null ) {
    				playErrorSound();
    				//New Alert
        			Alert a = new Alert(AlertType.ERROR);
        			a.setTitle("Error");
        			a.setHeaderText("There is no such ID, try again");
        			a.showAndWait();
    			} else {
    				observableArrayList = FXCollections.observableArrayList(comp); 
    				componentsList.setItems(observableArrayList);
    			}
    			
    		} catch (Exception e) {
    			playErrorSound();
    			//New Alert
    			Alert a = new Alert(AlertType.ERROR);
    			a.setTitle("Error");
    			a.setHeaderText("There is no such ID, try again");
    			a.showAndWait();
    		}	
    	} else {
    		observableArrayList = FXCollections.observableArrayList(Main.restaurant.getComponenets().values()); 
			componentsList.setItems(observableArrayList);
    	}
    }
	
	@FXML
	public void initialize() {
		
		componentsList.setItems(observableArrayList);
		replace.setVisible(false);
		
		
		if (componentsList.getSelectionModel().selectedItemProperty() != null) {
			componentsList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Component>() {
	
				@Override
				public void changed(ObservableValue<? extends Component> arg0, Component arg1, Component arg2) {
					playSound();
					showDetails();
				}
				
			});
		}
		
		//Alerts in real time whether the value in the field is correct
		price.addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
	          public void handle(KeyEvent arg0) {

					//Input Check
	        	  if(price.getText().matches("[0-9]+\\.[0-9]*$") || price.getText().matches("^[0-9]*.$")){
	        		  price.setStyle("-fx-border-radius: 30; -fx-background-radius: 30; -fx-border-color: #71def9");
	        	  } else {
	        		  price.setStyle("-fx-text-fill: red; -fx-border-radius: 30; -fx-background-radius: 30; -fx-border-color: red");
	        	  }
	        	  		
	          }
		});
		
		//Alerts in real time whether the value in the field is correct
		compPrice.addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
	          public void handle(KeyEvent arg0) {

					//Input Check
	        	  if(compPrice.getText().matches("[0-9]+\\.[0-9]*$") || compPrice.getText().matches("^[0-9]*.$")){
	        		  compPrice.setStyle("-fx-border-radius: 30; -fx-background-radius: 30; -fx-border-color: #71def9");
	        	  } else {
	        		  compPrice.setStyle("-fx-text-fill: red; -fx-border-radius: 30; -fx-background-radius: 30; -fx-border-color: red");
	        	  }
	        	  		
	          }
		});
		
		//Alerts in real time whether the value in the field is correct
		compName.addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
	          public void handle(KeyEvent arg0) {

					//Input Check
	        	  	//Can contain letters and space only
	        	  	boolean allLetters = compName.getText().chars().allMatch(Character::isLetter);
	        	  	if (! allLetters) {
	        	  		
	        	  		boolean isValid = true;
	        	  		for (int i=0 ; i < compName.getText().length(); i++) {
	        	  			if (! (Character.isLetter(compName.getText().charAt(i))) && !(Character.isSpaceChar(compName.getText().charAt(i)))) {
	        	  				isValid = false;
	        	  			}		
	        	  		}
	        	  		if (!isValid)
		        	  		compName.setStyle("-fx-text-fill: red; -fx-border-radius: 30; -fx-background-radius: 30; -fx-border-color: red");
	        	  		else 
		        	  		compName.setStyle("-fx-border-radius: 30; -fx-background-radius: 30; -fx-border-color: #71def9");
	        	  	} else {
	        	  		compName.setStyle("-fx-border-radius: 30; -fx-background-radius: 30; -fx-border-color: #71def9");
	        	  	}
	          }
		});
		
		
	}
	
	@FXML
	public void goBack (ActionEvent event) throws Exception {
		playSound();
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/ManagerView.fxml"));
		AnchorPane pane = loader.load();
		mainScreen.getChildren().removeAll(mainScreen.getChildren());
		mainScreen.getChildren().add(pane);
	}
	
	
	@FXML
	public void showDetails() {
		comp = componentsList.getSelectionModel().getSelectedItem();
		if(comp != null) {
			name.setText(comp.getComponentName());
			price.setStyle("-fx-border-radius: 30; -fx-background-radius: 30; -fx-border-color: white");
			price.setText("" + comp.getPrice());
			if(comp.isHasGluten()) {
				gluten.setText("Yes");
			}
			else {
				gluten.setText("No");
			}
			if(comp.isHasLactose()) {
				lactose.setText("Yes");
			}
			else {
				lactose.setText("No");
			}
			
			replace.setVisible(true);
		}
	}
	
	
	//"onAction" - Edit button
	@FXML
	public void edit (ActionEvent event) throws Exception {
		playSound();
		comp = componentsList.getSelectionModel().getSelectedItem();
		
		boolean numeric = true;
		Double num = 0.0;
		
		if (price.getText().length() ==  0) {
			playErrorSound();
			 numeric = false;
			throw new AllFieldsAreIncompleteException();
		}
		
		try {
             num = Double.parseDouble(price.getText());
        } catch (NumberFormatException e) {
            numeric = false;
        }
		
        if(numeric) {
            comp.setPrice(num);
        }
        else {
        	throw new InvalidInputException();
        }
        
      //New Alert
		Alert a = new Alert(AlertType.CONFIRMATION);
		a.setTitle("CONFIRMATION");
		a.setHeaderText("Changes saved successfully");
		a.showAndWait();
		
		save();
		
	}
	
	@FXML
	private void addComp(ActionEvent event) throws Exception {
		playSound();
		addComponentAP.setStyle("-fx-border-color: white; -fx-border-width: 1px");
		
		boolean flag = true;
		
		if (flag) {
			if (compName.getText().length() == 0) {
				compName.setStyle("-fx-text-fill: red; -fx-border-radius: 30; -fx-background-radius: 30; -fx-border-color: red");
				playErrorSound();
				status.setText("All fields must be field currectly");
				flag = false;
			}
			
			if (compPrice.getText().length() == 0) {
				compPrice.setStyle("-fx-text-fill: red; -fx-border-radius: 30; -fx-background-radius: 30; -fx-border-color: red");
				playErrorSound();
				status.setText("All fields must be field currectly");
				playErrorSound();
				flag = false;
			}
			// force the field to be with letters only and to nut contain any other characters
			for (int i = 0; i<compName.getText().length(); i++) {
				if (compName.getText().charAt(i) < '9' && compName.getText().charAt(i) > '0') {
					flag = false;
					throw new InvalidInputException(); 	
				}
			}

			// force the field to be numeric only and to nut contain string
			for (int i = 0; i<compPrice.getText().length(); i++) {
				if (compPrice.getText().charAt(i) < 'z' && compPrice.getText().charAt(i) > 'a' || 
						compPrice.getText().charAt(i) < 'Z' && compPrice.getText().charAt(i) > 'A') {
					flag = false;
					throw new InvalidInputException(); 	
				}
			}
			//Only if all fields are correct
		} 
		if (flag) {
			//if this id already exists 
			if (Main.restaurant.getComponenets().get(Component.getIdCounter()) != null) {
				Component.fixID();
			}
			Component c = new Component(compName.getText(), containLactose.isSelected(),containGluten.isSelected(), Double.parseDouble(compPrice.getText()));
			Main.restaurant.addComponent(c);
			save();
			componentsList.setItems(FXCollections.observableArrayList(Main.restaurant.getComponenets().values()));
			status.setText("Component Added successfully");
		}	
	}
	
	@FXML
	public void removeComp (ActionEvent event) throws Exception {
		playSound();
		comp = componentsList.getSelectionModel().getSelectedItem();
		Main.restaurant.removeComponent(comp);
		save();
		observableArrayList = FXCollections.observableArrayList(Main.restaurant.getComponenets().values());
		componentsList.setItems(observableArrayList);

		status.setText("Component removed successfully");
		
		if (Main.restaurant.getComponenets().size() == 0) {
			replace.setVisible(false);
		}
		
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
