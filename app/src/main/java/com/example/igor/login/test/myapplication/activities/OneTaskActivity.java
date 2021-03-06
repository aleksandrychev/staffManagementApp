package com.example.igor.login.test.myapplication.activities;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.igor.login.test.myapplication.R;
import com.example.igor.login.test.myapplication.clients.HttpClient;
import com.example.igor.login.test.myapplication.models.api.interfaces.Task;
import com.example.igor.login.test.myapplication.responseObjects.task.Data;
import com.example.igor.login.test.myapplication.responseObjects.task.TaskResponse;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OneTaskActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener {

    TextView name;
    TextView description;
    Button startButton;
    Button finishButton;
    Button pauseButton;
    JSONObject taskObject;

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.activity_one_task);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_one_task);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null)
            setSupportActionBar(toolbar);

        name = (TextView) findViewById(R.id.nameTask);
        description = (TextView) findViewById(R.id.descTask);
        startButton = (Button) findViewById(R.id.buttonStartTask);
        finishButton = (Button) findViewById(R.id.buttonFinishTask);
        pauseButton = (Button) findViewById(R.id.buttonPauseTask);

        description.setMovementMethod(new ScrollingMovementMethod());

        Intent intent = getIntent();
        String taskId = intent.getStringExtra("taskId").toString();
        try {
            retrofit.create(Task.class).getTask(taskId).enqueue(new Callback<TaskResponse>() {
                @Override
                public void onResponse(Call<TaskResponse> call, Response<TaskResponse> response) {

                    Data task = response.body().getData();

                    name.setText(task.getName());
                    description.setText(task.getDescription());
                    if (task.getStatus().equals("in_process")) {
                        try {
                            setButtonStopped();
                        }catch (Exception e){

                        }
                    } else {
                        setButtonStarted();
                    }

                }

                @Override
                public void onFailure(Call<TaskResponse> call, Throwable t) {
                    /**
                     * @TODO: Will be log error
                     */
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(getDrawerId());
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
    }

    /** @// TODO: 4/27/2017 change status to retofit2 **/
    public void startButtonClick(View v) throws Exception {
        setButtonStopped();
        String params = "status=in_process";
//        HttpClient.sendPut("api/v1/task/" + taskObject.get("id"), params, getApplicationContext());

    }

    public void finishButtonClick(View v) throws Exception {
        hideAllButtons();
        String params = "status=finished";
//        HttpClient.sendPut("api/v1/task/" + taskObject.get("id"), params, getApplicationContext());

    }

    public void pauseButtonClick(View v) throws Exception {
        setButtonStarted();
        String params = "status=new";
//        HttpClient.sendPut("api/v1/task/" + taskObject.get("id"), params, getApplicationContext());

    }

    private void setButtonStarted() {
        startButton.setVisibility(View.VISIBLE);
        finishButton.setVisibility(View.INVISIBLE);
        pauseButton.setVisibility(View.INVISIBLE);
    }

    private void setButtonStopped() throws Exception {
        startButton.setVisibility(View.INVISIBLE);
        finishButton.setVisibility(View.VISIBLE);
        pauseButton.setVisibility(View.VISIBLE);
    }

    private void hideAllButtons() {
        startButton.setVisibility(View.INVISIBLE);
        finishButton.setVisibility(View.INVISIBLE);
        pauseButton.setVisibility(View.INVISIBLE);
    }


    @Override
    protected int getDrawerId() {
        return R.id.activity_one_task;
    }
}
