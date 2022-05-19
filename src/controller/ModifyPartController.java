/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import static controller.ShawnRubyInventoryManagementSystemController.getSelectedPart;
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
import shawnrubyinventorymanagementsystem.Part;



/**
 * @author Shawn Ruby
 */

/** this class deals with modifying a part */

public class ModifyPartController implements Initializable {
    private ToggleGroup addPartRadioButtons;
    @FXML private RadioButton inHouse;
    @FXML private RadioButton outSourced;
    @FXML private Label ChangeBox;
    
    //These are to initialize the text boxes
    @FXML private TextField partIDText;
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
     * initializes the modify part screen 
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
        
        //retrieves selected part passed from main screen
        Part selectedPart = getSelectedPart();
        
        //sets the text boxes to display data from selected part
        partIDText.setText(Integer.toString(selectedPart.getId()));
        partNameText.setText(selectedPart.getName());
        InventoryText.setText(Integer.toString(selectedPart.getStock()));
        PriceText.setText(Double.toString(selectedPart.getPrice()));
        MaxText.setText(Integer.toString(selectedPart.getMax()));
        MinText.setText(Integer.toString(selectedPart.getMin()));
        ID = selectedPart.getId();
        
        //if the selected part is inhouse then it will select the inhouse radio button and changes machineid text box
        if(selectedPart instanceof InHouse)
        {
            inHouse.getToggleGroup().selectToggle(inHouse);
            ChangeText.setText(Integer.toString(((InHouse) selectedPart).getMachineID()));
        }
        
        //if the selected part is outsourced then it will select outsourced radio button 
        //and display company name data then set the label to company name
        else if (selectedPart instanceof OutSourced)
        {
            outSourced.getToggleGroup().selectToggle(outSourced);
            ChangeText.setText(((OutSourced) selectedPart).getCompanyName());
            ChangeBox.setText("Company Name");
        }
    }       

    
    /**
     * retrieves the inventory data from inventory class
     * @param inv 
     */
    
    public ModifyPartController (Inventory inv)
    {
        this.inv = inv;
    }
    
    /**
     * function that will modify a part if the save part button is pushed
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
        
        //input validation for outsourced/inhouse depending on which radio button is selected
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
        
        //if input validation is true
        if(inputValid == true)
        {   
            //creates an inhouse part and updates it if inhouse radio button is selected
            if(inHouse.isSelected())
            {             
                InHouse update = new InHouse(ID, Name, Price, Stock, Min, Max, InHouse);
                inv.updatePart(update);
            }   
            
            //creates an outsourced part and updates it if outsourced radio button is selected
            else if(outSourced.isSelected())
            {
                OutSourced update = new OutSourced(ID, Name, Price, Stock, Min, Max, OutSourced);
                inv.updatePart(update);
            }
            
            //message saying the part has been modified
            JOptionPane.showMessageDialog(null, Name + " has been modified!", "Part Successfully modified", JOptionPane.PLAIN_MESSAGE);   
            cancelButtonPushed(event);
        }
    }
    
    /**
     * function for creating an info box used in input validation
     * @param infoMessage
     * @param titleBar 
     */
    
    public static void infoBox(String infoMessage, String titleBar)
    {
        JOptionPane.showMessageDialog(null, infoMessage, titleBar, JOptionPane.ERROR_MESSAGE);
    }
    
    /**
     * function that reverts back to main screen if cancel button is pushed
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
     * function that will change the text label depending on which radio button is selected
     */
    
    public void radioButtonPushed()
    {
        if(this.addPartRadioButtons.getSelectedToggle().equals(this.inHouse))
            ChangeBox.setText("Machine ID");  
        if(this.addPartRadioButtons.getSelectedToggle().equals(this.outSourced))
            ChangeBox.setText("Company Name");
    }
}
