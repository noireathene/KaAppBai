package com.example.kaappbai;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class chatpage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatpage);

        EditText userInputEditText;
        FrameLayout sendButton;

        // Initialize views
        userInputEditText = findViewById(R.id.inputMessage);
        sendButton = findViewById(R.id.layoutSend);
       // chatbotResponseTextView = findViewById(R.id.chatbotResponseTextView);

        // Initialize ChatbotManager
        ChatbotManager chatbotManager = new ChatbotManager();

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Get user input message
                String userMessage = userInputEditText.getText().toString();

                // Send user message to the chatbot
                chatbotManager.sendMessageToChatbot(userMessage, new ChatbotManager.ChatbotResponseListener() {
                    @Override
                    public void onResponseReceived(String response) {
                        // Display the bot's response in the TextView
                        //chatbotResponseTextView.setText(response);
                    }
                });

                // Clear the user input field
                userInputEditText.setText("");
            }
        });
    }
}