package org.hsrw.mobilecomputing.loggerapp.activities;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

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
        List<LogCallElement> listLogCallElements = LogCallElement.getCallElements(this.getApplication());

        if (listLogCallElements != null) {
            final LogElement myListElement_data[] = listLogCallElements.toArray(new LogCallElement[listLogCallElements.size()]);
            LogElementAdapter adapter = new LogElementAdapter(this, R.layout.listview_item_row, myListElement_data);
            listViewLogs = (ListView) findViewById(listView_logs);


            if (listViewLogs != null) {
                listViewLogs.setAdapter(adapter);

                listViewLogs.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Intent i= new Intent(LogActivity.this, RecordActivity.class);
                        i.putExtra("name",myListElement_data[position].getName());
                        i.putExtra("dateL",myListElement_data[position].getDate().getTime());
                        i.putExtra("date",myListElement_data[position].getDate());
                        startActivity(i);
                    }
                });

            }
        }
    }

    public void onClickSettings(View view) {
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
    }
}
