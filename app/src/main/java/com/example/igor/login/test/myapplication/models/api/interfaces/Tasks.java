package com.example.igor.login.test.myapplication.models.api.interfaces;

import com.example.igor.login.test.myapplication.models.api.interfaces.responseObjects.tasks.TasksResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

public interface Tasks {
    @GET("task")
    Call<TasksResponse> getTasks(@Query("page") int page, @Query("status") String status);
}
