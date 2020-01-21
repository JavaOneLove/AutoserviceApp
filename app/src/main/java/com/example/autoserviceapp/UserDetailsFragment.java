package com.example.autoserviceapp;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.autoserviceapp.fragmentData.SQLiteHelper;
import com.example.autoserviceapp.retrofitInterfaceAPI.JsonPlaceHolderApi;


public class UserDetailsFragment extends Fragment {

    private JsonPlaceHolderApi jsonPlaceHolderApi;
    private SQLiteHelper sqLiteHelper;
    private TextView textId,textEmail,textPass,textRole;

    public UserDetailsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_user_details, container, false);
        return view;
    }

}
