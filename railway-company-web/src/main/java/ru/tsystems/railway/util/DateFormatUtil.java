package ru.tsystems.railway.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class DateFormatUtil {
    public static final DateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy");
    public static final DateFormat DATETIME_FORMAT = new SimpleDateFormat("dd/MM/yyyy HH:mm");

    private DateFormatUtil() {
    }
}
