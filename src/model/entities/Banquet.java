package model.entities;
import java.sql.Date;

/**
 * <h3>The {@code Banquet} Class</h3>
 * @author jimyang
 */
public class Banquet {
    private int BIN; // should we use long here?

    private String Name;

    private Date DateTime; // should we use date here?

    private String Address;

    private String Location;

    private String ContactStaffName;

    private boolean Available;

    private int Quota;

    public Banquet(int BIN, String Name, Date DateTime, String Address, String Location, String ContactStaffName, boolean Available, int Quota) {
        this.BIN = BIN;
        this.Name = Name;
        this.DateTime = DateTime;
        this.Address = Address;
        this.Location = Location;
        this.ContactStaffName = ContactStaffName;
        this.Available = Available;
        this.Quota = Quota;
    }

    public int getBIN() {
        return BIN;
    }

    public String getName() {
        return Name;
    }

    public Date getDateTime() {
        return DateTime;
    }

    public String getAddress() {
        return Address;
    }

    public String getLocation() {
        return Location;
    }

    public String getContactStaffName() {
        return ContactStaffName;
    }

    public boolean isAvailable() {
        return Available;
    }

    public int getQuota() {
        return Quota;
    }
}
