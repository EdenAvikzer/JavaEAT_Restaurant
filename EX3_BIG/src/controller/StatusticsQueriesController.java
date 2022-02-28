package controller;

import application.Main;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import model.Component;


public class StatusticsQueriesController {
	
	@FXML
	private AnchorPane mainScreen;
	@FXML
	private Label express;
	@FXML
	private Label regular;
	@FXML
	private ListView<Component> popularList;
	ObservableList<Component> observableArrayList = FXCollections.observableArrayList(Main.restaurant.getPopularComponents());
	
	
	
	
	@FXML
	public void initialize() {
		
		regular.setText("" + Main.restaurant.getNumberOfDeliveriesPerType().get("Regular delivery"));
		express.setText("" + Main.restaurant.getNumberOfDeliveriesPerType().get("Express delivery"));
		
		popularList.setItems(observableArrayList);

	}
	

}
