package partsapp.product;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import partsapp.part.Part;

public class Product {
    /**
     * List of parts which the product is made of.
     */
    private final ObservableList<Part> associatedParts;

    /**
     * Primary identifier for the product.
     */
    private int id;

    /**
     * Name of the product.
     */
    private String name;

    /**
     * Unit price of the product.
     */
    private double price;

    /**
     * Stock level of the product in inventory.
     */
    private int stock;

    /**
     * Minimum stock level of the product in inventory.
     */
    private int min;

    /**
     * Maximum stock level of the product in inventory.
     */
    private int max;

    /**
     * Constructor to initialize the product.
     *
     * @param id product identifier
     * @param name product name
     * @param price product unit price
     * @param stock product stock level
     * @param min minimum product stock level
     * @param max maximum product stock level
     */
    public Product(int id, String name, double price, int stock, int min, int max) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;

        associatedParts = FXCollections.observableArrayList();
    }

    /**
     * Set the product identifier.
     *
     * @param id product identifier
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Get the product identifier.
     *
     * @return product identifier
     */
    public int getId() {
        return id;
    }

    /**
     * Set the product name.
     *
     * @param name product name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Get the product name.
     *
     * @return product name
     */
    public String getName() {
        return name;
    }

    /**
     * Set the product unit price.
     *
     * @param price product unit price
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * Get the product unit price.
     *
     * @return product unit price
     */
    public double getPrice() {
        return price;
    }

    /**
     * Set the product stock level.
     *
     * @param stock product stock level
     */
    public void setStock(int stock) {
        this.stock = stock;
    }

    /**
     * Get the product stock level.
     *
     * @return product stock level
     */
    public int getStock() {
        return stock;
    }

    /**
     * Set product minimum stock level.
     *
     * @param min product minimum stock level
     */
    public void setMin(int min) {
        this.min = min;
    }

    /**
     * Get the product minimum stock level.
     *
     * @return product minimum stock level
     */
    public int getMin() {
        return min;
    }

    /**
     * Set the product maximum stock level.
     *
     * @param max product maximum stock level
     */
    public void setMax(int max) {
        this.max = max;
    }

    /**
     * Get the product maximum stock level.
     *
     * @return product maximum stock level
     */
    public int getMax() {
        return max;
    }

    /**
     * Add a part to be associated with the product.
     *
     * @param part to associate
     */
    public void addAssociatedPart(Part part) {
        associatedParts.add(part);
    }

    /**
     * Retrieve all the parts associated with the product.
     *
     * @return list of associated parts
     */
    public ObservableList<Part> getAllAssociatedParts() {
        return associatedParts;
    }

    /**
     * Remove an associated part from the product.
     *
     * @param selectedAssociatedPart part to be removed
     * @return true if successfully deleted, false otherwise
     */
    public boolean deleteAssociatedPart(Part selectedAssociatedPart) {
        boolean selectedPartInList = associatedParts.contains(selectedAssociatedPart);

        if (selectedPartInList) {
            associatedParts.remove(selectedAssociatedPart);
        }

        return selectedPartInList;
    }
}
