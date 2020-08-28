package partsapp.formatters;

import javafx.beans.property.SimpleIntegerProperty;
import partsapp.product.Product;

public class ProductFormatter {
    private Product product;

    public ProductFormatter(Product product) {
        this.product = product;
    }

    public SimpleIntegerProperty getId() {
        return new SimpleIntegerProperty(product.getId());
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}
