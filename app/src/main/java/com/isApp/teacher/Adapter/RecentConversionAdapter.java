package com.isApp.teacher.Adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.isApp.teacher.ChatActivity;
import com.isApp.teacher.Model.ChatMessage;
import com.isApp.teacher.databinding.TeacherRecentConversationBinding;

import java.util.List;

public class RecentConversionAdapter extends RecyclerView.Adapter<RecentConversionAdapter.ConversionViewHolder> {
    private final List<ChatMessage> chatMessages;

    public RecentConversionAdapter(List<ChatMessage> chatMessages) {
        this.chatMessages = chatMessages;
    }

    @NonNull
    @Override
    public ConversionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ConversionViewHolder(TeacherRecentConversationBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ConversionViewHolder holder, int position) {
        holder.setData(chatMessages.get(position));

    }

    @Override
    public int getItemCount() {
        return chatMessages.size();
    }

    class ConversionViewHolder extends RecyclerView.ViewHolder {
        TeacherRecentConversationBinding binding;

        ConversionViewHolder(TeacherRecentConversationBinding teacherRecentConversationBinding) {
            super(teacherRecentConversationBinding.getRoot());
            binding = teacherRecentConversationBinding;
        }

        void setData(ChatMessage chatMessage) {
            binding.recentTeacherNameChat.setText(chatMessage.conversionName);
            binding.teacherRecentMessage.setText(chatMessage.message);
            binding.timeRecentMessage.setText(chatMessage.dateObject.toString());
            String studentUserId = chatMessage.studentUserId;
            String studentName = chatMessage.studentName;
            String studentEmail = chatMessage.studentEmail;
            String studentSubject = chatMessage.studentCourse;



            binding.getRoot().setOnClickListener(new View.OnClickListener() {
                                                     @Override
                                                     public void onClick(View v) {
                                                         Intent intent = new Intent(v.getContext(), ChatActivity.class);
                                                         intent.putExtra("studentId", studentUserId);
                                                         intent.putExtra("studentName", studentName);
                                                         intent.putExtra("subject", studentSubject);
                                                         intent.putExtra("email", studentEmail);
                                                         v.getContext().startActivity(intent);

                                                     }
                                                 }


            );


        }
    }

}
