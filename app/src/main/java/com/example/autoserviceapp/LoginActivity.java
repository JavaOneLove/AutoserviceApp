package com.example.autoserviceapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.autoserviceapp.fragmentData.SQLiteHelper;
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
    SQLiteHelper sqLiteHelper;
    Button buttonBackHome,buttonLogin;

    private JsonPlaceHolderApi jsonPlaceHolderApi;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        sqLiteHelper = new SQLiteHelper(getApplicationContext());
        RedirectToRegistration = findViewById(R.id.textRedirectToRegistration);
        textEmail = (EditText) findViewById(R.id.editEmailLog);
        textPassword = (EditText) findViewById(R.id.editPasswordLog);
        buttonLogin = findViewById(R.id.buttonLogIn);
        buttonBackHome = findViewById(R.id.buttonLoginBack);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.0.13:8080/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Login();
            }
        });
        buttonBackHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent backHome = new Intent(LoginActivity.this,HomeActivity.class);
                startActivity(backHome);
            }
        });

    }

    public void Login(){
        Log.i("MyLOG -------",textEmail.getText().toString());
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
                if (response.code() == 200) {
                    sqLiteHelper.onInsert(textEmail.getText().toString(),
                            textEmail.getText().toString(),
                            textPassword.getText().toString());
                    Intent backHome = new Intent(LoginActivity.this,HomeActivity.class);
                    startActivity(backHome);
                }
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
}
