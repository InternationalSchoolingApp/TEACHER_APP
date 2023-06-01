package com.isApp.teacher;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.ProgressDialog;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.widget.Toast;

import com.isApp.teacher.Adapter.ScheduleAdapter;
import com.isApp.teacher.Model.ScheduleModel;
import com.isApp.teacher.Network.ApiInterface;
import com.isApp.teacher.Network.NetworkChangeListener;
import com.isApp.teacher.Network.Retrofit.RetroFitClient;
import com.isApp.teacher.common.ColorOfStatusAndNavBar;
import com.isApp.teacher.common.Constants;
import com.isApp.teacher.databinding.ActivityScheduleBinding;
import com.isApp.teacher.sharedPreference.PreferenceManager;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ScheduleActivity extends AppCompatActivity {

    PreferenceManager preferenceManager;
    private ActivityScheduleBinding binding;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityScheduleBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        progressDialog = new ProgressDialog(this);
        progressDialog.show();
        progressDialog.setCancelable(false);
        progressDialog.setTitle("Loading");
        ColorOfStatusAndNavBar colorOfStatusAndNavBar = new ColorOfStatusAndNavBar();
        colorOfStatusAndNavBar.loginAndForgetPassword(this);
        preferenceManager = new PreferenceManager(this);
        binding.scheduleRecyclerView.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        binding.scheduleRecyclerView.setLayoutManager(llm);
        binding.backButtonSchedule.setOnClickListener(v -> onBackPressed());


        Integer userId = Integer.valueOf(preferenceManager.getString(Constants.USER_ID));
        ApiInterface apiInterface = RetroFitClient.getRetrofit().create(ApiInterface.class);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date startDate = new Date();
        ScheduleModel scheduleModel = new ScheduleModel(userId, String.valueOf(simpleDateFormat.format(startDate)), String.valueOf(getNextDate(new Date(), 1, "yyyy-MM-dd")));
        Call<ScheduleModel> call = apiInterface.getSchedule(scheduleModel);
        call.enqueue(new Callback<ScheduleModel>() {
            @Override
            public void onResponse(Call<ScheduleModel> call, Response<ScheduleModel> response) {
                progressDialog.dismiss();
                if (response.body().getStatus().equals("success")) {
                    List<ScheduleModel.SchoolCalendar.Event> list = response.body().getSchoolCalendar().getEvent();
                    ScheduleAdapter scheduleAdapter = new ScheduleAdapter(list);
                    binding.scheduleRecyclerView.setAdapter(scheduleAdapter);
                } else {
                    Toast.makeText(ScheduleActivity.this, "No Schedule Activity today", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ScheduleModel> call, Throwable t) {
                progressDialog.dismiss();
            }
        });


    }

    static String getNextDate(final Date currnetDate, final int day, String dateFormat) {
        if (currnetDate == null)
            return null;
        final Date nextHourDate = new Date();
        nextHourDate.setTime(currnetDate.getTime() + (day * 24 * 60 * 60 * 1000));
        final SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);
        return formatter.format(nextHourDate);

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

    NetworkChangeListener networkChangeListner = new NetworkChangeListener();


}

