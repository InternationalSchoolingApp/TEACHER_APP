package com.isApp.teacher;


import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.Toast;

import com.isApp.teacher.Model.NotificationLog;
import com.isApp.teacher.Network.ApiInterface;
import com.isApp.teacher.Network.Retrofit.RetroFitClient;
import com.isApp.teacher.common.ColorOfStatusAndNavBar;
import com.isApp.teacher.databinding.ActivityNotificationViewBinding;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class NotificationViewActivity extends AppCompatActivity {

    private ActivityNotificationViewBinding binding;
    String title, message, time;
    Integer id, userId;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityNotificationViewBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        ColorOfStatusAndNavBar colorOfStatusAndNavBar = new ColorOfStatusAndNavBar();
        colorOfStatusAndNavBar.colorOfStatusBar(this);

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Loading");
        progressDialog.setCancelable(false);
        progressDialog.show();

        Bundle extra = getIntent().getExtras();
        if (extra != null) {
            title = extra.getString("title");
            message = extra.getString("message");
            time = extra.getString("time");
            id = extra.getInt("id");
            userId = extra.getInt("userId");
        }


        ApiInterface apiInterface = RetroFitClient.getRetrofit().create(ApiInterface.class);
        NotificationLog notificationLog = new NotificationLog(userId, id);

        Call<NotificationLog> call = apiInterface.getUpdateNotification(notificationLog);
        call.enqueue(new Callback<NotificationLog>() {
            @Override
            public void onResponse(Call<NotificationLog> call, Response<NotificationLog> response) {
                if (response.isSuccessful()){
                    if (response.body().getStatus().equals("success")){
                        progressDialog.dismiss();

                    }else{
                        progressDialog.dismiss();
                    }
                }
            }

            @Override
            public void onFailure(Call<NotificationLog> call, Throwable t) {
                progressDialog.dismiss();
            }
        });

        binding.title.setText(title);
        binding.message.setText(message);
        binding.time.setText(time);
        binding.teacherProfileBackButton.setOnClickListener(v->onBackPressed());



    }




}