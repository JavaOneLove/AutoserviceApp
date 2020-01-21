package com.example.autoserviceapp;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.autoserviceapp.adapter.VehicleListAdapter;
import com.example.autoserviceapp.fragmentData.FragmentDataListener;
import com.example.autoserviceapp.fragmentData.SQLiteHelper;
import com.example.autoserviceapp.model.User;
import com.example.autoserviceapp.model.Vehicle;
import com.example.autoserviceapp.retrofitInterfaceAPI.JsonPlaceHolderApi;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {
    private FragmentDataListener fragmentDataListener;
    private JsonPlaceHolderApi jsonPlaceHolderApi;
    private EditText editUsername;
    private EditText editEmail;
    private EditText editPassword;
    private Spinner spinner;
    private SQLiteHelper sqLiteHelper;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_profile, container, false);
        sqLiteHelper = new SQLiteHelper(getContext());
       editUsername = v.findViewById(R.id.editTextUsername);
       editEmail = v.findViewById(R.id.editTextEmail);
       editPassword = v.findViewById(R.id.editTextPassword);
       spinner = v.findViewById(R.id.spinner);
       Button buttonUserUpdate = v.findViewById(R.id.buttonUserUpdate);
       Button buttonAddVehicle = v.findViewById(R.id.buttonAddVehicle);
       Button buttonUpdateVehicle = v.findViewById(R.id.buttonUpdateVehicle);
       Button buttonLogOut = v.findViewById(R.id.buttonLogOut);
       buttonUserUpdate.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               updateUser();
           }
       });
       buttonAddVehicle.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
            fragmentDataListener.openAddVehicleFragment();
           }
       });
       buttonUpdateVehicle.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {

           }
       });
       buttonLogOut.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               sqLiteHelper.onDelete();
               Intent HomeActivity = new Intent(getActivity(),HomeActivity.class);
               startActivity(HomeActivity);
           }
       });

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.0.13:8080/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);
        getUserDetailsByName();
        getVehicleList();
        AdapterView.OnItemSelectedListener itemSelectedListener = new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Vehicle item = (Vehicle) parent.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        };
        spinner.setOnItemSelectedListener(itemSelectedListener);
        return v;
    }

    private void getVehicleList(){
        final List<Vehicle> vehicleList = new ArrayList<>();
        Call<List<Vehicle>> call = jsonPlaceHolderApi.getVehicleList();
        call.enqueue(new Callback<List<Vehicle>>() {
            @Override
            public void onResponse(Call<List<Vehicle>> call, Response<List<Vehicle>> response) {
                if (!response.isSuccessful()) {
                    Toast toast = Toast.makeText(getContext(),
                            "Code: " + response.code(), Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                    return;
                }
                vehicleList.addAll(response.body());
                VehicleListAdapter adapter = new VehicleListAdapter(getActivity(),R.layout.vehicle_list_item,vehicleList);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<List<Vehicle>> call, Throwable t) {
                Toast toast = Toast.makeText(getContext(),
                        t.getMessage(), Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
                Log.i("InfoLog",t.getMessage());
            }
        });
    }

    private void updateUser(){
        User user = new User(editUsername.getText().toString(),editEmail.getText().toString(),editPassword.getText().toString());
        Call<User> call = jsonPlaceHolderApi.updateUser(user);

        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {

                if (!response.isSuccessful()) {
                    Toast toast = Toast.makeText(getContext(),
                            "Code: " + response.code(), Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                    return;
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

    private void getUserDetailsByName(){
        Call<User> call = jsonPlaceHolderApi.getUserDetailsByName(sqLiteHelper.getName());
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (!response.isSuccessful()) {
                    Toast toast = Toast.makeText(getContext(),
                            "Code: " + response.code(), Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                }
               User user = response.body();
                editUsername.setText(user.getUsername());
                editEmail.setText(user.getEmail());
                editPassword.setText(user.getPassword());
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
