package com.example.autoserviceapp;

import com.example.autoserviceapp.model.Order;
import com.example.autoserviceapp.model.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface JsonPlaceHolderApi {
    @POST("home/login")
    Call<User> Loggin(@Body User user);
}
