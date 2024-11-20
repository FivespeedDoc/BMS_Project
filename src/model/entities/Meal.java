package model.entities;

/**
 * <h3>The {@code Meal} Class</h3>
 * @author jimyang
 */
public class Meal {
    private int BIN;

    private int ID;

    private String Name;

    private String Type;

    private double Price;

    private String SpecialCuisine;

    public Meal(int BIN, int ID, String Name, String Type, double Price, String SpecialCuisine) {
        this.BIN = BIN;
        this.ID = ID;
        this.Name = Name;
        this.Type = Type;
        this.Price = Price;
        this.SpecialCuisine = SpecialCuisine;

    }
}
