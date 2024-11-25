package model.entities;

/**
 * <h3>The {@code AttendeeAccount} Class</h3>
 * @author jimyang
 */
public final class AttendeeAccount {
    private final String ID;

    private final HashedPasswordAndSalt hashedPasswordAndSalt;

    private final String Name;

    private final String Address;

    private final String Type;

    private final long MobileNo;

    private final String Organization;

    public AttendeeAccount(String ID, HashedPasswordAndSalt hashedPasswordAndSalt, String name, String address, String type, long mobileNo, String organization) {
        this.ID = ID;
        this.hashedPasswordAndSalt = hashedPasswordAndSalt;
        this.Name = name;
        this.Address = address;
        this.Type = type;
        this.MobileNo = mobileNo;
        this.Organization = organization;
    }

    public String getID() {
        return ID;
    }

    public HashedPasswordAndSalt getHashedPasswordAndSalt() {
        return hashedPasswordAndSalt;
    }

    public String getName() {
        return Name;
    }

    public String getType() {
        return Type;
    }

    public long getMobileNo() {
        return MobileNo;
    }

    public String getOrganization() {
        return Organization;
    }

    public String getAddress() {
        return Address;
    }
}
