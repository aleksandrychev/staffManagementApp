package com.example.igor.login.test.myapplication.models.api.interfaces;

import com.example.igor.login.test.myapplication.responseObjects.tasks.TasksResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface Task {
    @GET("task/{id}")
    Call<TasksResponse> getTask(@Path("id") int id);
}
