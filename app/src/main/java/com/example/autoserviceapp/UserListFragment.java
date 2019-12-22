package com.example.autoserviceapp;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.ListFragment;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;


import com.example.autoserviceapp.adapter.UserListAdapter;
import com.example.autoserviceapp.model.User;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class UserListFragment extends ListFragment {

    public UserListFragment(){

    }

    private User userTest = new User("Username","UserEmail","UserPass");
    private User userTest1 = new User("Username","UserEmail","UserPass");
    private User userTest2 = new User("Username","UserEmail","UserPass");
    private User userTest3 = new User("Username","UserEmail","UserPass");
    final String[] catNames = new String[]{"Рыжик", "Барсик", "Мурзик",
            "Мурка", "Васька", "Томасина", "Кристина", "Пушок", "Дымка",
            "Кузя", "Китти", "Масяня", "Симба"};
    private List<User> users = new ArrayList<>();
    private JsonPlaceHolderApi jsonPlaceHolderApi;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_list, container, false);
        //Retrofit retrofit = new Retrofit.Builder()
        //        .baseUrl("http://192.168.0.13:8080/")
         //       .addConverterFactory(GsonConverterFactory.create())
         //      .build();

       // jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);
        users.add(userTest);
        users.add(userTest1);
        users.add(userTest2);
        users.add(userTest3);
       // getUserList();
        UserListAdapter adapter = new UserListAdapter(getContext(),
                R.layout.user_list_item, users);
        ListView usersList = view.findViewById(R.id.user_list);
        usersList.setAdapter(adapter);

    return view;
    }

    public void getUserList(){
        Call<List<User>> call = jsonPlaceHolderApi.getUsersList();
        call.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                if (!response.isSuccessful()) {
                    Toast toast = Toast.makeText(getContext(),
                            "Code: " + response.code(), Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                }
                     users = response.body();
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                Toast toast = Toast.makeText(getContext(),
                        t.getMessage(), Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
            }
        });
    }

}
