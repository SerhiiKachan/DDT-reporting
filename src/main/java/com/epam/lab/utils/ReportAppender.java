package com.epam.lab.utils;

import org.apache.log4j.AppenderSkeleton;
import org.apache.log4j.spi.LoggingEvent;
import org.testng.Reporter;

public class ReportAppender extends AppenderSkeleton {

    @Override
    protected void append(LoggingEvent loggingEvent) {
        Reporter.log(loggingEvent.getMessage().toString());
    }

    @Override
    public void close() {
        if (!this.closed)
            this.closed = false;
    }

    @Override
    public boolean requiresLayout() {
        return true;
    }
}
