package com.example.autoserviceapp;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.autoserviceapp.fragmentData.FragmentDataListener;
import com.example.autoserviceapp.fragmentData.SQLiteHelper;
import com.example.autoserviceapp.model.User;
import com.example.autoserviceapp.retrofitInterfaceAPI.JsonPlaceHolderApi;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class HomeFragment extends Fragment {

    private FragmentDataListener fragmentDataListener;
    private JsonPlaceHolderApi jsonPlaceHolderApi;
    private SQLiteHelper sqLiteHelper;
    private User user;
    private String role;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

       View view = inflater.inflate(R.layout.fragment_home, container, false);
        Button buttonRepairEngine = view.findViewById(R.id.buttonRepairEngine);
        Button buttonETC = view.findViewById(R.id.buttonETC);
        Button buttonPainting = view.findViewById(R.id.buttonPainting);
        Button buttonRepairBody = view.findViewById(R.id.buttonRepairBody);
        Button buttonTireService = view.findViewById(R.id.buttonTireService);
        sqLiteHelper = new SQLiteHelper(getContext());
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.0.13:8080/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);
        role = sqLiteHelper.getName();
        if(role != null){
        getUserDetailsByName(role);
        }

        buttonRepairEngine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (role != null)
                fragmentDataListener.openOrderDetailsFragment("Починка двигателя");
                else{
                    Intent intent = new Intent(getActivity(),LoginActivity.class);
                    startActivity(intent);
                }

            }
        });
        buttonRepairBody.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (role != null)
                fragmentDataListener.openOrderDetailsFragment("Кузовные работы");
                else{
                    Intent intent = new Intent(getActivity(),LoginActivity.class);
                    startActivity(intent);
                }
            }
        });
        buttonTireService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (role != null)
                fragmentDataListener.openOrderDetailsFragment("Шиномонтаж");
                else{
                    Intent intent = new Intent(getActivity(),LoginActivity.class);
                    startActivity(intent);
                }
            }
        });
        buttonPainting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (role != null)
                fragmentDataListener.openOrderDetailsFragment("Покраска автомобиля");
                else{
                    Intent intent = new Intent(getActivity(),LoginActivity.class);
                    startActivity(intent);
                }
            }
        });
        buttonETC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (role != null)
                fragmentDataListener.openOrderDetailsFragment("ТО автомобиля");
                else{
                    Intent intent = new Intent(getActivity(),LoginActivity.class);
                    startActivity(intent);
                }
            }
        });

       return view;
    }

    private void getUserDetailsByName(String role){
        Call<User> call = jsonPlaceHolderApi.getUserDetailsByName(role);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (!response.isSuccessful()) {
                    Toast toast = Toast.makeText(getContext(),
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
