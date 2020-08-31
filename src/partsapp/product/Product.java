package partsapp.product;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import partsapp.part.Part;

public class Product {
    private final ObservableList<Part> associatedParts;
    private int id;
    private String name;
    private double price;
    private int stock;
    private int min;
    private int max;

    public Product(int id, String name, double price, int stock, int min, int max) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;

        associatedParts = FXCollections.observableArrayList();
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getPrice() {
        return price;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public int getStock() {
        return stock;
    }

    public void setMin(int min) {
        this.min = min;
    }

    public int getMin() {
        return min;
    }

    public void setMax(int max) {
        this.max = max;
    }

    public int getMax() {
        return max;
    }

    public void addAssociatedPart(Part part) {
        associatedParts.add(part);
    }

    public ObservableList<Part> getAllAssociatedParts() {
        return associatedParts;
    }

    public boolean deleteAssociatedPart(Part selectedAssociatedPart) {
        boolean selectedPartInList = associatedParts.contains(selectedAssociatedPart);

        if (selectedPartInList) {
            associatedParts.remove(selectedAssociatedPart);
        }

        return selectedPartInList;
    }
}
