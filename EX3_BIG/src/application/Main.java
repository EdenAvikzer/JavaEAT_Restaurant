package application;
//Eden Avikzer HW3 ID-318296167


import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.Restaurant;
import java.io.IOException;


public class Main extends Application {
	
	public static Restaurant restaurant;
	
	public static void main(String[] args) throws IOException {
		launch(args);
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		
		restaurant = Restaurant.deserialize();
		if (restaurant == null) {
			restaurant = Restaurant.getInstance();
		}
		
		try {
			Parent root = FXMLLoader.load(getClass().getResource("/View/MainView.fxml"));
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("/controller/controller.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void restaurantSer() {
		restaurant.seralize();
	}


	public static Restaurant getRestaurant() {
		return restaurant;
	}


	@FXML
	public static void save() {
		Main.getRestaurant().seralize();
	}


	

}
