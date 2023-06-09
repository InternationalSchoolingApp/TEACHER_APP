package com.isApp.teacher.Adapter;


import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.isApp.teacher.Model.AssignedSubjects;
import com.isApp.teacher.R;
import com.isApp.teacher.SubjectDescriptionActivity;

import java.util.List;

public class AssignedSubjectAdapter extends RecyclerView.Adapter<AssignedSubjectAdapter.AssignedSubjectViewHolder>{

    public List<AssignedSubjects.TeacherSubjectDTO> list ;

    public AssignedSubjectAdapter(List<AssignedSubjects.TeacherSubjectDTO> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public AssignedSubjectViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.assign_subject_view, parent, false);
        AssignedSubjectAdapter.AssignedSubjectViewHolder viewHolder = new AssignedSubjectAdapter.AssignedSubjectViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull AssignedSubjectViewHolder holder, int position) {
        holder.courseName.setText(list.get(position).getSubjectName());
        holder.courseProvider.setText(list.get(position).getCourseProviderName());
        holder.startDate.setText(list.get(position).getStartDate());
        holder.standardName.setText(list.get(position).getStandardName());
        String subjectId = list.get(position).getSubjectId();
        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), SubjectDescriptionActivity.class);
                intent.putExtra("subjectId", subjectId);
                v.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class AssignedSubjectViewHolder extends RecyclerView.ViewHolder {

        TextView courseName, standardName, courseProvider, startDate;
        LinearLayout layout;
        public AssignedSubjectViewHolder(@NonNull View itemView) {
            super(itemView);
            startDate = itemView.findViewById(R.id.start_date);
            courseName = itemView.findViewById(R.id.course_name);
            courseProvider = itemView.findViewById(R.id.course_provider);
            standardName = itemView.findViewById(R.id.standard_name);
            layout = itemView.findViewById(R.id.subject_linear_layout);
        }
    }
}
