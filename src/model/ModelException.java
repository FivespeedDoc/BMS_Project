package model;

/**
 * <h3>The {@code ModelException} Exception</h3>
 * All errors in the Model part should throw this exception.
 * The other parts should catch this exception to get the error information from the Model.
 * @author FrankYang0610
 * @author ZacharyRE
 */
public class ModelException extends Exception {
    public ModelException(String message) {
        super(message);
    }
}
