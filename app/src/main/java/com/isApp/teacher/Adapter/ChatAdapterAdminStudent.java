package com.isApp.teacher.Adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.isApp.teacher.Model.ChatMessage;
import com.isApp.teacher.databinding.ItemContainerRecieveMessageBinding;
import com.isApp.teacher.databinding.ItemContainerSentMessageBinding;

import java.util.List;

public class ChatAdapterAdminStudent extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private final List<ChatMessage> chatMessageList;
    private final String senderId;

    public static final int VIEW_TYPE_SENT = 1;
    public static final int VIEW_TYPE_RECIEVE = 2;

    public ChatAdapterAdminStudent(List<ChatMessage> chatMessageList, String senderId) {
        this.chatMessageList = chatMessageList;
        this.senderId = senderId;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_SENT) {
            return new ChatAdapter.SendMessageViewHolder(
                    ItemContainerSentMessageBinding.inflate(
                            LayoutInflater.from(parent.getContext()),
                            parent, false
                    )
            );
        } else {
            return new ChatAdapter.RecieveMessageViewHolder(
                    ItemContainerRecieveMessageBinding.inflate(
                            LayoutInflater.from(parent.getContext()),
                            parent, false
                    )
            );
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) == VIEW_TYPE_SENT) {
            ((ChatAdapter.SendMessageViewHolder) holder).setData(chatMessageList.get(position));
        } else {
            ((ChatAdapter.RecieveMessageViewHolder) holder).setData(chatMessageList.get(position));
        }
    }

    @Override
    public int getItemCount() {
        return chatMessageList.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (chatMessageList.get(position).senderId.equals(senderId)) {
            return VIEW_TYPE_SENT;
        } else {
            return VIEW_TYPE_RECIEVE;
        }
    }

    static class SentMessageViewHolder extends RecyclerView.ViewHolder {

        private final ItemContainerSentMessageBinding binding;

        SentMessageViewHolder(ItemContainerSentMessageBinding itemContainerSentMessageBinding) {
            super(itemContainerSentMessageBinding.getRoot());
            binding = itemContainerSentMessageBinding;
        }

        void setData(ChatMessage chatMessage) {
            binding.textMessage.setText(chatMessage.message);
            binding.textDateTime.setText(chatMessage.time);
        }

    }

    static class RecievedMessageViewHolder extends RecyclerView.ViewHolder{

        private final ItemContainerRecieveMessageBinding binding;

        RecievedMessageViewHolder(ItemContainerRecieveMessageBinding itemContainerRecieveMessageBinding){
            super(itemContainerRecieveMessageBinding.getRoot());
            binding = itemContainerRecieveMessageBinding;
        }

        void setData(ChatMessage chatMessage) {
            binding.textMessageRecieve.setText(chatMessage.message);
            binding.textMessageRecieveDateTime.setText(chatMessage.time);
        }

    }

}
