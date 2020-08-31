package partsapp.part;

public class InHouse extends Part {
    /**
     * The part's machine identifier.
     */
    private int machineId;

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
    public InHouse(int id, String name, double price, int stock, int min, int max) {
        super(id, name, price, stock, min, max);
    }

    /**
     * Change the part's in-house machine identifier.
     *
     * @param machineId of the in-source machine used
     */
    public void setMachineId(int machineId) {
        this.machineId = machineId;
    }

    /**
     * Retrieve the in-house machine identifier.
     *
     * @return the in-house machine identifier
     */
    public int getMachineId() {
        return machineId;
    }
}
