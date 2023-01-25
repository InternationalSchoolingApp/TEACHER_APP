package com.isApp.teacher;


import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import com.isApp.teacher.common.ColorOfStatusAndNavBar;
import com.isApp.teacher.databinding.ActivityScheduleViewBinding;
public class ScheduleViewActivity extends AppCompatActivity {

    private ActivityScheduleViewBinding binding;
    String title, startDate, endDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityScheduleViewBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        ColorOfStatusAndNavBar colorOfStatusAndNavBar = new ColorOfStatusAndNavBar();
        colorOfStatusAndNavBar.colorOfStatusBar(this);

        Bundle extra = getIntent().getExtras();

        if (extra != null) {
            title = extra.getString("title");
            startDate = extra.getString("startDate");
            endDate = extra.getString("endDate");
        }

        binding.title.setText(title);
        binding.startDate.setText(startDate);
        binding.endDate.setText(endDate);

        binding.teacherProfileBackButton.setOnClickListener(v->onBackPressed());

    }


}