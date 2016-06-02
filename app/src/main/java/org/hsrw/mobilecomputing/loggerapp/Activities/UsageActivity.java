package org.hsrw.mobilecomputing.loggerapp.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import org.hsrw.mobilecomputing.loggerapp.R;

/*
The detail statistics acitivty of a particular app selected from the whole list of apps
 */
public class UsageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usage);

        // Get the GUI Elements
        TextView tv_packageName = (TextView) findViewById(R.id.tv_packageName);
        TextView tv_firstTime = (TextView) findViewById(R.id.tv_firstTime);
        TextView tv_lastTime = (TextView) findViewById(R.id.tv_lastTime);
        TextView tv_lastTimeUsed = (TextView) findViewById(R.id.tv_lastTimeUsed);
        TextView tv_totalTimeForeground = (TextView) findViewById(R.id.tv_totalTimeForeground);

        // Get the stat values of the selected app
        String packageName = getIntent().getExtras().getString("packageName");
        Long firstTime = getIntent().getExtras().getLong("firstTime");
        Long lastTime = getIntent().getExtras().getLong("lastTime");
        Long lastTimeUsed = getIntent().getExtras().getLong("lastTimeUsed");
        Long totalTimeForeground = getIntent().getExtras().getLong("totalTimeForeground");

        // Assign the values to the GUI elements
        assert tv_packageName != null;
        tv_packageName.setText(packageName);
        assert tv_firstTime != null;
        tv_firstTime.setText(firstTime.toString());
        assert tv_lastTime != null;
        tv_lastTime.setText(lastTime.toString());
        assert tv_lastTimeUsed != null;
        tv_lastTimeUsed.setText(lastTimeUsed.toString());
        assert tv_totalTimeForeground != null;
        tv_totalTimeForeground.setText(totalTimeForeground.toString());
    }
}
