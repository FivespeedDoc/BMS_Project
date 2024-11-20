package model.entities;

/**
 * @author jimyang
 */

public class AttendeeAccount {
    private String ID;
    private String Password;
    private String Salt;
    private String Name;
    private String Type;
    private int MobileNumber;
    private String Organization;

    public AttendeeAccount(String ID, String Password, String Salt, String Name, String Type, int MobileNumber, String Organization) {
        this.ID = ID;
        this.Password = Password;
        this.Salt = Salt;
        this.Name = Name;
        this.Type = Type;
        this.MobileNumber = MobileNumber;
        this.Organization = Organization;
    }

    public AttendeeAccount(String ID, HashedPassword hashedPassword, String Name, String Type, int MobileNumber, String Organization) {
        this.ID = ID;
        this.Password = hashedPassword.hashedPassword;
        this.Salt = hashedPassword.salt;
        this.Name = Name;
        this.Type = Type;
        this.MobileNumber = MobileNumber;
        this.Organization = Organization;
    }

    public String getID() {
        return ID;
    }

    public String getPassword() {
        return Password;
    }

    public String getSalt() {
        return Salt;
    }

    public String getName() {
        return Name;
    }

    public String getType() {
        return Type;
    }

    public int getMobileNumber() {
        return MobileNumber;
    }

    public String getOrganization() {
        return Organization;
    }
}
