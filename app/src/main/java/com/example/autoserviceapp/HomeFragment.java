package com.example.autoserviceapp;


import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;

import com.example.autoserviceapp.fragmentData.FragmentDataListener;


public class HomeFragment extends Fragment {

    private FragmentDataListener fragmentDataListener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

       View view = inflater.inflate(R.layout.fragment_home, container, false);
        Button buttonRepairEngine = view.findViewById(R.id.buttonRepairEngine);
        Button buttonETC = view.findViewById(R.id.buttonETC);
        Button buttonPainting = view.findViewById(R.id.buttonPainting);
        Button buttonRepairBody = view.findViewById(R.id.buttonRepairBody);
        Button buttonTireService = view.findViewById(R.id.buttonTireService);

        buttonRepairEngine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentDataListener.openOrderDetailsFragment("Починка двигателя");
            }
        });
        buttonRepairBody.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentDataListener.openOrderDetailsFragment("Кузовные работы");
            }
        });
        buttonTireService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentDataListener.openOrderDetailsFragment("Шиномонтаж");
            }
        });
        buttonPainting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentDataListener.openOrderDetailsFragment("Покраска автомобиля");
            }
        });
        buttonETC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentDataListener.openOrderDetailsFragment("ТО автомобиля");
            }
        });

       return view;
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof FragmentDataListener) {
            fragmentDataListener = (FragmentDataListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragment1DataListener");
        }
    }

}
