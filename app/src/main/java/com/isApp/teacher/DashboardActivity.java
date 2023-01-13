package com.isApp.teacher;

import android.annotation.SuppressLint;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowInsets;

import com.isApp.teacher.databinding.ActivityDashboardBinding;
import com.isApp.teacher.fragment.ChatFragment;
import com.isApp.teacher.fragment.DashboardFragment;
import com.isApp.teacher.fragment.MoreFragment;
import com.ismaeldivita.chipnavigation.ChipNavigationBar;


public class DashboardActivity extends AppCompatActivity {

    private static final boolean AUTO_HIDE = true;

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

}