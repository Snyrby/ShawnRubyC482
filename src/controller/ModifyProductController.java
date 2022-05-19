/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import shawnrubyinventorymanagementsystem.Inventory;
import shawnrubyinventorymanagementsystem.Part;
import shawnrubyinventorymanagementsystem.Product;
import static controller.ShawnRubyInventoryManagementSystemController.getSelectedProduct;
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import javax.swing.JOptionPane;

/**
 * @author Shawn Ruby
 */

/** this class deals with modifying a product */

public class ModifyProductController implements Initializable {

    Inventory inv;
    @FXML private TextField productNameText;
    @FXML private TextField InventoryText;
    @FXML private TextField PriceText;
    @FXML private TextField MaxText;
    @FXML private TextField MinText;
    @FXML private TextField productIDText;
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
    
    //stores passed product from mainscreen
    Product selectedProduct = getSelectedProduct();
    
    /**
     * initializes the modify product screen
     * @param url
     * @param rb 
     */
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        //configures part table view columns
        IDColumn.setCellValueFactory(new PropertyValueFactory<Part, Integer>("Id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<Part, String>("Name"));
        stockColumn.setCellValueFactory(new PropertyValueFactory<Part, Integer>("Stock"));
        
        //configures the associated part table view columns
        assocIDColumn.setCellValueFactory(new PropertyValueFactory<Part, Integer>("Id"));
        assocNameColumn.setCellValueFactory(new PropertyValueFactory<Part, String>("Name"));
        assocStockColumn.setCellValueFactory(new PropertyValueFactory<Part, Integer>("Stock"));
        
        //populates textboxes with data from the selected product passed from main screen
        productIDText.setText(Integer.toString(selectedProduct.getId()));
        productNameText.setText(selectedProduct.getName());
        InventoryText.setText(Integer.toString(selectedProduct.getStock()));
        PriceText.setText(Double.toString(selectedProduct.getPrice()));
        MaxText.setText(Integer.toString(selectedProduct.getMax()));
        MinText.setText(Integer.toString(selectedProduct.getMin()));
        
        //populates the table views
        generatePartsTable();
        generateAssociatedPartsTable();
    }    
    
    /**
     * retrieves the inventory data from the inventory class
     * @param inv 
     */
    
    public ModifyProductController (Inventory inv)
    {
        this.inv = inv;
    }
    
    /**
     * populates the part table view with all parts
     */
    
    private void generatePartsTable()
    {
        TableColumn<Part, Double> costCol = formatPrice();
        partTableView.getColumns().addAll(costCol);
        partTableView.setItems(inv.getAllParts());
        partTableView.refresh();
    }
    
    
    /**
     * populates the associated parts table view with all associated parts 
     */
    
    private void generateAssociatedPartsTable()
    {
        associatedPart = selectedProduct.getAllAssociatedParts();
        TableColumn<Part, Double> costCol = formatPrice();
        associatedPartTableView.getColumns().addAll(costCol);
        associatedPartTableView.setItems(associatedPart);
        associatedPartTableView.refresh();
    }
    
    /**
     * function that will modify a part when the save button is pushed
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
        
        //input validation for associating parts with the product
        if(associatedPart.isEmpty())
        {
            infoBox("PRODUCT NEEDS TO HAVE ASSOCIATED PARTS!", "PLEASE TRY AGAIN");
            inputValid = false;
        }     
        
        //if input validation is correct
        if(inputValid == true)
        {   
            ID = selectedProduct.getId();    
            
            //compares the price of product to the price of all associated parts
            if(Price < selectedProduct.returnCost())
            {
                infoBox("THE PRICE OF THE OBJECT NEEDS TO BE LESS THAN THE COST OF ALL ASSOCIATED PARTS", "PLEASE TRY AGAIN");
                PriceText.requestFocus();
                PriceText.clear();
            }
            
            //if the price is correct then it will update the product
            else
            {
                JOptionPane.showMessageDialog(null, Name + " has been updated!", "Product Successfully Updated", JOptionPane.PLAIN_MESSAGE);
                selectedProduct = new Product(ID, Name, Price, Stock, Min, Max);                
                selectedProduct.getAllAssociatedParts().clear();
                for(Part assocPart : associatedPart)
                {
                    selectedProduct.addAssociatedPart(assocPart);
                }
                inv.updateProduct(selectedProduct);
                cancelButtonPushed(event);
            }
        }
    }
    
    /**
     * function that will add an associated part to the observable list and
     * update the table view
     */
    
    public void addAssociatedPartButtonPushed()
    {
        
        //sets index to selected item
        Part part = partTableView.getSelectionModel().getSelectedItem();
        
        //if index is null then will display error message
        if(part == null)
        {
            infoBox("PLEASE SELECT A PART FROM THE PARTS LIST", "PLEASE TRY AGAIN");
        }
        
        //if the associated part list is empty then it will add the associated part and display data
        else if(associatedPart.isEmpty())
        {
            associatedPart.add(part);
            associatedPartTableView.setItems(associatedPart);
            associatedPartTableView.refresh();    
            partTableView.requestFocus();
            partTableView.getSelectionModel().select(null);
            partTableView.getFocusModel().focus(null);
        }
        
        //if the list is not empty
        else if(!associatedPart.isEmpty())
        {      
            
            //checks the associated parts list to see if it already has the part
            if(associatedPart.contains(part))
            {
                infoBox("THAT ASSOCIATED PART IS ALREADY ADDED", "PLEASE TRY AGAIN"); 
                partTableView.requestFocus();
                partTableView.getSelectionModel().select(null);
                partTableView.getFocusModel().focus(null);
            }
            
            //if not in list then will add item and display data
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
     * function that deletes associated part from the observable list and 
     * updates the table view
     */
    
    public void deleteAssociatedPartButtonPushed()
    {
        
        //sets index to selected item
        Part part = associatedPartTableView.getSelectionModel().getSelectedItem();
        
        //if index is null then displays error
        if(part == null)
        {
            infoBox("PLEASE SELECT A PART FROM THE  ASSOCIATED PARTS LIST", "PLEASE TRY AGAIN");
        }
        
        //if not null then it will remove associated part and displays data
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
     * searches all parts for part in search box. exact same as main screen
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
     * reverts back to main screen if cancel button is pushed
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
     * function for the info box to display error message used for input validation
     * @param infoMessage
     * @param titleBar 
     */
    
    public static void infoBox(String infoMessage, String titleBar)
    {
        JOptionPane.showMessageDialog(null, infoMessage, titleBar, JOptionPane.ERROR_MESSAGE);
    }   
    
    /**
     * function that configures price column
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
