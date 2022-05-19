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

/** this class manages the inventory */

public class Inventory {
    
    private ObservableList<Part> allParts = FXCollections.observableArrayList();
    private ObservableList<Part> partSearch = FXCollections.observableArrayList();
    
    private ObservableList<Product> allProducts = FXCollections.observableArrayList();
    private ObservableList<Product> productSearch = FXCollections.observableArrayList();
    
    
    /**
     * adds product to inventory
     * @param productToAdd 
     */
    
    public void addProduct(Product productToAdd)
    {
        if(productToAdd != null)
        {
            allProducts.add(productToAdd);
        }
    }
    
    /**
     * removes product from inventory
     * @param productToRemove
     * @return true or false
     */
    
    public boolean removeProduct(int productToRemove)
    {
        for(Product product : allProducts)
        {
            if(product.getId() == productToRemove)
            {
                allProducts.remove(product);
                return true;
            }
        }return false;
    }
    
    /**
     * function that loops through and searches for product
     * @param productToSearch
     * @return productSearch
     */
    
    public ObservableList<Product> lookUpProduct(String productToSearch)
    {
        Integer idCompare;
        String idCompareToString;
        productSearch.clear();
        if(!allProducts.isEmpty())
        {
            for(Product product : allProducts)
            {
                idCompare = product.getId();
                idCompareToString = idCompare.toString();
                if (idCompareToString.toUpperCase().contains(productToSearch) || product.getName().toUpperCase().contains(productToSearch))
                {
                    productSearch.add(product);
                }
            }           
        }
        return productSearch;
    }
    
    /**
     * function that will replace exiting product with new product
     * @param product 
     */
    
    public void updateProduct(Product product)
    {
       for(int i = 0; i < allProducts.size(); i++)
       {
           if(allProducts.get(i).getId() == product.getId())
           {
               allProducts.set(i, product);
               break;
           }
       }
    }
    
    /**
     * function that will return all products
     * @return allProducts
     */
    
    public ObservableList<Product> getAllProducts()
    {
        //return product list
        return allProducts;
    }
    
    /**
     * function that auto generates product id
     * @return IDCount
     */
    
    public int productCount()
    {
        Integer IDCount = 999;               
        do
        {
            IDCount++;
            String IDDupelicate = IDCount.toString();
            lookUpProduct(IDDupelicate);         
        }while(!productSearch.isEmpty());
        return IDCount;
    }
    
    
    /**
     * function that adds part to inventory
     * @param partToAdd 
     */
    
    public void addPart(Part partToAdd)
    {
        if(partToAdd != null)
        {
            allParts.add(partToAdd);
        }
    }
    
    /**
     * function that removes product from inventory
     * @param partToRemove
     * @return false or true
     */
    
    public boolean removePart(int partToRemove)
    {
        for(Part part: allParts)
        {
            if(part.getId() == partToRemove)
            {
                allParts.remove(part);
                return true;
            }
        }return false;
    }
    
    /**
     * function that loops through all parts for a search
     * @param partToSearch
     * @return partSearch
     */
    
    public ObservableList<Part> lookUpPart(String partToSearch)
    {
        Integer idCompare;
        String idCompareToString;
        partSearch.clear();
        if(!allParts.isEmpty())
        {
            for(Part part : allParts)
            {
                idCompare = part.getId();
                idCompareToString = idCompare.toString();
                if (idCompareToString.toUpperCase().contains(partToSearch) || part.getName().toUpperCase().contains(partToSearch))
                {
                    partSearch.add(part);
                }
            }           
        }
        return partSearch;
    }
      
    /**
     * function that will replace exiting part with new part
     * @param part 
     */
    
    public void updatePart(Part part)
    {
       int i = 0;
       for(Part updatepart : allParts)
       {
           if(updatepart.getId() == part.getId())
           {
               allParts.set(i, part);
               break;
           }
           i++;
       }
    }
    
    /**
     * returns the part observable list
     * @return allParts
     */
    
    public ObservableList<Part> getAllParts()
    {
    	//Return part list
        return allParts;
    }
    
    /**
     * function that auto generates part id
     * @return IDCount
     */
    
    public int partCount()
    {
        Integer IDCount = 0;               
        do
        {
            IDCount++;
            String IDDupelicate = IDCount.toString();
            lookUpPart(IDDupelicate);         
        }while(!partSearch.isEmpty());
        return IDCount;
    }
}
