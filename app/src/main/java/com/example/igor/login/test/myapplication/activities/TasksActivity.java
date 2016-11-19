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
import android.widget.ProgressBar;
import android.widget.TabHost;
import android.widget.Toast;


import com.example.igor.login.test.myapplication.R;
import com.example.igor.login.test.myapplication.clients.HttpClient;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


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
    private ProgressBar spinner;
    private TabHost tabHost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aktivity_tasks);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null)
            setSupportActionBar(toolbar);
        spinner = (ProgressBar)findViewById(R.id.progressBar1);

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

    private void initTasksListNew(){
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
                if(firstVisibleItem+visibleItemCount == totalItemCount && totalItemCount!=0 && totalItemsCount.get("new") != totalItemCount)
                {

                    int page = totalItemCount / 10 + 1;
                    /**
                     * set list view
                     */
                    setTaskViewItems(page, "new", idsNew, adapterNew);
                }
            }
        });
    }

    private void initTasksListProcess(){
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
                if(firstVisibleItem+visibleItemCount == totalItemCount && totalItemCount!=0 && totalItemsCount.get("in_process") != totalItemCount)
                {

                    int page = totalItemCount / 10 + 1;
                    /**
                     * set list view
                     */
                    setTaskViewItems(page, "in_process", idsProcess, adapterProcess);
                }
            }
        });
    }

    private void initTasksListFinished(){
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
                if(firstVisibleItem+visibleItemCount == totalItemCount && totalItemCount!=0 && totalItemsCount.get("finished") != totalItemCount)
                {

                    int page = totalItemCount / 10 + 1;
                    /**
                     * set list view
                     */
                    setTaskViewItems(page, "finished", idsFinished, adapterFinish);
                }
            }
        });
    }


    private void setTaskViewItems(int page, String status, List ids, ArrayAdapter adapter){

        spinner.setVisibility(View.VISIBLE);

        try {
            String response = HttpClient.getTasks(page, status, getApplicationContext());
            JSONObject jsonResponse = new JSONObject(response);
            Integer tc = (Integer) jsonResponse.get("total");
            totalItemsCount.put(status, tc);

            JSONArray cast = jsonResponse.getJSONArray("data");
            for (int i = 0; i < cast.length(); i++) {
                JSONObject actor = cast.getJSONObject(i);
                String name = actor.getString("name");
                adapter.add(name);
                ids.add(actor.getString("id"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        spinner.setVisibility(View.GONE);
    }

    private void initTabHost(){
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

        // вторая вкладка будет выбрана по умолчанию
        tabHost.setCurrentTabByTag("new");

        // обработчик переключения вкладок
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
