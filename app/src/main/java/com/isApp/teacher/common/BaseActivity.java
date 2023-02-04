package com.isApp.teacher.common;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.isApp.teacher.sharedPreference.PreferenceManager;

public class BaseActivity extends AppCompatActivity {

    DocumentReference documentReference;
    PreferenceManager preferenceManager;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        preferenceManager = new PreferenceManager(this);
        String email = preferenceManager.getString(Constants.USER_EMAIL).toLowerCase();
        FirebaseFirestore database = FirebaseFirestore.getInstance();
        documentReference = database.collection(Constants.FIREBASE_USER_DB).document(email);
    }


    @Override
    protected void onPause() {
        super.onPause();
        documentReference.update(Constants.KEY_AVAILABLITY,0);
    }

    @Override
    protected void onResume() {
        super.onResume();
        documentReference.update(Constants.KEY_AVAILABLITY,1);
    }
}
