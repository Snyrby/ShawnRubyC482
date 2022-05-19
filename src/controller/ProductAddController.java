/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javax.swing.JOptionPane;
import shawnrubyinventorymanagementsystem.Inventory;
import shawnrubyinventorymanagementsystem.Part;
import shawnrubyinventorymanagementsystem.Product;

/**
 * @author Shawn Ruby
 */

/** This class deals with adding a product */

public class ProductAddController implements Initializable {
    
    //These are to initialize the text boxes
    @FXML private TextField productNameText;
    @FXML private TextField InventoryText;
    @FXML private TextField PriceText;
    @FXML private TextField MaxText;
    @FXML private TextField MinText;
    @FXML private TextField partSearchBox;
    @FXML private TableView<Part> partTableView;
    @FXML private TableView <Part> associatedPartTableView;
    
    @FXML private TableColumn<Part, Integer> IDColumn;
    @FXML private TableColumn<Part, String> nameColumn;
    @FXML private TableColumn<Part, Integer> stockColumn;
    
    @FXML private TableColumn<Part, Integer> assocIDColumn;
    @FXML private TableColumn<Part, String> assocNameColumn;
    @FXML private TableColumn<Part, Integer> assocStockColumn;
    
        
    //These are for configuring the products    
    int ID;
    String Name;
    double Price;
    int Stock;
    int Min;
    int Max;
    
    private ObservableList<Part> associatedPart = FXCollections.observableArrayList();
    private ObservableList<Part> searchPart = FXCollections.observableArrayList();
    Inventory inv;
    
    /**
     * initializes the product add screen
     * @param url
     * @param rb 
     */
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        //configured the part table view
        IDColumn.setCellValueFactory(new PropertyValueFactory<Part, Integer>("Id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<Part, String>("Name"));
        stockColumn.setCellValueFactory(new PropertyValueFactory<Part, Integer>("Stock"));
        
        //configure the associated parts table view
        assocIDColumn.setCellValueFactory(new PropertyValueFactory<Part, Integer>("Id"));
        assocNameColumn.setCellValueFactory(new PropertyValueFactory<Part, String>("Name"));
        assocStockColumn.setCellValueFactory(new PropertyValueFactory<Part, Integer>("Stock"));
        
        //populates the table views with data
        generatePartsTable();
        generateAssociatedPartsTable();
    } 
    
    /**
     * function that will save the product into the inventory if save button selected
     * @param event
     * @throws IOException 
     */
    
    public void saveProductButtonPushed(ActionEvent event) throws IOException
    {
        boolean inputValid = true;
        
        //input validation for name
        if(productNameText.getText().isBlank())
        {
            infoBox("PRODUCT NAME IS BLANK!", "PLEASE TRY AGAIN");     
            productNameText.requestFocus();
            productNameText.setText("");
            inputValid = false;
        }
        else
        {
            Name = productNameText.getText();
        }
        
        //input validation for price
        try 
        {
            Price = Double.valueOf(PriceText.getText());
        }
        catch(NumberFormatException ignore)
        {
            infoBox("NOT A VALID NUMBER IN PRICE!", "PLEASE TRY AGAIN"); 
            PriceText.requestFocus();
            PriceText.setText("");
            inputValid = false;
        }
        
        //input validation for max
        try
        {
            Max = Integer.valueOf(MaxText.getText());
        }
        catch(NumberFormatException ignore)
        {
            infoBox("NOT A VALID NUMBER IN MAX!", "PLEASE TRY AGAIN");
            MaxText.requestFocus();
            MaxText.setText("");
            inputValid = false;
        }
        
        //input validation for min
        try
        {
            Min = Integer.parseInt(MinText.getText());
            if(Min > Max)
            {
                infoBox("MIN HAS TO BE BELOW MAX!", "PLEASE TRY AGAIN");
                MaxText.requestFocus();
                MaxText.setText("");
                MinText.setText("");
                inputValid = false;
            }
        }
        catch(NumberFormatException ignore)
        {
            infoBox("NOT A VALID NUMBER IN MIN!", "PLEASE TRY AGAIN");
            MinText.requestFocus();
            MinText.setText("");
            inputValid = false;
        }
        
        //input validation for stock
        try
        {  
            Stock = Integer.parseInt(InventoryText.getText());
            if(Stock > Max && Max > Min || Stock < Min && Max > Min)
            {
                infoBox("NOT A VALID NUMBER IN INVENTORY! PLEASE MAKE SURE IT IS BETWEEN MAX AND MIN!", "PLEASE TRY AGAIN");
                InventoryText.requestFocus();
                InventoryText.setText("");
                inputValid = false;
            }
        }
        catch(NumberFormatException ignore)
        {
            infoBox("NOT A VALID NUMBER IN INVENTORY! PLEASE MAKE SURE IT IS BETWEEN MAX AND MIN!", "PLEASE TRY AGAIN");
            InventoryText.requestFocus();
            InventoryText.setText("");
            inputValid = false;
        }
        
        //input validation for associated parts being added
        if(associatedPart.isEmpty())
        {
            infoBox("PRODUCT NEEDS TO HAVE ASSOCIATED PARTS!", "PLEASE TRY AGAIN");
            inputValid = false;
        }
        
        //if input validation was correct
        if(inputValid == true)
        {   
            //set the id to an auto generated value
            ID = inv.productCount();
            
            //creates a new product
            Product newProduct = new Product(ID, Name, Price, Stock, Min, Max);
            
            //loops through all associated parts and adds them to the product
            for(Part assocPart : associatedPart)
            {
                newProduct.addAssociatedPart(assocPart);
            }
            
            //compares the price of the product and all associated parts
            if(newProduct.returnCost() > newProduct.getPrice())
            {
                infoBox("THE PRICE OF THE OBJECT NEEDS TO BE LESS THAN THE COST OF ALL ASSOCIATED PARTS", "PLEASE TRY AGAIN");
                PriceText.requestFocus();
                PriceText.clear();
            }
            
            //if the product price is more then it will add the product to the inventory
            else
            {
                JOptionPane.showMessageDialog(null, Name + " has been added!", "Product Successfully Added", JOptionPane.PLAIN_MESSAGE);
                inv.addProduct(newProduct);
                cancelButtonPushed(event);
            }     
        }
    }
    
    /**
     * function that adds associated parts to the observable list and updates
     * the table view
     */
    
    public void addAssociatedPartButtonPushed()
    {
        //sets index to selected item
        Part part = partTableView.getSelectionModel().getSelectedItem();
        
        //if selected item is null then it ill display an error
        if(part == null)
        {
            infoBox("PLEASE SELECT A PART FROM THE PARTS LIST", "PLEASE TRY AGAIN");
        }
        
        //if the associated part list is empty then it will add the associated part to the table view
        else if(associatedPart.isEmpty())
        {
            associatedPart.add(part);
            associatedPartTableView.setItems(associatedPart);
            associatedPartTableView.refresh();    
            partTableView.requestFocus();
            partTableView.getSelectionModel().select(null);
            partTableView.getFocusModel().focus(null);
        }
        
        //if the list is not empty then it will check if the part has already been added
        else if(!associatedPart.isEmpty())
        {       
            if(associatedPart.contains(part))
            {
                infoBox("THAT ASSOCIATED PART IS ALREADY ADDED", "PLEASE TRY AGAIN"); 
                partTableView.requestFocus();
                partTableView.getSelectionModel().select(null);
                partTableView.getFocusModel().focus(null);
            }
            
            //if the part has not already been added then it will add it it to the list
            else
            {
                associatedPart.add(part);
                associatedPartTableView.setItems(associatedPart);
                associatedPartTableView.refresh();    
                partTableView.requestFocus();
                partTableView.getSelectionModel().select(null);
                partTableView.getFocusModel().focus(null);
            }          
        }
    }
    
    /** 
     * function that will delete an associated part from the observable list and updates
     * info in the table view
     */
    
    public void deleteAssociatedPartButtonPushed()
    {
        //sets index of the selected item
        Part part = associatedPartTableView.getSelectionModel().getSelectedItem();
        
        //if index is null then displays error
        if(part == null)
        {
            infoBox("PLEASE SELECT A PART FROM THE  ASSOCIATED PARTS LIST", "PLEASE TRY AGAIN");
        }
        
        //if no error then it will remove the associated part is display data in table view
        else
        {
            associatedPart.remove(part);
            associatedPartTableView.setItems(associatedPart);
            associatedPartTableView.refresh();
            associatedPartTableView.requestFocus();
            associatedPartTableView.getSelectionModel().select(null);
            associatedPartTableView.getFocusModel().focus(null);
        }

    }
    
    /**
     * Retrieves the inventory from inventory class
     * @param inv 
     */
    
    public ProductAddController (Inventory inv)
    {
        this.inv = inv;
    }
    
    /**
     * Searches through all parts and will display in table view
     * exact same as other searches
     */
    
    public void searchForPart()
    {
        String searchName = partSearchBox.getText().toUpperCase();
        searchPart.clear();
        if(searchName.isEmpty())
        {
            partTableView.setItems(inv.getAllParts());
            partTableView.refresh();
        }
        else
        {
            searchPart.addAll(inv.lookUpPart(searchName));            
            if(searchPart.isEmpty())
            {   
                partTableView.setItems(null);
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Part Search Error");
                alert.setHeaderText("There were no parts found!");
                alert.setContentText("The search term entered does not match any Part ID or \nPart Name!");
                alert.showAndWait();
            }
            else
            {
                partTableView.setItems(searchPart);
                partTableView.refresh();  
            }
        }      
    }
    
    /**
     * if the cancel button is pushed it will send user to main screen
     * @param event
     * @throws IOException 
     */
    
    public void cancelButtonPushed(ActionEvent event) throws IOException
    {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/view/ShawnRubyInventoryManagementSystem.fxml"));
        
        
        controller.ShawnRubyInventoryManagementSystemController controller = new controller.ShawnRubyInventoryManagementSystemController(inv);
        loader.setController(controller);
        Parent tableViewParent = loader.load();
        Scene tableViewScene = new Scene(tableViewParent);
        
        //This line gets the Stage information
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setTitle("Shawn Ruby Inventory Management System");
        window.setScene(tableViewScene);
        window.show();
    }
    
    /**
    * this will configure a error message used for input validation
    * @param infoMessage
    * @param titleBar 
    */
    
    public static void infoBox(String infoMessage, String titleBar)
    {
        JOptionPane.showMessageDialog(null, infoMessage, titleBar, JOptionPane.ERROR_MESSAGE);
    }   
    
    /**
     * populates parts table
     */
    
    private void generatePartsTable()
    {
        TableColumn<Part, Double> costCol = formatPrice();
        partTableView.getColumns().addAll(costCol);
        partTableView.setItems(inv.getAllParts());
        partTableView.refresh();
    }
    
    /**
     * populates associated parts table
     */
    
    private void generateAssociatedPartsTable()
    {
        TableColumn<Part, Double> costCol = formatPrice();
        associatedPartTableView.getColumns().addAll(costCol);
    }
    
    /**
     * configures the price column
     * @param <T>
     * @return costCol
     */
    
    private <T> TableColumn<T, Double> formatPrice()
    {
        TableColumn<T, Double> costCol = new TableColumn("Price");
        costCol.setPrefWidth(135);
        costCol.setCellValueFactory(new PropertyValueFactory<>("Price"));
        //format as currency
        costCol.setCellFactory((TableColumn<T, Double> column) -> {
            return new TableCell<T,Double>(){
                @Override
                protected void updateItem(Double item, boolean empty){
                    if(!empty){
                        setText("$" + String.format("%.2f",item));
                    }
                }
            };
        });
        return costCol;
    }
}
