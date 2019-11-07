package com.example.autoserviceapp;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class ContactsFragment extends Fragment implements View.OnClickListener {


    public ContactsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_contacts, container, false);
        TextView phoneText = v.findViewById(R.id.textPhone);
                return v;
    }

    @Override
    public void onClick(View v) {
        String phoneNumber = "8(900)777 77 77";
        Intent callIntent = new Intent(Intent.ACTION_DIAL, Uri.parse(phoneNumber));
        startActivity(callIntent);
    }
}
