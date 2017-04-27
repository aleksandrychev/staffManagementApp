package com.example.igor.login.test.myapplication.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;;
import android.support.v7.widget.Toolbar;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.Toast;


import com.example.igor.login.test.myapplication.R;
import com.example.igor.login.test.myapplication.heplers.PreferenceHelper;
import com.example.igor.login.test.myapplication.models.api.interfaces.Tasks;
import com.example.igor.login.test.myapplication.models.api.interfaces.responseObjects.tasks.Datum;
import com.example.igor.login.test.myapplication.models.api.interfaces.responseObjects.tasks.TasksResponse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class TasksActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private List<String> allNamesNew = new ArrayList<>();
    private List<String> allNamesProcess = new ArrayList<>();
    private List<String> allNamesFinish = new ArrayList<>();

    private List<String> idsNew = new ArrayList<>();
    private List<String> idsProcess = new ArrayList<>();
    private List<String> idsFinished = new ArrayList<>();

    private ListView tasksListNew, tasksListProcess, taskListFinish;
    private ArrayAdapter<String> adapterNew, adapterProcess, adapterFinish;
    private Map<String, Integer> totalItemsCount = new HashMap<>();
    //    private ProgressBar spinner;
    private TabHost tabHost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aktivity_tasks);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null)
            setSupportActionBar(toolbar);
//        spinner = (ProgressBar) findViewById(R.id.progressBar1);

        initTabHost();
        initTasksListNew();
        initTasksListProcess();
        initTasksListFinished();


        DrawerLayout drawer = (DrawerLayout) findViewById(getDrawerId());
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
    }

    private void initTasksListNew() {
        tasksListNew = (ListView) findViewById(R.id.tasks_list_new);
        adapterNew = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, allNamesNew);
        tasksListNew.setAdapter(adapterNew);
        /**
         * set list view
         */
        setTaskViewItems(1, "new", idsNew, adapterNew);
        tasksListNew.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {
                Intent intent = new Intent(TasksActivity.this, OneTaskActivity.class);
                intent.putExtra("taskId", idsNew.get(position));
                startActivity(intent);
            }
        });
        tasksListNew.setOnScrollListener(new AbsListView.OnScrollListener() {

            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            public void onScroll(AbsListView view, int firstVisibleItem,
                                 int visibleItemCount, int totalItemCount) {
                /**
                 * @// TODO: 4/20/2017 visible item issue, because on horizontposition it is 3
                 */
                if (firstVisibleItem + visibleItemCount == totalItemCount && totalItemCount != 0 && totalItemsCount.get("new") != totalItemCount) {

                    int page = totalItemCount / 10 + 1;
                    /**
                     * set list view
                     */
                    setTaskViewItems(page, "new", idsNew, adapterNew);
                }
            }
        });
    }

    private void initTasksListProcess() {
        tasksListProcess = (ListView) findViewById(R.id.tasks_list_in_process);
        adapterProcess = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, allNamesProcess);
        tasksListProcess.setAdapter(adapterProcess);
        /**
         * set list view
         */
        setTaskViewItems(1, "in_process", idsProcess, adapterProcess);
        tasksListProcess.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {
                Intent intent = new Intent(TasksActivity.this, OneTaskActivity.class);
                intent.putExtra("taskId", idsProcess.get(position));
                startActivity(intent);
            }
        });
        tasksListProcess.setOnScrollListener(new AbsListView.OnScrollListener() {

            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            public void onScroll(AbsListView view, int firstVisibleItem,
                                 int visibleItemCount, int totalItemCount) {

                if (firstVisibleItem + visibleItemCount == totalItemCount && totalItemCount != 0 && totalItemsCount.get("in_process") != totalItemCount) {

                    int page = totalItemCount / 10 + 1;

                    /**
                     * set list view
                     */
                    setTaskViewItems(page, "in_process", idsProcess, adapterProcess);
                }
            }
        });
    }

    private void initTasksListFinished() {
        taskListFinish = (ListView) findViewById(R.id.tasks_list_in_finished);
        adapterFinish = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, allNamesFinish);
        taskListFinish.setAdapter(adapterFinish);
        /**
         * set list view
         */
        setTaskViewItems(1, "finished", idsFinished, adapterFinish);
        taskListFinish.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {
                Intent intent = new Intent(TasksActivity.this, OneTaskActivity.class);
                intent.putExtra("taskId", idsFinished.get(position));
                startActivity(intent);
            }
        });
        taskListFinish.setOnScrollListener(new AbsListView.OnScrollListener() {

            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            public void onScroll(AbsListView view, int firstVisibleItem,
                                 int visibleItemCount, int totalItemCount) {
                if (firstVisibleItem + visibleItemCount == totalItemCount && totalItemCount != 0 && totalItemsCount.get("finished") != totalItemCount) {

                    int page = totalItemCount / 10 + 1;
                    /**
                     * set list view
                     */
                    setTaskViewItems(page, "finished", idsFinished, adapterFinish);

                }
            }
        });
    }


    private void setTaskViewItems(int page, final String status, final List ids, final ArrayAdapter adapter) {

        /**
         * @// TODO: 4/20/2017 implement Authorization header for all requests
         */
        retrofit.create(Tasks.class).getTasks(page, status).enqueue(new Callback<TasksResponse>() {
            @Override
            public void onResponse(Call<TasksResponse> call, Response<TasksResponse> response) {


                totalItemsCount.put(status, response.body().getData().getTotal());
                List<Datum> tasks = response.body().getData().getData();

                for (int i = 0; i < tasks.size(); i++) {
                    adapter.add(tasks.get(i).getName());
                    ids.add(tasks.get(i).getId().toString());
                }

            }

            @Override
            public void onFailure(Call<TasksResponse> call, Throwable t) {
                /**
                 * @TODO: Will be log error
                 */
            }
        });


    }

    private void initTabHost() {
        tabHost = (TabHost) findViewById(R.id.tasksTabHost);
        tabHost.setup();
        TabHost.TabSpec tabSpec;

        /**
         * add new task tab
         */
        tabSpec = tabHost.newTabSpec("new");
        tabSpec.setIndicator("New");
        tabSpec.setContent(R.id.tab_new);
        tabHost.addTab(tabSpec);

        /**
         * add process task tab
         */
        tabSpec = tabHost.newTabSpec("in_process");
        tabSpec.setIndicator("In process");
        tabSpec.setContent(R.id.tab_in_process);
        tabHost.addTab(tabSpec);

        /**
         * add finished task tab
         */
        tabSpec = tabHost.newTabSpec("finished");
        tabSpec.setIndicator("Finished");
        tabSpec.setContent(R.id.tab_finished);
        tabHost.addTab(tabSpec);
        tabHost.setCurrentTabByTag("new");
        tabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            public void onTabChanged(String tabId) {
//                Toast.makeText(getBaseContext(), "tabId = " + tabId, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected int getDrawerId() {
        return R.id.drawer_layout;
    }
}
