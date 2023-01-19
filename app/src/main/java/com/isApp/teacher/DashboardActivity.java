package com.isApp.teacher;


import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.View;

import com.isApp.teacher.Network.NetworkChangeListener;
import com.isApp.teacher.databinding.ActivityDashboardBinding;
import com.isApp.teacher.fragment.ChatFragment;
import com.isApp.teacher.fragment.DashboardFragment;
import com.isApp.teacher.fragment.MoreFragment;
import com.ismaeldivita.chipnavigation.ChipNavigationBar;


public class DashboardActivity extends AppCompatActivity {


    NetworkChangeListener networkChangeListner = new NetworkChangeListener();
    private ActivityDashboardBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityDashboardBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.bottomNavMenu.setItemSelected(R.id.nav_dashboard, true);
        getSupportFragmentManager().beginTransaction().replace(binding.fragmentContainer.getId(), new DashboardFragment()).commit();
        bottomMenu();
        binding.notificationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DashboardActivity.this, Notification.class);
                startActivity(intent);
            }
        });


    }

    private void bottomMenu() {

        binding.bottomNavMenu.setOnItemSelectedListener(new ChipNavigationBar.OnItemSelectedListener() {
            @Override
            public void onItemSelected(int i) {
                Fragment fragment = null;
                switch (i) {
                    case R.id.nav_dashboard:
                        fragment = new DashboardFragment();
                        break;
                    case R.id.nav_chat:
                        fragment = new ChatFragment();
                        break;
                    case R.id.nav_more:
                        fragment = new MoreFragment();

                        break;
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).commit();
            }
        });

    }

    @Override
    protected void onStart() {
        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(networkChangeListner, filter);
        super.onStart();
    }

    @Override
    protected void onStop() {
        unregisterReceiver(networkChangeListner);
        super.onStop();
    }

}