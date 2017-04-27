package com.example.igor.login.test.myapplication.interceptors;

import android.content.Context;

import com.example.igor.login.test.myapplication.heplers.PreferenceHelper;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Ihor on 4/20/2017.
 */

public class AuthorizationInterceptor implements Interceptor {

    protected Context context;

    @Override
    public Response intercept(Interceptor.Chain chain) throws IOException {
        Request request = chain.request();
        request = request.newBuilder().header("Authorization", "Bearer " + PreferenceHelper.getDefaults("tokenKey", context)).build();
        Response response = chain.proceed(request);

        return response;
    }

    public void setContext(Context context)
    {
        this.context = context;
    }
}