package com.example.autoserviceapp;


import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.autoserviceapp.adapter.VehicleListAdapter;
import com.example.autoserviceapp.fragmentData.FragmentDataListener;
import com.example.autoserviceapp.model.Order;
import com.example.autoserviceapp.model.User;
import com.example.autoserviceapp.model.Vehicle;
import com.example.autoserviceapp.retrofitInterfaceAPI.JsonPlaceHolderApi;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class OrderDetailsFragment extends Fragment {
    private TextView currentDateTime;
    private JsonPlaceHolderApi jsonPlaceHolderApi;
    private Calendar dateAndTime = Calendar.getInstance();
    private FragmentDataListener fragmentDataListener;
    private Spinner spinner;
    private TextView textHeader;
    private TextInputEditText textComment;
    static final String KEY = "";
    static final String Status = "В обработке";
    private User user;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_order_details, container, false);
        currentDateTime = view.findViewById(R.id.currentDateTime);
        setInitialDateTime();
        Button buttonDate = view.findViewById(R.id.button3);
        spinner = view.findViewById(R.id.vehicleChoose);
        textHeader = view.findViewById(R.id.textHeader);
        String textHead = getArguments().getString(KEY);
        textComment = view.findViewById(R.id.textComment);
        textHeader.setText(textHead);
        Button buttonAccept = view.findViewById(R.id.buttonAccept);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.0.13:8080/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);
        buttonAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createOrder();
            }
        });
        AdapterView.OnItemSelectedListener itemSelectedListener = new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
               // vehicle = (Vehicle) parent.getItemAtPosition(position);
               // Log.i("MyLOG----------",vehicle.getMark());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        };
        buttonDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setDate(v);
            }
        });
        //getUserDetails();
        getVehicleList();
        return view;
    }


    public void setDate(View v) {
        new DatePickerDialog(getActivity(), d,
                dateAndTime.get(Calendar.YEAR),
                dateAndTime.get(Calendar.MONTH),
                dateAndTime.get(Calendar.DAY_OF_MONTH))
                .show();
    }

    private void setInitialDateTime() {

        currentDateTime.setText(DateUtils.formatDateTime(getActivity(),
                dateAndTime.getTimeInMillis(),
                DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_SHOW_YEAR
                        | DateUtils.FORMAT_SHOW_TIME));
    }

    DatePickerDialog.OnDateSetListener d=new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            dateAndTime.set(Calendar.YEAR, year);
            dateAndTime.set(Calendar.MONTH, monthOfYear);
            dateAndTime.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            setInitialDateTime();
        }
    };

    private void createOrder(){
        Vehicle vehicle = (Vehicle) spinner.getSelectedItem();
        Order order = new Order(currentDateTime.getText().toString(),
                textHeader.getText().toString(),
                Status,textComment.getText().toString(),user,vehicle);
        Call<Order> call = jsonPlaceHolderApi.createOrder(order);
        call.enqueue(new Callback<Order>() {
            @Override
            public void onResponse(Call<Order> call, Response<Order> response) {
                if (!response.isSuccessful()) {
                    Toast toast = Toast.makeText(getContext(),
                            "Code: " + response.code(), Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                    return;
                }
            }

            @Override
            public void onFailure(Call<Order> call, Throwable t) {
                Toast toast = Toast.makeText(getContext(),
                        t.getMessage(), Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
                Log.i("InfoLog",t.getMessage());
            }
        });
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
                List<Vehicle> userVehicles = new ArrayList<>();
                if (!vehicleList.isEmpty()) {
                    for (Vehicle vehicle : vehicleList) {
                        if (vehicle.getPrimaryUser().getId() == user.getId()) {
                            userVehicles.add(vehicle);
                        }
                    }
                }
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
    /*private void getUserDetails(){
        Call<User> call = jsonPlaceHolderApi.getUserDetailsByName();
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
                Log.i("MyLOG -----",user.getUsername());
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
    }*/
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
