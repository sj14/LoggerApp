package org.hsrw.mobilecomputing.loggerapp.logapp;

import org.hsrw.mobilecomputing.loggerapp.LogElement;

import java.util.Date;

/**
 * Created by simon on 26.04.16.
 * Represents a log of an used App.
 */
public class LogAppElement extends LogElement {

    public LogAppElement(Date date, String name) {
        super(date, name);
    }
}
