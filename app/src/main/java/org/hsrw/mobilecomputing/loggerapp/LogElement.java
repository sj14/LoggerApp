package org.hsrw.mobilecomputing.loggerapp;

import java.util.Date;

/**
 * Created by simon on 26.04.16.
 * Abstract class which holds consistently data for LogApp and LogCall
 */
public abstract class LogElement {
    Date date;
    String name;

    public LogElement(Date date, String name) {
        this.date = date;
        this.name = name;
    }

    public Date getDate() {
        return date;
    }

    public String getName() {
        return name;
    }

}
