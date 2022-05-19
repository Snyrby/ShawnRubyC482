/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import shawnrubyinventorymanagementsystem.Inventory;
import shawnrubyinventorymanagementsystem.Part;
import shawnrubyinventorymanagementsystem.Product;

/**
 * @author Shawn Ruby
 */

/** This class controls the main screen and displays parts and products in table view */

public class ShawnRubyInventoryManagementSystemController implements Initializable 
{
    
    //instance variable that stores the inventory info
    Inventory inv;
    
    /**
     * These configure the Table view
     */
    @FXML private TableView<Part> partTableView;
    @FXML private TableView<Product> productTableView;
    
    
    //These will configure the columns for part table view
    @FXML private TableColumn<Part, Integer> IDColumn;
    @FXML private TableColumn<Part, String> nameColumn;
    @FXML private TableColumn<Part, Integer> stockColumn;
    
    //These will configure the columns for product table view
    @FXML private TableColumn<Product, Integer> productIDColumn;
    @FXML private TableColumn<Product, String> productNameColumn;
    @FXML private TableColumn<Product, Integer> productStockColumn;
    
    //This is for setting focus to null at program launch
    @FXML private Label StartText;   
    
    //creates observable lists for both all parts and the searched parts
    private ObservableList<Part> allPart = FXCollections.observableArrayList();
    private ObservableList<Part> searchPart = FXCollections.observableArrayList();
    
    //creates observable lists for both all products and the searched products  
    private ObservableList<Product> allProduct = FXCollections.observableArrayList();
    private ObservableList<Product> searchProduct = FXCollections.observableArrayList();
    
    /**
     * Names the text search Boxes
     */
    @FXML private TextField partSearchBox;
    @FXML private TextField productSearchBox;
    
    private static Part selectedPart;
    private static Product selectedProduct;
    
    /**
     * function that retrieves the inventory data from inventory class
     * @param inv 
     */
    
    public ShawnRubyInventoryManagementSystemController (Inventory inv)
    {
        this.inv = inv;
    }
    
    /**
     * initializes the main screen controller
     * @param url
     * @param rb 
     */
    
    @Override
    public void initialize(URL url, ResourceBundle rb) 
    {
        //sets up the part table columns
        IDColumn.setCellValueFactory(new PropertyValueFactory<Part, Integer>("Id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<Part, String>("Name"));
        stockColumn.setCellValueFactory(new PropertyValueFactory<Part, Integer>("Stock"));
        
        //sets up the product table columns
        productIDColumn.setCellValueFactory(new PropertyValueFactory<Product, Integer>("Id"));
        productNameColumn.setCellValueFactory(new PropertyValueFactory<Product, String>("Name"));
        productStockColumn.setCellValueFactory(new PropertyValueFactory<Product, Integer>("Stock"));
        
        //populates the tables with data
        generatePartsTable();
        generateProductsTable();
            
        /**
         * function sets the focus to null on program launch
         */
        
        Platform.runLater(new Runnable() {
        @Override
        public void run() {
            StartText.requestFocus();
        }
    });
    }
    
    /**
     * function used to fill the part table view with all parts
     */
    
    private void generatePartsTable()
    {
        //retrieves all parts in inventory
        allPart.setAll(inv.getAllParts());
        
        //sets up table column for price
        TableColumn<Part, Double> costCol = formatPrice();
        partTableView.getColumns().addAll(costCol);
        
        //displays all parts in the part table view
        partTableView.setItems(allPart);
        partTableView.refresh();
    }
    
    /**
     * function used to fill the product table view with all product
     */
    
    private void generateProductsTable()
    {
        //retrieves all products in inventory
        allProduct.setAll(inv.getAllProducts());
        
        //sets up table column for price
        TableColumn<Product, Double> costCol = formatPrice();
        productTableView.getColumns().addAll(costCol);
        
        //displays all products in the product table view
        productTableView.setItems(allProduct);
        productTableView.refresh();
    }
    
    /**
     * function that is used to initialize the part search bar 
     */
    
    public void searchForPart()
    {
        //retrieves data in the search box
        String searchName = partSearchBox.getText().toUpperCase();
        
        //clears the searchpart observable list
        searchPart.clear();
        
        //if nothing is in search box, it will display all parts
        if(searchName.isEmpty())
        {
            partTableView.setItems(allPart);
            partTableView.refresh();
        }
        else
        {
            //calls the search function in the inventory then adds all results to the search part list
            searchPart.addAll(inv.lookUpPart(searchName));     
            
            //there were no results it will display error
            if(searchPart.isEmpty())
            {   
                partTableView.setItems(null);
                Alert alert = new Alert(AlertType.WARNING);
                alert.setTitle("Part Search Error");
                alert.setHeaderText("There were no parts found!");
                alert.setContentText("The search term entered does not match any Part ID or \nPart Name!");
                alert.showAndWait();
            }
            else
            {
                //if there were results then shows them in table view
                partTableView.setItems(searchPart);
                partTableView.refresh();  
            }
        }      
    }
    
    /**
     * This function is the exact same as search for part, but for product
     */
    
    public void searchForProduct()
    {
        String searchName = productSearchBox.getText().toUpperCase();
        searchProduct.clear();
        if(searchName.isEmpty())
        {
            productTableView.setItems(allProduct);
            productTableView.refresh();
        }
        else
        {
            searchProduct.addAll(inv.lookUpProduct(searchName));
            if(searchProduct.isEmpty())
            {
                productTableView.setItems(null);
                Alert alert = new Alert(AlertType.WARNING);
                alert.setTitle("Product Search Error");
                alert.setHeaderText("There were no product found!");
                alert.setContentText("The search term entered does not match any Product ID or \nProduct Name!");
                alert.showAndWait();
            }
            else
            {
                productTableView.setItems(searchProduct);
                productTableView.refresh();
            }         
        }
    }
    
    /**
     * function that will delete a part
     */
    
    public void partDeleteButtonPushed()
    {
        //sets the part to the selected item in part table view
        Part part = partTableView.getSelectionModel().getSelectedItem();
        
        //if nothing is selected then it will show error
        if(part == null)
        {
            Alert nullalert = new Alert(AlertType.ERROR);
            nullalert.setTitle("Part Deletion Error");
            nullalert.setHeaderText("The part was NOT deleted!");
            nullalert.setContentText("There was no part selected!");
            nullalert.showAndWait();
        }
        else
        {
            //if something was selected then it will ask the user to confirm deletion
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Delete Part");
            alert.setHeaderText("Are you sure you want to delete this part?");
            alert.setContentText("Press OK to delete the part. \nPress Cancel to cancel the deletion.");
            alert.showAndWait();
            
            //if user confirms then it will remove the part from the inventory and display data
            if(alert.getResult() == ButtonType.OK)
            {
                inv.removePart(part.getId());
                
                //loops through every product and checks if the part being deleted is linked to a product
                for(Product product : allProduct)
                {
                    //if the product contains the part then it will remove the part
                    if(product.getAllAssociatedParts().contains(part))
                    {
                        product.removeAssociatedPart(part);
                    }
                }
                generatePartsTable(); 
            }
            else
            {
                alert.close();
            }
        }               
    }
    
    /**
     * This function is the exact same as part delete but for product
     */
    
    public void productDeleteButtonPushed()
    {
        Product product = productTableView.getSelectionModel().getSelectedItem();
        if(product == null)
        {
            Alert nullalert = new Alert(AlertType.ERROR);
            nullalert.setTitle("Product Deletion Error");
            nullalert.setHeaderText("The product was NOT deleted!");
            nullalert.setContentText("There was no product selected!");
            nullalert.showAndWait();
        }
        else
        {
            //checks if the product has any parts linked to it and if it does, it will throw an error
            if(!product.getAllAssociatedParts().isEmpty())
            {
                Alert partAlert = new Alert(AlertType.ERROR);
                partAlert.setTitle("Product Deletion Error");
                partAlert.setHeaderText("The product was NOT deleted!");
                partAlert.setContentText("Please remove all associated parts before removing a product!");
                partAlert.showAndWait();
            }
            else
            {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Delete Product");
                alert.setHeaderText("Are you sure you want to delete this product?");
                alert.setContentText("Press OK to delete the product. \nPress Cancel to cancel the deletion.");
                alert.showAndWait();
                if(alert.getResult() == ButtonType.OK)
                {
                    inv.removeProduct(product.getId());
                    generateProductsTable(); 
                }
                else
                {
                    alert.close();
                }  
            }
        }               
    }

    
    /**
     * function that will open add part screen when add part button is pushed
     * @param event
     * @throws IOException 
     */
    
    public void partAddButtonPushed(ActionEvent event) throws IOException
    {
        //sets up the file to open
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/view/PartAdd.fxml"));
        
        //passes the inventory to 
        controller.PartAddController controller = new controller.PartAddController(inv);
        loader.setController(controller);
        
        //loads the scene
        Parent tableViewParent = loader.load();
        Scene tableViewScene = new Scene(tableViewParent);
        
        //This line gets the Stage information
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setTitle("Add Part");
        window.setScene(tableViewScene);
        window.show();
    }
    
    /**
     * function that will configure modify part screen
     * @param event
     * @throws IOException 
     */
    
    public void modifyPartButtonPushed(ActionEvent event) throws IOException
    {
        //sets selected part as the selected item in table view
        selectedPart = partTableView.getSelectionModel().getSelectedItem();
        
        //if no item is selected then will throw error
        if (selectedPart == null)
        {
            Alert nullalert = new Alert(AlertType.ERROR);
            nullalert.setTitle("Part Modify Error");
            nullalert.setHeaderText("Can't Modify Part");
            nullalert.setContentText("There was no part selected!");
            nullalert.showAndWait();
        }
        else
        {
            //if no error then opens modify part screen and sends the inventory data
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/view/ModifyPart.fxml"));
            controller.ModifyPartController controller = new controller.ModifyPartController(inv);
            loader.setController(controller);
            Parent tableViewParent = loader.load();
            Scene tableViewScene = new Scene(tableViewParent);
        
            //This line gets the Stage information
            Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
            window.setTitle("Modify Part");
            window.setScene(tableViewScene);
            window.show();
        }               
    }
    
    /**
     * returns the selected part used in modify part screen
     * @return selectedPart
     */
    
    public static Part getSelectedPart()
    {
        return selectedPart;
    }
  
    /**
     * This function is the exact same as part add but for products
     * @param event
     * @throws IOException 
     */
    
    public void addProductButtonPushed(ActionEvent event) throws IOException
    {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/view/ProductAdd.fxml"));
        controller.ProductAddController controller = new controller.ProductAddController(inv);
        loader.setController(controller);
        Parent tableViewParent = loader.load();
        Scene tableViewScene = new Scene(tableViewParent);
        
        //This line gets the Stage information
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setTitle("Add Part");
        window.setScene(tableViewScene);
        window.show();
    }
    
    /**
     * This function is the exact same as modify part but for products
     * @param event
     * @throws IOException 
     */
    
    public void modifyProductButtonPushed(ActionEvent event) throws IOException
    {
        selectedProduct = productTableView.getSelectionModel().getSelectedItem();
        if (selectedProduct == null)
        {
            Alert nullalert = new Alert(AlertType.ERROR);
            nullalert.setTitle("Product Modify Error");
            nullalert.setHeaderText("Can't Modify Product");
            nullalert.setContentText("There was no product selected!");
            nullalert.showAndWait();
        }
        else
        {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/view/ModifyProduct.fxml"));
            controller.ModifyProductController controller = new controller.ModifyProductController(inv);
            loader.setController(controller);
            Parent tableViewParent = loader.load();
            Scene tableViewScene = new Scene(tableViewParent);
        
            //This line gets the Stage information
            Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
            window.setTitle("Modify Part");
            window.setScene(tableViewScene);
            window.show();
        }               
    }
      
    /** 
     * returns selected product that is used in the modify product screen
     * @return selectedProduct
     */
    
    public static Product getSelectedProduct()
    {
        return selectedProduct;
    }
    
    
    /**
     * closes the program when exit button pushed
     */
    
    public void exitButtonPushed()
    {
        System.exit(0);
    }
    
    /**
     * Function that will configure the price column and format it in currency
     * This function and the other instances of this function were received from
     * the instructors example
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
