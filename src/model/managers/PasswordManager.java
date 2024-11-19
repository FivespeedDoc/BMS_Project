package model.managers;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import model.entities.HashedPassword;


/**
 * <h2> Password Manager Class </h2>
 * @author jimyang
 * </p> This class provides functionality for password cryptography encryption and verification
 * </p>
 *
 * Operations include {@code GetHashedPassword}
 */
public class PasswordManager {

/**
 * <h3>Password Hashing function</h3>
 * @param password (String, in plain text)
 * @return HashedPassword Object
 * @throws Exception
 */

    public static HashedPassword hashPassword(String password) throws Exception { //Exception handling might need modification
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update(salt);
        byte[] hashedPassword = md.digest(password.getBytes(StandardCharsets.UTF_8));
        String hashedPasswordBase64 = Base64.getEncoder().encodeToString(hashedPassword);
        String saltBase64 = Base64.getEncoder().encodeToString(salt);

        return new HashedPassword(hashedPasswordBase64, saltBase64);
    }

    /**
     * <h3>Password verify function</h3>
     * @param password (String, in plain text)
     * @param storedHash (String, in base64 coding)
     * @param storedSalt (String, in base64 coding)
     * @return boolean (if the password matches)
     * @throws Exception
     */
    public static boolean verifyPassword(String password, String storedHash, String storedSalt) throws Exception { //Exception handling might need modification
        byte[] salt = Base64.getDecoder().decode(storedSalt);
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update(salt);
        byte[] hashedPassword = md.digest(password.getBytes(StandardCharsets.UTF_8));
        String hashedPasswordBase64 = Base64.getEncoder().encodeToString(hashedPassword);
        return hashedPasswordBase64.equals(storedHash);
    }

    /**
     * <h3>Password verify function</h3>
     * @param password (String, in plain text)
     * @param hashedPassword (HashedPassword Object)
     * @return boolean (if the password matches)
     * @throws Exception
     */

    public static boolean verifyPassword(String password, HashedPassword hashedPassword) throws Exception { //Exception handling might need modification
        byte[] salt = Base64.getDecoder().decode(hashedPassword.salt);
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update(salt);
        byte[] passwordCompare = md.digest(password.getBytes(StandardCharsets.UTF_8));
        String hashedPasswordBase64 = Base64.getEncoder().encodeToString(passwordCompare);
        return hashedPasswordBase64.equals(hashedPassword.salt);
    }
}