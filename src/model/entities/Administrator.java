package model.entities;

/**
 * <h3>The {@code Administrator} Class</h3>
 * @author jimyang
 */
public class Administrator {
    private final int ID;

    private final String Password;

    private final String Salt;

    public Administrator(int id, String password, String salt) {
        this.ID = id;
        this.Password = password;
        this.Salt = salt;
    }

    public int getID() {
        return ID;
    }

    public String getPassword() {
        return Password;
    }

    public String getSalt() {
        return Salt;
    }
}
