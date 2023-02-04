package com.isApp.teacher;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.View;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.isApp.teacher.Model.DashboardNotificationModel;
import com.isApp.teacher.Network.ApiInterface;
import com.isApp.teacher.Network.NetworkChangeListener;
import com.isApp.teacher.Network.Retrofit.RetroFitClient;
import com.isApp.teacher.common.ColorOfStatusAndNavBar;
import com.isApp.teacher.common.Constants;
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
    private FirebaseFirestore firebaseFirestore;
    private PreferenceManager preferenceManager;


    private void updateFireBaseToken() {
        DocumentReference docRef = firebaseFirestore.collection(Constants.FIREBASE_USER_DB).document(preferenceManager.getString(Constants.USER_EMAIL).toLowerCase());
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                docRef.update("firebaseToken", preferenceManager.getString(Constants.FIREBASE_TOKEN));
            }
        });

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ColorOfStatusAndNavBar colorOfStatusAndNavBar = new ColorOfStatusAndNavBar();
        colorOfStatusAndNavBar.dashboard(this);
        init();
        updateFireBaseToken();
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
        firebaseFirestore = FirebaseFirestore.getInstance();
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

}