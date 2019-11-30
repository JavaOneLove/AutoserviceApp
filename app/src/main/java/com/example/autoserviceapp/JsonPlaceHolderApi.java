package com.example.autoserviceapp;

import com.example.autoserviceapp.model.Order;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface JsonPlaceHolderApi {
    @GET("posts")
    Call<List<Order>> getOrders();
}
