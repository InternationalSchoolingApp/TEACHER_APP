package com.isApp.teacher.Firebase;


import android.content.SharedPreferences;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.isApp.teacher.common.Constants;
import com.isApp.teacher.sharedPreference.PreferenceManager;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private PreferenceManager preferenceManager;
    @Override
    public void onNewToken(@NonNull String token) {
        super.onNewToken(token);
        Log.d("FCM", "Token: " + token);
        preferenceManager = new PreferenceManager(getApplicationContext());
        preferenceManager.putString(Constants.FIREBASE_TOKEN, token);
    }

    @Override
    public void onMessageReceived(@NonNull RemoteMessage message) {
        super.onMessageReceived(message);
        Log.d("FCM", "Message: "+message.getNotification().getBody());
    }



}
