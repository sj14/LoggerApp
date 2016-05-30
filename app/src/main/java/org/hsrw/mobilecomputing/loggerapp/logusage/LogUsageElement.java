package org.hsrw.mobilecomputing.loggerapp.logusage;

/**
 * Created by simon on 26.04.16.
 * Represents a log of a call.
 */
public class LogUsageElement {

    String packageName;
    Long firstTimeStamp, lastTimeStamp, lastTimeUsed, totalTimeInForeground;

    public LogUsageElement(String packageName, Long firstTimeStamp, Long lastTimeStamp, Long lastTimeUsed, Long totalTimeInForeground) {
        this.packageName = packageName;
        this.firstTimeStamp = firstTimeStamp;
        this.lastTimeStamp = lastTimeStamp;
        this.lastTimeUsed = lastTimeUsed;
        this.totalTimeInForeground = totalTimeInForeground;
    }

    public String getPackageName() {
        return packageName;
    }

    public Long getFirstTimeStamp() {
        return firstTimeStamp;
    }

    public Long getLastTimeStamp() {
        return lastTimeStamp;
    }

    public Long getLastTimeUsed() {
        return lastTimeUsed;
    }

    public Long getTotalTimeInForeground() {
        return totalTimeInForeground;
    }
}
