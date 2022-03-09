package model;

/**
 * This class represents a Car
 * @author Khaled Badran <gym4programming@gmail.com>
 * @version 1.00 - 06/02/2022
 */
public class Car {
	
	private String modelName; // modelName is the name of the car // e.g. BMW M3 
	private int modelYear; // modelYear is The production year // e.g. 2018
	private String fullModel; // fullModel = modelName + modelYear // e.g. BMW M3 2018
	private	double weight; // weight of the car // e.g. 2.3
	private boolean isNew; // is the car new or used/second-hand? 
	
	/**
     * constructor method of the Car class
     * 
     * @author Khaled Badran <gym4programming@gmail.com>
     * 
	 * @param modelName is the name of the car e.g. BMW M3 
	 * @param modelYear is The production year e.g. 2018
	 * @param weight weight of the car e.g. 2.3
	 * @param isNew is the car new or used/second-hand? 
	 */
	public Car(String modelName, int modelYear, double weight, boolean isNew) {
		this.modelName = modelName; 
		this.modelYear = modelYear; 
		this.weight = weight; 
		this.isNew = isNew;
		setFullModel();
	}
	
    /**
     * Setter method to set the Full/Complete Model e.g. BMW M3 2018
     * 
     * @author Khaled Badran <gym4programming@gmail.com>
     */
	private void setFullModel() {
		fullModel = modelName + " " + String.valueOf(modelYear);
	}
	
    /**
     * Setter method to set the modelName of a car.
     * 
     * @author Khaled Badran <gym4programming@gmail.com>
     * @param modelName is the name of the car e.g. BMW M3
     */
	public void setModelName(String modelName) {
		this.modelName = modelName;
	}
	
    /**
     * Setter method to set the ModelYear of a car.
     * 
     * @author Khaled Badran <gym4programming@gmail.com>
     * @param modelYear is The production year e.g. 2018
     */
	public void setModelYear(int modelYear) {
		this.modelYear = modelYear;
	}
	
    /**
     * Setter method to set the weight of a car 
     * 
     * @author Khaled Badran <gym4programming@gmail.com>
     * @param weight is the weight of a car
     */
	public void setWeight(double weight) {
		this.weight = weight;
	}
	
    /**
     * Setter method to set weather the car new or not
     * 
     * @author Khaled Badran <gym4programming@gmail.com>
     * @param isNew either the car is new (true) or the car is used (false).
     */
	public void setNew(boolean isNew) {
		this.isNew = isNew;
	}
	
    /**
     * Getter method to get the Full/Complete Model e.g. BMW M3 2018
     * 
     * @author Khaled Badran <gym4programming@gmail.com>
     * @return Full/Complete Model e.g. BMW M3 2018
     */
	public String getFullModel() {
		return fullModel;
	}
	
    /**
     * Getter method to get the number placed on the the current face of the dice with the given index.
     * 
     * @author Khaled Badran <gym4programming@gmail.com>
     * @return the number placed on the current face of the dice as an integer
     */
	public String getModelName() {
		return modelName;
	}
	
    /**
     * Getter method to get the ModelYear.
     * 
     * @author Khaled Badran <gym4programming@gmail.com>
     * @return ModelYear/production year
     */
	public int getModelYear() {
		return modelYear;
	}
	
    /**
     * Getter method to get the weight of a car.
     * 
     * @author Khaled Badran <gym4programming@gmail.com>
     * @return the weight of a car.
     */
	public double getWeight() {
		return weight;
	}
	
    /**
     * Getter method to get weather the car new or not.
     * 
     * @author Khaled Badran <gym4programming@gmail.com>
     * @return true if the car is new. Otherwise, false
     */
	public boolean isNew() {
		return isNew;
	}
	
	/**
	 * override the default toString()
	 * @author Khaled Badran <gym4programming@gmail.com>
	 * @return String to be displayed on the list view
	 */
	@Override
	public String toString() {
		return fullModel;
	}
	
}