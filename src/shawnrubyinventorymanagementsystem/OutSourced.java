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

/** This class deals with managing an outsourced part */

public class OutSourced extends Part{
    private String companyName;
    
    /**
     * constructor used to form outSourced part
     * @param partID
     * @param name
     * @param price
     * @param stock
     * @param min
     * @param max
     * @param company 
     */
    
    public OutSourced(int partID, String name, double price, int stock, int min, int max, String company) 
    {
        setId(partID);
        setName(name);
        setPrice(price);
        setStock(stock);
        setMin(min);
        setMax(max);
        setCompanyName(company);
    }
    
    /**
     * sets company name 
     * @param name 
     */
    
    public void setCompanyName(String name)
    {
        this.companyName = name;
    }
    
    /**
     * returns company name
     * @return companyName
     */
    
    public String getCompanyName()
    {
        return companyName;
    }    
}
