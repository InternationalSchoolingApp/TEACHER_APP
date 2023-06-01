package com.isApp.teacher.Adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.isApp.teacher.ChatActivity;
import com.isApp.teacher.Model.AssignStudentModel;
import com.isApp.teacher.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AssignStudentAdapter extends RecyclerView.Adapter<AssignStudentAdapter.ViewHolderChat> {

    List<AssignStudentModel.YeLo> list;
    public AssignStudentAdapter(List<AssignStudentModel.YeLo> list){
        this.list = list;
    }

    public AssignStudentAdapter(){

    }


    @NonNull
    @Override
    public ViewHolderChat onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.assign_student_list_view,parent,false);
        ViewHolderChat viewHolderChat = new ViewHolderChat(view);
        return viewHolderChat;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderChat holder, int position) {
        String studentId = list.get(position).getStudentId();
        String studentName = list.get(position).getStudentName();
        String subject = list.get(position).getCoursesName();
        String grade = list.get(position).getGrade();
        String email = list.get(position).getEmail();
        String country = list.get(position).getCountryName();
        String city = list.get(position).getCityName();
        String currentTime = list.get(position).getCurrentTime();
        String timeZone = list.get(position).getTimeZone();

        try {

            SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            SimpleDateFormat dt1 = new SimpleDateFormat("MMM dd, yyyy hh:mm aa");
            Date date = dt.parse(currentTime);
            String finalDate = dt1.format(date);
            holder.studentSubject.setText(subject+"\n"+ "Location :"+city+" | "+country+"\n"+"Current Time : "+finalDate+"\n"+"Student TimeZone : "+timeZone);


        }catch (Exception e){

        }

        holder.studentName.setText(studentName);
        holder.studentGrade.setText(grade);

        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), ChatActivity.class);
                intent.putExtra("studentId", studentId);
                intent.putExtra("studentName", studentName);
                intent.putExtra("subject", subject);
                intent.putExtra("grade",grade);
                intent.putExtra("email",email.toLowerCase());
                v.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolderChat extends RecyclerView.ViewHolder {
        TextView studentName, studentSubject, studentGrade;
        LinearLayout linearLayout;

        public ViewHolderChat(@NonNull View itemView) {
            super(itemView);
            studentName= itemView.findViewById(R.id.student_name_chat);
            studentSubject = itemView.findViewById(R.id.student_subject_name);
            studentGrade = itemView.findViewById(R.id.student_grade_name);
            linearLayout = itemView.findViewById(R.id.student_row_chat);

        }
    }

    public void filterList(ArrayList<AssignStudentModel.YeLo> filterlist) {
        list = filterlist;
        notifyDataSetChanged();
    }
}
