package model.entities;

/**
 * <h3>The {@code Administrator} Class</h3>
 * @author jimyang
 */
public class Administrator {
    private final String ID;

    private final String Password;

    // private final String Salt;

    public Administrator(String id, String password/*, String salt*/) {
        this.ID = id;
        this.Password = password;
        // this.Salt = salt;
    }

    public String getID() {
        return ID;
    }

    public String getPassword() {
        return Password;
    }

    //Might add back later, not used for now, password stored in plain text.
    /* public String getSalt() {
        return Salt;
    } */
}
