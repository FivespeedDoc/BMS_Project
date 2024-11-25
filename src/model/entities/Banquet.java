package model.entities;
import java.sql.Date;
import java.sql.Timestamp;

/**
 * <h3>The {@code Banquet} Class</h3>
 * @author jimyang
 */
public final class Banquet {
    private final long BIN; // it is safer to use long here.

    private final String Name;

    private final Timestamp DateTime;

    private final String Address;

    private final String Location;

    private final String ContactStaffName;

    private final char Available; // 'Y' or 'N, Stored as CHAR'

    private final int Quota;

    public Banquet(long BIN, String name, Timestamp dateTime, String address, String location, String contactStaffName, char available, int quota) {
        this.BIN = BIN;
        this.Name = name;
        this.DateTime = dateTime;
        this.Address = address;
        this.Location = location;
        this.ContactStaffName = contactStaffName;
        this.Available = available;
        this.Quota = quota;
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
