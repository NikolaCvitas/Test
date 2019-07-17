package hr.nikola.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Time relate utility class
 */
public class TimeUtils {

    /**
     * Date formatter with date and time till milliseconds 
     */
    private static final SimpleDateFormat DATE_FORMATTER_TILL_MILLIS = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");

    /**
     * Get the current date formatted in this way
     * {@link TimeUtils#DATE_FORMATTER_TILL_MILLIS}
     *
     * @return
     */
    public static String getFormattedDateTillMilliseconds() {

        return getFormattedDateTillMilliseconds(new Date());
    }

    /**
     * Get the date formatted in this way
     * {@link TimeUtils#DATE_FORMATTER_TILL_MILLIS}
     *
     * @return
     */
    public static String getFormattedDateTillMilliseconds( Date date ) {

        return DATE_FORMATTER_TILL_MILLIS.format(date);
    }
}
