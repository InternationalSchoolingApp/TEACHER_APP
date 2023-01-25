package com.isApp.teacher.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.isApp.teacher.AssignedStudent;
import com.isApp.teacher.R;
import com.isApp.teacher.RecentChatActivity;


public class ChatFragment extends Fragment {

    RelativeLayout relativeLayout;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chat, container, false);
        relativeLayout = view.findViewById(R.id.student_row);
        relativeLayout.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(v.getContext(), RecentChatActivity.class);
                        v.getContext().startActivity(intent);

                    }
                }
        );

        return view;
    }
}