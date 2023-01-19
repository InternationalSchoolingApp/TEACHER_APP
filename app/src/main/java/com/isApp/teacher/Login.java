package com.isApp.teacher;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.isApp.teacher.Model.FirebaseTokenModel;
import com.isApp.teacher.Model.LoginModel;
import com.isApp.teacher.Network.ApiInterface;
import com.isApp.teacher.Network.NetworkChangeListener;
import com.isApp.teacher.Network.Retrofit.RetroFitClient;
import com.isApp.teacher.common.ColorOfStatusAndNavBar;
import com.isApp.teacher.common.Constants;
import com.isApp.teacher.common.MobileModel;
import com.isApp.teacher.databinding.ActivityLoginBinding;
import com.isApp.teacher.sharedPreference.PreferenceManager;
import java.util.HashMap;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Login extends AppCompatActivity {

    NetworkChangeListener networkChangeListner = new NetworkChangeListener();
    private ActivityLoginBinding binding;
    private String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    PreferenceManager preferenceManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ColorOfStatusAndNavBar colorOfStatusAndNavBar = new ColorOfStatusAndNavBar();
        colorOfStatusAndNavBar.colorOfStatusBar(this);
        binding.forgetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (binding.username.getEditText().getText().toString()!=null){
                    Intent intent = new Intent(v.getContext(), Forget.class);
                    intent.putExtra("email", binding.username.getEditText().getText().toString());
                    startActivity(intent);
                }else{
                    Intent i = new Intent(Login.this, Forget.class);
                    startActivity(i);
                }
            }
        });
        preferenceManager = new PreferenceManager(getApplicationContext());
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
            checking();
        });
    }

    void loginNow() {
        MobileModel model = new MobileModel();
        String username = binding.username.getEditText().getText().toString().trim();
        String password = binding.password.getEditText().getText().toString().trim();
        ApiInterface apiInterface = RetroFitClient.getRetrofit().create(ApiInterface.class);
        LoginModel loginModel = new LoginModel(username, password, model.getDeviceName(), 1);
        Call<LoginModel> call = apiInterface.loginPostData(loginModel);
        call.enqueue(new Callback<LoginModel>() {

            @Override
            public void onResponse(Call<LoginModel> call, Response<LoginModel> response) {

                if (response.isSuccessful()){
                }
                String status = response.body().getStatus();
                Log.d("RESPONSE CHECK", "onResponse: "+ response.body());
                if (status.equals("success")) {
                    int userIdOnResponse = response.body().getUserId();
                    String usermail = response.body().getUsername();
                    int id = response.body().getPlatformId().intValue();
                    String name = response.body().getName();
                    Integer teacherId = response.body().getTeacherId();
                    preferenceManager.putBoolean(Constants.USER_LOGGED, true);
                    preferenceManager.putString(Constants.USER_EMAIL, usermail);
                    preferenceManager.putString(Constants.PLATFORM_ID, String.valueOf(id));
                    preferenceManager.putString(Constants.USER_ID, String.valueOf(userIdOnResponse));
                    preferenceManager.putString(Constants.NAME, name);
                    preferenceManager.putInt(Constants.TEACHER_ID, teacherId);
                    registerInFirebase(usermail, name, userIdOnResponse, teacherId);
                    saveTokenFireBase(userIdOnResponse);
                    Intent intent = new Intent(Login.this, DashboardActivity.class);
                    startActivity(intent);
                    finish();
                }
            }

            @Override
            public void onFailure(Call<LoginModel> call, Throwable t) {
                binding.username.getEditText().setText(t.toString());
                Toast.makeText(Login.this, t.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void registerInFirebase(String usermail, String name, Integer userId, Integer teacherId) {
        String mail = usermail.toLowerCase();
        if (!firebaseCheck(mail)){
            FirebaseFirestore database = FirebaseFirestore.getInstance();
            CollectionReference user = database.collection(Constants.FIREBASE_USER_DB);
            HashMap<String, Object> data = new HashMap<>();
            data.put("firebaseToken", preferenceManager.getString(Constants.FIREBASE_TOKEN));
            data.put("NAME", name);
            data.put("EMAIL", mail);
            data.put("USER_ID", userId);
            data.put("ROLE", "TEACHER");
            data.put("TEACHER_ID", teacherId);
            user.document(mail).set(data).addOnSuccessListener(d -> {

            }).addOnFailureListener(exception -> {
                preferenceManager.putBoolean(Constants.CHAT_ELIGIBLE, false);
            });
        }

    }

    public Boolean firebaseCheck(String email) {
        Integer i[] = {0};
        FirebaseFirestore database = FirebaseFirestore.getInstance();
        DocumentReference docRef = database.collection(Constants.FIREBASE_USER_DB).document(email);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    if (task.getResult().getData()!= null) {
                        i[0] = 1;
                        preferenceManager.putString(Constants.USER_ID_FIREBASE, task.getResult().getId());
                        Log.d("FCM User", "Exist : " + task.getResult().getData());
                    } else {
                        Log.d("FCM User", "Not Found");
                    }
                } else {
                    Log.d("TAG", "get failed with ", task.getException());
                }
            }
        });


        if (i[0] == 1) {
            return true;
        } else {
            return false;
        }

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
            loginNow();
            return true;
        }
        return false;
    }


    public void saveTokenFireBase(Integer userId){
        MobileModel mobileModel = new MobileModel();
        mobileModel.getDeviceName();
        FirebaseTokenModel firebaseTokenModel = new FirebaseTokenModel(userId, mobileModel.getDeviceName(), preferenceManager.getString(Constants.FIREBASE_TOKEN));
        ApiInterface apiInterface = RetroFitClient.getRetrofit().create(ApiInterface.class);
        Call<FirebaseTokenModel> call = apiInterface.firebaseToken(firebaseTokenModel);
        call.enqueue(new Callback<FirebaseTokenModel>() {
            @Override
            public void onResponse(Call<FirebaseTokenModel> call, Response<FirebaseTokenModel> response) {
                if (response.body().getToken()==null){

                }
            }

            @Override
            public void onFailure(Call<FirebaseTokenModel> call, Throwable t) {

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