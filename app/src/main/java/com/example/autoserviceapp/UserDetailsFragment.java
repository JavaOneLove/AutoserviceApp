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

import com.example.autoserviceapp.fragmentData.SQLiteHelper;
import com.example.autoserviceapp.model.User;
import com.example.autoserviceapp.retrofitInterfaceAPI.JsonPlaceHolderApi;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class UserDetailsFragment extends Fragment {

    private JsonPlaceHolderApi jsonPlaceHolderApi;
    private SQLiteHelper sqLiteHelper;
    private TextView textId,textEmail,textPass,textRole,textUsername;
    static final String KEY = "text";
    private User user;
    private String id;

    public UserDetailsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_user_details, container, false);
        textId = view.findViewById(R.id.textIdUser);
        textEmail = view.findViewById(R.id.textEmailUser);
        textPass = view.findViewById(R.id.textPasswordUser);
        textUsername =view.findViewById(R.id.textUsernameUser);
        textRole = view .findViewById(R.id.textUserRole);
        Button buttonDeleteUser = view.findViewById(R.id.buttonDeleteUser);
        id = getArguments().getString(KEY);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.0.13:8080/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);
        getUserDetails();
        buttonDeleteUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteUser();
            }
        });
        return view;
    }

    private void deleteUser(){
        Call<User> call = jsonPlaceHolderApi.deleteUser(user);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (!response.isSuccessful()) {
                    Toast toast = Toast.makeText(getContext(),
                            "Code: " + response.code(), Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast toast = Toast.makeText(getContext(),
                        t.getMessage(), Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();            }
        });
    }

    private void getUserDetails(){
        Call<User> call =jsonPlaceHolderApi.getUserDetails(Integer.parseInt(id));
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
                Log.i("MyLog", Integer.toString(user.getId()));
                textId.setText(Integer.toString(user.getId()));
                textEmail.setText(user.getEmail());
                textPass.setText(user.getPassword());
                textUsername.setText(user.getUsername());
                textRole.setText(user.getRole());
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast toast = Toast.makeText(getContext(),
                        t.getMessage(), Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
            }
        });
    }

}
