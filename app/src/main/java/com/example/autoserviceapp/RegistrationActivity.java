package com.example.autoserviceapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.autoserviceapp.model.User;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RegistrationActivity extends AppCompatActivity implements View.OnClickListener {
    private JsonPlaceHolderApi jsonPlaceHolderApi;
    Button RedirectToLogin;
    Button RegUser;
    TextView EmailText;
    TextView PasswordText;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registration);
        RedirectToLogin = findViewById(R.id.buttonRegistrationBack);
        RegUser = findViewById(R.id.buttonRegistration);
        EmailText = findViewById(R.id.editMailRegistration);
        PasswordText = findViewById(R.id.editPasswordRegistration);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.0.13:8080/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);
    }

    public void Registration(){
        User user = new User("",EmailText.getText().toString(),PasswordText.getText().toString());
        Call<User> call = jsonPlaceHolderApi.Registration(user);

        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {

                if (!response.isSuccessful()) {
                    Toast toast = Toast.makeText(getApplicationContext(),
                            "Code: " + response.code(), Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                    return;
                }

            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast toast = Toast.makeText(getApplicationContext(),
                        t.getMessage(), Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
            }
        });
    }

    @Override
    public void onClick(View v) {
        Intent backLogin = new Intent(RegistrationActivity.this,LoginActivity.class);
        startActivity(backLogin);
    }
    public void onClickRegistration(View v){
        Registration();
    }
}
