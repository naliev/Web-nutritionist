package ru.javawebinar.topjava;

import org.junit.rules.ExternalResource;
import org.junit.rules.Stopwatch;
import org.junit.runner.Description;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;


public class StopWatchRules {

    private static final Logger log = LoggerFactory.getLogger(StopWatchRules.class);

    private static final StringBuilder results = new StringBuilder();
    public static final Stopwatch STOPWATCH = new Stopwatch() {

        @Override
        protected void finished(long nanos, Description description) {
            log.debug(String.format("%s %d ms %n", description.getMethodName(), TimeUnit.NANOSECONDS.toMillis(nanos)));
            results.append(String.format("%-25s %d ms %n", description.getMethodName(), TimeUnit.NANOSECONDS.toMillis(nanos)));
        }

    };
    public static final ExternalResource FINAL_RESULT = new ExternalResource() {
        @Override
        protected void before() {
            results.setLength(0);
            results.append(String.format("%n"));
        }

        @Override
        protected void after() {
            log.info(results.toString());
        }
    };

}
