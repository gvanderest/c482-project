package partsapp.windows.products;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import partsapp.formatters.PartFormatter;
import partsapp.inventory.Inventory;
import partsapp.part.Part;
import partsapp.product.Product;
import partsapp.windows.main.MainWindow;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * Window controller for adding or modifying products.
 */
public class ProductWindow implements Initializable {
    /**
     * States that the form can be in.
     */
    private enum FormMode {
        ADD,
        MODIFY,
    }

    /**
     * Visible title in top left of window which changes based on adding or modifying.
     */
    @FXML private Label title;

    /**
     * Search field for filtering available parts.
     */
    @FXML private TextField partSearchField;

    /**
     * Product ID field.
     */
    @FXML private TextField productIdField;

    /**
     * Product Name field.
     */
    @FXML private TextField productNameField;

    /**
     * Product stock level field.
     */
    @FXML private TextField productStockField;

    /**
     * Product price field.
     */
    @FXML private TextField productPriceField;

    /**
     * Product minimum stock level field.
     */
    @FXML private TextField productMinField;

    /**
     * Product maximum stock level field.
     */
    @FXML private TextField productMaxField;

    /**
     * TableView for showing filtered available parts.
     */
    @FXML private TableView<PartFormatter> availablePartsTable;

    /**
     * TableView for showing associated parts used by the product.
     */
    @FXML private TableView<PartFormatter> usedPartsTable;

    /**
     * Stored list of the parts associated with the product.
     */
    private final ObservableList<PartFormatter> usedParts = FXCollections.observableArrayList();

    /**
     * Stored list of the available parts filtered by search to add to the product associations.
     */
    private final ObservableList<PartFormatter> filteredAvailableParts = FXCollections.observableArrayList();

    /**
     * Reference to the calling window that created this window.
     */
    private MainWindow mainWindow;

    /**
     * State of add or edit to inform how to format and validate the data.
     */
    private FormMode formMode;

    /**
     * Optional storage for the product that was provided for modification.
     */
    private Product productBeingModified;

    /**
     * Associate this window with the calling main window for message passing.
     *
     * @param mainWindow bound to this window
     */
    public void setMainWindow(MainWindow mainWindow) {
        this.mainWindow = mainWindow;

        setAddMode();
        refreshForm();
    }

    /**
     * Change the form's mode to 'add' a product.
     */
    public void setAddMode() {
        title.setText("Add Product");
        formMode = FormMode.ADD;
        productBeingModified = null;
        productIdField.setText("Auto Gen- Disabled");
        refreshForm();
    }

    /**
     * Change the form's mode to 'modify' a product.
     *
     * @param selectedProduct to modify
     */
    public void setModifyMode(Product selectedProduct) {
        title.setText("Modify Product");
        formMode = FormMode.MODIFY;
        productBeingModified = selectedProduct;

        // Extract values to populate the form.
        populateFormFromProduct(selectedProduct);

        refreshForm();
    }

    /**
     * Process the form based on the user clicking save.
     */
    public void handleSaveButtonClick() {
        Product newProduct;
        try {
            newProduct = getProductFromFormInputs();
        } catch (Exception error) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText(error.getMessage());
            alert.show();
            return;
        }

        // Create a new inventory entry.
        if (formMode == FormMode.ADD) {
            Inventory.addProduct(newProduct);

            mainWindow.handleProductAdded(newProduct);

            // Update the inventory.
        } else {
            // Find existing product which matches and replace.
            Product existingProduct = Inventory.lookupProduct(newProduct.getId());
            int productIndex = Inventory.getAllProducts().indexOf(existingProduct);
            Inventory.updateProduct(productIndex, newProduct);

            mainWindow.handleProductModified(newProduct);
        }
    }

    /**
     * Close the form.
     */
    public void handleCancelButtonClick() {
        mainWindow.handleProductWindowClose();
    }

    /**
     * Initialize inventory data for window to use.
     *
     * @param url            for resolving relative paths
     * @param resourceBundle context for controller
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        refreshForm();
    }

    /**
     * Update the form visuals based on the values in the class.
     */
    private void refreshForm() {
        refreshAvailableParts();
    }

    /**
     * Retrieve the list of parts from the inventory and populate local list.
     * Filtering is performed if there is a search parameter filled.
     */
    private void refreshAvailableParts() {
        filteredAvailableParts.clear();

        // Use the searched list or full list of parts.
        ObservableList<Part> sourceParts;
        String partSearchString = partSearchField.getText();
        if (partSearchString.isEmpty()) {
            sourceParts = Inventory.getAllParts();
        } else {
            // Attempt to convert to an integer, for an ID search.
            try {
                int partId = Integer.parseInt(partSearchString.trim());
                sourceParts = FXCollections.observableArrayList();

                Part foundPart = Inventory.lookupPart(partId);
                if (foundPart != null) {
                    sourceParts.add(foundPart);
                }

                // Fall back to a string search.
            } catch (Exception e) {
                sourceParts = Inventory.lookupPart(partSearchString);
            }
        }

        // Wrap parts in a formatter class for TableView display.
        for (Part part : sourceParts) {
            filteredAvailableParts.add(new PartFormatter(part));
        }

        availablePartsTable.setItems(filteredAvailableParts);
        availablePartsTable.refresh();

        // If only one available part is returned, select it as per rubric requirements
        if (availablePartsTable.getItems().size() == 1) {
            availablePartsTable.getSelectionModel().selectFirst();
        }
    }


    /**
     * Convert form inputs into product creation.
     *
     * @return product based on form values
     * @throws Exception if a validation error occurs
     */
    private Product getProductFromFormInputs() throws Exception {
        // Perform form validation.
        List<String> errors = validateForm();
        if (!errors.isEmpty()) {
            throw new Exception(errors.get(0));
        }

        // Extract field values.
        int id;
        if (formMode == FormMode.ADD) {
            id = Inventory.getNextProductId();
        } else {
            id = productBeingModified.getId();
        }
        String name = productNameField.getText().strip();
        double price = Double.parseDouble(productPriceField.getText());
        int stock = Integer.parseInt(productStockField.getText());
        int min = Integer.parseInt(productMinField.getText());
        int max = Integer.parseInt(productMaxField.getText());

        Product product = new Product(id, name, price, stock, min, max);

        // Populate with the selected parts used.
        for (PartFormatter usedPart : usedParts) {
            product.addAssociatedPart(usedPart.getPart());
        }

        return product;

    }

    /**
     * Validate that the data making up the product is valid.
     * <p>
     * TODO: In a future version, add functionality to sum up the parts that make up the product and ensure that
     * the product pricing is greater than or equal to the parts.  This will be an added protection to ensure the
     * sale price does not cause the store a loss based on manually input data.
     * </p>
     *
     * @return list of error message strings
     */
    private List<String> validateForm() {
        ArrayList<String> errors = new ArrayList<>();

        if (productNameField.getText().isEmpty()) {
            errors.add("Product name is required.");
        }

        // Validate min.
        int min = 0;
        try {
            min = Integer.parseInt(productMinField.getText());
            if (min < 0) {
                errors.add("Minimum stock value must be a positive whole number.");
            }
        } catch (Exception e) {
            errors.add("Minimum stock value must be a positive whole number.");
        }

        // Validate max.
        int max = 0;
        try {
            max = Integer.parseInt(productMaxField.getText());
            if (max < 0) {
                errors.add("Maximum stock value must be a positive whole number.");
            }
            if (max < min) {
                errors.add("Maximum stock value must be equal to or larger than minimum stock value.");
            }
        } catch (Exception e) {
            errors.add("Maximum stock value must be a positive whole number.");
        }

        // Validate stock value.
        int stock = 0;
        try {
            stock = Integer.parseInt(productStockField.getText());
        } catch (Exception e) {
            errors.add("Stock level must be a valid positive whole number.");
        }
        if (stock < min || stock > max) {
            errors.add("Stock level must be within the minimum and maximum stock values.");
        }

        // Validate price.
        try {
            double price = Double.parseDouble(productPriceField.getText());
            if (price < 0) {
                errors.add("Price must be a positive dollar amount.");
            }
        } catch (Exception e) {
            errors.add("Price must be a valid dollar amount.");
        }

        return errors;
    }

    /**
     * Update the form fields with the product data provided.
     *
     * @param product to update form with
     */
    private void populateFormFromProduct(Product product) {
        productIdField.setText(Integer.toString(product.getId()));
        productNameField.setText(product.getName());
        productMinField.setText(Integer.toString(product.getMin()));
        productMaxField.setText(Integer.toString(product.getMax()));
        productStockField.setText(Integer.toString(product.getStock()));
        productPriceField.setText(String.format("%.2f", product.getPrice()));

        usedParts.clear();
        for (Part usedPart : product.getAllAssociatedParts()) {
            usedParts.add(new PartFormatter(usedPart));
        }
        usedPartsTable.setItems(usedParts);
        usedPartsTable.refresh();
    }

    /**
     * Move a part from being available to being used for the product.
     */
    public void handleAddPartButtonClick() {
        // Get the index in the parts table view.
        int partIndex = availablePartsTable.getSelectionModel().getSelectedIndex();

        // Show an error if no part selected.
        if (partIndex < 0) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("You must select an available part to add to the product.");
            alert.show();
            return;
        }

        // Copy the part over.
        usedParts.add(filteredAvailableParts.get(partIndex));
        usedPartsTable.setItems(usedParts);
        usedPartsTable.refresh();
    }

    /**
     * Remove a part from being used for the product.
     */
    public void handleRemovePartButtonClick() {
        // Get the index in the used parts table view.
        int usedPartIndex = usedPartsTable.getSelectionModel().getSelectedIndex();

        // Show an error if no part selected.
        if (usedPartIndex < 0) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("You must select a used part to remove it from the product.");
            alert.show();
            return;
        }

        // Confirm the customer wants to remove the part.
        Part partToDelete = usedParts.get(usedPartIndex).getPart();
        Alert deleteConfirm = new Alert(Alert.AlertType.CONFIRMATION);
        deleteConfirm.setContentText(String.format(
                "Are you sure you want to remove the associated part?\n\n%d - %s",
                partToDelete.getId(),
                partToDelete.getName()
        ));
        Optional<ButtonType> result = deleteConfirm.showAndWait();

        // Remove the part and refresh the table.
        if (result.isPresent() && result.get() == ButtonType.OK) {
            usedParts.remove(usedPartIndex);
            usedPartsTable.setItems(usedParts);
            usedPartsTable.refresh();
        }
    }

    /**
     * Handle a change in the parts search field to refresh the filtered table.
     */
    public void handlePartsSearchFieldChanged() {
        refreshForm();
    }
}

