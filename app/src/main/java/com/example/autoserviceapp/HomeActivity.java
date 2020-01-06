package com.example.autoserviceapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.example.autoserviceapp.fragmentData.FragmentDataListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomeActivity extends AppCompatActivity implements FragmentDataListener {

    public int role = 2; // 0 is no auth, 1 is auth, 3 is manager, 2 is administrator

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView bottomNavigationView = findViewById(R.id.btm_nav);
        switch (role){
            case (0):
                bottomNavigationView.inflateMenu(R.menu.menu_bottom_no_auth);
                break;
            case(1):
                bottomNavigationView.inflateMenu(R.menu.menu_bottom_auth);
                break;
            case(2):
                bottomNavigationView.inflateMenu(R.menu.menu_bottom_admin);
                break;
            case (3):
                bottomNavigationView.inflateMenu(R.menu.menu_bottom_manager);
                break;
        }

                HomeFragment fragment = new HomeFragment();
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.frame_layout, fragment);
                fragmentTransaction.commit();

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

    @Override
    public void openOrderDetailsFragment(){
            OrderDetailsFragment fragment = new OrderDetailsFragment();
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.frame_layout,fragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
    }

    @Override
    public void openUserDetailsFragment() {
        UserDetailsFragment fragment = new UserDetailsFragment();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout,fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }


}
