package com.isApp.teacher.fragment;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import com.isApp.teacher.Login;
import com.isApp.teacher.NotesActivity;
import com.isApp.teacher.ProfileActivity;
import com.isApp.teacher.R;
import com.isApp.teacher.common.Constants;
import com.isApp.teacher.common.LogoutDone;
import com.isApp.teacher.sharedPreference.PreferenceManager;

public class MoreFragment extends Fragment {
    TextView textViewName, textViewEmail;
    PreferenceManager preferenceManager;
    AppCompatButton viewProfile, notes, logout;
    String usermail, firebaseToken;
    Integer platformId, userId;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_more, container, false);
        textViewName = view.findViewById(R.id.more_name_tv);
        textViewEmail = view.findViewById(R.id.more_email_tv);
        viewProfile = view.findViewById(R.id.btn_more_profile);
        notes = view.findViewById(R.id.notes_btn_more);
        logout = view.findViewById(R.id.log_out_btn_more);
        preferenceManager = new PreferenceManager(view.getContext());
        platformId = Integer.valueOf(preferenceManager.getString(Constants.PLATFORM_ID));
        userId = Integer.valueOf(preferenceManager.getString(Constants.USER_ID));
        usermail = preferenceManager.getString(Constants.USER_EMAIL);
        textViewName.setText(preferenceManager.getString(Constants.NAME));
        textViewName.setAllCaps(true);
        textViewEmail.setText(preferenceManager.getString(Constants.USER_EMAIL));
        notes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(v.getContext(), NotesActivity.class);
                startActivity(i);
            }
        });
        viewProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(v.getContext(), ProfileActivity.class);
                startActivity(i);
            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LogoutDone logoutDone = new LogoutDone();
                firebaseToken = preferenceManager.getString(Constants.FIREBASE_TOKEN);
                if(logoutDone.logout(platformId,userId,usermail)){
                    preferenceManager.clear();
                    preferenceManager.putString(Constants.FIREBASE_TOKEN, firebaseToken);
                    Intent intent = new Intent(getActivity(), Login.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    getActivity().finishAffinity();
                }else {
                    Toast.makeText(getContext(), "failed", Toast.LENGTH_SHORT).show();
                }
            }
        });
        return  view;
    }
}