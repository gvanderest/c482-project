package partsapp.windows.parts;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import partsapp.inventory.Inventory;
import partsapp.part.InHouse;
import partsapp.part.Outsourced;
import partsapp.part.Part;
import partsapp.windows.main.MainWindow;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class PartWindow implements Initializable {
    @FXML private Label title;

    @FXML private RadioButton partInHouseRadio;
    @FXML private RadioButton partOutsourcedRadio;

    @FXML private TextField partIdField;
    @FXML private TextField partNameField;
    @FXML private TextField partStockField;
    @FXML private TextField partPriceField;
    @FXML private TextField partMinField;
    @FXML private TextField partMaxField;
    @FXML private TextField partMachineIdField;
    @FXML private TextField partCompanyNameField;

    @FXML private HBox partInHouseSection;
    @FXML private HBox partOutsourcedSection;

    private MainWindow mainWindow;
    private FormMode formMode;
    private Part partBeingModified;

    private enum FormMode {
        ADD,
        MODIFY,
    }

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
     * Change the form's mode to 'add' a part.
     */
    public void setAddMode() {
        title.setText("Add Part");
        formMode = FormMode.ADD;
        partBeingModified = null;
        partIdField.setText("Auto Gen- Disabled");
        refreshForm();
    }

    /**
     * Change the form's mode to 'modify' a part.
     *
     * @param selectedPart to modify
     */
    public void setModifyMode(Part selectedPart) {
        title.setText("Modify Part");
        formMode = FormMode.MODIFY;
        partBeingModified = selectedPart;

        // Extract values to populate the form.
        populateFormFromPart(selectedPart);

        refreshForm();
    }

    /**
     * Process the form based on the user clicking save.
     */
    public void handleSaveButtonClick() {
        Part newPart;
        try {
            newPart = getPartFromFormInputs();
        } catch (Exception error) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText(error.getMessage());
            alert.show();
            return;
        }

        // Create a new inventory entry.
        if (formMode == FormMode.ADD) {
            Inventory.addPart(newPart);

            mainWindow.handlePartAdded(newPart);

        // Update the inventory.
        } else {
            // Find existing part which matches and replace.
            Part existingPart = Inventory.lookupPart(newPart.getId());
            int partIndex = Inventory.getAllParts().indexOf(existingPart);
            Inventory.updatePart(partIndex, newPart);

            mainWindow.handlePartModified(newPart);
        }
    }

    /**
     * Close the form.
     */
    public void handleCancelButtonClick() {
        mainWindow.handlePartWindowClose();
    }

    /**
     * Initialize inventory data for window to use.
     *
     * @param url for resolving relative paths
     * @param resourceBundle context for controller
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Link the radio buttons to ensure they are mutually exclusive.
        ToggleGroup partTypeGroup = new ToggleGroup();
        partInHouseRadio.setToggleGroup(partTypeGroup);
        partOutsourcedRadio.setToggleGroup(partTypeGroup);

        // Default to in-house being selected.
        partInHouseRadio.setSelected(true);
    }

    /**
     * Update the form visuals based on the values in the class.
     */
    private void refreshForm() {
        partInHouseSection.setVisible(partIsInHouse());
        partInHouseSection.setManaged(partIsInHouse());

        partOutsourcedSection.setVisible(partIsOutsourced());
        partOutsourcedSection.setManaged(partIsOutsourced());

        // Clear the values for outsourced parts.
        if (!partIsOutsourced()) {
            partCompanyNameField.setText("");
        }

        // Clear the values for in-house parts.
        if (!partIsInHouse()) {
            partMachineIdField.setText("");
        }
    }

    /**
     * Convert form inputs into part creation.
     *
     * @return part based on form values
     * @throws Exception if a validation error occurs
     */
    private Part getPartFromFormInputs() throws Exception {
        // Perform form validation.
        List<String> errors = validateForm();
        if (!errors.isEmpty()) {
            throw new Exception(errors.get(0));
        }

        // Extract field values.
        int id;
        if (formMode == FormMode.ADD) {
            id = Inventory.getNextPartId();
        } else {
            id = partBeingModified.getId();
        }
        String name = partNameField.getText().strip();
        double price = Double.parseDouble(partPriceField.getText());
        int stock = Integer.parseInt(partStockField.getText());
        int min = Integer.parseInt(partMinField.getText());
        int max = Integer.parseInt(partMaxField.getText());

        // Instantiate the appropriate part class based on form selections.
        Part part;
        if (partOutsourcedRadio.isSelected()) {
            Outsourced outsourced = new Outsourced(id, name, price, stock, min, max);
            outsourced.setCompanyName(partCompanyNameField.getText().strip());
            part = outsourced;
        } else {
            InHouse inHouse = new InHouse(id, name, price, stock, min, max);
            inHouse.setMachineId(Integer.parseInt(partMachineIdField.getText().strip()));
            part = inHouse;
        }

        return part;
    }

    /**
     * Validate that the data making up the part is valid.
     *
     * @return list of error message strings
     */
    private List<String> validateForm() {
        ArrayList<String> errors = new ArrayList<>();

        if (partNameField.getText().isEmpty()) {
            errors.add("Part name is required.");
        }

        // Validate min.
        int min = 0;
        try {
            min = Integer.parseInt(partMinField.getText());
            if (min < 0) {
                errors.add("Minimum stock value must be a positive whole number.");
            }
        } catch (Exception e) {
            errors.add("Minimum stock value must be a positive whole number.");
        }

        // Validate max.
        int max = 0;
        try {
            max = Integer.parseInt(partMaxField.getText());
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
            stock = Integer.parseInt(partStockField.getText());
        } catch (Exception e) {
            errors.add("Stock level must be a valid positive whole number.");
        }
        if (stock < min || stock > max) {
            errors.add("Stock level must be within the minimum and maximum stock values.");
        }

        // Validate price.
        try {
            double price = Double.parseDouble(partPriceField.getText());
            if (price < 0) {
                errors.add("Price must be a positive dollar amount.");
            }
        } catch (Exception e) {
            errors.add("Price must be a valid dollar amount.");
        }

        // Perform validation based on part type.
        if (partIsInHouse()) {
            // Validate Machine ID.
            try {
                int machineId = Integer.parseInt(partMachineIdField.getText());
                if (machineId < 0) {
                    errors.add("Machine ID must be a positive whole number.");
                }
            } catch (Exception e) {
                errors.add("Machine ID must be a valid whole number.");
            }
        } else {
            // Validate company name.
            if (partCompanyNameField.getText().isEmpty()) {
                errors.add("Company name is required for outsourced parts.");
            }
        }

        return errors;
    }

    /**
     * Helper function for determining part being in-house.
     * @return true if in-house, false otherwise.
     */
    private boolean partIsInHouse() {
        return partInHouseRadio.isSelected();
    }

    /**
     * Helper function for determining part being outsourced;
     * @return true if outsourced, false otherwise
     */
    private boolean partIsOutsourced() {
        return !partIsInHouse();
    }

    /**
     * Event handler for part changing between in-house and outsourced, triggering a form redraw.
     */
    public void handlePartTypeChanged() {
        refreshForm();
    }

    /**
     * Update the form fields with the part data provided.
     *
     * @param part to update form with
     */
    private void populateFormFromPart(Part part) {
        partIdField.setText(Integer.toString(part.getId()));
        partNameField.setText(part.getName());
        partMinField.setText(Integer.toString(part.getMin()));
        partMaxField.setText(Integer.toString(part.getMax()));
        partStockField.setText(Integer.toString(part.getStock()));
        partPriceField.setText(String.format("%.2f", part.getPrice()));

        if (part instanceof InHouse) {
            partInHouseRadio.setSelected(true);
            partMachineIdField.setText(Integer.toString(((InHouse) part).getMachineId()));
        } else {
            partOutsourcedRadio.setSelected(true);
            partCompanyNameField.setText(((Outsourced) part).getCompanyName());
        }
    }
}

