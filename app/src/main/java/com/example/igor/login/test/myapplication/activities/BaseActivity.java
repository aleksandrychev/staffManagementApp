package com.example.igor.login.test.myapplication.activities;

import android.content.Intent;
import android.os.Bundle;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.igor.login.test.myapplication.R;
import com.example.igor.login.test.myapplication.heplers.PreferenceHelper;
import com.example.igor.login.test.myapplication.interceptors.AuthorizationInterceptor;

/**
 * Created by igor on 29.10.16.
 */

public abstract class BaseActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    TextView headerName;
    TextView headerEmail;
    protected Retrofit retrofit;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        AuthorizationInterceptor interceptor = new AuthorizationInterceptor();
        interceptor.setContext(getApplicationContext());
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .build();

        /**
         * @// TODO: 4/27/2017 move url address to xml settings
         */
        retrofit = new Retrofit.Builder()
                .baseUrl("http://staff.aleksandrychev.name/api/v1/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_info_drawer, menu);

        headerEmail = (TextView) findViewById(R.id.header_email);
        headerName = (TextView) findViewById(R.id.header_name);

        headerEmail.setText(PreferenceHelper.getDefaults("email", getApplicationContext()));
        headerName.setText(PreferenceHelper.getDefaults("name", getApplicationContext()));

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.tasks) {
            final Intent taskActivity = new Intent(this, TasksActivity.class);
            startActivity(taskActivity);
        } else if (id == R.id.edit_profile) {

        }
        else if (id == R.id.gps_menu) {
            final Intent gpsActivity = new Intent(this, GpsActivity.class);
            startActivity(gpsActivity);
        }else if (id == R.id.log_out) {
            /**
             * @// TODO: 29.10.16 Clear Preference
             */
            final Intent loginActivity = new Intent(this, LoginActivity.class);
            startActivity(loginActivity);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(getDrawerId());
        drawer.closeDrawer(GravityCompat.START);

        return false;
    }

    protected abstract int getDrawerId();
}
