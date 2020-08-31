package partsapp.windows.main;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import partsapp.formatters.PartFormatter;
import partsapp.formatters.ProductFormatter;
import partsapp.inventory.Inventory;
import partsapp.part.Part;
import partsapp.product.Product;
import partsapp.windows.parts.PartWindow;
import partsapp.windows.products.ProductWindow;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Window controller class for the starting window of the application.
 */
public class MainWindow implements Initializable {
    /**
     * List of search results for parts to populate parts table.
     */
    @FXML private final ObservableList<PartFormatter> filteredParts;

    /**
     * List of search results for parts to populate products table.
     */
    @FXML private final ObservableList<ProductFormatter> filteredProducts;

    /**
     * Search field for filtering parts.
     */
    @FXML private TextField partsSearchField;

    /**
     * Search field for filtering products.
     */
    @FXML private TextField productsSearchField;

    /**
     * Table for displaying search results of parts.
     */
    public TableView<PartFormatter> partsTable;

    /**
     * Table for displaying search results of products.
     */
    public TableView<ProductFormatter> productsTable;

    /**
     * JavaFX Stage reference for parts window.
     */
    private Stage partStage;

    /**
     * Controller reference for parts window.
     */
    private PartWindow partController;

    /**
     * JavaFX Stage reference for products window.
     */
    private Stage productStage;

    /**
     * Controller reference for products window.
     */
    private ProductWindow productController;

    /**
     * Default constructor, sets up containers for parts and products.
     */
    public MainWindow() {
        filteredParts = FXCollections.observableArrayList();
        filteredProducts = FXCollections.observableArrayList();
    }

    /**
     * Initialize inventory data for window to use.
     *
     * @param url for resolving relative paths
     * @param resourceBundle context for controller
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        refreshAllData();
    }

    /**
     * Retrieve the list of parts from the inventory and populate local list.
     * Filtering is performed if there is a search parameter filled.
     */
    private void refreshInventoryParts() {
        filteredParts.clear();

        // Use the searched list or full list of parts.
        ObservableList<Part> sourceParts;
        String partsSearchString = partsSearchField.getText();
        if (partsSearchString.isEmpty()) {
            sourceParts = Inventory.getAllParts();
        } else {
            // Attempt to convert to an integer, for an ID search.
            try {
                int partId = Integer.parseInt(partsSearchString.trim());
                sourceParts = FXCollections.observableArrayList();

                Part foundPart = Inventory.lookupPart(partId);
                if (foundPart != null) {
                    sourceParts.add(foundPart);
                }

                // Fall back to a string search.
            } catch (Exception e) {
                sourceParts = Inventory.lookupPart(partsSearchString);
            }
        }

        // Wrap parts in a formatter class for TableView display.
        for (Part part: sourceParts) {
            filteredParts.add(new PartFormatter(part));
        }

        partsTable.setItems(filteredParts);
        partsTable.refresh();
    }

    /**
     * Retrieve the list of products from the inventory and populate local list.
     * Filtering is performed if there is a search parameter filled.
     */
    private void refreshInventoryProducts() {
        filteredProducts.clear();

        // Use the searched list or full list of products.
        ObservableList<Product> sourceProducts;
        String productsSearchString = productsSearchField.getText();
        if (productsSearchString.isEmpty()) {
            sourceProducts = Inventory.getAllProducts();
        } else {
            // Attempt to convert to an integer, for an ID search.
            try {
                int productId = Integer.parseInt(productsSearchString.trim());
                sourceProducts = FXCollections.observableArrayList();

                Product foundProduct = Inventory.lookupProduct(productId);
                if (foundProduct != null) {
                    sourceProducts.add(foundProduct);
                }

            // Fall back to a string search.
            } catch (Exception e) {
                sourceProducts = Inventory.lookupProduct(productsSearchString);
            }
        }

        // Wrap products in a formatter class for TableView display.
        for (Product product: sourceProducts) {
            filteredProducts.add(new ProductFormatter(product));
        }

        productsTable.setItems(filteredProducts);
        productsTable.refresh();
    }

    /**
     * Refresh all of the data available for parts and products.
     */
    private void refreshAllData() {
        refreshInventoryParts();
        refreshInventoryProducts();
    }

    /**
     * Handle the exit button click and terminates the program.
     */
    public void handleExitButtonClick() {
        System.exit(0);
    }

    /**
     * Instantiate the add parts form.
     *
     * @throws IOException when unable to load template
     */
    public void handlePartsAddButtonClick() throws IOException {
        Stage stage = createOrFocusPartWindow();
        stage.setTitle("Add Part");
    }

    /**
     * Event handler for after the product is added.
     *
     * @param product that was added
     */
    public void handleProductAdded(Product product) {
        closeProductWindow();
        refreshAllData();
    }

    /**
     * Event handler for after the product is modified.
     *
     * @param product that was modified
     */
    public void handleProductModified(Product product) {
        closeProductWindow();
        refreshAllData();
    }

    /**
     * Event handler for after the part is added.
     *
     * @param part that was added
     */
    public void handlePartAdded(Part part) {
        closePartWindow();
        refreshAllData();
    }

    /**
     * Event handler for after the part is modified.
     *
     * @param part that was modified
     */
    public void handlePartModified(Part part) {
        closePartWindow();
        refreshAllData();
    }

    /**
     * Event handler for requesting the part window be closed by clicking the 'X'.
     */
    public void handlePartWindowCloseEvent(WindowEvent event) {
        event.consume();
        closePartWindow();
    }

    /**
     * Event handler for requesting the part window be closed.
     */
    public void handlePartWindowClose() {
        closePartWindow();
    }

    /**
     * Event handler for requesting the product window be closed.
     */
    public void handleProductWindowClose() {
        closeProductWindow();
    }

    /**
     * Close the part window and clear its reference.
     */
    private void closePartWindow() {
        partController = null;

        partStage.close();
        partStage = null;
    }

    /**
     * Close the product window and clear its reference.
     */
    private void closeProductWindow() {
        productController = null;

        productStage.close();
        productStage = null;
    }

    /**
     * Instantiate the modify parts form.
     *
     * @throws Exception when unable to open window
     */
    public void handlePartsModifyButtonClick() throws Exception {
        // Retrieve the selected part from inventory.
        int partIndex = partsTable.getSelectionModel().getSelectedIndex();

        // Show an error if no part selected.
        if (partIndex < 0) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("You must select a part to modify.");
            alert.show();
            return;
        }

        Part selectedPart = filteredParts.get(partIndex).getPart();

        // Setup the window/form.
        Stage stage = createOrFocusPartWindow();
        stage.setTitle("Modify Part");

        // Provide it to the form controller for modifying.
        partController.setModifyMode(selectedPart);
    }

    /**
     * Create a new version or reuse the existing version of the parts window.
     *
     * @return JavaFX stage for referencing the window
     * @throws IOException if unable to load the FXML layout file
     */
    private Stage createOrFocusPartWindow() throws IOException {
        if (partStage != null) {
            partStage.requestFocus();
            return partStage;
        }

        FXMLLoader addPartsLoader = new FXMLLoader(getClass().getResource("../parts/part_window.fxml"));
        Parent addPartsRoot = addPartsLoader.load();

        partController = addPartsLoader.getController();
        partController.setMainWindow(this);

        partStage = new Stage();
        partStage.setScene(new Scene(addPartsRoot));
        partStage.show();

        return partStage;
    }

    /**
     * Create a new version or reuse the existing version of the product window.
     *
     * @return JavaFX stage for referencing the window
     * @throws IOException if unable to load the FXML layout file
     */
    private Stage createOrFocusProductWindow() throws IOException {
        if (productStage != null) {
            productStage.requestFocus();
            return productStage;
        }

        FXMLLoader addProductsLoader = new FXMLLoader(getClass().getResource("../products/product_window.fxml"));
        Parent addProductsRoot = addProductsLoader.load();

        productController = addProductsLoader.getController();
        productController.setMainWindow(this);

        productStage = new Stage();
        productStage.setScene(new Scene(addProductsRoot));
        productStage.show();

        productStage.addEventFilter(javafx.stage.WindowEvent.WINDOW_CLOSE_REQUEST, this::handlePartWindowCloseEvent);

        return productStage;
    }

    /**
     * Event handler for the delete parts button, removing a part from the inventory.
     *
     * Note: A runtime error was encountered int the following method.  The error was caused by initially using the
     * selectedPartIndex value to call Inventory.getAllParts.get(selectedPartIndex), resulting in the incorrect
     * product being removed inventory if the customer was removing during a part search.  To remedy this issue,
     * the getAllParts() reference was replaced with the filteredParts variable used locally which the search is
     * correctly being applied against.
     */
    public void handlePartsDeleteButtonClick() {
        // Find requested part to delete.
        int selectedPartIndex = partsTable.getSelectionModel().getSelectedIndex();

        // Show error if no part selected.
        if (selectedPartIndex < 0) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("You must select a part to delete.");
            alert.show();
            return;
        }

        Part selectedPart = filteredParts.get(selectedPartIndex).getPart();

        // Delete the selected part and refresh list.
        boolean deleteResult = Inventory.deletePart(selectedPart);
        if (deleteResult) {
            refreshAllData();
        }
    }

    /**
     * Event handler to open the window for adding a product.
     *
     * @throws Exception if the window was unable to be instantiated
     */
    public void handleProductsAddButtonClick() throws Exception {
        Stage stage = createOrFocusProductWindow();
        stage.setTitle("Add Product");
    }

    /**
     * Instantiate the modify products form.
     *
     * @throws Exception when unable to open window
     */
    public void handleProductsModifyButtonClick() throws Exception {
        // Retrieve the selected product from inventory.
        int productIndex = productsTable.getSelectionModel().getSelectedIndex();

        // Show an error if no product selected.
        if (productIndex < 0) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("You must select a product to modify.");
            alert.show();
            return;
        }

        Product selectedProduct = filteredProducts.get(productIndex).getProduct();

        // Setup the window/form.
        Stage stage = createOrFocusProductWindow();
        stage.setTitle("Modify Product");

        // Provide it to the form controller for modifying.
        productController.setModifyMode(selectedProduct);
    }

    /**
     * Event handler for the product delete button to remove it from inventory.
     */
    public void handleProductsDeleteButtonClick() {
        // Find requested product to delete.
        int selectedProductIndex = productsTable.getSelectionModel().getSelectedIndex();

        // Show error if no product selected.
        if (selectedProductIndex < 0) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("You must select a product to delete.");
            alert.show();
            return;
        }

        Product selectedProduct = filteredProducts.get(selectedProductIndex).getProduct();

        // Delete the selected product and refresh list.
        boolean deleteResult = Inventory.deleteProduct(selectedProduct);
        if (deleteResult) {
            refreshAllData();
        }
    }

    /**
     * Handle a change in the parts search field to refresh the filtered table.
     */
    public void handlePartsSearchFieldChanged() {
        refreshAllData();
    }

    /**
     * Handle a change in the products search field to refresh the filtered table.
     */
    public void handleProductsSearchFieldChanged() {
        refreshAllData();
    }
}
