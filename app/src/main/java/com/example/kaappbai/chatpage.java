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
    private ChatbotManager chatbotManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatpage);

        // Initialize views
        userInputEditText = findViewById(R.id.inputMessage);
        sendButton = findViewById(R.id.layoutSend);
        chatRecyclerView = findViewById(R.id.chatRecyclerView);

        // Initialize chatMessages list (make sure it's not null)
        chatMessages = new ArrayList<>();

        // Initialize chatAdapter and set it to RecyclerView
        chatAdapter = new ChatAdapter(chatMessages);
        chatRecyclerView.setAdapter(chatAdapter);
        chatRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Set the initial visibility of chatRecyclerView to "VISIBLE"
        chatRecyclerView.setVisibility(View.VISIBLE);

        // Initialize ChatbotManager
        chatbotManager = new ChatbotManager(chatAdapter, chatMessages);

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
                    chatAdapter.addMessage(sentMessage);

                    // Notify the adapter that the data has changed
                    chatAdapter.notifyDataSetChanged();

                    // Clear the user input field
                    userInputEditText.setText("");

                    // Use the ChatbotManager to send the user message to the chatbot
                    chatbotManager.sendMessageToChatbot(userMessage, new ChatbotManager.ChatbotResponseListener() {
                        @Override
                        public void onResponseReceived(String botResponse) {
                            // Create a received message (chatbot's response)
                            ChatMessage receivedMessage = new ChatMessage("Chatbot", botResponse, true);

                            // Add the received message to the chatMessages list
                            chatAdapter.addMessage(receivedMessage);

                            // Notify the adapter that the data has changed
                            chatAdapter.notifyDataSetChanged();
                        }
                    });
                }
            }
        });
    }
}
