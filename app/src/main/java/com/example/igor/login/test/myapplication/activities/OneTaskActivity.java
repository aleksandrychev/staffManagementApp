package com.example.igor.login.test.myapplication.activities;


import android.os.Bundle;
import com.example.igor.login.test.myapplication.R;

public class OneTaskActivity extends BaseActivity {
    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_one_task);
    }
}
