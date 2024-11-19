package model.entities;

/**
 * @author jimyang
 */

public class Attendee_Accounts {
    private int ID;
    private String Password;
    private String Salt;
    private String Name;
    private String Type;
    private int MobileNumber;
    private String Organization;

    public Attendee_Accounts(int ID, String Password, String Salt, String Name, String Type, int MobileNumber, String Organization) {
        this.ID = ID;
        this.Password = Password;
        this.Salt = Salt;
        this.Name = Name;
        this.Type = Type;
        this.MobileNumber = MobileNumber;
        this.Organization = Organization;
    }

    public Attendee_Accounts(int ID, HashedPassword hashedPassword, String Name, String Type, int MobileNumber, String Organization) {
        this.ID = ID;
        this.Password = hashedPassword.hashedPassword;
        this.Salt = hashedPassword.salt;
        this.Name = Name;
        this.Type = Type;
        this.MobileNumber = MobileNumber;
        this.Organization = Organization;
    }

}
