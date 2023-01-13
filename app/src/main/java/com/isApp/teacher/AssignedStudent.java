package com.isApp.teacher;

import android.annotation.SuppressLint;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowInsets;
import android.widget.Toast;

import com.isApp.teacher.Adapter.AssignStudentAdapter;
import com.isApp.teacher.Model.AssignStudentModel;
import com.isApp.teacher.Network.ApiInterface;
import com.isApp.teacher.Network.Retrofit.RetroFitClient;
import com.isApp.teacher.common.Constants;
import com.isApp.teacher.databinding.ActivityAssignedStudentBinding;
import com.isApp.teacher.sharedPreference.PreferenceManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class AssignedStudent extends AppCompatActivity {

    private ActivityAssignedStudentBinding binding;
    PreferenceManager preferenceManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityAssignedStudentBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.assignStudentRV.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        binding.assignStudentRV.setLayoutManager(llm);

        preferenceManager = new PreferenceManager(getApplicationContext());

        ApiInterface apiInterface = RetroFitClient.getRetrofit().create(ApiInterface.class);
        AssignStudentModel assignStudentModel = new AssignStudentModel(preferenceManager.getInt(Constants.TEACHER_ID));
        Toast.makeText(this, preferenceManager.getInt(Constants.TEACHER_ID).toString(), Toast.LENGTH_SHORT).show();

        Call<AssignStudentModel> call = apiInterface.assignStudentModelCall(assignStudentModel);
        call.enqueue(new Callback<AssignStudentModel>() {
            @Override
            public void onResponse(Call<AssignStudentModel> call, Response<AssignStudentModel> response) {
                if (response.body().getStatus().equals("success")){

                    AssignStudentAdapter assignStudentAdapter = new AssignStudentAdapter(response.body().getYeLoList());
                    binding.assignStudentRV.setAdapter(assignStudentAdapter);
                }else{
                    //Toast.makeText(AssignedStudent.this, "No Student Assigned", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<AssignStudentModel> call, Throwable t) {

            }
        });

         }

}