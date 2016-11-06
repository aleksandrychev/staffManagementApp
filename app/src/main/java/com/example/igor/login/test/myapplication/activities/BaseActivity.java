package com.example.igor.login.test.myapplication.activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.igor.login.test.myapplication.R;
import com.example.igor.login.test.myapplication.heplers.PreferenceHelper;

/**
 * Created by igor on 29.10.16.
 */

public abstract class BaseActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    TextView headerName;
    TextView headerEmail;

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

        } else if (id == R.id.log_out) {
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