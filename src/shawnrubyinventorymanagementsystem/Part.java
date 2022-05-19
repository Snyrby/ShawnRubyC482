/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shawnrubyinventorymanagementsystem;


/**
 * @author shawn Ruby
 */

/** this class deals with managing parts */

public abstract class Part {
    private int partID;
    private String partName;
    private double partPrice=0.0;
    private int stock;
    private int min;
    private int max;    

    
    public Part() 
    {
        setId(partID);
        setName(partName);
        setPrice(partPrice);
        setStock(stock);
        setMin(min);
        setMax(max);
    }
    
    /**
     * @return the id
     */
    public int getId() {
        return this.partID;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.partID = id;
    }

    /**
     * @return the name
     */
    public String getName() {
        return this.partName;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.partName = name;
    }

    /**
     * @return the price
     */
    public double getPrice() {
        return this.partPrice;
    }

    /**
     * @param price the price to set
     */
    public void setPrice(double price) {
        this.partPrice = price;
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
}

