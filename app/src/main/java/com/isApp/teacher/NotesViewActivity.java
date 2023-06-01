package com.isApp.teacher;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.isApp.teacher.Model.DeleteNotes;
import com.isApp.teacher.Network.ApiInterface;
import com.isApp.teacher.Network.Retrofit.RetroFitClient;
import com.isApp.teacher.common.ColorOfStatusAndNavBar;
import com.isApp.teacher.databinding.ActivityNotesViewBinding;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NotesViewActivity extends AppCompatActivity {

    private ActivityNotesViewBinding binding;
    String title, message, time, id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityNotesViewBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        ColorOfStatusAndNavBar colorOfStatusAndNavBar = new ColorOfStatusAndNavBar();
        colorOfStatusAndNavBar.colorOfStatusBar(this);



        Bundle extra = getIntent().getExtras();
        if (extra != null) {
            title = extra.getString("title");
            message = extra.getString("message");
            time = extra.getString("time");
            id = extra.getString("id");
        }

        ApiInterface apiInterface = RetroFitClient.getRetrofit().create(ApiInterface.class);
        DeleteNotes deleteNotes = new DeleteNotes(Integer.valueOf(id));
        Call<DeleteNotes> call = apiInterface.deleteNotes(deleteNotes);


        binding.delete.setOnClickListener(v->{
            call.enqueue(new Callback<DeleteNotes>() {
                @Override
                public void onResponse(Call<DeleteNotes> call, Response<DeleteNotes> response) {
                    if (response.body().getStatus().equals("success")){
                        Toast.makeText(NotesViewActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(v.getContext(), NotesActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }

                @Override
                public void onFailure(Call<DeleteNotes> call, Throwable t) {
                    Toast.makeText(NotesViewActivity.this, "Technical Glitch", Toast.LENGTH_SHORT).show();
                }
            });

                });




        binding.title.setText(title);
        binding.message.setText(message);
        binding.time.setText(time);

        binding.teacherProfileBackButton.setOnClickListener(v -> onBackPressed());
    }
}