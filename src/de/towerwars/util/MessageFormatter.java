package de.towerwars.util;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

public class MessageFormatter {

    private final DecimalFormat messageFormat;

    public MessageFormatter() {
        messageFormat = (DecimalFormat) NumberFormat.getNumberInstance(Locale.GERMAN);
    }

    public String format(final long number) {
        return messageFormat.format(number);
    }
}
