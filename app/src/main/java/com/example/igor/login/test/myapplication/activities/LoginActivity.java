package com.example.igor.login.test.myapplication.activities;

import android.content.Intent;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


import com.example.igor.login.test.myapplication.R;
import com.example.igor.login.test.myapplication.clients.HttpClient;
import com.example.igor.login.test.myapplication.heplers.PreferenceHelper;

import org.json.JSONObject;


public class LoginActivity extends AppCompatActivity {


    EditText login;
    EditText password;
    Button loginButton;


    final String tokenKey = "tokenKey";


    @Override
    protected void onCreate(Bundle savedInstanceState)  {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        login = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);
        loginButton = (Button) findViewById(R.id.loginButtong);


    }

    public void loginButtonListener(View v) {
        try {
            final Intent taskActivity = new Intent(this, TasksActivity.class);
            String params = "email=" + login.getText().toString() + "&password=" + password.getText().toString();
            String response = HttpClient.sendPost(params);
            JSONObject json = new JSONObject(response);

            /**
             * set preferences
             */
            PreferenceHelper.setDefaults(tokenKey,(String) json.get("api_token"),getApplicationContext());
            PreferenceHelper.setDefaults("name",(String) json.get("name"),getApplicationContext());
            PreferenceHelper.setDefaults("email",(String) json.get("email"),getApplicationContext());

            startActivity(taskActivity);

        } catch (Exception ex){
            ex.printStackTrace();
        }
    }




}
