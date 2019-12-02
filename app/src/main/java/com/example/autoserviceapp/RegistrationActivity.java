package com.example.autoserviceapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class RegistrationActivity extends AppCompatActivity implements View.OnClickListener {
    Button RedirectToLogin;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registration);
        RedirectToLogin = findViewById(R.id.buttonRegistrationBack);
    }

    @Override
    public void onClick(View v) {
        Intent backLogin = new Intent(RegistrationActivity.this,LoginActivity.class);
        startActivity(backLogin);
    }
}
