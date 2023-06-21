package com.isApp.teacher.fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.isApp.teacher.AssignSubjectActivity;
import com.isApp.teacher.AssignedStudent;
import com.isApp.teacher.Model.DashBoardModel;
import com.isApp.teacher.Network.ApiInterface;
import com.isApp.teacher.Network.Retrofit.RetroFitClient;
import com.isApp.teacher.NotesActivity;
import com.isApp.teacher.ProfileActivity;
import com.isApp.teacher.R;
import com.isApp.teacher.ScheduleActivity;
import com.isApp.teacher.common.Constants;
import com.isApp.teacher.sharedPreference.PreferenceManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class DashboardFragment extends Fragment {


    public DashboardFragment() {
        // Required empty public constructor
    }

    PreferenceManager preferenceManager;
    ImageView image;
    TextView name, applicationNumber;
    RelativeLayout relativeLayout, assignSubjects, schedule, notes, assignedStudents;
    ProgressDialog progressDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dashboard, container, false);
        image = view.findViewById(R.id.profile_pic_dashboard);
        name = view.findViewById(R.id.name_dashboard);
        applicationNumber = view.findViewById(R.id.application_number);
        relativeLayout = view.findViewById(R.id.relativeLayoutProfile);
        assignSubjects = view.findViewById(R.id.assignSubject);
        notes = view.findViewById(R.id.notes);
        assignedStudents = view.findViewById(R.id.assignedstudents);

        assignedStudents.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), AssignedStudent.class);
                startActivity(intent);
            }
        });
        notes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), NotesActivity.class);
                startActivity(intent);
            }
        });
        assignSubjects.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), AssignSubjectActivity.class);
                startActivity(intent);
            }
        });
        relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), ProfileActivity.class);
                startActivity(intent);
            }
        });
        schedule = view.findViewById(R.id.schedule);
        schedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), ScheduleActivity.class);
                v.getContext().startActivity(intent);
            }
        });
        progressDialog = new ProgressDialog(getContext());
        progressDialog.show();
        progressDialog.setCancelable(false);
        progressDialog.setTitle("Loading");
        preferenceManager = new PreferenceManager(this.getActivity());
        ApiInterface apiInterface = RetroFitClient.getRetrofit().create(ApiInterface.class);
        DashBoardModel dashBoardModel = new DashBoardModel(preferenceManager.getInt(Constants.TEACHER_ID));
        Call<DashBoardModel> call = apiInterface.dashTeacherProfile(dashBoardModel);
        call.enqueue(new Callback<DashBoardModel>() {
            @Override
            public void onResponse(Call<DashBoardModel> call, Response<DashBoardModel> response) {
                progressDialog.dismiss();
                if (response.isSuccessful()) {

                    if (response.body().getStatus().equals("success")) {
                        name.setText(response.body().getfName());
                        applicationNumber.setText(response.body().getApplicationNumber());
                        String url = response.body().getPictureLink();
                        url.replace("sch/", "sch/thumb_");
                        Glide.with(view).load(url).into(image);
                        Log.d("RESPONSE DASHBOARD", "response :" + response.body().getfName().toString());
                    }

                } else {
                    progressDialog.dismiss();
                    Toast.makeText(getActivity(), "Reponse Failed", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<DashBoardModel> call, Throwable t) {

            }
        });
        return view;
    }
}