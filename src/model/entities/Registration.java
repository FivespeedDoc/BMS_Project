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

    public Registration(long ID, String attendeeID, String guestName, long BIN, long mealID, String drink, String seat) {
        this.ID = ID;
        this.AttendeeID = attendeeID;
        this.GuestName = guestName;
        this.BIN = BIN;
        this.MealID = mealID;
        this.Drink = drink;
        this.Seat = seat;
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
