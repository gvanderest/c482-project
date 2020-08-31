package partsapp;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import partsapp.inventory.Inventory;
import partsapp.part.InHouse;
import partsapp.part.Outsourced;
import partsapp.product.Product;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception{
        // Add example parts
        InHouse brakes = new InHouse(Inventory.getNextPartId(), "Brakes", 15.00, 10, 1, 20);
        brakes.setMachineId(1234);
        Inventory.addPart(brakes);

        Outsourced wheel = new Outsourced(Inventory.getNextPartId(), "Wheel", 11.00, 16, 1, 20);
        wheel.setCompanyName("Wheels 'R Us");
        Inventory.addPart(wheel);

        InHouse seat = new InHouse(Inventory.getNextPartId(), "Seat", 15.00, 10, 1, 20);
        seat.setMachineId(5555);
        Inventory.addPart(seat);

        // Add example products
        Product giantBike = new Product(Inventory.getNextProductId(), "Giant Bike", 99.99, 5, 1, 10);
        giantBike.addAssociatedPart(wheel);
        giantBike.addAssociatedPart(wheel);
        giantBike.addAssociatedPart(seat);
        giantBike.addAssociatedPart(brakes);
        Inventory.addProduct(giantBike);

        Product tricycle = new Product(Inventory.getNextProductId(), "Tricycle", 199.99, 3, 1, 5);
        tricycle.addAssociatedPart(wheel);
        tricycle.addAssociatedPart(wheel);
        tricycle.addAssociatedPart(wheel);
        tricycle.addAssociatedPart(seat);
        tricycle.addAssociatedPart(brakes);
        Inventory.addProduct(tricycle);

        // Instantiate main window
        FXMLLoader loader = new FXMLLoader(getClass().getResource("windows/main/main_window.fxml"));
        Parent root = loader.load();
        primaryStage.setTitle("Inventory Management System");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
