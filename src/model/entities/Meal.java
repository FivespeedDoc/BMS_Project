package model.entities;

/**
 * <h3>The {@code Meal} Class</h3>
 * @author jimyang
 */
public class Meal {
    private final int BIN;
    private final int ID;

    private final String Name;

    private final String Type;

    private final double Price;

    private final String SpecialCuisine;

    public Meal(int BIN, int ID, String Name, String Type, double Price, String SpecialCuisine) {
        this.BIN = BIN;
        this.ID = ID;
        this.Name = Name;
        this.Type = Type;
        this.Price = Price;
        this.SpecialCuisine = SpecialCuisine;

    }

    public int getBIN() {
        return BIN;
    }

    public int getID() {
        return ID;
    }

    public String getName() {
        return Name;
    }

    public String getType() {
        return Type;
    }

    public double getPrice() {
        return Price;
    }

    public String getSpecialCuisine() {
        return SpecialCuisine;
    }
}
