package com.example.igor.login.test.myapplication.models.api.interfaces;


import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Query;
import com.example.igor.login.test.myapplication.responseObjects.auth.AuthResponse;

public interface Auth {

    @POST("auth/login")
    Call<AuthResponse> login(@Query("email") String email, @Query("password") String password);

}
