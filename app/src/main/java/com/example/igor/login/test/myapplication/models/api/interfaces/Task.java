package com.example.igor.login.test.myapplication.models.api.interfaces;

import com.example.igor.login.test.myapplication.responseObjects.task.TaskResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface Task {
    @GET("task/{id}")
    Call<TaskResponse> getTask(@Path("id") String id);
}
