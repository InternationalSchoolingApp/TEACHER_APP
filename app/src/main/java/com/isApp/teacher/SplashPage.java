package com.isApp.teacher;


import androidx.appcompat.app.AppCompatActivity;

import android.app.NotificationManager;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.isApp.teacher.common.ColorOfStatusAndNavBar;
import com.isApp.teacher.common.Constants;
import com.isApp.teacher.databinding.ActivitySplashPageBinding;
import com.isApp.teacher.sharedPreference.PreferenceManager;


public class SplashPage extends AppCompatActivity {
    private PreferenceManager preferenceManager;
    private ActivitySplashPageBinding binding;
    private int SPLASH_TIME = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        NotificationManager notificationManager = getSystemService(NotificationManager.class);
        boolean areNotificationsEnabled = notificationManager.areNotificationsEnabled();
        if (!areNotificationsEnabled) {
            SPLASH_TIME =15000;
            Intent intent = new Intent();
            intent.setAction("android.settings.APP_NOTIFICATION_SETTINGS");
            intent.putExtra("android.provider.extra.APP_PACKAGE", getPackageName());
            startActivity(intent);
        }

        binding = ActivitySplashPageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ColorOfStatusAndNavBar colorOfStatusAndNavBar = new ColorOfStatusAndNavBar();
        colorOfStatusAndNavBar.colorOfStatusBar(this);
        preferenceManager = new PreferenceManager(getApplicationContext());



        new Handler().postDelayed(new Runnable() {
            Intent intent;
            @Override
            public void run() {
                if (preferenceManager.getBoolean(Constants.USER_LOGGED)) {
                    intent = new Intent(SplashPage.this, DashboardActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    intent = new Intent(SplashPage.this, Login.class);
                    startActivity(intent);
                    finish();
                }
            }

        }, SPLASH_TIME);
    }

}