package com.isApp.teacher.Network;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;

import androidx.appcompat.widget.AppCompatButton;

import com.isApp.teacher.R;


public class NetworkChangeListener extends BroadcastReceiver {


    @Override
    public void onReceive(Context context, Intent intent) {
        if(!NetworkCheck.isConnectedToInternet(context)) {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            View layout_dialogue = LayoutInflater.from(context).inflate(R.layout.check_network_dialogue, null);
            builder.setView(layout_dialogue);
            AppCompatButton button = layout_dialogue.findViewById(R.id.btn_retry);

            AlertDialog dialog = builder.create();
            dialog.show();
            dialog.setCancelable(false);

            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                    onReceive(context, intent);
                }
            });

        }
    }
}
