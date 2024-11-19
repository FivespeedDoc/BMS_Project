package model.entities;

public class HashedPassword {
    public String hashedPassword;
    public String salt;

    public HashedPassword(String hashedPassword, String salt) {
        this.hashedPassword = hashedPassword;
        this.salt = salt;
    }
}
