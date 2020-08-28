package partsapp.windows.main;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import partsapp.formatters.PartFormatter;
import partsapp.formatters.ProductFormatter;
import partsapp.inventory.Inventory;
import partsapp.part.Part;
import partsapp.product.Product;

public class MainWindowController {
    @FXML private final ObservableList<PartFormatter> filteredParts = FXCollections.observableArrayList();
    @FXML private final ObservableList<ProductFormatter> filteredProducts = FXCollections.observableArrayList();

    public MainWindowController() {
        for (Part part: Inventory.getAllParts()) {
            filteredParts.add(new PartFormatter(part));
        }
        System.out.println(filteredParts.size());

        for (Product product: Inventory.getAllProducts()) {

            filteredProducts.add(new ProductFormatter(product));
        }
    }

    public void handleExit() {
        System.exit(0);
    }
}
