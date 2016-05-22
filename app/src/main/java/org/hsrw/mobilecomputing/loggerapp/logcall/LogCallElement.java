package org.hsrw.mobilecomputing.loggerapp.logcall;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by simon on 26.04.16.
 * Represents a log of a call.
 */
public class LogCallElement {

    Date date;
    String name;

    public LogCallElement(Date date, String name) {
        this.date = date;
        this.name =name;
    }

    public Date getDate() {
        return date;
    }

    public String getName() {
        return name;
    }

    public static List getCallElements(Context context) {
        SharedPreferences appSharedPrefs = PreferenceManager.getDefaultSharedPreferences(context);

        String jsonGet = appSharedPrefs.getString("LogCallElements", "");
        Type type = new TypeToken<List<LogCallElement>>(){}.getType();
        Gson gson = new Gson();
        List<LogCallElement> listLogCallElements = gson.fromJson(jsonGet, type);
        return listLogCallElements;
    }

    public static void addCallElement(LogCallElement e, Context context) {
        List<LogCallElement> listLogCallElements = getCallElements(context);
        if (listLogCallElements == null) {
            listLogCallElements = new ArrayList<>();
        }
        listLogCallElements.add(0, e);

        Gson gson = new Gson();
        String jsonAdd = gson.toJson(listLogCallElements);

        SharedPreferences appSharedPrefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor prefsEditor = appSharedPrefs.edit();
        prefsEditor.putString("LogCallElements", jsonAdd);
        prefsEditor.apply();
    }
}
