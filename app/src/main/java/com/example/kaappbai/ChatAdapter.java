package com.example.kaappbai;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;
import java.util.List;
import android.content.Context;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ChatViewHolder> {
    private List<ChatMessage> messages;
    private Context context;

    public ChatAdapter(List<ChatMessage> messages) {
        this.context = context;
        this.messages = messages;
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    @Override
    public int getItemViewType(int position) {
        return messages.get(position).isBotMessage() ? 0 : 1;
    }

    @Override
    public ChatViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        int layoutId = viewType == 0 ? R.layout.received_message_layout : R.layout.sent_message_layout;
        View itemView = LayoutInflater.from(parent.getContext()).inflate(layoutId, parent, false);
        return new ChatViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ChatViewHolder holder, int position) {
        ChatMessage message = messages.get(position);
        if (message.isBotMessage()) {
            holder.receivedmessageTextView.setVisibility(View.VISIBLE);
            holder.sentmessageTextView.setVisibility(View.GONE);
            holder.receivedmessageTextView.setText(message.getMessageText());
        } else {
            holder.sentmessageTextView.setVisibility(View.VISIBLE);
            holder.receivedmessageTextView.setVisibility(View.GONE);
            holder.sentmessageTextView.setText(message.getMessageText());
        }
    }

    class ChatViewHolder extends RecyclerView.ViewHolder {
        TextView receivedmessageTextView, sentmessageTextView;

        ChatViewHolder(View itemView) {
            super(itemView);
            receivedmessageTextView = itemView.findViewById(R.id.receivedMessageTextView);
            sentmessageTextView = itemView.findViewById(R.id.sentMessageTextView);
        }
    }
}



/*public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ViewHolder> {
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
        try {
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
        } catch (Exception e) {
            // Handle any exceptions that may occur during view creation
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ChatMessage message;
        try {
            message = chatMessages.get(position);
        } catch (Exception e) {
            // Handle any exceptions that may occur when accessing chatMessages
            e.printStackTrace();
            message = null;
        }

        if (message != null) {
            if (message.isSent()) {
                try {
                    holder.showSentMessageViews();
                    holder.messageTextView.setText(message.getMessage());
                } catch (Exception e) {
                    // Handle exceptions related to updating the UI
                    e.printStackTrace();
                }
            } else {
                try {
                    holder.showReceivedMessageViews();
                    holder.receivedMessageTextView.setText(message.getMessage());
                } catch (Exception e) {
                    // Handle exceptions related to updating the UI
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public int getItemCount() {
        try {
            return chatMessages.size();
        } catch (Exception e) {
            // Handle exceptions related to accessing chatMessages size
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public int getItemViewType(int position) {
        ChatMessage message;
        try {
            message = chatMessages.get(position);
        } catch (Exception e) {
            // Handle any exceptions that may occur when accessing chatMessages
            e.printStackTrace();
            message = null;
        }

        if (message != null) {
            return message.isSent() ? SENT_MESSAGE : RECEIVED_MESSAGE;
        } else {
            return RECEIVED_MESSAGE; // Default to received message type in case of an exception
        }
    }

    // Add this method to add a new message to the chatMessages list
    public void addMessage(ChatMessage message) {
        try {
            chatMessages.add(message);
            notifyItemInserted(chatMessages.size() - 1);
        } catch (Exception e) {
            // Handle exceptions related to modifying the chatMessages list
            e.printStackTrace();
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView messageTextView;
        public TextView receivedMessageTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            try {
                messageTextView = itemView.findViewById(R.id.sentMessageTextView);
                receivedMessageTextView = itemView.findViewById(R.id.receivedMessageTextView);
            } catch (Exception e) {
                // Handle exceptions related to finding view elements
                e.printStackTrace();
            }
        }

        public void showSentMessageViews() {
            try {
                messageTextView.setVisibility(View.VISIBLE);
                receivedMessageTextView.setVisibility(View.GONE);
            } catch (Exception e) {
                // Handle exceptions related to updating the UI
                e.printStackTrace();
            }
        }

        public void showReceivedMessageViews() {
            try {
                messageTextView.setVisibility(View.GONE);
                receivedMessageTextView.setVisibility(View.VISIBLE);
            } catch (Exception e) {
                // Handle exceptions related to updating the UI
                e.printStackTrace();
            }
        }
    }
}
*/