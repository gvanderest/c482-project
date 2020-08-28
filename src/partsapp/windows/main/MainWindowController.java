package partsapp.windows.main;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableView;
import partsapp.formatters.PartFormatter;
import partsapp.formatters.ProductFormatter;
import partsapp.inventory.Inventory;
import partsapp.part.Part;
import partsapp.product.Product;

import java.net.URL;
import java.util.ResourceBundle;

public class MainWindowController implements Initializable {
    @FXML private final ObservableList<PartFormatter> filteredParts = FXCollections.observableArrayList();
    @FXML private final ObservableList<ProductFormatter> filteredProducts = FXCollections.observableArrayList();

    public TableView<PartFormatter> partsTable;
    public TableView<ProductFormatter> productsTable;

    public MainWindowController() {}

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        refreshInventoryParts();
        refreshInventoryProducts();
    }

    private void refreshInventoryParts() {
        filteredParts.clear();
        for (Part part: Inventory.getAllParts()) {
            filteredParts.add(new PartFormatter(part));
        }
        partsTable.setItems(filteredParts);
    }

    private void refreshInventoryProducts() {
        filteredProducts.clear();
        for (Product product: Inventory.getAllProducts()) {
            filteredProducts.add(new ProductFormatter(product));
        }
        productsTable.setItems(filteredProducts);
    }

    public void handleExit() {
        System.exit(0);
    }
}
