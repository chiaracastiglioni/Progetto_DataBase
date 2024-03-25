package it.unibo;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.Optional;

public final class Utils {
    private Utils() {}
    
    public static java.util.Date sqlDateToDate(final java.sql.Date sqlDate) {
        return sqlDate == null ? null : new java.util.Date(sqlDate.getTime());
    }

    public static java.util.Date sqlDateToDateHours(final java.sql.Timestamp sqlDate) {
        return sqlDate == null ? null : new java.util.Date(sqlDate.getTime());
    }
    
    public static java.sql.Date dateToSqlDate(final java.util.Date date) {
        return date == null ? null : new java.sql.Date(date.getTime());
    }

    public static java.sql.Timestamp dateToSqlDateHours(final java.util.Date date) {
        return date == null ? null : new java.sql.Timestamp(date.getTime());
    }
    
    public static Optional<java.util.Date> buildDateSimple(final int day, final int month, final int year) {
        try {
            final String dateFormatString = "dd/MM/yyyy";
            final String dateString = day + "/" + month + "/" + year;
            final java.util.Date date = new SimpleDateFormat(dateFormatString, Locale.ITALIAN).parse(dateString);
            return Optional.of(date);
        } catch (final ParseException e) {
            return Optional.empty();
        }
    }

    public static Optional<java.util.Date> buildDate(final String dateString) {
        try {
            final String dateFormatString = "yyyy/MM/dd HH:mm";
            final java.util.Date date = new SimpleDateFormat(dateFormatString, Locale.ITALIAN).parse(dateString);
            return Optional.of(date);
        } catch (final ParseException e) {
            return Optional.empty();
        }
    }

    public static Optional<java.util.Date> buildDate(final int day, final int month, final int year, final int hour, final int minute) {
        final String dateString = day + "/" + month + "/" + year + " " + hour + ":" + minute;
        return buildDate(dateString);
    }

}
