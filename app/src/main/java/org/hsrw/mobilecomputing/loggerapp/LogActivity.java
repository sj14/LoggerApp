package org.hsrw.mobilecomputing.loggerapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import java.util.Calendar;
import java.util.Date;

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
        LogElement myListElement_data[] = new LogElement[30];

        for (int i = 0; i < 30; i++) {
            myListElement_data[i] = new LogElement(Calendar.getInstance().getTime(), "Description " + i) {
            };
        }


        LogElementAdapter adapter = new LogElementAdapter(this,
                R.layout.listview_item_row, myListElement_data);


        listViewLogs = (ListView) findViewById(listView_logs);


        if (listViewLogs != null) {
            listViewLogs.setAdapter(adapter);
        }
    }

    public void onClickSettings(View view) {
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
    }

}
