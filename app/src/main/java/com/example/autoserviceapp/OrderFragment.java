package com.example.autoserviceapp;


import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.autoserviceapp.fragmentData.FragmentDataListener;
import com.example.autoserviceapp.model.Order;
import com.example.autoserviceapp.retrofitInterfaceAPI.JsonPlaceHolderApi;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class OrderFragment extends Fragment {

    private FragmentDataListener fragmentDataListener;
    private JsonPlaceHolderApi jsonPlaceHolderApi;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_order, container, false);
       // SQLiteHelper sqLiteHelper = new SQLiteHelper(getContext());
        //sqLiteHelper.onInsert("anime","a","p");
       // Log.i("-------Log------",Integer.toString(sqLiteHelper.onRead()));
        //sqLiteHelper.onDelete();
       // Log.i("-------Log------",Integer.toString(sqLiteHelper.onRead()));

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.0.13:8080/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);

        return view;
    }

    private void getOrderList(){
        Call<List<Order>> call = jsonPlaceHolderApi.getOrderList();
        call.enqueue(new Callback<List<Order>>() {
            @Override
            public void onResponse(Call<List<Order>> call, Response<List<Order>> response) {
                if (!response.isSuccessful()) {
                    Toast toast = Toast.makeText(getActivity(),
                            "Code: " + response.code(), Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                }
                List<Order> orders = new ArrayList<>();
                orders.addAll(response.body());
            }

            @Override
            public void onFailure(Call<List<Order>> call, Throwable t) {
                Toast toast = Toast.makeText(getContext(),
                        t.getMessage(), Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
                Log.i("InfoLog",t.getMessage());
            }
        });
    }
}
