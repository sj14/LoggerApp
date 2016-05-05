package org.hsrw.mobilecomputing.loggerapp.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.hsrw.mobilecomputing.loggerapp.R;

public class LoginActivity extends AppCompatActivity {

    Button mLoginButton;
    TextView mWrontPasswordText;
    EditText mPassword;
    SharedPreferences sharedPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initialiseElements();
        passwordCheckEnabled();
    }

    private void initialiseElements() {
        mLoginButton = (Button) findViewById(R.id.btn_open);
        mWrontPasswordText = (TextView) findViewById(R.id.textView_wrongPassword);
        mPassword = (EditText) findViewById(R.id.editText_password);
        sharedPref = PreferenceManager.getDefaultSharedPreferences(this);

        mPassword.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if ((event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) || (actionId == EditorInfo.IME_ACTION_DONE)) {
                    onClickLogin();
                }
                return false;
            }
        });

    }

    private void passwordCheckEnabled() {
        boolean prefEnablePassword = sharedPref.getBoolean("pref_enable_password", true);

        if (!prefEnablePassword) {
            Intent logActivity = new Intent(this, LogActivity.class);
            startActivity(logActivity);
        }
    }

    public void onClickLogin() {
        String prefPassword = sharedPref.getString("pref_password", "");


        if(mPassword.getText().toString().equals(prefPassword)) {
            Intent intent = new Intent(this, LogActivity.class);
            startActivity(intent);
        } else {
            mWrontPasswordText.setVisibility(View.VISIBLE);
        }
    }



}
