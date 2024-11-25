package model.entities;

/**
 * <h3>The {@code HashedPassword} Class</h3>
 * @author jimyang
 * @author FrankYang0610
 */
public final class HashedPasswordAndSalt {
    private final String hashedPassword; // don't need to use char[] here, because the password is already hashed with salt.

    private final String hashedSalt;

    public HashedPasswordAndSalt(String hashedPassword, String hashedSalt) {
        this.hashedPassword = hashedPassword;
        this.hashedSalt = hashedSalt;
    }

    public String getHashedPassword() {
        return hashedPassword;
    }

    public String getHashedSalt() {
        return hashedSalt;
    }
}
