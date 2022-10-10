package ru.javawebinar.topjava.util;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class TimeUtil {
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public static boolean isBetweenHalfOpen(LocalTime lt, LocalTime startTime, LocalTime endTime) {
        return lt.compareTo(startTime) >= 0 && lt.compareTo(endTime) < 0;
    }

    public String getFormattedDate(LocalDateTime localDateTime) {
        return (localDateTime == null) ? "" : localDateTime.format(DATE_FORMATTER);
    }

    public static LocalDateTime getDateFromString(String str) {
        return LocalDateTime.parse(str, DATE_FORMATTER);
    }
}
