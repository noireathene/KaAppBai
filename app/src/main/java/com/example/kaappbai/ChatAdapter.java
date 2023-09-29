package com.example.kaappbai;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ViewHolder> {
    private static final int RECEIVED_MESSAGE = 1;
    private static final int SENT_MESSAGE = 2;
    private List<ChatMessage> chatMessages;

    public ChatAdapter(List<ChatMessage> chatMessages) {
        this.chatMessages = chatMessages;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        int layoutResId;
        switch (viewType) {
            case RECEIVED_MESSAGE:
                layoutResId = R.layout.received_message_layout;
                break;
            case SENT_MESSAGE:
                layoutResId = R.layout.sent_message_layout;
                break;
            default:
                layoutResId = R.layout.default_message_layout;
                break;
        }

        View view = LayoutInflater.from(parent.getContext())
                .inflate(layoutResId, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ChatMessage message = chatMessages.get(position);
        holder.senderTextView.setText(message.getSender());
        holder.messageTextView.setText(message.getMessage());
    }

    @Override
    public int getItemCount() {
        return chatMessages.size();
    }

    @Override
    public int getItemViewType(int position) {
        ChatMessage message = chatMessages.get(position);
        return message.isSent() ? SENT_MESSAGE : RECEIVED_MESSAGE;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView senderTextView;
        public TextView messageTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            senderTextView = itemView.findViewById(R.id.senderTextView);
            messageTextView = itemView.findViewById(R.id.messageTextView);
        }
    }
}
