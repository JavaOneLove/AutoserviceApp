package com.example.autoserviceapp;


import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.autoserviceapp.fragmentData.FragmentDataListener;
import com.example.autoserviceapp.fragmentData.SQLiteHelper;
import com.example.autoserviceapp.model.User;
import com.example.autoserviceapp.model.Vehicle;
import com.example.autoserviceapp.retrofitInterfaceAPI.JsonPlaceHolderApi;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class AddVehicleFragment extends Fragment {
    private FragmentDataListener fragmentDataListener;
    private JsonPlaceHolderApi jsonPlaceHolderApi;
    private User user;
    private EditText editMark;
    private EditText editModel;
    private EditText editColor;
    private EditText editDate;
    private SQLiteHelper sqLiteHelper;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_vehicle, container, false);
        sqLiteHelper = new SQLiteHelper(getContext());
        editMark = view.findViewById(R.id.editMark);
        editModel = view.findViewById(R.id.editModel);
        editColor = view.findViewById(R.id.editColor);
        editDate = view.findViewById(R.id.editDate);
        Button buttonDateChange = view.findViewById(R.id.buttonDateChange);
        Button buttonAddVehicle = view.findViewById(R.id.buttonAddVehicle);
        Button buttonReturnToProfile = view.findViewById(R.id.buttonReturnToProfile);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.0.13:8080/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);
        getUserDetails();
        buttonDateChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        buttonAddVehicle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            createVehicle();
            }
        });
        buttonReturnToProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            fragmentDataListener.openProfileFragment();
            }
        });
        return view;
    }
    private void createVehicle() {
        Vehicle vehicle = new Vehicle(editMark.getText().toString()
                ,editModel.getText().toString()
                ,editColor.getText().toString()
                ,editDate.getText().toString()
                ,user);
        Call<Vehicle> call = jsonPlaceHolderApi.createVehicle(vehicle);
        call.enqueue(new Callback<Vehicle>() {
            @Override
            public void onResponse(Call<Vehicle> call, Response<Vehicle> response) {
                if (!response.isSuccessful()) {
                    Toast toast = Toast.makeText(getContext(),
                            "Code: " + response.code(), Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                    return;
                }
            }

            @Override
            public void onFailure(Call<Vehicle> call, Throwable t) {
                Toast toast = Toast.makeText(getContext(),
                        t.getMessage(), Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
                Log.i("InfoLog",t.getMessage());
            }
        });
    }
    private void getUserDetails(){
        Call<User> call = jsonPlaceHolderApi.getUserDetailsByName(sqLiteHelper.getName());
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (!response.isSuccessful()) {
                    Toast toast = Toast.makeText(getContext(),
                            "Code: " + response.code(), Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                  user = response.body();
                }
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
