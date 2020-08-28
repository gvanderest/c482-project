package partsapp.formatters;

import partsapp.part.Part;

import java.text.NumberFormat;

public class PartFormatter {
    private static final NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance();

    private Part part;

    public PartFormatter(Part part) {
        this.part = part;
    }

    public Part getPart() {
        return part;
    }

    public void setPart(Part part) {
        this.part = part;
    }

    public int getId() {
        return part.getId();
    }

    public String getName() {
        return part.getName();
    }

    public int getStock() {
        return part.getStock();
    }

    public String getPrice() {
        return currencyFormatter.format(part.getPrice());

    }
}
