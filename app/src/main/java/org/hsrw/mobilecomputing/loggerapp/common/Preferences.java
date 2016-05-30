package org.hsrw.mobilecomputing.loggerapp.common;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by simon on 30.05.16.
 */
public class Preferences {
    static SharedPreferences sharedPref;

    public static SharedPreferences getPreferences(Context context) {
        sharedPref = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPref;
    }
}
