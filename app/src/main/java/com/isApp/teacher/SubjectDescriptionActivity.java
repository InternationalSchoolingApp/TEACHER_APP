package com.isApp.teacher;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.isApp.teacher.Model.SubjectDescription;
import com.isApp.teacher.Network.ApiInterface;
import com.isApp.teacher.Network.NetworkChangeListener;
import com.isApp.teacher.Network.Retrofit.RetroFitClient;
import com.isApp.teacher.common.ColorOfStatusAndNavBar;
import com.isApp.teacher.databinding.ActivitySubjectDescriptionBinding;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SubjectDescriptionActivity extends AppCompatActivity {

    private ActivitySubjectDescriptionBinding binding;
    Integer subjectId;
    String htmlContent;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivitySubjectDescriptionBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        progressDialog = new ProgressDialog(this);
        progressDialog.show();
        ColorOfStatusAndNavBar colorOfStatusAndNavBar = new ColorOfStatusAndNavBar();
        colorOfStatusAndNavBar.loginAndForgetPassword(this);
        progressDialog.setCancelable(false);
        progressDialog.setTitle("Loading");
        binding.backButton.setOnClickListener(v -> onBackPressed());
        Bundle extra = getIntent().getExtras();
        if (extra != null) {
            subjectId = Integer.valueOf(extra.getString("subjectId"));
        }

        binding.webView.setWebViewClient(new WebViewClient());
        binding.webView.getSettings().setJavaScriptEnabled(true);




        ApiInterface apiInterface = RetroFitClient.getRetrofit().create(ApiInterface.class);
        SubjectDescription subjectDescription = new SubjectDescription(subjectId);
        Call<SubjectDescription> call = apiInterface.getSubjectDescription(subjectDescription);
        call.enqueue(new Callback<SubjectDescription>() {
            @Override
            public void onResponse(Call<SubjectDescription> call, Response<SubjectDescription> response) {
                if (response.isSuccessful()) {
                    binding.subjectNameTextView.setText(response.body().getSubjectName());
                    htmlContent = response.body().getContent().replaceAll("\\\\", "");
                    binding.webView.loadDataWithBaseURL(null, "<html><body><div>" + htmlContent + "</div></body></html>", "text/html; charset=utf-8", "UTF-8", null);
                    progressDialog.dismiss();
                    Log.d("TAG", "onResponse: " + htmlContent);


                } else {
                    progressDialog.dismiss();
                    Toast.makeText(SubjectDescriptionActivity.this, "response not successfull", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<SubjectDescription> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(SubjectDescriptionActivity.this, "Sorry No Data Available" , Toast.LENGTH_SHORT).show();
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

    NetworkChangeListener networkChangeListner = new NetworkChangeListener();

}