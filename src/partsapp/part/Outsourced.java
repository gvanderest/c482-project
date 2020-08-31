package partsapp.part;

public class Outsourced extends Part {
    private String companyName;

    /**
     * Constructor for the Outsourced part.
     *
     * @param id part id
     * @param name part name
     * @param price part price
     * @param stock part stock level
     * @param min part minimum stock level
     * @param max part maximum stock level
     */
    public Outsourced(int id, String name, double price, int stock, int min, int max) {
        super(id, name, price, stock, min, max);

        this.companyName = "";
    }

    /**
     * Change the outsourced part's company name.
     *
     * @param companyName to set
     */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    /**
     * Retrieve the outsourced part's company name.
     *
     * @return part's company name
     */
    public String getCompanyName() {
        return companyName;
    }
}
