package com.isApp.teacher;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.ProgressBar;

import com.bumptech.glide.Glide;
import com.isApp.teacher.Model.ProfileModel;
import com.isApp.teacher.Network.ApiInterface;
import com.isApp.teacher.Network.Retrofit.RetroFitClient;
import com.isApp.teacher.common.Constants;
import com.isApp.teacher.databinding.ActivityProfileBinding;
import com.isApp.teacher.sharedPreference.PreferenceManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileActivity extends AppCompatActivity {

    PreferenceManager preferenceManager;
    private ActivityProfileBinding binding;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        progressDialog = new ProgressDialog(this);
        progressDialog.show();
        progressDialog.setCancelable(false);
        progressDialog.setTitle("Loading");
        preferenceManager = new PreferenceManager(this);
        binding.profileBackButton.setOnClickListener(v-> onBackPressed());

        ProfileModel profileModel = new ProfileModel(preferenceManager.getInt(Constants.TEACHER_ID));
        ApiInterface apiInterface = RetroFitClient.getRetrofit().create(ApiInterface.class);
        Call<ProfileModel> call = apiInterface.profileModel(profileModel);
        call.enqueue(new Callback<ProfileModel>() {
            @Override
            public void onResponse(Call<ProfileModel> call, Response<ProfileModel> response) {
                progressDialog.dismiss();
                if (response.isSuccessful()){
                    if (response.body().getStatus().equals("success")){
                        binding.nameTv.setText(response.body().getFullName());
                        binding.email.setText(response.body().getEmail());
                        binding.dojTv.setText(response.body().getDoj());
                        binding.address.setText(response.body().getAddress());
                        binding.applicationNumberProfile.setText(response.body().getApplicationNumber());
                        binding.phoneNumber.setText(response.body().getPhone());
                        binding.designation.setText(response.body().getDesignation());
                        String url = response.body().getPhoto();
                        url.replace("sch/", "sch/thumb_");
                        Glide.with(getApplicationContext()).load(url).into(binding.profileImage);
                    }
                }
            }

            @Override
            public void onFailure(Call<ProfileModel> call, Throwable t) {
                progressDialog.dismiss();
            }
        });







    }

}