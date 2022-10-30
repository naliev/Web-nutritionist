package ru.javawebinar.topjava;

import org.junit.AssumptionViolatedException;
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
        protected void succeeded(long nanos, Description description) {
            summarizeInfo(description, "succeeded", nanos);
        }

        @Override
        protected void failed(long nanos, Throwable e, Description description) {
            summarizeInfo(description, "failed", nanos);
        }

        @Override
        protected void skipped(long nanos, AssumptionViolatedException e, Description description) {
            summarizeInfo(description, "skipped", nanos);
        }
    };
    public static final ExternalResource FINAL_RESULT = new ExternalResource() {
        @Override
        protected void after() {
            log.info(results.toString());
        }
    };

    private static void summarizeInfo(Description description, String status, long nanos) {
        String testName = description.getMethodName();
        results.append(String.format("Test %s %s, spent %d microseconds %n",
                testName, status, TimeUnit.NANOSECONDS.toMicros(nanos)));
    }
}
