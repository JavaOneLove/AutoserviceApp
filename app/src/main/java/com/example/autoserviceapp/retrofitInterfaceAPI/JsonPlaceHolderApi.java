package com.example.autoserviceapp.retrofitInterfaceAPI;

import com.example.autoserviceapp.model.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface JsonPlaceHolderApi {
    @POST("home/login")
    Call<User> Login(@Body User user);


    @Headers("Content-Type: application/json")
    @POST("registration")
    Call<User> Registration(@Body User user);

    @Headers("Content-Type: application/json")
    @GET("home/userList")
    Call<List<User>> getUsersList();
}
