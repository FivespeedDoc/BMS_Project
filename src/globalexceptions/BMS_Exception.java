package globalexceptions;

/**
 * <h3>The {@code BMS_Exception} Exception</h3>
 * This is the general exception to the BMS.
 * All exceptions to the BMS should extend this exception.
 * @author FrankYang0610
 */
public class BMS_Exception extends Exception {
    public BMS_Exception(String message) {
        super(message);
    }
}
