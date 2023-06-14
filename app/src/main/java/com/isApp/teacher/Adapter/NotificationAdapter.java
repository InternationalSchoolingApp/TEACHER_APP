package com.isApp.teacher.Adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.isApp.teacher.Model.NotificationForApp;
import com.isApp.teacher.NotificationViewActivity;
import com.isApp.teacher.R;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.NotificationViewHolder> {
    public List<NotificationForApp.ListForApp> list;

    public NotificationAdapter(List<NotificationForApp.ListForApp> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public NotificationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.notification_view, parent, false);
        NotificationViewHolder notificationViewHolder = new NotificationViewHolder(view);
        return notificationViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull NotificationViewHolder holder, int position) {

        String title = list.get(position).getEntityName();
        String message = list.get(position).getMessage();
        String time = list.get(position).getCreatedDate();
        Integer id = list.get(position).getId();
        Integer userId = list.get(position).getUserId();

        try {

            SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            SimpleDateFormat dt1 = new SimpleDateFormat("MMM dd, yyyy hh:mm aa");
            Date date = dt.parse(time);
            String finalDate = dt1.format(date);

            holder.title.setText(list.get(position).getEntityName());
            holder.message.setText(list.get(position).getMessage());
            holder.time.setText(finalDate);


            holder.layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    Intent intent = new Intent(v.getContext(), NotificationViewActivity.class);
                    intent.putExtra("title", title);
                    intent.putExtra("message", message);
                    intent.putExtra("time", finalDate);
                    intent.putExtra("id", id);
                    intent.putExtra("userId", userId);
                    v.getContext().startActivity(intent);
                }
            });
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class NotificationViewHolder extends RecyclerView.ViewHolder {

        TextView title, message, time;
        LinearLayout layout;

        public NotificationViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.notification_title);
            message = itemView.findViewById(R.id.notification_message);
            time = itemView.findViewById(R.id.date_notification);
            layout = itemView.findViewById(R.id.complete_notification_field);
        }
    }
}
