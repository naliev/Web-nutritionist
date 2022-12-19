package ru.javawebinar.topjava.util.exception;

import javax.validation.constraints.NotNull;

public class ErrorInfo {
    private final String url;
    private final ErrorType type;
    private final String[] details;

    public ErrorInfo(@NotNull CharSequence url, ErrorType type, String... details) {
        this.url = url.toString();
        this.type = type;
        this.details = details;
    }
}