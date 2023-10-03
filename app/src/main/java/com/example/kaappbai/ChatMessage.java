package com.example.kaappbai;

import android.util.Log;
import static com.example.kaappbai.login.TAG;

public class ChatMessage {
    private String sender;
    private String message;
    private boolean sent; // Add a field to indicate whether the message is sent

    public ChatMessage(String sender, String message, boolean sent) {
        this.sender = sender;
        this.message = message;
        this.sent = sent; // Set the 'sent' field
    }

    public String getSender() {
        return sender;
    }

    public String getMessage() {
        return message;
    }

    public boolean isSent() {
        Log.d(TAG, "isSent: true");
        return sent; // Return the value of the 'sent' field
    }
}
