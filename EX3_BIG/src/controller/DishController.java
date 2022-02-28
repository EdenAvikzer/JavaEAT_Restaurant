package controller;

import java.text.DecimalFormat;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import model.Dish;

public class DishController {
	
    @FXML
    private Label name;
    @FXML
    private Label price;
    @FXML
    private Label components;

    private Dish dish;
    private MyListener myListener;


	public void setData(Dish dish, MyListener myListener) {
		this.dish = dish;
		name.setText("" + dish.getDishName());

		DecimalFormat df2 = new DecimalFormat("#.##");
		price.setText(df2.format(dish.getPrice()) + " $");

		this.myListener = myListener;
		components.setText(dish.getComponenets().toString());
	}
    
    @FXML
    public void click(MouseEvent mouseEvent ) {
    	myListener.onClickListener(dish);
    }

}
