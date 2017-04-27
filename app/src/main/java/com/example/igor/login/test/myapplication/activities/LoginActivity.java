package com.example.igor.login.test.myapplication.activities;

import android.content.Intent;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import com.example.igor.login.test.myapplication.R;
import com.example.igor.login.test.myapplication.heplers.PreferenceHelper;
import com.example.igor.login.test.myapplication.models.api.interfaces.Auth;
import com.example.igor.login.test.myapplication.responseObjects.auth.AuthResponse;

public class LoginActivity extends AppCompatActivity {


    EditText login;
    EditText password;
    Button loginButton;


    final String tokenKey = "tokenKey";

    protected Retrofit retrofit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        login = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);
        loginButton = (Button) findViewById(R.id.loginButtong);


        retrofit = new Retrofit.Builder()
                .baseUrl("http://staff.aleksandrychev.name/api/v1/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

    }

    public void loginButtonListener(View v) {
        try {
            Auth authApi = retrofit.create(Auth.class);
            Response response = authApi.login(login.getText().toString(), password.getText().toString()).execute();
            if (response.isSuccessful()) {
                /**
                 * set preferences
                 */
                AuthResponse responseData = (AuthResponse) response.body();
                PreferenceHelper.setDefaults("tokenKey", responseData.getData().getApiToken(), getApplicationContext());
                PreferenceHelper.setDefaults("name", responseData.getData().getName(), getApplicationContext());
                PreferenceHelper.setDefaults("email", responseData.getData().getEmail(), getApplicationContext());
                final Intent taskActivity = new Intent(this, TasksActivity.class);
                startActivity(taskActivity);
            }


        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


}
