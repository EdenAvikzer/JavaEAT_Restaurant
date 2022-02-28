package model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.TreeSet;

import org.apache.poi.wp.usermodel.HeaderFooterType;
import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFHeader;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;

import application.Main;
import exceptions.ConvertToExpressException;
import exceptions.IllegalCustomerException;
import exceptions.NoComponentsExceptions;
import exceptions.SensitiveException;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import utils.Expertise;
import utils.Neighberhood;


public class Restaurant implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static Restaurant restaurant = null;

	private HashMap<Integer, Cook> cooks;
	private HashMap<Integer, DeliveryPerson> deliveryPersons;
	private HashMap<Integer, Customer> customers;
	private HashMap<Integer, Dish> dishes;
	private HashMap<Integer, Component> componenets;
	private HashMap<Integer, Order> orders;
	private HashMap<Integer, Delivery> deliveries;
	private HashMap<Integer, DeliveryArea> areas;
	private HashMap<Customer, TreeSet<Order>> orderByCustomer;
	private HashMap<DeliveryArea, HashSet<Order>> orderByDeliveryArea;
	private HashSet<Customer> blackList;
	
	
	public static Restaurant getInstance()
	{
		if(restaurant == null)
			restaurant = new Restaurant();
		return restaurant;
	}
	


	private Restaurant() {
		cooks = new HashMap<>();
		deliveryPersons = new HashMap<>();
		customers = new HashMap<>();
		dishes = new HashMap<>();
		componenets = new HashMap<>();
		orders = new HashMap<>();
		deliveries = new HashMap<>();
		areas = new HashMap<>();
		orderByCustomer = new HashMap<>();
		orderByDeliveryArea = new HashMap<>();
		blackList = new HashSet<>();

	}

	public HashMap<Integer, Cook> getCooks() {
		return cooks;
	}

	public void setCooks(HashMap<Integer, Cook> cooks) {
		this.cooks = cooks;
	}

	public HashMap<Integer, DeliveryPerson> getDeliveryPersons() {
		return deliveryPersons;
	}

	public void setDeliveryPersons(HashMap<Integer, DeliveryPerson> deliveryPersons) {
		this.deliveryPersons = deliveryPersons;
	}

	public HashMap<Integer, Customer> getCustomers() {
		return customers;
	}

	public void setCustomers(HashMap<Integer, Customer> customers) {
		this.customers = customers;
	}

	public HashMap<Integer, Dish> getDishes() {
		return dishes;
	}

	public void setDishes(HashMap<Integer, Dish> dishes) {
		this.dishes = dishes;
	}

	public HashMap<Integer, Component> getComponenets() {
		return componenets;
	}

	public void setComponenets(HashMap<Integer, Component> componenets) {
		this.componenets = componenets;
	}

	public HashMap<Integer, Order> getOrders() {
		return orders;
	}

	public void setOrders(HashMap<Integer, Order> orders) {
		this.orders = orders;
	}

	public HashMap<Integer, Delivery> getDeliveries() {
		return deliveries;
	}

	public void setDeliveries(HashMap<Integer, Delivery> deliveries) {
		this.deliveries = deliveries;
	}

	public HashMap<Integer, DeliveryArea> getAreas() {
		return areas;
	}

	public void setAreas(HashMap<Integer, DeliveryArea> areas) {
		this.areas = areas;
	}

	public HashMap<Customer, TreeSet<Order>> getOrderByCustomer() {
		return orderByCustomer;
	}

	public void setOrderByCustomer(HashMap<Customer, TreeSet<Order>> orderByCustomer) {
		this.orderByCustomer = orderByCustomer;
	}

	public HashMap<DeliveryArea, HashSet<Order>> getOrderByDeliveryArea() {
		return orderByDeliveryArea;
	}

	public void setOrderByDeliveryArea(HashMap<DeliveryArea, HashSet<Order>> orderByDeliveryArea) {
		this.orderByDeliveryArea = orderByDeliveryArea;
	}

	public HashSet<Customer> getBlackList() {
		return blackList;
	}

	public void setBlackList(HashSet<Customer> blackList) {
		this.blackList = blackList;
	}

	public boolean addCook(Cook cook) {
		if(cook == null || getCooks().containsKey(cook.getId()))
			return false;

		return getCooks().put(cook.getId(), cook) == null;
	}

	public boolean addDeliveryPerson(DeliveryPerson dp, DeliveryArea da) {
		if(dp == null || getDeliveryPersons().containsKey(dp.getId()) || !getAreas().containsKey(da.getId()))
			return false;

		return deliveryPersons.put(dp.getId() , dp) ==null && da.addDelPerson(dp);
	}

	public boolean addCustomer(Customer cust) {
		if(cust == null || getCustomers().containsKey(cust.getId()))
			return false;

		return getCustomers().put(cust.getId(), cust) ==null;
	}

	public boolean addDish(Dish dish) {
		if(dish == null || getDishes().containsKey(dish.getId()))
			return false;
		for(Component c : dish.getComponenets()) {
			if(!getComponenets().containsKey(c.getId()))
				return false;
		}

		return getDishes().put(dish.getId(), dish) == null;
	}

	public boolean addComponent(Component comp) {
		if(comp == null || getComponenets().containsKey(comp.getId()))
			return false;

		return getComponenets().put(comp.getId(), comp) ==null;
	}

	public boolean addOrder(Order order) throws IllegalCustomerException {
		try {
			if(order == null || getOrders().containsKey(order.getId()))
				return false;
			if(order.getCustomer() == null || !getCustomers().containsKey(order.getCustomer().getId()))
				return false;
			if(getBlackList().contains(order.getCustomer())) {
				throw new IllegalCustomerException();
			}
			for(Dish d : order.getDishes()){
				if(!getDishes().containsKey(d.getId()))
					return false;
				for(Component c: d.getComponenets()) {
					if(order.getCustomer().isSensitiveToGluten() && c.isHasGluten()) {
						throw new SensitiveException(d.getDishName());
					}
					else if(order.getCustomer().isSensitiveToLactose() && c.isHasLactose()) {
						throw new SensitiveException(d.getDishName());
					}
				}
			}
			//New
			//ADDING TO orderByDeliveryArea
			HashSet<Order> orders = new HashSet<>();
			if (orderByDeliveryArea ==  null) {
				orderByDeliveryArea = new HashMap<>();
			}
			if (orderByDeliveryArea.get(order.getDa()) == null) {
				orders.add(order);
				orderByDeliveryArea.put(order.getDa(), orders);
			}else {
				orderByDeliveryArea.get(order.getDa()).add(order);
			}
			
			if(order.getCustomer().getAllOrders() == null) {
				TreeSet<Order> Allorders = new TreeSet<Order>();
				order.getCustomer().setAllOrders(Allorders);
			} else {
				order.getCustomer().getAllOrders().add(order);
			}
			save();
	
			
			return getOrders().put(order.getId(), order) == null;
		}catch(SensitiveException e) {
			//New Alert
			Alert a = new Alert(AlertType.ERROR);
			a.setTitle("ERROR");
			a.setHeaderText("Customer " + order.getCustomer().getFirstName() + " is sensitive to one of the components in the dish ");
			a.showAndWait();
			return false;
			
		}
	}

	public boolean addDelivery(Delivery delivery) {
		try {
			if(delivery == null || getDeliveries().containsKey(delivery.getId()) || !getDeliveryPersons().containsKey(delivery.getDeliveryPerson().getId()))
			{
				return false;
			}
			if(delivery instanceof RegularDelivery) {
				RegularDelivery rd = (RegularDelivery)delivery;
				for(Order o : rd.getOrders()){
					if(!getOrders().containsKey(o.getId()))
						return false;
					o.setDelivery(delivery);
				}
				if(rd.getOrders().size() ==1) {
					throw new ConvertToExpressException();
				}
				if(rd.getOrders().isEmpty())
					return false;
			}
			else {
				ExpressDelivery ed = (ExpressDelivery)delivery;
				if(!getOrders().containsKey(ed.getOrder().getId()))
						return false;
					ed.getOrder().setDelivery(delivery);
			}
		}catch(ConvertToExpressException e) {
			RegularDelivery rd = (RegularDelivery)delivery;
			delivery = new ExpressDelivery(rd.getDeliveryPerson(), rd.getArea(),rd.isDelivered(), rd.getOrders().first() ,rd.getDeliveredDate());
		}finally {
			delivery.getArea().addDelivery(delivery);
			if(delivery instanceof RegularDelivery) {
				RegularDelivery rg = (RegularDelivery)delivery;
				for(Order order: rg.getOrders()) {
					TreeSet<Order> orders = orderByCustomer.get(order.getCustomer());
					if(orders == null)
						orders = new TreeSet<>(new Comp());
					orders.add(order);
					orderByCustomer.put(order.getCustomer(), orders);
				}
			}
			else {
				ExpressDelivery ex = (ExpressDelivery)delivery;
				TreeSet<Order> orders = orderByCustomer.get(ex.getOrder().getCustomer());
				if(orders == null)
					orders = new TreeSet<>(new Comp());	
				orders.add(ex.getOrder());
				orderByCustomer.put(ex.getOrder().getCustomer(), orders);
			}
		}
		return getDeliveries().put(delivery.getId(),delivery) ==null;
	}

	public boolean addDeliveryArea(DeliveryArea da) {
		if(da == null || getAreas().containsKey(da.getId()))
			return false;
		//NEW
		Main.restaurant.getOrderByDeliveryArea().put(da, null);
		return getAreas().put(da.getId(), da) ==null;
	}
	
	public boolean addCustomerToBlackList(Customer c) {
		if(c == null)
			return false;
		return getBlackList().add(c);
	}

	public boolean removeCook(Cook cook) {
		if(cook == null || !getCooks().containsKey(cook.getId()))
			return false;
		return getCooks().remove(cook.getId()) !=null;
	}

	public boolean removeDeliveryPerson(DeliveryPerson dp) {
		if(dp == null || !getDeliveryPersons().containsKey(dp.getId()))
			return false;
		for(Delivery d : getDeliveries().values()) {
			if(d.getDeliveryPerson().equals(dp)) {
				d.setDeliveryPerson(null);
			}
		}
		return getDeliveryPersons().remove(dp.getId())!= null && dp.getArea().removeDelPerson(dp);
	}

	public boolean removeCustomer(Customer cust) {
		if(cust == null || !getCustomers().containsKey(cust.getId()))
			return false;
		blackList.remove(cust);
		return getCustomers().remove(cust.getId())!=null;
	}

	public boolean removeDish(Dish dish) {
		if(dish == null || !getDishes().containsKey(dish.getId()))
			return false;
		for(Delivery d : deliveries.values()) {
			if(!d.isDelivered()) {
				if(d instanceof RegularDelivery) {
					RegularDelivery rd = (RegularDelivery)d;
					for(Order o : rd.getOrders()) {
						o.removeDish(dish);
					}
				}
				else {
					ExpressDelivery ed = (ExpressDelivery)d;
					ed.getOrder().removeDish(dish);
				}
			}
		}
		return getDishes().remove(dish.getId())!=null;
	}

	public boolean removeComponent(Component comp) {
		Dish removeDish = null;
		try {
			if(comp == null || !getComponenets().containsKey(comp.getId()))
				return false;
			for(Dish d : getDishes().values()) {
				d.removeComponent(comp);
				if(d.getComponenets().isEmpty()) {
					removeDish = d;
					throw new NoComponentsExceptions(d);
				}
			}
		}catch(NoComponentsExceptions e) {
			removeDish(removeDish);
		}
		return getComponenets().remove(comp.getId())!=null;
	}

	public boolean removeOrder(Order order) {
		if(order == null || !getOrders().containsKey(order.getId()))
			return false;
		if(getOrders().remove(order.getId())!=null) {
			if(order.getDelivery() instanceof RegularDelivery) {
				RegularDelivery rd = (RegularDelivery) order.getDelivery();
				return rd.removeOrder(order);
			}
			if (order.getDelivery() == null) {
				orders.remove(order.getId());
			}
			else {
				ExpressDelivery ed = (ExpressDelivery) order.getDelivery();
				ed.setOrder(null);
				return true;
			}
			if (getOrderByCustomer() != null) {
				if (getOrderByCustomer().get(order.getCustomer()) != null) {
					getOrderByCustomer().get(order.getCustomer()).remove(order);
				} 
			}
		}
		return false;
	}

	public boolean removeDelivery(Delivery delivery) {
		if(delivery == null || !getDeliveries().containsKey(delivery.getId()))
			return false;
		if(delivery instanceof RegularDelivery) {
			RegularDelivery rd = (RegularDelivery)delivery;
			for(Order o : rd.getOrders()) {
				o.setDelivery(null);
			}
		}
		else {
			ExpressDelivery ed = (ExpressDelivery) delivery;
			if(ed.getOrder() != null) {
				ed.getOrder().setDelivery(null);
			}
		}
		return getDeliveries().remove(delivery.getId()) != null && delivery.getArea().removeDelivery(delivery);
	}

	public boolean removeDeliveryArea(DeliveryArea oldArea, DeliveryArea newArea) {
		if(oldArea == null || newArea == null || !getAreas().containsKey(oldArea.getId()) || !getAreas().containsKey(newArea.getId()))
			return false;
		for(Neighberhood n : oldArea.getNeighberhoods()) {
			newArea.addNeighberhood(n);
		}
		for(DeliveryPerson dp : oldArea.getDelPersons()) {
			newArea.addDelPerson(dp);
		}
		for(DeliveryPerson dp : oldArea.getDelPersons()) {
			dp.setArea(newArea);
		}
		getOrderByDeliveryArea().remove(oldArea);
		return getAreas().remove(oldArea.getId()) != null;
	}

	public Cook getRealCook(int id) {
		return getCooks().get(id);
	}

	public DeliveryPerson getRealDeliveryPerson(int id) {
		return getDeliveryPersons().get(id);
	}

	public Customer getRealCustomer(int id) {
		return getCustomers().get(id);
	}

	public Dish getRealDish(int id) {
		return getDishes().get(id);
	}

	public Component getRealComponent(int id) {
		return getComponenets().get(id);
	}

	public Order getRealOrder(int id) {
		return getOrders().get(id);
	}

	public Delivery getRealDelivery(int id) {
		return getDeliveries().get(id);
	}

	public DeliveryArea getRealDeliveryArea(int id) {
		return getAreas().get(id);
	}


	/*QUEREIES*/
	public Collection<Dish> getReleventDishList(Customer c){
		ArrayList<Dish> dishList = new ArrayList<>();
		if(!c.isSensitiveToGluten() && !c.isSensitiveToLactose())
			return getDishes().values();
		for(Dish d : getDishes().values()) {
			boolean isValid = true;
			for(Component comp : d.getComponenets()) {
				if(c.isSensitiveToGluten() && c.isSensitiveToLactose()) {
					if(comp.isHasGluten() || comp.isHasLactose())
						isValid = false;
				}
				else if(c.isSensitiveToGluten() && comp.isHasGluten()) {
					isValid = false;
				}
				else if(c.isSensitiveToLactose() && comp.isHasLactose()) {
					isValid = false;
				}
			}
			if(isValid)
				dishList.add(d);
		}
		return dishList;
	}
	
	public void deliver(Delivery d) {
		d.setDelivered(true);
		//only if all the deliveries is sent
		for (Delivery del: d.getArea().getDelivers()) {
			if (!del.isDelivered() && d.getDeliveryPerson().equals(d.getDeliveryPerson())) {
				return;
			}
		}
		d.getDeliveryPerson().setAvailable(true);
		save();
		
	}
	
	public ArrayList<Cook> GetCooksByExpertise(Expertise e){
		ArrayList<Cook> cooks = new ArrayList<>();
		for(Cook c : getCooks().values()) {
			if(c.getExpert().equals(e))
				cooks.add(c);
		}
		return cooks;
	}
	
	/*NEW QUERIES*/
	public TreeSet<Delivery> getDeliveriesByPerson(DeliveryPerson dp , int month){
		TreeSet<Delivery> delivered = new TreeSet<>(new Comparator<Delivery>() {

			@Override
			public int compare(Delivery o1, Delivery o2) {
				if(o1.getDeliveredDate().getDayOfMonth() > o2.getDeliveredDate().getDayOfMonth())
					return 1;
				if(o1.getDeliveredDate().getDayOfMonth() < o2.getDeliveredDate().getDayOfMonth())
					return -1;
				return o1.getId()>o2.getId() ? 1 :-1;
			}
		});
		for(Delivery d: getDeliveries().values()) {
			if(d.getDeliveryPerson().equals(dp) && d.getDeliveredDate().getMonthValue() == month)
				delivered.add(d);
		}
		return delivered;
	}
	
	public HashMap<String,Integer> getNumberOfDeliveriesPerType(){
		HashMap<String, Integer> deliveriesPerType = new HashMap<>();
		deliveriesPerType.put("Regular delivery", 0);
		deliveriesPerType.put("Express delivery", 0);
		int amount;
		for(Delivery d: getDeliveries().values()) {
			LocalDate today = LocalDate.now();
			if(d instanceof RegularDelivery && d.getDeliveredDate().getYear() == today.getYear()) {
				amount = deliveriesPerType.get("Regular delivery");
				deliveriesPerType.put("Regular delivery",amount+1);
			}
			else {
				if(d.getDeliveredDate().getYear() == today.getYear()) {
					amount = deliveriesPerType.get("Express delivery");
					deliveriesPerType.put("Express delivery",amount+1);
				}
			}
		}
		return deliveriesPerType;
	}
	
	public double revenueFromExpressDeliveries() {
		HashSet<Customer> customers = new HashSet<>();
		double amountOfPostages = 0;
		for(Delivery d: getDeliveries().values()) {
			if(d instanceof ExpressDelivery) {
				ExpressDelivery ed = (ExpressDelivery)d;
				amountOfPostages+=ed.getPostage();
				if (ed.getOrder() != null) {
					customers.add(ed.getOrder().getCustomer());
				}
			}
		}
		amountOfPostages+=(30*customers.size());
		return amountOfPostages;
	}
	
	public LinkedList<Component> getPopularComponents(){
		HashMap<Component, Integer> componentsandAmount = new HashMap<>();
		for(Dish d: getDishes().values()) {
			for(Component c: d.getComponenets()) {
				Integer numOfComponent = componentsandAmount.get(c);
				if(numOfComponent == null)
					numOfComponent = 0;
				componentsandAmount.put(c, numOfComponent+1);
			}
		}
		LinkedList<Component> popularComponents = new LinkedList<>();
		for(Component c: componentsandAmount.keySet()) {
			popularComponents.add(c);
		}
		popularComponents.sort(new Comparator<Component>() {

			@Override
			public int compare(Component o1, Component o2) {
				int result = -1 * componentsandAmount.get(o1).compareTo(componentsandAmount.get(o2));
				if(result !=0)
					return result;
				if(o1.getId() > o2.getId())
					return -1;
				return 1;
			}
		});
		return popularComponents;
	}
	
	
	public TreeSet<Dish> getProfitRelation()  {
		
		TreeSet<Dish> profit = new TreeSet<Dish>((Dish o1, Dish o2) -> {
			if((o2.getPrice()/o2.getTimeToMake())>(o1.getPrice()/o1.getTimeToMake()))
				return 1;
			else if((o2.getPrice()/o2.getTimeToMake())<(o1.getPrice()/o1.getTimeToMake()))
				return -1;
			return -1*o1.getId().compareTo(o2.getId());
		});
		for(Dish d: getDishes().values()) {
			profit.add(d);
		}
		return profit;
	}
	
	public void exportToWordDoc() throws Exception {
	
		TreeSet<Dish> profit = new TreeSet<Dish>(Main.restaurant.getProfitRelation());
		System.out.println(profit);
	
			//Export to Word document
			try {
				XWPFDocument document = new XWPFDocument();
				FileOutputStream out = new FileOutputStream(new File("Profit.docx"));
				
				//create header
				XWPFHeader header = document.createHeader(HeaderFooterType.DEFAULT);
				header.createParagraph().createRun().setText(LocalDate.now().toString());
			
				XWPFHeader header2 = document.createHeader(HeaderFooterType.DEFAULT);
				header2.createParagraph().createRun().setText(LocalTime.now().toString());
				
				//create title
				XWPFParagraph title = document.createParagraph();
				title.setAlignment(ParagraphAlignment.CENTER);
				XWPFRun run = title.createRun();
				run.setFontSize(22);
				run.setText("Java EAT profit relations");
				run.setBold(true);
				title.setSpacingAfter(100); 
				
				//create table
			    XWPFTable table = document.createTable();
			    table.setWidth("100%");
			    
			    //create first row
			    XWPFTableRow tableRowOne = table.getRow(0);
			    
			    //set value and design the first row
			    tableRowOne.getCell(0).setText("Dish Name");
			    tableRowOne.getCell(0).getParagraphs().get(0).getRuns().get(0).setBold(true);
			    tableRowOne.getCell(0).getParagraphs().get(0).getRuns().get(0).setFontSize(14);
			    tableRowOne.getCell(0).getParagraphs().get(0).setAlignment(ParagraphAlignment.CENTER);			    
			    
			    tableRowOne.addNewTableCell().setText("Price");
			    tableRowOne.getCell(1).getParagraphs().get(0).getRuns().get(0).setBold(true);
			    tableRowOne.getCell(1).getParagraphs().get(0).getRuns().get(0).setFontSize(14);
			    tableRowOne.getCell(1).getParagraphs().get(0).setAlignment(ParagraphAlignment.CENTER);			    

			    tableRowOne.addNewTableCell().setText("Preparation time");
			    tableRowOne.getCell(2).getParagraphs().get(0).getRuns().get(0).setBold(true);
			    tableRowOne.getCell(2).getParagraphs().get(0).getRuns().get(0).setFontSize(14);
			    tableRowOne.getCell(2).getParagraphs().get(0).setAlignment(ParagraphAlignment.CENTER);			    

			    
			    //create the other rows of the tables
			    for (Dish d : profit) {
			    	XWPFTableRow tableRow = table.createRow();
			    	tableRow.getCell(0).setText(d.getDishName());
			    	tableRow.getCell(1).setText(d.getPrice() + " $");
			    	tableRow.getCell(2).setText(d.getTimeToMake() + "");
			    }
			    
			    for (int i=0; i < table.getNumberOfRows(); i++) {
			    	table.getRow(i).getCell(0).getParagraphs().get(0).setAlignment(ParagraphAlignment.CENTER);			    
			    	table.getRow(i).getCell(1).getParagraphs().get(0).setAlignment(ParagraphAlignment.CENTER);			    
			    	table.getRow(i).getCell(2).getParagraphs().get(0).setAlignment(ParagraphAlignment.CENTER);			    
			    }
	
			    document.write(out);
			    out.close();
			    System.out.println("docx written successully");
				
			} catch (Exception e) {
				e.printStackTrace();
			}
	}


	public TreeSet<Delivery> createAIMacine(DeliveryPerson dp, DeliveryArea da, TreeSet<Order> orders){		
		//the delPer needs to be in the same delArea
		
		TreeSet<Delivery> AIDecision = new TreeSet<>(new Comparator<Delivery>() {

			@Override
			public int compare(Delivery o1, Delivery o2) {
				if(o2 instanceof RegularDelivery && o1 instanceof ExpressDelivery)
					return -1;
				if(o2 instanceof ExpressDelivery && o1 instanceof RegularDelivery)
					return 1;
				return o2.getId()>o1.getId() ? -1: 1;
			}
		});
		
		TreeSet<Order> toRegularDelivery = new TreeSet<>();
		
		if(orders.size() <= 2) {
			for(Order o: orders) {
				//if this id already exists 
				if (Main.restaurant.getDeliveries().get(Delivery.getIdCounter()) != null) {
					Delivery.fixID();
				}
				Delivery ed = new ExpressDelivery(dp, da, false, o,LocalDate.now());
				AIDecision.add(ed);
			}
		}
		else {
			for(Order o: orders) {
				if(o.getCustomer().isSensitiveToGluten() || o.getCustomer().isSensitiveToLactose()) {
					//if this id already exists 
					if (Main.restaurant.getDeliveries().get(Delivery.getIdCounter()) != null) {
						Delivery.fixID();
					}
					Delivery ed = new ExpressDelivery(dp, da, false, o,LocalDate.now());
					AIDecision.add(ed);
				}
				else
					toRegularDelivery.add(o);
			}
			if(toRegularDelivery.size() < 2) {
				//if this id already exists 
				if (Main.restaurant.getDeliveries().get(Delivery.getIdCounter()) != null) {
					Delivery.fixID();
				}
				Delivery ed = new ExpressDelivery(dp, da, false, toRegularDelivery.first(),LocalDate.now());
				AIDecision.add(ed);
			}
			else {
				//if this id already exists 
				if (Main.restaurant.getDeliveries().get(Delivery.getIdCounter()) != null) {
					Delivery.fixID();
				}
				Delivery rd = new RegularDelivery(toRegularDelivery, dp, da, false, LocalDate.now());
				AIDecision.add(rd);
			}
		}
			
		//New
		//ADDING TO orderByCustomer
		TreeSet<Order> ordersByCust = new TreeSet<>();
		
		for (Order o : orders) {
			if (Main.restaurant.getOrderByCustomer() !=  null) {
				if (Main.restaurant.getOrderByCustomer().get(o.getCustomer()) == null) {
					ordersByCust.add(o);
					Main.restaurant.getOrderByCustomer().put(o.getCustomer(), ordersByCust);
				} else {
					Main.restaurant.getOrderByCustomer().get(o.getCustomer()).add(o);	
				}
			}	
		}
		
		for (Delivery d: AIDecision) {
			if (d instanceof RegularDelivery) {
				Main.restaurant.addDelivery((RegularDelivery)d);
				for (Order o: ((RegularDelivery) d).getOrders()) {
					o.setDelivery(d);
				}
			} else {
				Main.restaurant.addDelivery((ExpressDelivery)d);
				((ExpressDelivery)d).getOrder().setDelivery(d);
			}
		}
		
		save();
		
		return AIDecision;
	}
	
	
	//NEW METHODS
	

	public void seralize() {
		
		try {
			
			FileOutputStream fileOut = new FileOutputStream("Rest.ser");
			ObjectOutputStream out = new ObjectOutputStream(fileOut);
			out.writeObject(this);
			out.close();
			fileOut.close();
			System.out.println("ser data is saved in 'Rest.ser'");
			
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	public static Restaurant deserialize() {
		
		Restaurant rest = null;
		try {
			
			ObjectInputStream fileIn = new ObjectInputStream(new FileInputStream("Rest.ser"));
			rest = (Restaurant)fileIn.readObject();
			fileIn.close();
			System.out.println("Rest is deserialize");
			
		} catch (IOException e) {
			e.printStackTrace();		
		} catch (ClassNotFoundException e) {
			System.out.println("Restaurant class not found");
			e.printStackTrace();
			
		}
		return rest;
		
	}
	
	public class Comp implements Serializable, Comparator<Order> {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		@Override
		public int compare(Order o1, Order o2) {
			if (o1.getDelivery() != null && o2.getDelivery() != null)
				return o1.getDelivery().getDeliveredDate().compareTo(o2.getDelivery().getDeliveredDate());
			else {
				return o1.getId() - o2.getId();
			}
		}
	}
	
	//onAction when the manager press the button to collect orders
	public static void createNewDeliveries() {
		//call the AI method after find the available delPer + delArea and collect the orders
		for (DeliveryArea da: Main.restaurant.getAreas().values()) {
			
			TreeSet<Order> ordersToAI = new TreeSet<>();
			
			if (Main.restaurant.getOrderByDeliveryArea().get(da) != null ) {
				for (Order o: Main.restaurant.getOrderByDeliveryArea().get(da)) {
					//we make delivery for orders that does not sent yet
					if (o.getDelivery() == null) {
						ordersToAI.add(o);
					}
				}
				boolean flag = false;
				for (DeliveryPerson dp : da.getDelPersons()) {
					
					if (!flag) {
						
						if (dp.getAvailable()) {
							flag = true;
							System.out.println(dp + " " + da);
							dp.setAvailable(false);
							System.out.println(Main.restaurant.createAIMacine(dp, da,ordersToAI ));
						}
					}
				}
				
				if (!flag) {
					
					//NEW ALERT
					Alert a = new Alert(AlertType.ERROR);
					a.setTitle("Error");
					a.setHeaderText("Unfortunately there is no available delivery person right now, please try again later");
					a.showAndWait();
						
				}
				
				
			}
			
			
		}
		save();
	}

	
	@FXML
	public static void save() {
		Main.getRestaurant().seralize();
	}
	
	

}
