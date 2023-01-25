package com.isApp.teacher.Adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.isApp.teacher.Model.GetNotes;
import com.isApp.teacher.NotesViewActivity;
import com.isApp.teacher.R;

import java.util.List;


public class MyNotesAdapter extends RecyclerView.Adapter<MyNotesAdapter.NotesViewHolder>{

    public List<GetNotes.List> list;

    public MyNotesAdapter(List<GetNotes.List> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public NotesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.my_notes_preview, parent, false);
        NotesViewHolder notesViewHolder = new NotesViewHolder(view);
        return notesViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull NotesViewHolder holder, int position) {

        String title = list.get(position).getTitle();
        String message = list.get(position).getMessage();
        String time = list.get(position).getDate();
        holder.title.setText(list.get(position).getTitle());
        holder.message.setText(list.get(position).getMessage());
        holder.time.setText(list.get(position).getDate());

        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), NotesViewActivity.class);
                intent.putExtra("title", title);
                intent.putExtra("message", message);
                intent.putExtra("time", time);
                v.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class NotesViewHolder extends RecyclerView.ViewHolder {

        TextView title, message, time;
        LinearLayout layout;
        public NotesViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.preview_notes_title);
            message = itemView.findViewById(R.id.preview_notes_message);
            time = itemView.findViewById(R.id.date_preview_notes);
            layout = itemView.findViewById(R.id.notes_view);
        }
    }
}
