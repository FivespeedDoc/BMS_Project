package model.entities;

public class Registration {
    private int ID;
    private String AttendeeID;
    private String GuestName;
    private int BIN;
    private String MealID;
    private String Drink;
    private String Seat;

    public Registration(int ID, String AttendeeID, String GuestName, int BIN, String mealID, String Drink, String Seat) {
        this.ID = ID;
        this.AttendeeID = AttendeeID;
        this.GuestName = GuestName;
        this.BIN = BIN;
        this.MealID = mealID;
        this.Drink = Drink;
        this.Seat = Seat;
    }
}
