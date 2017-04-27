package com.example.igor.login.test.myapplication.responseObjects.auth;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AuthResponse {

    @SerializedName("success")
    @Expose
    private Boolean success;
    @SerializedName("data")
    @Expose
    private AuthResponseData data;

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public AuthResponseData getData() {
        return data;
    }

    public void setData(AuthResponseData data) {
        this.data = data;
    }

}

