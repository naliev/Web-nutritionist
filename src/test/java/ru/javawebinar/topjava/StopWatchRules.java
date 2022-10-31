package ru.javawebinar.topjava;

import org.junit.rules.ExternalResource;
import org.junit.rules.Stopwatch;
import org.junit.runner.Description;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;


public class StopWatchRules {

    private static final Logger log = LoggerFactory.getLogger(StopWatchRules.class);

    private static final StringBuilder results = new StringBuilder(String.format("%n"));
    public static final Stopwatch STOPWATCH = new Stopwatch() {

        @Override
        protected void finished(long nanos, Description description) {
            summarizeInfo(description, nanos);
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

    private static void summarizeInfo(Description description, long nanos) {
        String testResults = String.format("Test %s, spent %d ms %n", description.getMethodName(), TimeUnit.NANOSECONDS.toMicros(nanos));
        log.debug(testResults);
        results.append(testResults);
    }
}
