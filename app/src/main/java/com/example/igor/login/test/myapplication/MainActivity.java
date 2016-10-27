package com.example.igor.login.test.myapplication;

import android.content.Intent;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.content.SharedPreferences;

import com.example.igor.login.test.myapplication.clients.HttpClient;
import com.example.igor.login.test.myapplication.heplers.PreferenceHelper;

import org.json.JSONObject;


public class MainActivity extends AppCompatActivity {


    EditText login;
    EditText password;
    Button loginButton;

    SharedPreferences sPref;

    final String tokenKey = "tokenKey";


    @Override
    protected void onCreate(Bundle savedInstanceState)  {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        login = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);
        loginButton = (Button) findViewById(R.id.loginButtong);
        final Intent dashBoardActivity = new Intent(this, InfoActivity.class);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String params = "email=" + login.getText().toString() + "&password=" + password.getText().toString();
                   String response = HttpClient.sendPost(params);
                    JSONObject json = new JSONObject(response);

                    PreferenceHelper.setDefaults(tokenKey,(String) json.get("api_token"),getApplicationContext());
                    PreferenceHelper.setDefaults("name",(String) json.get("name"),getApplicationContext());
                    PreferenceHelper.setDefaults("email",(String) json.get("email"),getApplicationContext());

                    System.out.println(json.toString());

                    startActivity(dashBoardActivity);


                } catch (Exception ex){
                    ex.printStackTrace();
                }
            }
        });

    }




}
