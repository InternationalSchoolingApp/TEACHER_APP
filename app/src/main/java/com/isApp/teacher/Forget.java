package com.isApp.teacher;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.isApp.teacher.Model.ForgetPasswordModel;
import com.isApp.teacher.Network.ApiInterface;
import com.isApp.teacher.Network.Retrofit.RetroFitClient;
import com.isApp.teacher.common.ColorOfStatusAndNavBar;
import com.isApp.teacher.databinding.ActivityForgetBinding;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Forget extends AppCompatActivity {

    private ActivityForgetBinding binding;

    

    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    Dialog dialog;
    ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityForgetBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        progressDialog = new ProgressDialog(this);

        ColorOfStatusAndNavBar colorOfStatusAndNavBar = new ColorOfStatusAndNavBar();
        colorOfStatusAndNavBar.colorOfStatusBar(this);

        String email="";
        Bundle extra = getIntent().getExtras();
        if (extra != null) {
            email = extra.getString("email");
            binding.forgetEmailTv.getEditText().setInputType(InputType.TYPE_CLASS_TEXT);
            binding.forgetEmailTv.getEditText().setText(email);

        }
        binding.forgetEmailTv.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String username = binding.forgetEmailTv.getEditText().getText().toString().trim();
                if (username.matches(emailPattern)) {
                    binding.forgetEmailTv.setHelperText("");
                    binding.forgetEmailTv.setError(null);
                } else {
                    binding.forgetEmailTv.setHelperText("");
                    binding.forgetEmailTv.setError("Invalid Mail");
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        binding.forgetPageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = binding.forgetEmailTv.getEditText().getText().toString().trim();

                if (username.isEmpty()) {
                    binding.forgetEmailTv.setError("Email field is empty");
                    binding.forgetEmailTv.setHelperText("Email field is empty");
                } else if (!username.matches(emailPattern)) {
                    binding.forgetEmailTv.setHelperText("");
                    binding.forgetEmailTv.setError("Invalid Mail");
                } else {
                    binding.forgetEmailTv.setHelperText("");
                    binding.forgetEmailTv.setError("");
                    progressDialog.setMessage("Loading...");
                    progressDialog.setCancelable(false);
                    progressDialog.show();
                    forget(username);
                }
            }


        });



    }

    private void forget(String username) {


        ApiInterface apiInterface = RetroFitClient.getRetrofit().create(ApiInterface.class);
        final ForgetPasswordModel forgetPasswordModel = new ForgetPasswordModel(username);
        Call<ForgetPasswordModel> call = apiInterface.forgetPassword(forgetPasswordModel);
        call.enqueue(new Callback<ForgetPasswordModel>() {
            @Override
            public void onResponse(Call<ForgetPasswordModel> call, Response<ForgetPasswordModel> response) {
                progressDialog.dismiss();
                String statusValue = response.body().getStatusValue();
                String status = response.body().getStatus();
                getStatus(status, statusValue);
            }

            @Override
            public void onFailure(Call<ForgetPasswordModel> call, Throwable t) {
                progressDialog.dismiss();
            }
        });

    }

    private void getStatus(String status, String statusValue) {
        dialog = new Dialog(this);
        dialog.setContentView(R.layout.pop_up_dialog);
        dialog.show();
        TextView textViewTitle, textViewMessage;
        ImageView imageView;

        textViewTitle = dialog.findViewById(R.id.popup_textView);
        textViewMessage = dialog.findViewById(R.id.popup_message);
        imageView = dialog.findViewById(R.id.close_popup);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        if (status.equals("success")){
            textViewTitle.setText("Got It ");
            textViewMessage.setText("Check your mail, you will get a reset link shortly");
        }else if (status.equals("failed")){
            if(statusValue.equals("userNotExist")){
                textViewTitle.setText("Not Exist");
                textViewMessage.setText("The user you are trying to access is not Registered");
            }else if(statusValue.equals("notEnabled")){
                textViewTitle.setText("Sorry");
                textViewMessage.setText("User Not Enabled");
            }
        }else{
            textViewTitle.setText("Technical Glitch");
            textViewMessage.setText("Sorry,"+"/n there is a technical Glitch");
        }

    }

}