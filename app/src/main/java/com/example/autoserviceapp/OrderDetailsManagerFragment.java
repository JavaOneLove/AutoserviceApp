package com.example.autoserviceapp;


import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.autoserviceapp.model.Order;
import com.example.autoserviceapp.retrofitInterfaceAPI.JsonPlaceHolderApi;
import com.google.android.material.textfield.TextInputEditText;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class OrderDetailsManagerFragment extends Fragment {
    static final String KEY = "text";
    private JsonPlaceHolderApi jsonPlaceHolderApi;
    private Order order;
    private TextView textHeader,textVehicle,textDate;
    private TextInputEditText textComment;
    private String id;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_order_details_manager, container, false);
        Button buttonAccept = view.findViewById(R.id.buttonAccept);
        Button buttonDeni = view.findViewById(R.id.buttonDeni);
        textHeader = view.findViewById(R.id.textHeader);
        textVehicle = view.findViewById(R.id.textVehicle);
        textDate = view.findViewById(R.id.currentDateTime);
        textComment =view.findViewById(R.id.textComment);
        id = getArguments().getString(KEY);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.0.13:8080/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);
        getOrderDetails();
        buttonAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                order.setStatus("Завершен");
                updateOrder(order);
            }
        });
        buttonDeni.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                order.setStatus("Отклонен");
                updateOrder(order);
            }
        });
        return view;
    }

    private void updateOrder(Order order){
        Call<Order> call =jsonPlaceHolderApi.updateOrder(order);
        call.enqueue(new Callback<Order>() {
            @Override
            public void onResponse(Call<Order> call, Response<Order> response) {
                if (!response.isSuccessful()) {
                    Toast toast = Toast.makeText(getContext(),
                            "Code: " + response.code(), Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                }
            }

            @Override
            public void onFailure(Call<Order> call, Throwable t) {
                Toast toast = Toast.makeText(getContext(),
                        t.getMessage(), Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
            }
        });
    }

    private void getOrderDetails(){
        Call<Order> call =jsonPlaceHolderApi.getOrderDetails(Integer.parseInt(id));
        call.enqueue(new Callback<Order>() {
            @Override
            public void onResponse(Call<Order> call, Response<Order> response) {
                if (!response.isSuccessful()) {
                    Toast toast = Toast.makeText(getContext(),
                            "Code: " + response.code(), Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                }
                order = response.body();
                Log.i("MyLog", Integer.toString(order.getId()));
                textHeader.setText(order.getWork());
                textVehicle.setText(order.getPrimaryVehicle().getMark());
                textDate.setText(order.getDate());
                textComment.setText(order.getComment());
            }

            @Override
            public void onFailure(Call<Order> call, Throwable t) {
                Toast toast = Toast.makeText(getContext(),
                        t.getMessage(), Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
            }
        });
    }

}
