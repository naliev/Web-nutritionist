package ru.javawebinar.topjava.formatter;

import org.springframework.format.Formatter;
import ru.javawebinar.topjava.util.DateTimeUtil;

import javax.validation.constraints.NotNull;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public final class LocalDateFormatter implements Formatter<LocalDate> {

    public String print(@NotNull LocalDate date, @NotNull Locale locale) {
        return date.format(DateTimeFormatter.ISO_LOCAL_DATE);
    }

    public LocalDate parse(@NotNull String formatted, @NotNull Locale locale) throws ParseException {
        return DateTimeUtil.parseLocalDate(formatted);
    }
}