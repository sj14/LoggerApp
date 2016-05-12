package org.hsrw.mobilecomputing.loggerapp.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.hsrw.mobilecomputing.loggerapp.LogElement;
import org.hsrw.mobilecomputing.loggerapp.LogElementAdapter;
import org.hsrw.mobilecomputing.loggerapp.R;
import org.hsrw.mobilecomputing.loggerapp.logcall.LogCallElement;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.List;

import static org.hsrw.mobilecomputing.loggerapp.R.id.listView_logs;

public class LogActivity extends AppCompatActivity {

    private ListView listViewLogs;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log);
        loadItems();
    }

    private void loadItems() {
//        LogElement myListElement_data[] = new LogElement[30];
//
//        for (int i = 0; i < 30; i++) {
//            myListElement_data[i] = new LogElement(Calendar.getInstance().getTime(), "Description " + i) {
//            };
//        }

        SharedPreferences appSharedPrefs = PreferenceManager
                .getDefaultSharedPreferences(this.getApplicationContext());
        Gson gson = new Gson();

        String json = appSharedPrefs.getString("LogCallElements", "");

        Type type = new TypeToken<List<LogCallElement>>(){}.getType();
        List<LogCallElement> listLogCallElements = gson.fromJson(json, type);

        if (listLogCallElements != null) {
            LogElement myListElement_data[] = listLogCallElements.toArray(new LogCallElement[listLogCallElements.size()]);


            LogElementAdapter adapter = new LogElementAdapter(this,
                    R.layout.listview_item_row, myListElement_data);


            listViewLogs = (ListView) findViewById(listView_logs);


            if (listViewLogs != null) {
                listViewLogs.setAdapter(adapter);
            }
        }
    }

    public void onClickSettings(View view) {
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
    }

}
