package partsapp.windows.main;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.cell.PropertyValueFactory;
import partsapp.inventory.Inventory;
import partsapp.part.Part;
import partsapp.product.Product;

public class MainWindowController {
    @FXML private ObservableList<Part> filteredParts;
    @FXML private ObservableList<Product> filteredProducts;

    @FXML private PropertyValueFactory<Part, String> partIdValueFormatter = new PropertyValueFactory<>("id");

    public MainWindowController() {
        filteredParts = Inventory.getAllParts();
        filteredProducts = Inventory.getAllProducts();
    }

    public void handleExit() {
        System.exit(0);
    }
}
