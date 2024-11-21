package controller;

/**
 * <h3>The {@code WrongApplicationStateException} Exception</h3>
 * This is the general exception to the Controller part.
 * @author FrankYang0610
 */
public class WrongApplicationStateException extends Exception {
    public WrongApplicationStateException(String message) {
        super(message);
    }
}
