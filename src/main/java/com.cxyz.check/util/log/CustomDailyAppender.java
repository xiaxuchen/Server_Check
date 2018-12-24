package com.cxyz.check.util.log;

import org.apache.log4j.DailyRollingFileAppender;
import org.apache.log4j.Priority;

public class CustomDailyAppender extends DailyRollingFileAppender {

    @Override
    public boolean isAsSevereAsThreshold(Priority priority) {
        return this.getThreshold().equals(priority);
    }
}
