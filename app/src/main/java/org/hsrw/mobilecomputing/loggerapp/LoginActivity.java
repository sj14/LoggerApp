package org.hsrw.mobilecomputing.loggerapp;

import android.app.*;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class LoginActivity extends AppCompatActivity {

    Button mLoginButton;
    TextView mWrontPasswordText;
    EditText mPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mLoginButton = (Button) findViewById(R.id.btn_open);
        mWrontPasswordText = (TextView) findViewById(R.id.textView_wrongPassword);
        mPassword = (EditText) findViewById(R.id.editText_password);
    }

    public void onLogin(View view) {
        Intent intent = new Intent(this, LogActivity.class);

        if(mPassword.getText().toString().equals("0000")) {
            startActivity(intent);
        } else {
            mWrontPasswordText.setVisibility(View.VISIBLE);
        }
    }
}
