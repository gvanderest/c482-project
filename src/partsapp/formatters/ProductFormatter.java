package partsapp.formatters;

import javafx.beans.property.SimpleIntegerProperty;
import partsapp.product.Product;

import java.text.NumberFormat;

public class ProductFormatter {
    private static final NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance();

    /**
     * Internal Product object which is wrapped by this class for formatting in TableViews.
     */
    private Product product;

    /**
     * Constructor to wrap Product in TableView formatter model.
     * @param product to wrap
     */
    public ProductFormatter(Product product) {
        this.product = product;
    }

    /**
     * Retrieve internal Product object.
     * @return raw product
     */
    public Product getProduct() {
        return product;
    }

    /**
     * Set the internal Product object.
     * @param product to store internally
     */
    public void setProduct(Product product) {
        this.product = product;
    }

    /**
     * Get formatted ID field.
     * @return part ID
     */
    public int getId() {
        return product.getId();
    }

    /**
     * Get formatted name field.
     * @return part name
     */
    public String getName() {
        return product.getName();
    }

    /**
     * Get formatted stock field.
     * @return part stock level
     */
    public int getStock() {
        return product.getStock();
    }

    /**
     * Get formatted price field.
     * @return part price, formatted as currency
     */
    public String getPrice() {
        return currencyFormatter.format(product.getPrice());
    }
}
