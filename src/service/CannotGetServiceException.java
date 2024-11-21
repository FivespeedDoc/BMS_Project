package service;

/**
 * <h3>The {@code CannotGetServiceException} Exception</h3>
 * This is the general exception to the Service part.
 * @author FrankYang0610
 */
public class CannotGetServiceException extends Exception {
    public CannotGetServiceException(String message) {
        super(message);
    }
}
