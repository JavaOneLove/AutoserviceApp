package com.example.autoserviceapp.fragmentData;

public interface FragmentDataListener {
   void openOrderDetailsFragment(String text);
   void openUserDetailsFragment(String text);
   void openAddVehicleFragment();
   void openProfileFragment();
   void openOrderDetailsManagerFragment(String text);
}
