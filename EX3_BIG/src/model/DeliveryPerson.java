package model;

import java.io.Serializable;
import java.time.LocalDate;

import application.Main;
import utils.Gender;
import utils.Vehicle;

public class DeliveryPerson extends Person implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static int idCounter = 1;
	private Vehicle vehicle;
	private DeliveryArea area;
	private Boolean available;
	
	public DeliveryPerson(String firstName, String lastName, LocalDate birthDay, Gender gender, Vehicle vehicle,
			DeliveryArea area, Boolean available) {
		super(idCounter++, firstName, lastName, birthDay, gender);
		this.vehicle = vehicle;
		this.area = area;
		this.available = available;
	}
	
	public DeliveryPerson(int id) {
		super(id);
	}
	
	public static int getIdCounter() {
		return idCounter;
	}
	public static void setIdCounter(int idCounter) {
		DeliveryPerson.idCounter = idCounter;
	}
	public Vehicle getVehicle() {
		return vehicle;
	}
	public void setVehicle(Vehicle vehicle) {
		this.vehicle = vehicle;
	}
	public DeliveryArea getArea() {
		return area;
	}
	public void setArea(DeliveryArea area) {
		this.area = area;
	}
	@Override
	public String toString() {
		return super.getFirstName() + " " + super.getLastName() + ", " + area;
			
	}

	public Boolean getAvailable() {
		return available;
	}

	public void setAvailable(Boolean available) {
		this.available = available;
	}

	public static void fixID() {
		int maxKey = 0;
		;
		for (Integer i : Main.restaurant.getDeliveryPersons().keySet()) {
			if (maxKey < i) {
				maxKey = i;
			}
		}
		setIdCounter(maxKey+1);
	}

}
