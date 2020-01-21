package com.example.autoserviceapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.example.autoserviceapp.fragmentData.FragmentDataListener;
import com.example.autoserviceapp.fragmentData.SQLiteHelper;
import com.example.autoserviceapp.model.User;
import com.example.autoserviceapp.retrofitInterfaceAPI.JsonPlaceHolderApi;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HomeActivity extends AppCompatActivity implements FragmentDataListener {
    JsonPlaceHolderApi jsonPlaceHolderApi;
    public String role = "NO_AUTH";
    BottomNavigationView bottomNavigationView;// 0 is no auth, 1 is auth, 3 is manager, 2 is administrator
    SQLiteHelper sqLiteHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sqLiteHelper = new SQLiteHelper(getApplicationContext());
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.0.13:8080/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);
         bottomNavigationView = findViewById(R.id.btm_nav);
                HomeFragment fragment = new HomeFragment();
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.frame_layout, fragment);
                fragmentTransaction.commit();


                String name = sqLiteHelper.getName();
                if(name != null){
                    getUserDetailsByName(name);
                }
                else{
                    getUserMenu("NO_AUTH");
                }
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                int id = menuItem.getItemId();

                if (id == R.id.profile) {
                    ProfileFragment fragment = new ProfileFragment();
                    FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.frame_layout, fragment);
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();
                }
                if (id == R.id.order) {
                    OrderFragment fragment = new OrderFragment();
                    FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.frame_layout, fragment);
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();
                }
                if (id == R.id.contacts) {
                    ContactsFragment fragment = new ContactsFragment();
                    FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.frame_layout, fragment);
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();
                }
                if (id == R.id.home) {
                    HomeFragment fragment = new HomeFragment();
                    FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.frame_layout, fragment);
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();
                }
                if (id == R.id.auth) {
                    Intent LoginIntent = new Intent(HomeActivity.this, LoginActivity.class);
                    startActivity(LoginIntent);
                }
                if (id == R.id.user_list) {
                     UserListFragment fragment = (UserListFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_user_list);
                    if (fragment == null) {
                        fragment = new UserListFragment();
                        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.frame_layout, fragment);
                        fragmentTransaction.addToBackStack(null);
                        fragmentTransaction.commit();
                        } else
                            {
                            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                            fragmentTransaction.replace(R.id.frame_layout, fragment);
                            fragmentTransaction.addToBackStack(null);
                            fragmentTransaction.commit();
                            }
                                        }
                return true;
            }
        });
        bottomNavigationView.setSelectedItemId(R.id.main);

    }
    public void getUserMenu(String role){
        switch (role){
            case ("NO_AUTH"):
                bottomNavigationView.inflateMenu(R.menu.menu_bottom_no_auth);
                break;
            case("USER"):
                bottomNavigationView.inflateMenu(R.menu.menu_bottom_auth);
                break;
            case("ADMIN"):
                bottomNavigationView.inflateMenu(R.menu.menu_bottom_admin);
                break;
            case ("MANAGER"):
                bottomNavigationView.inflateMenu(R.menu.menu_bottom_manager);
                break;
        }
    }

    @Override
    public void openOrderDetailsFragment(String text){
        OrderDetailsFragment fragment = new OrderDetailsFragment();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        Bundle bundle = new Bundle();
        bundle.putString(fragment.KEY,text);
        fragment.setArguments(bundle);
        fragmentTransaction.replace(R.id.frame_layout,fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    @Override
    public void openUserDetailsFragment(String text) {
        UserDetailsFragment fragment = new UserDetailsFragment();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        Bundle bundle = new Bundle();
        bundle.putString(fragment.KEY,text);
        fragment.setArguments(bundle);
        fragmentTransaction.replace(R.id.frame_layout,fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    @Override
    public void openAddVehicleFragment() {
        AddVehicleFragment fragment = new AddVehicleFragment();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout,fragment);
        fragmentTransaction.commit();
    }

    @Override
    public void openProfileFragment() {
        ProfileFragment fragment = new ProfileFragment();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout,fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    @Override
    public void openOrderDetailsManagerFragment(String text) {

    }

    public void getUserDetailsByName(String name){
        Call<User> call = jsonPlaceHolderApi.getUserDetailsByName(name);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (!response.isSuccessful()) {
                    Toast toast = Toast.makeText(getApplicationContext(),
                            "Code: " + response.code(), Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                }
                User user = response.body();
                Log.i("MyLOG", user.getRole());
                if (user != null){
                    role =  user.getRole();
                } else {
                    role = "NO_AUTH";
                }
                getUserMenu(role);
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


}
