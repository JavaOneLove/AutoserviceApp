package com.example.autoserviceapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
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
import com.example.autoserviceapp.model.Login;
import com.example.autoserviceapp.model.TestUser;
import com.example.autoserviceapp.retrofitInterfaceAPI.JsonPlaceHolderApi;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private SharedPreferences sPref;
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

    @SuppressLint("LongLogTag")
    private void saveToken(String access_token) {
        sPref = getSharedPreferences("token", MODE_PRIVATE);
        SharedPreferences.Editor ed = sPref.edit();
        ed.putString("access_token", access_token);
        Log.i("access_token save", access_token);
        ed.commit();
    }
    private void saveUsername(String entered_username) {
        sPref = getSharedPreferences("username", MODE_PRIVATE);
        SharedPreferences.Editor ed = sPref.edit();
        ed.putString("entered_username",entered_username);
        Log.i("entered_username save", entered_username);
        ed.commit();
    }

    public void Login(){
    Login login = new Login(textEmail.getText().toString(), textPassword.getText().toString());
        Call<TestUser> call = jsonPlaceHolderApi.login(login);
        call.enqueue(new Callback<TestUser>() {
            @Override
            public void onResponse(Call<TestUser> call, Response<TestUser> response) {

                if (!response.isSuccessful()) {
                    Toast toast = Toast.makeText(getApplicationContext(),
                            "Code: " + response.code(), Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                }

                    Intent backHome = new Intent(LoginActivity.this,HomeActivity.class);
                    startActivity(backHome);
                    if (response.body() != null) {
                        String token = response.body().getToken();
                        String username = response.body().getEmail();
                        Toast toast = Toast.makeText(getApplicationContext(),
                                "Token: " + token + " " + username , Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();
                        saveToken(token);
                        saveUsername(username);
                    }

            }

            @Override
            public void onFailure(Call<TestUser> call, Throwable t) {
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
