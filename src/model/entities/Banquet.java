package model.entities;
import java.sql.Date;
import java.sql.Timestamp;

/**
 * <h3>The {@code Banquet} Class</h3>
 * @author jimyang
 */
public class Banquet {
    private final long BIN; // it is safer to use long here.

    private final String Name;

    private final Timestamp DateTime;

    private final String Address;

    private final String Location;

    private final String ContactStaffName;

    private final char Available; // 'Y' or 'N'

    private final int Quota;

    public Banquet(long BIN, String Name, Timestamp DateTime, String Address, String Location, String ContactStaffName, char Available, int Quota) {
        this.BIN = BIN;
        this.Name = Name;
        this.DateTime = DateTime;
        this.Address = Address;
        this.Location = Location;
        this.ContactStaffName = ContactStaffName;
        this.Available = Available;
        this.Quota = Quota;
    }

    public long getBIN() {
        return BIN;
    }

    public String getName() {
        return Name;
    }

    public Timestamp getDateTime() {
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

    public char isAvailable() {
        return Available;
    }

    public int getQuota() {
        return Quota;
    }
}
