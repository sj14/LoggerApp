package org.hsrw.mobilecomputing.loggerapp.activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.hsrw.mobilecomputing.loggerapp.R;

public class LoginActivity extends AppCompatActivity {

    private static final int MY_PERMISSIONS_REQUEST = 0;

    Button mLoginButton;
    TextView mWrongPasswordText;
    EditText mPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        checkPermissions();

        initialiseElements();
        passwordCheckEnabled();
    }

    private void initialiseElements() {
        mLoginButton = (Button) findViewById(R.id.btn_open);
        mWrongPasswordText = (TextView) findViewById(R.id.textView_wrongPassword);
        mPassword = (EditText) findViewById(R.id.editText_password);

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
        boolean prefEnablePassword = PreferenceManager.getDefaultSharedPreferences(this).getBoolean("pref_enable_password", false);

        if (!prefEnablePassword) {
            Intent logActivity = new Intent(this, LogActivity.class);
            startActivity(logActivity);
        }
    }

    private void onClickLogin() {
        String prefPassword = PreferenceManager.getDefaultSharedPreferences(this).getString("pref_password", "0000");

        if(mPassword.getText().toString().equals(prefPassword)) {
            Intent intent = new Intent(this, LogActivity.class);
            startActivity(intent);
        } else {
            mWrongPasswordText.setVisibility(View.VISIBLE);
        }
    }

    // to satisfy the necessary onClick signature
    public void onClickLogin(View view) {
        onClickLogin();
    }

    public void checkPermissions() {

        // READ PHONE STATE
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

                ActivityCompat.requestPermissions(this,
                        new String[]{
                                Manifest.permission.READ_PHONE_STATE,
                                Manifest.permission.RECORD_AUDIO,
                                Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        MY_PERMISSIONS_REQUEST);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST: {
                // If request is cancelled, the result arrays are empty.

                for (int grantResult : grantResults) {
                    if (grantResult == PackageManager.PERMISSION_DENIED) {
                        Toast exiting = Toast.makeText(this, R.string.permission_denied, Toast.LENGTH_LONG);
                        exiting.show();
                        finish();
                    }
                }
                return;
            }
            // other 'case' lines to check for other
            // permissions this app might request
        }
    }
}
