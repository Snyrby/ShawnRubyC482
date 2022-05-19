/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;
import javax.swing.JOptionPane;
import shawnrubyinventorymanagementsystem.InHouse;
import shawnrubyinventorymanagementsystem.Inventory;
import shawnrubyinventorymanagementsystem.OutSourced;

/**
 * @author Shawn Ruby
 */

/** this class deals with adding a part */

public class PartAddController implements Initializable {

    private ToggleGroup addPartRadioButtons;
    @FXML private RadioButton inHouse;
    @FXML private RadioButton outSourced;
    @FXML private Label ChangeBox;
    
    //These are to initialize the text boxes
    @FXML private TextField partNameText;
    @FXML private TextField InventoryText;
    @FXML private TextField PriceText;
    @FXML private TextField MaxText;
    @FXML private TextField MinText;
    @FXML private TextField ChangeText;
    
    int ID;
    String Name;
    double Price;
    int Stock;
    int Min;
    int Max;
    String OutSourced;
    int InHouse;
    Inventory inv;
    
    /**
     * initializes the part add screen
     * @param url
     * @param rb 
     */
    
    @Override
    public void initialize(URL url, ResourceBundle rb) 
    {
        //Configures the radio buttons so only 1 can be toggled at a time
        addPartRadioButtons = new ToggleGroup();
        this.inHouse.setToggleGroup(addPartRadioButtons);
        this.outSourced.setToggleGroup(addPartRadioButtons);
    }       

    /**
     * retrieves the inventory data from inventory class
     * @param inv 
     */
    
    public PartAddController (Inventory inv)
    {
        this.inv = inv;
    }
    
    /**
     * adds a new part if the save part button is pushed
     * @param event
     * @throws IOException 
     */
    
    public void savePartButtonPushed(ActionEvent event) throws IOException
    {
        boolean inputValid = true;
        
        //input validation for name
        if(partNameText.getText().isBlank())
        {
            infoBox("PART NAME IS BLANK!", "PLEASE TRY AGAIN");     
            partNameText.requestFocus();
            partNameText.setText("");
            inputValid = false;
        }
        else
        {
            Name = partNameText.getText();
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
            infoBox("NOT A VALID NUMBER IN Min!", "PLEASE TRY AGAIN");
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
        //input validation for outsourced/inhouse
        if(ChangeText.getText().isBlank())
        {
            if(inHouse.isSelected())
            {
                infoBox("MACHINE ID IS BLANK!", "PLEASE TRY AGAIN");     
                ChangeText.requestFocus(); 
                ChangeText.setText(""); 
                inputValid = false;
                
            }
            else if(outSourced.isSelected())
            {
                infoBox("COMPANY NAME IS BLANK!", "PLEASE TRY AGAIN");     
                ChangeText.requestFocus(); 
                ChangeText.setText("");
                inputValid = false;
            }
        }
        
        //input validation for inhouse
        else if(inHouse.isSelected() && !ChangeText.getText().isBlank())
        {
            try 
            {
               InHouse = Integer.parseInt(ChangeText.getText());
            }
            catch(NumberFormatException ignore)
            {
                infoBox("NOT A VALID NUMBER IN Machine ID!", "PLEASE TRY AGAIN");
                ChangeText.requestFocus();
                ChangeText.setText("");
                inputValid = false;      
            }
        }
        
        //input validation for outsourced
        else if(outSourced.isSelected() && !ChangeText.getText().isBlank())
        {
            OutSourced = ChangeText.getText();
        }
        
        //if input validation is correct then will add a new inhouse or outsourced part
        //depending on which radio button is selected
        if(inputValid == true)
        {   

            ID = inv.partCount();
            if(inHouse.isSelected())
            {               
                inv.addPart(new InHouse(ID, Name, Price, Stock, Min, Max, InHouse));
            }   
            else if(outSourced.isSelected())
            {
                inv.addPart(new OutSourced(ID, Name, Price, Stock, Min, Max, OutSourced));
            }
            JOptionPane.showMessageDialog(null, Name + " has been added!", "Part Successfully Added", JOptionPane.PLAIN_MESSAGE);
            cancelButtonPushed(event);
        }
    }
    
    /**
     * function that configures a info box used for input validation
     * @param infoMessage
     * @param titleBar 
     */
    
    public static void infoBox(String infoMessage, String titleBar)
    {
        JOptionPane.showMessageDialog(null, infoMessage, titleBar, JOptionPane.ERROR_MESSAGE);
    }
    
    
    /**
     * function that will revert to main screen if the cancel button is pushed
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
     * changes text based on which radio button is selected
     */
    
    public void radioButtonPushed()
    {
        if(this.addPartRadioButtons.getSelectedToggle().equals(this.inHouse))
            ChangeBox.setText("Machine ID");  
        if(this.addPartRadioButtons.getSelectedToggle().equals(this.outSourced))
            ChangeBox.setText("Company Name");
    }
}
