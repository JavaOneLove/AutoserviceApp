package com.example.autoserviceapp;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {

    EditText editName;
    EditText editSurname;
    EditText editEmail;
    EditText editPhone;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_profile, container, false);
       editName = v.findViewById(R.id.editTextName);
       editSurname = v.findViewById(R.id.editTextSurname);
       editEmail = v.findViewById(R.id.editTextEmail);
       editPhone = v.findViewById(R.id.editTextPhone);
        return v;
    }

}
