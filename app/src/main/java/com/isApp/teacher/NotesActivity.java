package com.isApp.teacher;


import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.widget.Toast;

import com.isApp.teacher.Adapter.MyNotesAdapter;
import com.isApp.teacher.Model.GetNotes;
import com.isApp.teacher.Network.ApiInterface;
import com.isApp.teacher.Network.NetworkChangeListener;
import com.isApp.teacher.Network.Retrofit.RetroFitClient;
import com.isApp.teacher.common.ColorOfStatusAndNavBar;
import com.isApp.teacher.common.Constants;
import com.isApp.teacher.databinding.ActivityNotesBinding;
import com.isApp.teacher.sharedPreference.PreferenceManager;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NotesActivity extends AppCompatActivity {

    private ActivityNotesBinding binding;
    private PreferenceManager preferenceManager;

    private ProgressDialog progressDialog;

    NetworkChangeListener networkChangeListner = new NetworkChangeListener();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ColorOfStatusAndNavBar colorOfStatusAndNavBar = new ColorOfStatusAndNavBar();
        colorOfStatusAndNavBar.loginAndForgetPassword(this);
        preferenceManager = new PreferenceManager(this);
        binding = ActivityNotesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        progressDialog = new ProgressDialog(this);
        progressDialog.show();
        progressDialog.setCancelable(false);
        binding.backButtonNotes.setOnClickListener(v -> {
            onBackPressed();
        });

        binding.recentNotesRv.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        binding.recentNotesRv.setLayoutManager(llm);

        binding.createButtonNotes.setOnClickListener(v -> {
            Intent i = new Intent(NotesActivity.this, CreateNotes.class);
            startActivity(i);
            finish();
        });

        getList();

    }

    private void getList() {

        ApiInterface apiInterface = RetroFitClient.getRetrofit().create(ApiInterface.class);
        GetNotes getNotes = new GetNotes(Integer.valueOf(preferenceManager.getString(Constants.USER_ID)));
        Call<GetNotes> call = apiInterface.getMyNotes(getNotes);
        call.enqueue(new Callback<GetNotes>() {
            @Override
            public void onResponse(Call<GetNotes> call, Response<GetNotes> response) {
                progressDialog.dismiss();
                if (response.body().getStatus().equals("success")) {
                    List<GetNotes.List> list = response.body().getList();
                    MyNotesAdapter myNotesAdapter = new MyNotesAdapter(list);
                    binding.recentNotesRv.setAdapter(myNotesAdapter);
                } else if (response.body().getStatus().equals("empty")) {
                    Toast.makeText(NotesActivity.this, "Empty", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(NotesActivity.this, "Sorry Technical Glitch", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<GetNotes> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(NotesActivity.this, "Sorry Technical Glitch", Toast.LENGTH_SHORT).show();
            }
        });

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

