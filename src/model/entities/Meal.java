package model.entities;

/**
 * <h3>The {@code Meal} Class</h3>
 * @author jimyang
 */
public final class Meal {
    private final long BIN;
    private final long ID;

    private final String Name;

    private final String Type;

    private final double Price;

    private final String SpecialCuisine;

    public Meal(long BIN, long ID, String name, String type, double price, String specialCuisine) {
        this.BIN = BIN;
        this.ID = ID;
        this.Name = name;
        this.Type = type;
        this.Price = price;
        this.SpecialCuisine = specialCuisine;
    }

    public long getBIN() {
        return BIN;
    }

    public long getID() {
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
