package com.isApp.teacher;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.widget.Toast;

import com.isApp.teacher.Adapter.AssignedSubjectAdapter;
import com.isApp.teacher.Model.AssignedSubjects;
import com.isApp.teacher.Network.ApiInterface;
import com.isApp.teacher.Network.Retrofit.RetroFitClient;
import com.isApp.teacher.common.Constants;
import com.isApp.teacher.databinding.ActivityAssignSubjectBinding;
import com.isApp.teacher.sharedPreference.PreferenceManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AssignSubjectActivity extends AppCompatActivity {

    private ActivityAssignSubjectBinding binding;
    PreferenceManager preferenceManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityAssignSubjectBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.profileBackButton.setOnClickListener(v -> onBackPressed());

        binding.assignSubjectRecyclerView.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        binding.assignSubjectRecyclerView.setLayoutManager(llm);
        preferenceManager = new PreferenceManager(getApplicationContext());

        ApiInterface apiInterface = RetroFitClient.getRetrofit().create(ApiInterface.class);
        AssignedSubjects assignedSubjects = new AssignedSubjects(Integer.valueOf(preferenceManager.getString(Constants.USER_ID)));
        Call<AssignedSubjects> call = apiInterface.getSubject(assignedSubjects);
        call.enqueue(new Callback<AssignedSubjects>() {
            @Override
            public void onResponse(Call<AssignedSubjects> call, Response<AssignedSubjects> response) {
                if (response.body().getStatus().equals("success")) {

                    AssignedSubjectAdapter assignedSubjectAdapter = new AssignedSubjectAdapter(response.body().getTeacherSubjectDTO());
                    binding.assignSubjectRecyclerView.setAdapter(assignedSubjectAdapter);
                } else {
                    Toast.makeText(AssignSubjectActivity.this, "No Student Assigned", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<AssignedSubjects> call, Throwable t) {

            }
        });


    }


}