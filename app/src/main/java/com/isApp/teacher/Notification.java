package com.isApp.teacher;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;
import com.isApp.teacher.Adapter.NotificationAdapter;
import com.isApp.teacher.Model.NotificationForApp;
import com.isApp.teacher.Network.ApiInterface;
import com.isApp.teacher.Network.Retrofit.RetroFitClient;
import com.isApp.teacher.common.ColorOfStatusAndNavBar;
import com.isApp.teacher.common.Constants;
import com.isApp.teacher.databinding.ActivityNotificationBinding;
import com.isApp.teacher.sharedPreference.PreferenceManager;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Notification extends AppCompatActivity {

    private ActivityNotificationBinding binding;
    ProgressDialog progressDialog;
    PreferenceManager preferenceManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityNotificationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ColorOfStatusAndNavBar colorOfStatusAndNavBar = new ColorOfStatusAndNavBar();
        colorOfStatusAndNavBar.loginAndForgetPassword(this);
        binding.notificationList.setHasFixedSize(true);
        progressDialog = new ProgressDialog(this);
        binding.notificationBackButton.setOnClickListener(v-> onBackPressed());
        LinearLayoutManager llm = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        binding.notificationList.setLayoutManager(llm);
        preferenceManager = new PreferenceManager(getApplicationContext());
        progressDialog.show();
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Loading");


        String userId = preferenceManager.getString(Constants.USER_ID);
        ApiInterface apiInterface = RetroFitClient.getRetrofit().create(ApiInterface.class);
        NotificationForApp notificationForApp = new NotificationForApp(Integer.valueOf(userId));
        Call<NotificationForApp> call = apiInterface.notificationForApp(notificationForApp);
        call.enqueue(new Callback<NotificationForApp>() {
            @Override
            public void onResponse(Call<NotificationForApp> call, Response<NotificationForApp> response) {
                progressDialog.dismiss();
                if(response.isSuccessful()){
                    if (response.body().getMessage().equals("Notification got")){
                        List<NotificationForApp.ListForApp> notification = response.body().getListForApp();
                        NotificationAdapter notificationAdapter = new NotificationAdapter(notification);
                        binding.notificationList.setAdapter(notificationAdapter);
                    }else{
                        Toast.makeText(Notification.this, "No notification", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<NotificationForApp> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(Notification.this, "Technical Glitch", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(Notification.this, DashboardActivity.class);
        startActivity(i);
        finish();

    }
}