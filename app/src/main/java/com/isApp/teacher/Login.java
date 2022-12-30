package com.isApp.teacher;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;

import com.isApp.teacher.common.ColorOfStatusAndNavBar;
import com.isApp.teacher.databinding.ActivityLoginBinding;

public class Login extends AppCompatActivity {

    private ActivityLoginBinding binding;
    private String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ColorOfStatusAndNavBar colorOfStatusAndNavBar = new ColorOfStatusAndNavBar();
        colorOfStatusAndNavBar.colorOfStatusBar(this);
        binding.username.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String username = binding.username.getEditText().getText().toString().trim();
                if (username.matches(emailPattern)) {
                    binding.username.setHelperText("");
                    binding.username.setError(null);
                } else {
                    binding.username.setHelperText("");
                    binding.username.setError("Invalid Mail");
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }

        });
        binding.loginBtn.setOnClickListener(v -> {
            if (checking()) {

            } else {

            }
        });
    }

    public Boolean checking() {

        String username = binding.username.getEditText().getText().toString().trim();
        String password = binding.password.getEditText().getText().toString().trim();

        if (username.isEmpty()) {
            binding.username.setError("Email field is empty");
            binding.username.setHelperText("Email field is empty");
        } else if (password.isEmpty()) {
            binding.password.setError("Password is empty");
            binding.password.setHelperText("Password field is empty");
        } else if (!username.matches(emailPattern)) {
            binding.username.setHelperText("");
            binding.username.setError("Invalid Mail");
        } else {
            binding.username.setHelperText("");
            binding.username.setError("");
            binding.password.setHelperText("");
            binding.password.setError("");
            return true;
        }
        return false;
    }

}