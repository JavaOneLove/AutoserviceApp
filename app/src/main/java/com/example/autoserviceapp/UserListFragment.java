package com.example.autoserviceapp;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.fragment.app.ListFragment;

import com.example.autoserviceapp.adapter.UserListAdapter;
import com.example.autoserviceapp.fragmentData.FragmentDataListener;
import com.example.autoserviceapp.model.User;
import com.example.autoserviceapp.retrofitInterfaceAPI.JsonPlaceHolderApi;

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
    private FragmentDataListener fragmentDataListener;
    private List<User> users = new ArrayList<>();
    private JsonPlaceHolderApi jsonPlaceHolderApi;
    private ListView usersList;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_list, container, false);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.0.13:8080/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);
        getUserList();
        usersList = view.findViewById(android.R.id.list);
        usersList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                User selectUser = (User) parent.getItemAtPosition(position);
                Log.i("MyLOG",selectUser.getUsername());
                Log.i("MyLOG", "itemClick: position = " + position + ", id = "
                        + id);
            }
        });
        usersList.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                Log.d("MyLOG", "itemSelect: position = " + position + ", id = "
                        + id);
            }

            public void onNothingSelected(AdapterView<?> parent) {
                Log.d("MyLOG", "itemSelect: nothing");
            }
        });

        return view;
    }

    private void getUserList(){
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
                else {
                    users.addAll(response.body());
                    UserListAdapter adapter = new UserListAdapter(getActivity(),
                            R.layout.user_list_item, users);

                    usersList.setAdapter(adapter);
                    usersList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        User selectUser = (User) parent.getItemAtPosition(position);
                      int text =  selectUser.getId();
                        Log.i("MyLOG",selectUser.getUsername());
                        fragmentDataListener.openUserDetailsFragment(Integer.toString(text));
                    }
                });
                }
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
