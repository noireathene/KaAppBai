package com.example.kaappbai;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.api.gax.core.FixedCredentialsProvider;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.dialogflow.v2.*;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class chatpage extends AppCompatActivity {
    private EditText userInputEditText;
    private FrameLayout sendButton;
    private RecyclerView chatRecyclerView;
    private ChatAdapter chatAdapter;
    private List<ChatMessage> chatMessages = new ArrayList<>();
    private SessionsClient sessionsClient;
    private String projectId = "kaappbai-400915";
    private String jsonKeyFileName = "kaappbai-400915-c668a0614aeb.json"; // Place your JSON key file in the 'assets' folder.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatpage);

        userInputEditText = findViewById(R.id.inputMessage);
        chatRecyclerView = findViewById(R.id.chatRecyclerView);

        sendButton = findViewById(R.id.layoutSend);

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userMessage = userInputEditText.getText().toString();
                if (!userMessage.isEmpty()) {
                    // Send the user message to Dialogflow and retrieve the bot's response
                    String botResponse = sendMessageToDialogflow(userMessage);

                    // Update the chat UI with the user and bot messages
                    updateChatUI(userMessage, botResponse);

                    // Clear the user input field
                    userInputEditText.setText("");
                }
            }
        });

        try {
            sessionsClient = createSessionsClient();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Initialize the RecyclerView and adapter
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setStackFromEnd(true); // Start the chat from the bottom
        chatRecyclerView.setLayoutManager(layoutManager);
        chatAdapter = new ChatAdapter(chatMessages);
        chatRecyclerView.setAdapter(chatAdapter);
    }

    private SessionsClient createSessionsClient() throws IOException {
        // Read the JSON key file from the "assets" folder
        InputStream keyFileStream = getAssets().open(jsonKeyFileName);
        GoogleCredentials credentials = GoogleCredentials.fromStream(keyFileStream);
        SessionsSettings settings = SessionsSettings.newBuilder()
                .setCredentialsProvider(FixedCredentialsProvider.create(credentials))
                .build();
        return SessionsClient.create(settings);
    }

    private String sendMessageToDialogflow(String userMessage) {
        // Implement Dialogflow interaction here and return the bot's response.
        // You need to send the user message to Dialogflow using sessionsClient.

        return "This is a bot response to: " + userMessage;
    }

    private void updateChatUI(String userMessage, String botResponse) {
        // Create ChatMessage objects for user and bot messages
        ChatMessage userChatMessage = new ChatMessage(userMessage, false);
        ChatMessage botChatMessage = new ChatMessage(botResponse, true);

        // Add the messages to the chatMessages list
        chatMessages.add(userChatMessage);
        chatMessages.add(botChatMessage);

        // Notify the adapter to refresh the RecyclerView
        chatAdapter.notifyDataSetChanged();
    }
}


/*public class chatpage extends AppCompatActivity {

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
*/