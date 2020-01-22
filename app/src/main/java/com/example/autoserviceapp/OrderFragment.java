package com.example.autoserviceapp;


import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.autoserviceapp.adapter.OrderListAdapter;
import com.example.autoserviceapp.fragmentData.FragmentDataListener;
import com.example.autoserviceapp.fragmentData.SQLiteHelper;
import com.example.autoserviceapp.model.Order;
import com.example.autoserviceapp.model.User;
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
    private ListView ordersList;
    private SQLiteHelper sqLiteHelper;
    private User user;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_order, container, false);
        sqLiteHelper = new SQLiteHelper(getContext());
        ordersList = view.findViewById(android.R.id.list);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.0.13:8080/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);
        getUserDetails();
        getOrderList();
        return view;
    }

    private void getUserDetails(){
        Call<User> call = jsonPlaceHolderApi.getUserDetailsByName(sqLiteHelper.getName());
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (!response.isSuccessful()) {
                    Toast toast = Toast.makeText(getActivity(),
                            "Code: " + response.code(), Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                }
                user = response.body();
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast toast = Toast.makeText(getContext(),
                        t.getMessage(), Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
                Log.i("InfoLog",t.getMessage());
            }
        });
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
                List<Order> selectOrders = new ArrayList<>();
                List<Order> endList =new ArrayList<>();
                if(user.getRole().equals("USER")){
                    for (Order order: orders) {
                        if(order.getPrimaryUser().getId() == user.getId()){
                            selectOrders.add(order);
                            endList.addAll(selectOrders);
                        }
                    }
                }else{
                endList.addAll(orders);
                }
                OrderListAdapter adapter = new OrderListAdapter(getActivity(),
                        R.layout.order_list_item, orders);
                ordersList.setAdapter(adapter);
                ordersList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Order selectOrder = (Order) parent.getItemAtPosition(position);
                        int text =  selectOrder.getId();
                        Log.i("MyLOG",selectOrder.getStatus());
                        fragmentDataListener.openOrderDetailsManagerFragment(Integer.toString(text));
                    }
                });
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
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof FragmentDataListener) {
            fragmentDataListener = (FragmentDataListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragment1DataListener");
        }
    }
}
