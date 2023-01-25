package com.isApp.teacher;

import android.annotation.SuppressLint;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowInsets;
import android.widget.Toast;

import com.isApp.teacher.Model.CreateMyNotes;
import com.isApp.teacher.Network.ApiInterface;
import com.isApp.teacher.Network.NetworkChangeListener;
import com.isApp.teacher.Network.Retrofit.RetroFitClient;
import com.isApp.teacher.common.ColorOfStatusAndNavBar;
import com.isApp.teacher.common.Constants;
import com.isApp.teacher.databinding.ActivityCreateNotesBinding;
import com.isApp.teacher.sharedPreference.PreferenceManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class CreateNotes extends AppCompatActivity {
    ActivityCreateNotesBinding binding;
    ProgressDialog progressDialog;
    PreferenceManager preferenceManager;
    NetworkChangeListener networkChangeListner = new NetworkChangeListener();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ColorOfStatusAndNavBar color = new ColorOfStatusAndNavBar();
        color.loginAndForgetPassword(this);
        binding = ActivityCreateNotesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        preferenceManager = new PreferenceManager(this);
        progressDialog = new ProgressDialog(this);
        binding.createNoteBtn.setOnClickListener(v -> {
            if (validate()) {
                saving();
                progressDialog.show();
                progressDialog.setCancelable(false);
                binding.createNoteMessage.getEditText().setText(null);
                binding.createNoteTitle.getEditText().setText(null);
            }
        });

        binding.backButtonCreateNotes.setOnClickListener(v-> onBackPressed());

    }

    private void saving() {
        String title = binding.createNoteTitle.getEditText().getText().toString();
        String message = binding.createNoteMessage.getEditText().getText().toString();

        Integer userId = Integer.valueOf(preferenceManager.getString(Constants.USER_ID));

        CreateMyNotes createMyNotes = new CreateMyNotes(message, userId, title);
        ApiInterface apiInterface = RetroFitClient.getRetrofit().create(ApiInterface.class);
        Call<CreateMyNotes> call = apiInterface.createMyNotes(createMyNotes);
        call.enqueue(new Callback<CreateMyNotes>() {
            @Override
            public void onResponse(Call<CreateMyNotes> call, Response<CreateMyNotes> response) {
                progressDialog.dismiss();
                if (response.body().getStatus().equals("success")) {
                    Toast.makeText(CreateNotes.this, "Note Saved Successfully", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(CreateNotes.this, "OOPS! Error Occured", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<CreateMyNotes> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(CreateNotes.this, "OOPS! Error Occured", Toast.LENGTH_SHORT).show();
            }
        });


    }

    boolean validate() {
        if (binding.createNoteTitle.getEditText().getText().toString().equals("") || binding.createNoteMessage.getEditText().getText().toString().equals("")) {
            if (binding.createNoteTitle.getEditText().getText().toString().equals("")) {
                binding.createNoteTitle.getEditText().setError("Title Field Empty");
            } else if (binding.createNoteMessage.getEditText().getText().toString().equals("")) {
                binding.createNoteMessage.getEditText().setError("Message Field Empty");
            }
        } else {
            return true;
        }
        return false;
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


}