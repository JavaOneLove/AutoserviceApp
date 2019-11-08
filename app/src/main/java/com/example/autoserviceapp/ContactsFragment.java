package com.example.autoserviceapp;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class ContactsFragment extends Fragment implements View.OnClickListener {
    Context context;

    private String phone = "8(900)000 00 00";
    private String email = "autoservice@mail.com";
    private String address = "ул. Пушкина дом. Колотушкина";
    private String webSite = "www.autoservice.com";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_contacts, container, false);
        TextView phoneText = v.findViewById(R.id.textPhone);
        TextView emailText = v.findViewById(R.id.textMail);
        TextView addressText = v.findViewById(R.id.textAddress);
        TextView webSiteText = v.findViewById(R.id.textWebSite);
        phoneText.setOnClickListener(this);
        phoneText.setText("Номер телефона: " + phone);
        emailText.setText("Email:: " + email);
        addressText.setText("Физический адрес:: " + address);
        webSiteText.setText("Web site:: " + webSite);
                return v;
    }
    @SuppressLint("MissingPermission")
    @Override
    public void onClick(View v) {
        Intent callIntent = new Intent(Intent.ACTION_CALL);
        callIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        callIntent.setData(Uri.parse("tel:" + phone));
        getActivity().startActivity(callIntent);
    }
}
