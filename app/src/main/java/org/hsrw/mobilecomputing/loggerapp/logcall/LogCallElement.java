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

    // Add a logged call element to the list of all logged call elements
    public static void addCallElement(LogCallElement e, Context context) {
        // Get the list of all currently saved objects and just append a new one
        List<LogCallElement> listLogCallElements = getCallElements(context);
        if (listLogCallElements == null) {
            listLogCallElements = new ArrayList<>();
        }
        listLogCallElements.add(0, e);

        // Gson is needed to store the objects into the sharedpreferences, which is only able to handly
        // primitives like strings and not objects. Gson converts this Objects to serializable Json data.
        // https://github.com/google/gson
        Gson gson = new Gson();
        String jsonAdd = gson.toJson(listLogCallElements);

        // Append the new Object to the list
        SharedPreferences.Editor prefsEditor = PreferenceManager.getDefaultSharedPreferences(context).edit();
        prefsEditor.putString("LogCallElements", jsonAdd);
        prefsEditor.apply();
    }

    // Get all logged call elements from the settings
    public static List getCallElements(Context context) {
        SharedPreferences appSharedPrefs = PreferenceManager.getDefaultSharedPreferences(context);

        String jsonGet = appSharedPrefs.getString("LogCallElements", "");
        // Json data has been returned, which has to be converted to a list of LogElement Objects.
        Type type = new TypeToken<List<LogCallElement>>(){}.getType();
        Gson gson = new Gson();
        List<LogCallElement> listLogCallElements = gson.fromJson(jsonGet, type);
        return listLogCallElements;
    }
}
