package partsapp;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import partsapp.inventory.Inventory;
import partsapp.part.InHouse;
import partsapp.product.Product;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception{
        // Add example parts
        // TODO: Detect and prevent collisions of IDs
        Inventory.addPart(new InHouse(1, "Brakes", 15.00, 10, 1, 20));
        Inventory.addPart(new InHouse(2, "Wheel", 11.00, 16, 1, 20));
        Inventory.addPart(new InHouse(3, "Seat", 15.00, 10, 1, 20));

        // Add example products
        // TODO: Detect and prevent collisions of IDs
        Inventory.addProduct(new Product(1000, "Giant Bike", 299.99, 5, 1, 10));
        Inventory.addProduct(new Product(1001, "Tricycle", 99.99, 3, 1, 5));

        // Instantiate main window
        Parent root = FXMLLoader.load(getClass().getResource("windows/main/main_window.fxml"));
        primaryStage.setTitle("Inventory Management System");
        primaryStage.setScene(new Scene(root, 800, 500));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
