package model.entities;

/**
 * <h3>The {@code AttendeeAccount} Class</h3>
 * @author jimyang
 */
public class AttendeeAccount {
    private final String ID;

    private final String Password;

    private final String Salt;

    private final String Name;

    private final String Type;

    private final int MobileNo;

    private final String Organization;

    public AttendeeAccount(String ID, String Password, String Salt, String Name, String Type, int MobileNo, String Organization) {
        this.ID = ID;
        this.Password = Password;
        this.Salt = Salt;
        this.Name = Name;
        this.Type = Type;
        this.MobileNo = MobileNo;
        this.Organization = Organization;
    }

    public AttendeeAccount(String ID, HashedPassword hashedPassword, String Name, String Type, int MobileNo, String Organization) {
        this.ID = ID;
        this.Password = hashedPassword.hashedPassword;
        this.Salt = hashedPassword.salt;
        this.Name = Name;
        this.Type = Type;
        this.MobileNo = MobileNo;
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

    public int getMobileNo() {
        return MobileNo;
    }

    public String getOrganization() {
        return Organization;
    }
}
