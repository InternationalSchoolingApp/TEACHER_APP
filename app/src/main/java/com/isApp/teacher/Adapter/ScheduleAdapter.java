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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;
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

            SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            SimpleDateFormat dt1 = new SimpleDateFormat("MMM dd, yyyy hh:mm aa");


            String title = event.getTitle();
            String startDate = event.getStart().replace("T", " ");
            String endDate = event.getEnd().replace("T", " ");



            try {
              Date  date = dt.parse(startDate);
              String start = dt1.format(date);

                Date  endDateBefore = dt.parse(endDate);
                String end = dt1.format(endDateBefore);

                binding.titleSchedule.setText(title);
                binding.startSchedule.setText(start);
                binding.endSchedule.setText(end);
                binding.scheduleRelativeLayout.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {

                        Intent intent = new Intent(v.getContext(), ScheduleViewActivity.class);
                        intent.putExtra("title", title);
                        intent.putExtra("startDate", start);
                        intent.putExtra("endDate", end);
                        v.getContext().startActivity(intent);
                    }
                });










            } catch (ParseException e) {
                throw new RuntimeException(e);
            }




        }

    }
}
