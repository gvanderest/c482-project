package partsapp.formatters;

import partsapp.part.Part;

import java.text.NumberFormat;

public class PartFormatter {
    /**
     * Number formatter used for the currency values, such as price.
     */
    private static final NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance();

    /**
     * Internal Part object which is wrapped by this class for formatting in TableViews.
     */
    private Part part;

    /**
     * Constructor to wrap Part in TableView formatter model.
     * @param part to wrap
     */
    public PartFormatter(Part part) {
        this.part = part;
    }

    /**
     * Retrieve internal Part object.
     * @return raw part
     */
    public Part getPart() {
        return part;
    }

    /**
     * Set the internal Part object.
     * @param part to store internally
     */
    public void setPart(Part part) {
        this.part = part;
    }

    /**
     * Get formatted ID field.
     * @return part ID
     */
    public int getId() {
        return part.getId();
    }

    /**
     * Get formatted name field.
     * @return part name
     */
    public String getName() {
        return part.getName();
    }

    /**
     * Get formatted stock field.
     * @return part stock level
     */
    public int getStock() {
        return part.getStock();
    }

    /**
     * Get formatted price field.
     * @return part price, formatted as currency
     */
    public String getPrice() {
        return currencyFormatter.format(part.getPrice());
    }
}
