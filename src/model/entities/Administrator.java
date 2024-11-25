package model.entities;

/**
 * <h3>The {@code Administrator} Class</h3>
 * @author jimyang
 * @author FrankYang0610
 */
public final class Administrator {
    private final String ID;

    private final HashedPasswordAndSalt hashedPasswordAndSalt;

    public Administrator(String id, HashedPasswordAndSalt hashedPasswordAndSalt) {
        this.ID = id;
        this.hashedPasswordAndSalt = hashedPasswordAndSalt;
    }

    public String getID() {
        return ID;
    }

    public HashedPasswordAndSalt getHashedPassword() {
        return hashedPasswordAndSalt;
    }
}
