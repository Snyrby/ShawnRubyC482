/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shawnrubyinventorymanagementsystem;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


/**
 * @author Shawn Ruby
 */

/** this class deals with managing products */

public class Product {
    
    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();
    private int productID;
    private String name;
    private double price = 0.0;
    private int stock;
    private int min;
    private int max;
    private double cost;
    
    public Product(int id, String name, double price, int stock, int min, int max) 
    {
        setId(id);
        setName(name);
        setPrice(price);
        setStock(stock);
        setMin(min);
        setMax(max);
    }
    
    /**
     * @return the id
     */
    public int getId() {
        return this.productID;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.productID = id;
    }

    /**
     * @return the name
     */

   public String getName() {
        return this.name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the price
     */
    public double getPrice() {
        return this.price;
    }

    /**
     * @param price the price to set
     */
    public void setPrice(double price) {
        this.price = price;
    }
    
    /**
     * @return the stock
     */
    public int getStock() {
        return this.stock;
    }

    /**
     * @param stock the stock to set
     */
    public void setStock(int stock) {
        this.stock = stock;
    }

    /**
     * @return the min
     */

    public int getMin() {
        return this.min;
    }

    /**
     * @param min the min to set
     */
    public void setMin(int min) {
        this.min = min;
    }

    /**
     * @return the max
     */
    public int getMax() {
        return this.max;
    }

    /**
     * @param max the max to set
     */
    public void setMax(int max) {
        this.max = max;
    }
    
    /**
     * function that will add an associated part to the product and adds it to total 
     * cost of parts
     * @param part 
     */
    
    public void addAssociatedPart(Part part)
    {
        associatedParts.add(part);
        cost += part.getPrice();
    }
    
    /**
     * function that will remove the part from the product and subtracts the cost
     * @param part 
     */
    
    public void removeAssociatedPart(Part part)
    {
        associatedParts.remove(part);
        cost -= part.getPrice();
    }
    
    /**
     * gets all associated parts
     * @return associatedParts
     */
    
    public ObservableList <Part> getAllAssociatedParts()
    {
    	//Return associated part list
        return associatedParts;
    }
    
    /**
     * returns the cost
     * @return cost 
     */
    
    public double returnCost()
    {
        return cost;
    }
}

