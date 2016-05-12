package org.hsrw.mobilecomputing.loggerapp.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import org.hsrw.mobilecomputing.loggerapp.R;

import java.util.Date;

public class RecordActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record);

        String name = getIntent().getExtras().getString("name");
        String date = getIntent().getExtras().getString("date");

        Log.d("RecordActivity", name);
        Log.d("RecordActivity", date);

        TextView tv_name = (TextView) findViewById(R.id.tv_number);
        TextView tv_date = (TextView) findViewById(R.id.tv_date);

        if (tv_name != null) {
            tv_name.setText(name);
        }

        if (tv_date != null) {
            tv_date.setText(date);
        }


    }
}
