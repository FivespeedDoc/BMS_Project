package service.managers;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

import model.ModelException;
import model.entities.HashedPasswordAndSalt;


/**
 * <h2> Password Manager Class </h2>
 * This class provides functionality for password cryptography encryption and verification.
 *
 * @author jimyang
 * @author FrankYang0610
 */
public final class PasswordManager {
    /**
     * <h3>Password Hashing Function</h3>
     * @param originalPassword the password, in plain string.
     * @return the {@code HashedPassword} object corresponding to the password string.
     * @throws ModelException if any errors encountered
     */
    public HashedPasswordAndSalt generateHashedPassword(char[] originalPassword) throws ModelException {
        try {
            // generate the salt
            SecureRandom random = new SecureRandom();
            byte[] salt = new byte[16];
            random.nextBytes(salt);

            // hash the password
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(salt);
            byte[] hashedPassword = md.digest(new String(originalPassword).getBytes(StandardCharsets.UTF_8));

            // encode the results as Base64
            String hashedPasswordBase64 = Base64.getEncoder().encodeToString(hashedPassword);
            String saltBase64 = Base64.getEncoder().encodeToString(salt);
            return new HashedPasswordAndSalt(hashedPasswordBase64, saltBase64);
        } catch (NoSuchAlgorithmException e) {
            throw new ModelException("Error: " + e.getMessage());
        }
    }

    /**
     * <h3>Password Verify Function</h3>
     * @param userInputPassword the password, in plain string.
     * @param storedHashedPasswordAndSalt the {@code HashedPassword} object representing the hashed password.
     * @return if the password matches the hashed password.
     * @throws ModelException if any errors encountered.
     */
    public boolean verifyPassword(String userInputPassword, HashedPasswordAndSalt storedHashedPasswordAndSalt) throws ModelException {
        try {
            byte[] salt = Base64.getDecoder().decode(storedHashedPasswordAndSalt.getHashedSalt());

            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(salt);
            byte[] passwordCompare = md.digest(userInputPassword.getBytes(StandardCharsets.UTF_8));

            String hashedPasswordBase64 = Base64.getEncoder().encodeToString(passwordCompare);
            return hashedPasswordBase64.equals(storedHashedPasswordAndSalt.getHashedPassword());
        } catch (NoSuchAlgorithmException e) {
            throw new ModelException("Error: " + e.getMessage());
        }
    }
}
