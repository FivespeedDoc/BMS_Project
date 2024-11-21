package model.entities;

/**
 * <h3>The {@code Registration} Class</h3>
 * @author jimyang
 */
public class Registration {
    private final int ID;

    private final String AttendeeID;

    private final String GuestName;

    private final int BIN;

    private final String MealID;

    private final String Drink;

    private final String Seat;

    public Registration(int ID, String AttendeeID, String GuestName, int BIN, String mealID, String Drink, String Seat) {
        this.ID = ID;
        this.AttendeeID = AttendeeID;
        this.GuestName = GuestName;
        this.BIN = BIN;
        this.MealID = mealID;
        this.Drink = Drink;
        this.Seat = Seat;
    }

    public int getID() {
        return ID;
    }

    public String getAttendeeID() {
        return AttendeeID;
    }

    public String getGuestName() {
        return GuestName;
    }

    public int getBIN() {
        return BIN;
    }

    public String getMealID() {
        return MealID;
    }

    public String getDrink() {
        return Drink;
    }

    public String getSeat() {
        return Seat;
    }
}
