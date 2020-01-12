package com.example.autoserviceapp;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.autoserviceapp.fragmentData.FragmentDataListener;
import com.example.autoserviceapp.fragmentData.SQLiteHelper;


public class OrderFragment extends Fragment {

    private FragmentDataListener fragmentDataListener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_order, container, false);
        SQLiteHelper sqLiteHelper = new SQLiteHelper(getContext());
        sqLiteHelper.onInsert("anime","a","p");
        Log.i("-------Log------",Integer.toString(sqLiteHelper.onRead()));
        sqLiteHelper.onDelete();
        Log.i("-------Log------",Integer.toString(sqLiteHelper.onRead()));
        return view;
    }
}
