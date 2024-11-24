package model.entities;

/**
 * <h3>The {@code HashedPassword} Class</h3>
 * @author jimyang
 */
public final class HashedPassword {
    public String hashedPassword;

    public String salt;

    public HashedPassword(String hashedPassword, String salt) {
        this.hashedPassword = hashedPassword;
        this.salt = salt;
    }
}
