package partsapp.inventory;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import partsapp.part.Part;
import partsapp.product.Product;

public class Inventory {
    @FXML private static final ObservableList<Part> allParts = FXCollections.observableArrayList();
    @FXML private static final ObservableList<Product> allProducts = FXCollections.observableArrayList();

    public static void addPart(Part part) {
        allParts.add(part);
    }

    public static void addProduct(Product product) {
        allProducts.add(product);
    }

    public static Part lookupPart(int id) {
        for (Part part: allParts) {
            if (part.getId() == id) {
                return part;
            }
        }
        return null;
    }

    public static Product lookupProduct(int id) {
        for (Product product: allProducts) {
            if (product.getId() == id) {
                return product;
            }
        }
        return null;
    }

    public static ObservableList<Part> lookupPart(String partName) {
        return allParts.filtered(p -> p.getName().toLowerCase().contains(partName.toLowerCase()));
    }

    public static ObservableList<Product> lookupProduct(String productName) {
        return allProducts.filtered(p -> p.getName().toLowerCase().contains(productName.toLowerCase()));
    }

    public static void updatePart(int index, Part selectedPart) {
        allParts.set(index, selectedPart);
    }

    public static void updateProduct(int index, Product selectedProduct) {
        allProducts.set(index, selectedProduct);
    }

    public static boolean deletePart(Part selectedPart) {
        boolean partInList = allParts.contains(selectedPart);
        if (partInList) {
            allParts.remove(selectedPart);
        }
        return partInList;
    }

    public static boolean deleteProduct(Product selectedProduct) {
        boolean productInList = allProducts.contains(selectedProduct);
        if (productInList) {
            allProducts.remove(selectedProduct);
        }
        return productInList;
    }

    public static ObservableList<Part> getAllParts() {
        return allParts;
    }

    public static ObservableList<Product> getAllProducts() {
        return allProducts;
    }
}
