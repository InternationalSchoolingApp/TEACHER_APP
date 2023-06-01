package com.isApp.teacher;


import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.isApp.teacher.common.ColorOfStatusAndNavBar;
import com.isApp.teacher.databinding.ActivityNotificationViewBinding;


public class NotificationViewActivity extends AppCompatActivity {

    private ActivityNotificationViewBinding binding;
    String title, message, time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityNotificationViewBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        ColorOfStatusAndNavBar colorOfStatusAndNavBar = new ColorOfStatusAndNavBar();
        colorOfStatusAndNavBar.colorOfStatusBar(this);

        Bundle extra = getIntent().getExtras();
        if (extra != null) {
            title = extra.getString("title");
            message = extra.getString("message");
            time = extra.getString("time");
        }

        binding.title.setText(title);
        binding.message.setText(message);
        binding.time.setText(time);
        binding.teacherProfileBackButton.setOnClickListener(v->onBackPressed());



    }




}