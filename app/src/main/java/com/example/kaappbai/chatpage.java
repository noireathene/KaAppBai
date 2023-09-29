package com.example.kaappbai;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class chatpage extends AppCompatActivity {

    private EditText userInputEditText;
    private FrameLayout sendButton;
    private RecyclerView chatRecyclerView;
    private ChatAdapter chatAdapter;
    private List<ChatMessage> chatMessages;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatpage);

        // Initialize views
        userInputEditText = findViewById(R.id.inputMessage);
        sendButton = findViewById(R.id.layoutSend);
        chatRecyclerView = findViewById(R.id.chatRecyclerView);

        // Initialize ChatbotManager
        ChatbotManager chatbotManager = new ChatbotManager();

        // Initialize chatMessages list
        chatMessages = new ArrayList<>();

        // Initialize chatAdapter and set it to RecyclerView
        chatAdapter = new ChatAdapter(chatMessages);
        chatRecyclerView.setAdapter(chatAdapter);
        chatRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Set the initial visibility of chatRecyclerView to "visible"
        chatRecyclerView.setVisibility(View.VISIBLE);

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Get user input from userInputEditText
                String userMessage = userInputEditText.getText().toString().trim();

                // Check if userMessage is not empty
                if (!userMessage.isEmpty()) {
                    // Create a sent message
                    ChatMessage sentMessage = new ChatMessage("You", userMessage, true);

                    // Add the sent message to the chatMessages list
                    chatMessages.add(sentMessage);

                    // Notify the adapter that the data has changed
                    chatAdapter.notifyDataSetChanged();

                    // Clear the user input field
                    userInputEditText.setText("");

                    // Handle the chatbot response (you can add your logic here)
                    String botResponse = chatbotManager.generateResponse(userMessage);

                    // Create a received message (chatbot's response)
                    ChatMessage receivedMessage = new ChatMessage("Chatbot", botResponse, false);

                    // Add the received message to the chatMessages list
                    chatMessages.add(receivedMessage);

                    // Notify the adapter that the data has changed
                    chatAdapter.notifyDataSetChanged();
                }
            }
        });
    }
}
