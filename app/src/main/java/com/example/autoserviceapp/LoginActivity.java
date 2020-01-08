package com.example.autoserviceapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.autoserviceapp.retrofitInterfaceAPI.JsonPlaceHolderApi;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    TextView RedirectToRegistration;
    TextView textEmail;
    TextView textPassword;
    SharedPreferences sPref;

    private JsonPlaceHolderApi jsonPlaceHolderApi;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        RedirectToRegistration = findViewById(R.id.textRedirectToRegistration);
        textEmail = findViewById(R.id.editEmailLog);
        textPassword = findViewById(R.id.editPasswordLog);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.0.13:8080/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);
    }

    public void Login(){
        Call<Integer> call = jsonPlaceHolderApi.Login(textEmail.getText().toString(),textPassword.getText().toString());
        call.enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {

                if (!response.isSuccessful()) {
                    Toast toast = Toast.makeText(getApplicationContext(),
                            "Code: " + response.code(), Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                }
                sPref = getSharedPreferences("User", MODE_PRIVATE);
                SharedPreferences.Editor ed = sPref.edit();
                ed.putInt("id", response.body());
                ed.commit();
            }

            @Override
            public void onFailure(Call<Integer> call, Throwable t) {
                Toast toast = Toast.makeText(getApplicationContext(),
                        t.getMessage(), Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
            }
        });
    }

    @Override
    public void onClick(View v) {
        Intent RegistrationActivity = new Intent(LoginActivity.this,RegistrationActivity.class);
        startActivity(RegistrationActivity);
    }
    public void OnClickLogin(View v){
        Login();
    }
    public void OnClickReturnToHome(View view){
        Intent intentGoHome = new Intent(this,HomeActivity.class);
        startActivity(intentGoHome);
    }
}
