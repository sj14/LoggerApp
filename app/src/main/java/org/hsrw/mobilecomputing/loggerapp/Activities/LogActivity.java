package org.hsrw.mobilecomputing.loggerapp.activities;

import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import org.hsrw.mobilecomputing.loggerapp.logcall.LogCallAdapter;
import org.hsrw.mobilecomputing.loggerapp.R;
import org.hsrw.mobilecomputing.loggerapp.logcall.LogCallElement;

import java.util.Calendar;
import java.util.List;

import static org.hsrw.mobilecomputing.loggerapp.R.id.listView_calls;
import static org.hsrw.mobilecomputing.loggerapp.R.id.listView_usage;

public class LogActivity extends AppCompatActivity {

    private ListView listViewCalls;
    private ListView listViewUsage;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log);
        loadCallItems();
    }

    private void loadCallItems() {
        List<LogCallElement> listLogCallElements = LogCallElement.getCallElements(this.getApplication());

        if (listLogCallElements != null) {
            final LogCallElement myListElement_data[] = listLogCallElements.toArray(new LogCallElement[listLogCallElements.size()]);
            LogCallAdapter adapter = new LogCallAdapter(this, R.layout.listview_call_row, myListElement_data);
            listViewCalls = (ListView) findViewById(listView_calls);

            if (listViewCalls != null) {
                listViewCalls.setAdapter(adapter);

                listViewCalls.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Intent i= new Intent(LogActivity.this, RecordActivity.class);
                        i.putExtra("name",myListElement_data[position].getName());
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
