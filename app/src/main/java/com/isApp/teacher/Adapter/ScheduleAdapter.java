package com.isApp.teacher.Adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.isApp.teacher.Model.ScheduleModel;
import com.isApp.teacher.ScheduleViewActivity;
import com.isApp.teacher.databinding.ScheduleViewBinding;

import java.util.List;

public class ScheduleAdapter extends  RecyclerView.Adapter<ScheduleAdapter.ScheduleViewHolder>{

    List<ScheduleModel.SchoolCalendar.Event> list;

    public ScheduleAdapter(List<ScheduleModel.SchoolCalendar.Event> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public ScheduleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ScheduleViewHolder(ScheduleViewBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ScheduleViewHolder holder, int position) {


            holder.setData(list.get(position));
        



    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ScheduleViewHolder extends RecyclerView.ViewHolder{
        ScheduleViewBinding binding;

        ScheduleViewHolder(ScheduleViewBinding scheduleViewBinding){
            super(scheduleViewBinding.getRoot());
            binding = scheduleViewBinding;
        }


        public void setData(ScheduleModel.SchoolCalendar.Event event){

            String title = event.getTitle();
            String startDate = event.getStart().replace("T", " ");
            String endDate = event.getEnd().replace("T", " ");

            binding.titleSchedule.setText(title);
            binding.startSchedule.setText(startDate);
            binding.endSchedule.setText(endDate);
            binding.scheduleRelativeLayout.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(v.getContext(), ScheduleViewActivity.class);
                    intent.putExtra("title", title);
                    intent.putExtra("startDate", startDate);
                    intent.putExtra("endDate", endDate);
                    v.getContext().startActivity(intent);
                }
            });



        }

    }
}
