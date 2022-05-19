
package shawnrubyinventorymanagementsystem;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


/**
 * @author Shawn Ruby
 */

/** this class is where the program starts and launches the main screen and generates data */

public class ShawnRubyInventoryManagementSystem extends Application{

    /**
     * @param args the command line arguments
     */
       
    public static void main(String[] args) {
        // TODO code application logic here
        launch(args);
    }
    
    /**
     * start place for program. launches the main screen
     * @param stage
     * @throws Exception 
     */
    
    @Override
    public void start(Stage stage) throws Exception {
       Inventory inv = new Inventory();
       addTestData(inv);
       
       FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/ShawnRubyInventoryManagementsystem.fxml"));
       controller.ShawnRubyInventoryManagementSystemController controller = new controller.ShawnRubyInventoryManagementSystemController(inv);
       loader.setController(controller);
       Parent root = loader.load();
       Scene scene = new Scene(root);
       stage.setScene(scene);
       stage.setTitle("Shawn Ruby Inventory Management System");
       stage.show();
    }
    
    /**
     * adds test data to the program
     * @param inv 
     */
    
    void addTestData(Inventory inv)
    {
        //adds all parts
        //this layout was used from the instructor
        Part Brakes = new InHouse(1, "Brakes", 15.00 , 10, 1, 100, 100);
        inv.addPart(Brakes);
        Part Wheel = new InHouse(2, "Wheel", 11.00, 16, 1, 100, 101);
        inv.addPart(Wheel);
        Part Seat = new OutSourced(3, "Seat", 15.00, 10, 1, 100, "Bikers Co.");
        inv.addPart(Seat);   
        
        //adds all products
        Product GiantBike = new Product(1000, "Giant Bike", 299.99, 5, 1, 100);       
        GiantBike.addAssociatedPart(Brakes);
        GiantBike.addAssociatedPart(Seat);
        GiantBike.addAssociatedPart(Wheel);
        inv.addProduct(GiantBike);
              
        Product Tricycle = new Product(1001, "Tricycle", 99.99, 3, 1, 100);
        Tricycle.addAssociatedPart(Brakes);
        Tricycle.addAssociatedPart(Seat);
        Tricycle.addAssociatedPart(Wheel);
        inv.addProduct(Tricycle);
    }
}
