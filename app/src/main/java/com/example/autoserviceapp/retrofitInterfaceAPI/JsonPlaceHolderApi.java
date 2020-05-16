package com.example.autoserviceapp.retrofitInterfaceAPI;

import com.example.autoserviceapp.model.Login;
import com.example.autoserviceapp.model.Order;
import com.example.autoserviceapp.model.TestUser;
import com.example.autoserviceapp.model.User;
import com.example.autoserviceapp.model.Vehicle;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface JsonPlaceHolderApi {

    @Headers("Content-Type: application/json")
    @GET("home/orderDetails/{id}")
    Call<Order> getOrderDetails(@Path("id") int id);

    @Headers("Content-Type: application/json")
    @POST("home/createOrder")
    Call<Order> createOrder(@Body Order order);

    @Headers("Content-Type: application/json")
    @POST("home/deleteUser")
    Call<User> deleteUser(@Body User user);

    @Headers("Content-Type: application/json")
    @GET("home/vehicleList")
    Call<List<Vehicle>> getVehicleList();

    @Headers("Content-Type: application/json")
    @POST("home/createVehicle")
    Call<Vehicle> createVehicle(@Body Vehicle vehicle);

    @Headers("Content-Type: application/json")
    @GET("home/orderList")
    Call<List<Order>> getOrderList();

    @Headers("Content-Type: application/json")
    @POST("home/updateOrder")
    Call<Order> updateOrder(@Body Order order);
 //updated
    @Headers("Content-Type: application/json")
    @POST("api/auth/login")
    Call<TestUser> login(@Body Login login);

    @Headers("Content-Type: application/json")
    @POST("api/reg/registration")
    Call<User> Registration(@Body User user);

    @Headers("Content-Type: application/json")
    @GET("api/user/getUserVehicle")
    Call<List<Vehicle>> getUserVehicle(@Header("Authorization") String authorization);

    @Headers("Content-Type: application/json")
    @GET("api/user/userDetailsName")
    Call<User> getUserDetails(@Header("Authorization") String authorization);

    @Headers("Content-Type: application/json")
    @PUT("api/user/updateUser")
    Call<User> updateUser(@Header("Authorization") String authorization,@Body User user);

    @Headers("Content-Type: application/json")
    @GET("api/admin/userDetailsName/{name}")
    Call<User> getUserDetailsByName(@Header("Authorization") String authorization);

    @Headers("Content-Type: application/json")
    @GET("api/admin/userList")
    Call<List<User>> getUsersList(@Header("Authorization") String authorization);

    @Headers("Content-Type: application/json")
    @GET("api/admin/userDetails/{id}")
    Call<User> getUserDetails(@Header("Authorization") String authorization, @Path("id") Long id);

}
