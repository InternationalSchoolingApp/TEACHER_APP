package com.isApp.teacher;


import android.app.ProgressDialog;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.isApp.teacher.Model.DashboardNotificationModel;
import com.isApp.teacher.Model.Status;
import com.isApp.teacher.Network.ApiInterface;
import com.isApp.teacher.Network.NetworkChangeListener;
import com.isApp.teacher.Network.Retrofit.RetroFitClient;
import com.isApp.teacher.common.ColorOfStatusAndNavBar;
import com.isApp.teacher.common.Constants;
import com.isApp.teacher.common.LogoutDone;
import com.isApp.teacher.databinding.ActivityDashboardBinding;
import com.isApp.teacher.fragment.ChatFragment;
import com.isApp.teacher.fragment.DashboardFragment;
import com.isApp.teacher.fragment.MoreFragment;
import com.isApp.teacher.sharedPreference.PreferenceManager;
import com.ismaeldivita.chipnavigation.ChipNavigationBar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class DashboardActivity extends AppCompatActivity {

    ProgressDialog progressDialog;
    NetworkChangeListener networkChangeListner = new NetworkChangeListener();
    private ActivityDashboardBinding binding;
    private PreferenceManager preferenceManager;

    long pressedTime;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ColorOfStatusAndNavBar colorOfStatusAndNavBar = new ColorOfStatusAndNavBar();
        colorOfStatusAndNavBar.dashboard(this);
        init();
        getStatus();
        getNotificationCount();
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
                finish();
            }
        });


    }

    private void init(){
        preferenceManager = new PreferenceManager(this);
        progressDialog = new ProgressDialog(this);
        progressDialog.show();
        progressDialog.setCancelable(false);
        progressDialog.setTitle("Loading");
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

    public void getNotificationCount() {

        Integer userId = Integer.valueOf(preferenceManager.getString(Constants.USER_ID));
        Integer count = preferenceManager.getInt(Constants.COUNT);
        ApiInterface apiInterface = RetroFitClient.getRetrofit().create(ApiInterface.class);
        DashboardNotificationModel dashboardNotificationModel = new DashboardNotificationModel(userId);
        Call<DashboardNotificationModel> call = apiInterface.dashboardNotificationModel(dashboardNotificationModel);
        call.enqueue(new Callback<DashboardNotificationModel>() {
            @Override
            public void onResponse(Call<DashboardNotificationModel> call, Response<DashboardNotificationModel> response) {
                progressDialog.dismiss();
                if (response.body().getNotificationCount().intValue() == count) {

                } else {
                    changeImage(response.body().getNotificationCount());
                }
            }

            @Override
            public void onFailure(Call<DashboardNotificationModel> call, Throwable t) {
                progressDialog.dismiss();
            }
        });

    }

    private void changeImage(Integer i) {
        preferenceManager.putInt(Constants.COUNT, i);
        binding.notificationBtn.setImageResource(R.drawable.bellnotify);
    }

    private void getStatus() {

        Integer userId = Integer.valueOf(preferenceManager.getString(Constants.USER_ID));
        Status status = new Status(userId);
        ApiInterface apiInterface = RetroFitClient.getRetrofit().create(ApiInterface.class);
        Call<Status> call = apiInterface.getUserStatus(status);
        call.enqueue(new Callback<Status>() {
            @Override
            public void onResponse(Call<Status> call, Response<Status> response) {
                if (!response.body().getStatus().equals("success")){
                    LogoutDone logoutDone = new LogoutDone();
                    logoutDone.logout(Integer.valueOf(preferenceManager.getString(Constants.PLATFORM_ID)), userId, preferenceManager.getString(Constants.USER_EMAIL));
                }
            }
            @Override
            public void onFailure(Call<Status> call, Throwable t) {

            }
        });

    }

    @Override
    public void onBackPressed() {
        if (pressedTime + 2000 > System.currentTimeMillis()) {
            super.onBackPressed();
            finish();
        } else {
            getSupportFragmentManager().beginTransaction().replace(binding.fragmentContainer.getId(), new DashboardFragment()).commit();
            binding.bottomNavMenu.setItemSelected(R.id.nav_dashboard, true);
            Toast.makeText(this, "Press back again to quit", Toast.LENGTH_SHORT).show();
        }
        pressedTime = System.currentTimeMillis();
    }

}