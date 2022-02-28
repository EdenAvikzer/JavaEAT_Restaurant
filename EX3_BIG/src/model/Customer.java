package model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.TreeSet;

import application.Main;
import javafx.fxml.FXML;
import utils.Gender;
import utils.Neighberhood;

public class Customer extends Person implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static int idCounter = 1;
	private Neighberhood neighberhood;
	private boolean isSensitiveToLactose;
	private boolean isSensitiveToGluten;
	private String userName;
	private String password;
	
	// NEW !! //
	private ArrayList<Dish> shoppingCart = null;
	private TreeSet<Order> AllOrders;



	public Customer(String firstName, String lastName, LocalDate birthDay, Gender gender,
			utils.Neighberhood neighberhood,	boolean isSensitiveToLactose, boolean isSensitiveToGluten, 
			String userName, String password) {
		super(idCounter++, firstName, lastName, birthDay, gender );
		this.neighberhood = neighberhood;
		this.isSensitiveToLactose = isSensitiveToLactose;
		this.isSensitiveToGluten = isSensitiveToGluten;
		this.userName = userName;
		this.password = password;
		AllOrders = new TreeSet<Order>();
	}
	
	

	@FXML
	public void save() {
		Main.getRestaurant().seralize();
	}
	
	public TreeSet<Order> getAllOrders() {
		return AllOrders;
	}

	public void setAllOrders(TreeSet<Order> allOrders) {
		AllOrders = allOrders;
	}
	

	public Customer(int id) {
		super(id);
	}

	public static int getIdCounter() {
		return idCounter;
	}
	
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public static void setIdCounter(int idCounter) {
		Customer.idCounter = idCounter;
	}

	public Neighberhood getNeighberhood() {
		return neighberhood;
	}

	public void setNeighberhood(Neighberhood neighberhood) {
		this.neighberhood = neighberhood;
	}

	public boolean isSensitiveToLactose() {
		return isSensitiveToLactose;
	}

	public void setSensitiveToLactose(boolean isSensitiveToLactose) {
		this.isSensitiveToLactose = isSensitiveToLactose;
	}

	public boolean isSensitiveToGluten() {
		return isSensitiveToGluten;
	}

	public void setSensitiveToGluten(boolean isSensitiveToGluten) {
		this.isSensitiveToGluten = isSensitiveToGluten;
	}
	
	

	@Override
	public String toString() {
		return  super.getFirstName() + " " + super.getLastName(); 
	}
	
	public static void fixID() {
		int maxKey = 0;
		;
		for (Integer i : Main.restaurant.getCustomers().keySet()) {
			if (maxKey < i) {
				maxKey = i;
			}
		}
		setIdCounter(maxKey+1);
	}
	
	
	public ArrayList<Dish> getShoppingCart() {
		return shoppingCart;
	}


	public void setShoppingCart(ArrayList<Dish> shoppingCart) {
		this.shoppingCart = shoppingCart;
	}
	
	public void addToShopCart(Dish dish, int qty) {
		
		if (shoppingCart == null) {
			shoppingCart = new ArrayList<Dish>();
		}	
		if (qty == 1) {
			if ( shoppingCart.add(dish)) {
				System.out.println(shoppingCart.toString());
				save();
			}else {
				System.out.println("");
			}
		} else {
			for (int i = 0; i<qty; i++) {
				shoppingCart.add(dish);
				save();
			}
			System.out.println(shoppingCart.toString());
		}
	}
}
