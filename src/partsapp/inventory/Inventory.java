package partsapp.inventory;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import partsapp.part.Part;
import partsapp.product.Product;

/**
 * Shared inventory class for tracking parts and products.
 *
 * TODO: In a future version of this project, create a database backing mechanism to persist data.
 */
public class Inventory {
    /**
     * List of all parts available within the inventory.
     */
    private static final ObservableList<Part> allParts = FXCollections.observableArrayList();

    /**
     * List of all the products available within the inventory.
     */
    private static final ObservableList<Product> allProducts = FXCollections.observableArrayList();

    /**
     * Current part ID, set to one value before the expected first value. (0 -> 1)
     */
    private static int currentPartId = 0;

    /**
     * Current product ID, set to one value before the expected first value. (999 -> 1000)
     */
    private static int currentProductId = 999;

    /**
     * Add a part to the store of parts.
     *
     * @param part to add
     */
    public static void addPart(Part part) {
        allParts.add(part);
    }

    /**
     * Add a product to the store of products.
     *
     * @param product to add
     */
    public static void addProduct(Product product) {
        allProducts.add(product);
    }

    /**
     * Retrieve a part by its part ID.
     *
     * @param id to search
     * @return part matching ID or null
     */
    public static Part lookupPart(int id) {
        for (Part part: allParts) {
            if (part.getId() == id) {
                return part;
            }
        }
        return null;
    }

    /**
     * Retrieve a product by its product ID.
     *
     * @param id to search
     * @return product matching ID or null
     */
    public static Product lookupProduct(int id) {
        for (Product product: allProducts) {
            if (product.getId() == id) {
                return product;
            }
        }
        return null;
    }

    /**
     * Retrieve parts by a string matching the name, case-insensitive.
     *
     * @param partName to search
     * @return list of parts partially matching the name
     */
    public static ObservableList<Part> lookupPart(String partName) {
        return allParts.filtered(p -> p.getName().toLowerCase().contains(partName.toLowerCase()));
    }

    /**
     * Retrieve products by a string matching the name, case-insensitive.
     *
     * @param productName to search
     * @return list of products partially matching the name
     */
    public static ObservableList<Product> lookupProduct(String productName) {
        return allProducts.filtered(p -> p.getName().toLowerCase().contains(productName.toLowerCase()));
    }

    /**
     * Update a part within the inventory with a new version.
     *
     * @param index of existing part
     * @param selectedPart to replace existing
     */
    public static void updatePart(int index, Part selectedPart) {
        allParts.set(index, selectedPart);
    }

    /**
     * Update a product within the inventory with a new version.
     *
     * @param index of existing part
     * @param selectedProduct to replace existing
     */
    public static void updateProduct(int index, Product selectedProduct) {
        allProducts.set(index, selectedProduct);
    }

    /**
     * Remove a part from the inventory.
     *
     * @param selectedPart to remove
     * @return true if successfully deleted, false otherwise
     */
    public static boolean deletePart(Part selectedPart) {
        boolean partInList = allParts.contains(selectedPart);
        if (partInList) {
            allParts.remove(selectedPart);
        }
        return partInList;
    }

    /**
     * Remove a product from the inventory.
     *
     * @param selectedProduct to remove
     * @return true if successfully deleted, false otherwise
     */
    public static boolean deleteProduct(Product selectedProduct) {
        boolean productInList = allProducts.contains(selectedProduct);
        if (productInList) {
            allProducts.remove(selectedProduct);
        }
        return productInList;
    }

    /**
     * Retrieve all of the parts in inventory.
     *
     * @return list of parts
     */
    public static ObservableList<Part> getAllParts() {
        return allParts;
    }

    /**
     * Retrieve all of the products in inventory.
     *
     * @return list of products
     */
    public static ObservableList<Product> getAllProducts() {
        return allProducts;
    }

    /**
     * Generator for next inventory ID for parts.
     *
     * @return part ID for use with new part
     */
    public static int getNextPartId() {
        currentPartId++;
        return currentPartId;
    }

    /**
     * Generator for next inventory ID for products.
     *
     * @return product ID for use with new product
     */
    public static int getNextProductId() {
        currentProductId++;
        return currentProductId;
    }
}
