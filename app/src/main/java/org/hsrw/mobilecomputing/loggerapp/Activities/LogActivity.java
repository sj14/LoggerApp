package org.hsrw.mobilecomputing.loggerapp.activities;

import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TabHost;

import org.hsrw.mobilecomputing.loggerapp.R;
import org.hsrw.mobilecomputing.loggerapp.logcall.LogCallAdapter;
import org.hsrw.mobilecomputing.loggerapp.logcall.LogCallElement;
import org.hsrw.mobilecomputing.loggerapp.logusage.LogUsageAdapter;
import org.hsrw.mobilecomputing.loggerapp.logusage.LogUsageElement;

import java.util.ArrayList;
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
        loadUsageItems();
        loadTabHost();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.settings, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // action with ID action_settings was selected
            case R.id.action_settings:
                Intent intent = new Intent(this, SettingsActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }
        return true;
    }

    TabHost tabHost;
    private void loadTabHost() {
        tabHost = (TabHost) findViewById(R.id.tabHost);
        assert tabHost != null;
        tabHost.setup();

        TabHost.TabSpec tab1 = tabHost.newTabSpec("First Tab");
        TabHost.TabSpec tab2 = tabHost.newTabSpec("Second Tab");

        tab1.setIndicator("Calls");
        tab1.setContent(listView_calls);

        tab2.setIndicator("Apps");
        tab2.setContent(listView_usage);

        tabHost.addTab(tab1);
        tabHost.addTab(tab2);
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
                        Intent i = new Intent(LogActivity.this, CallActivity.class);
                        i.putExtra("name", myListElement_data[position].getName());
                        i.putExtra("date", myListElement_data[position].getDate());
                        startActivity(i);
                    }
                });

            }
        }
    }

    private void loadUsageItems() {
        UsageStatsManager usageStatsManager = (UsageStatsManager) this.getSystemService(Context.USAGE_STATS_SERVICE); //Context.USAGE_STATS_SERVICE
        Calendar beginCal = Calendar.getInstance();
        beginCal.set(Calendar.YEAR, -1);


        final List<UsageStats> queryUsageStats = usageStatsManager.queryUsageStats(UsageStatsManager.INTERVAL_DAILY, beginCal.getTimeInMillis(), System.currentTimeMillis());

        List<LogUsageElement> listLogUsageElements = new ArrayList();

        for (UsageStats stat : queryUsageStats) {
            listLogUsageElements.add(new LogUsageElement(stat.getPackageName(), stat.getFirstTimeStamp(), stat.getLastTimeStamp(), stat.getLastTimeUsed(), stat.getTotalTimeInForeground()));
        }


        if (listLogUsageElements != null) {
            final LogUsageElement myListElement_data[] = listLogUsageElements.toArray(new LogUsageElement[listLogUsageElements.size()]);
            LogUsageAdapter usageAdapter = new LogUsageAdapter(this, R.layout.listview_usage_row, myListElement_data);
            listViewUsage = (ListView) findViewById(listView_usage);

            if (listViewUsage != null) {
                listViewUsage.setAdapter(usageAdapter);

                listViewUsage.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Intent usageActivity = new Intent(LogActivity.this, UsageActivity.class);
                        usageActivity.putExtra("packageName", myListElement_data[position].getPackageName());
                        usageActivity.putExtra("firstTime", myListElement_data[position].getFirstTimeStamp());
                        usageActivity.putExtra("lastTime", myListElement_data[position].getLastTimeStamp());
                        usageActivity.putExtra("lastTimeUsed", myListElement_data[position].getLastTimeUsed());
                        usageActivity.putExtra("totalTimeForeground", myListElement_data[position].getTotalTimeInForeground());

                        startActivity(usageActivity);
                    }
                });

            }
        }


    }
}
