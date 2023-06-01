package com.isApp.teacher;


import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.isApp.teacher.alarm.AlarmReceiver;
import com.isApp.teacher.common.ColorOfStatusAndNavBar;
import com.isApp.teacher.databinding.ActivityScheduleViewBinding;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class ScheduleViewActivity extends AppCompatActivity {

    private ActivityScheduleViewBinding binding;
    String title, startDate, endDate;

    Date date;


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

        SimpleDateFormat dt1 = new SimpleDateFormat("MMM dd, yyyy hh:mm aa");
        try {
            date = dt1.parse(startDate);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }


        binding.title.setText(title);
        binding.startDate.setText(startDate);
        binding.endDate.setText(endDate);

        binding.teacherProfileBackButton.setOnClickListener(v->onBackPressed());
        binding.setAlarm.setOnClickListener(v->{
            setAlarm();
        });

    }

    private void setAlarm() {
        int hour = date.getHours();
        int minute = date.getMinutes();

        Intent intent = new Intent(ScheduleViewActivity.this, AlarmReceiver.class);
        intent.putExtra("title", title);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(ScheduleViewActivity.this, 0, intent, PendingIntent.FLAG_IMMUTABLE);

        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, 0);
        long alarmTime = calendar.getTimeInMillis();

        alarmManager.set(AlarmManager.RTC_WAKEUP, alarmTime, pendingIntent);
        Toast.makeText(this, "Alarm set for " + hour + ":" + minute, Toast.LENGTH_SHORT).show();
    }


}