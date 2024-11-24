package service.utilities;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * <h3>The {@code DateTimeFormatter} Class</h3>
 * This class introduces useful methods for handling conversions between the {@code DATETIME} type in database (represented as the {@code TimsStamp} type in Java) and the {@code String} type, which is general in the GUI part.
 * @author FrankYang0610
 */
public final class DateTimeFormatter {
    public static final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm"); // the timestamp formatter

    public static Timestamp parse(String dateTimeStr) throws ParseException {
        return new Timestamp(sdf.parse(dateTimeStr).getTime());
    }

    public static String format(Timestamp timestamp) {
        return sdf.format(timestamp);
    }
}
