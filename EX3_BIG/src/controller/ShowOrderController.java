package controller;

import java.io.File;
import java.util.ArrayList;
import java.util.TreeSet;

import application.Main;
import exceptions.NoDishException;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import model.Customer;
import model.Dish;
import model.Order;

public class ShowOrderController {
	
	@FXML
	private AnchorPane mainScreen;
	@FXML
	private AnchorPane replace;
	@FXML
	private AnchorPane addOrderAP;
	@FXML
	private ListView<Order> ordersList;
	ObservableList<Order> observableArrayList = FXCollections.observableArrayList(Main.restaurant.getOrders().values());
	@FXML
	private Label customerName;
	@FXML
	private Label deliveryID;
	@FXML
	private Label dishes;
	@FXML
	private Label status;
	@FXML
	private Order order;
    @FXML
    private Label area;
    @FXML
    private Label orderDelivery;

	@FXML
	private ChoiceBox<Customer> customerBox;
	@FXML
	private ListView<Dish> dishesList;
	@FXML
	private TextField searchById;
	

	
	@FXML
	public void save() {
		Main.getRestaurant().seralize();
	}
	
	 @FXML
	    void search(ActionEvent event) {
		 playSound();
	    	
	    	if (searchById.getText().length() != 0) {
	    		try {
	    			int id = Integer.parseInt(searchById.getText());
	    			Order order = Main.restaurant.getRealOrder(id);
	    			if (order == null ) {
	    				playErrorSound();
	    				//New Alert
	        			Alert a = new Alert(AlertType.ERROR);
	        			a.setTitle("Error");
	        			a.setHeaderText("There is no such ID, try again");
	        			a.showAndWait();
	    			} else {
	    				observableArrayList = FXCollections.observableArrayList(order); 
	    				ordersList.setItems(observableArrayList);
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
	    		observableArrayList = FXCollections.observableArrayList(Main.restaurant.getOrders().values()); 
	    		ordersList.setItems(observableArrayList);
	    	}
	    }
	
	@FXML
	public void initialize() {
		
		replace.setVisible(false);
		
		ordersList.setItems(observableArrayList);
		
		
		
		ordersList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Order>() {

			@Override
			public void changed(ObservableValue<? extends Order> arg0, Order arg1, Order arg2) {
				playSound();
				showDetails();
			}
			
		});
		
		customerBox.setItems(FXCollections.observableArrayList(Main.restaurant.getCustomers().values()));
		
		
		dishesList.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		dishesList.setItems(FXCollections.observableArrayList(Main.restaurant.getDishes().values()));
	}
	
	protected void showDetails() {
		
		order = ordersList.getSelectionModel().getSelectedItem();
		
		//Set labels
		customerName.setText(order.getCustomer().toString()) ;
		dishes.setText(order.getDishes().toString());
		if (order.getDelivery() != null) {
			orderDelivery.setText(order.getDelivery().toString());
		} else {
			orderDelivery.setText("");
		}
		area.setText(order.getDa().getAreaName());
		
		replace.setVisible(true);
	}
	
	@FXML
	public void removeOrder (ActionEvent event) throws Exception {
		playSound();
		order = ordersList.getSelectionModel().getSelectedItem();
		Main.restaurant.removeOrder(order);
		save();
		ordersList.setItems(FXCollections.observableArrayList(Main.restaurant.getOrders().values()));
		status.setText("Order removed successfully");
		
		if (Main.restaurant.getOrders().size() == 0) {
			replace.setVisible(false);
		}
	}
	
	@FXML
	public void addOrder (ActionEvent event) throws Exception {
		playSound();
		boolean flag = true;
		if (flag) {
			if (dishesList.isPressed()) {
				dishesList.setStyle("-fx-border-color: #f02491; -fx-border-width: 1px");
				status.setText("All fields must be field currectly");
				flag = false;
				playErrorSound();
			}
			
			if (customerBox.isPressed()) {
				customerBox.setStyle("-fx-border-color: red; -fx-border-width: 2px");
				status.setText("All fields must be field currectly");
				flag = false;
				playErrorSound();
			}
		}
		
		if (flag) {
			ArrayList<Dish> al = new ArrayList<>();
			ObservableList<Dish> selectedItems =  dishesList.getSelectionModel().getSelectedItems();
			 
			for(Dish d : selectedItems){
	            al.add(d);
	        }
			
			//at least one dish in the order
			if (al.size() > 0) {
		
				//if this id already exists 
				if (Main.restaurant.getOrders().get(Order.getIdCounter()) != null) {
					Order.fixID();
				}
				
				if (customerBox.getValue() == null) {
					playErrorSound();
	    			//New Alert
	    			Alert a = new Alert(AlertType.ERROR);
	    			a.setTitle("Error");
	    			a.setHeaderText("You must choose customer");
	    			a.showAndWait();
				} else {
					Order o = new Order (customerBox.getValue(), al,null);
					if (Main.restaurant.addOrder(o)) {
						if(o.getCustomer().getAllOrders() == null) {
							TreeSet<Order> Allorders = new TreeSet<Order>();
							o.getCustomer().setAllOrders(Allorders);
						} else {
							o.getCustomer().getAllOrders().add(o);
						}
					}
					
					save();
					
					status.setText("Order Added successfully");
					ordersList.setItems(FXCollections.observableArrayList(Main.restaurant.getOrders().values()));
				}
				
				
		
			}
			
			else {
				throw new NoDishException();
			}	
		} 
	}

	@FXML
	public void goBack (ActionEvent event) throws Exception {
		playSound();
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/ManagerView.fxml"));
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
