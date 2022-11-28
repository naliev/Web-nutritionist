package ru.javawebinar.topjava.formatter;

import org.springframework.format.Formatter;
import ru.javawebinar.topjava.util.DateTimeUtil;

import javax.validation.constraints.NotNull;
import java.text.ParseException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public final class LocalTimeFormatter implements Formatter<LocalTime> {

    public String print(@NotNull LocalTime date, @NotNull Locale locale) {
        return date.format(DateTimeFormatter.ISO_INSTANT);
    }

    public LocalTime parse(@NotNull String formatted, @NotNull Locale locale) throws ParseException {
        return DateTimeUtil.parseLocalTime(formatted);
    }
}