package partsapp.formatters;

import javafx.beans.property.SimpleIntegerProperty;
import partsapp.part.Part;

public class PartFormatter {
    private Part part;

    public PartFormatter(Part part) {
        this.part = part;
    }

    public SimpleIntegerProperty getId() {
        return new SimpleIntegerProperty(part.getId());
    }

    public Part getPart() {
        return part;
    }

    public void setPart(Part part) {
        this.part = part;
    }
}
