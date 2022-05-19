/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shawnrubyinventorymanagementsystem;

/**
 * @see Part
 * @author snyrb
 */

/** This class deals with managing an in house part */

public class InHouse extends Part{
    private int machineID;    

    /**
     * constructor used to form inHouse part
     * @param id
     * @param name
     * @param price
     * @param stock
     * @param min
     * @param max
     * @param machineID 
     */
    
    public InHouse(int id, String name, double price, int stock, int min, int max, int machineID) 
    {
        setId(id);
        setName(name);
        setPrice(price);
        setStock(stock);
        setMin(min);
        setMax(max);
        setMachineID(machineID);
    }
    
    /**
     * sets machine ID
     * @param machineID 
     */
    
    public void setMachineID(int machineID)
    {
        this.machineID = machineID;
    }
    
    /**
     * returns the machine ID
     * @return machineID
     */
    
    public int getMachineID()
    {
        return machineID;
    }    
}
