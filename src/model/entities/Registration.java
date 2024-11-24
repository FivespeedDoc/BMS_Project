package model.entities;

/**
 * <h3>The {@code Registration} Class</h3>
 * @author jimyang
 */
public final class Registration {
    private final long ID;

    private final String AttendeeID;

    private final String GuestName;

    private final long BIN;

    private final long MealID;

    private final String Drink;

    private final String Seat;

    public Registration(long ID, String AttendeeID, String GuestName, long BIN, long mealID, String Drink, String Seat) {
        this.ID = ID;
        this.AttendeeID = AttendeeID;
        this.GuestName = GuestName;
        this.BIN = BIN;
        this.MealID = mealID;
        this.Drink = Drink;
        this.Seat = Seat;
    }

    public long getID() {
        return ID;
    }

    public String getAttendeeID() {
        return AttendeeID;
    }

    public String getGuestName() {
        return GuestName;
    }

    public long getBIN() {
        return BIN;
    }

    public long getMealID() {
        return MealID;
    }

    public String getDrink() {
        return Drink;
    }

    public String getSeat() {
        return Seat;
    }
}
