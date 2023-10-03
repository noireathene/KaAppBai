package com.example.kaappbai;

import static com.example.kaappbai.RetrofitClient.BASE_URL;
import static com.example.kaappbai.login.TAG;

import android.util.Log;
import android.widget.Toast;
import android.content.Context;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import java.util.List;


public class ChatbotManager {
    private ChatbotService chatbotService;
    private List<ChatMessage> chatMessages;
    private ChatAdapter chatAdapter;

    public ChatbotManager(ChatAdapter chatAdapter, List<ChatMessage> chatMessages) {
        this.chatAdapter = chatAdapter;
        this.chatMessages = chatMessages;

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://chatbotkaappbai-kaappbai.azurewebsites.net") // Replace with your API endpoint
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        chatbotService = retrofit.create(ChatbotService.class);
    }

    public void sendMessageToChatbot(String userMessage, final ChatbotResponseListener listener) {
        ChatbotRequest message = new ChatbotRequest(userMessage);

        Call<ChatbotResponse> call = chatbotService.sendMessage(BASE_URL, message); // Replace BASE_URL with your actual URL
        call.enqueue(new Callback<ChatbotResponse>() {
            @Override
            public void onResponse(Call<ChatbotResponse> call, Response<ChatbotResponse> response) {
                if (response.isSuccessful()) {
                    ChatbotResponse botResponse = response.body();
                    String botMessage = botResponse != null ? botResponse.getReply() : "No response from bot.";

                    // Create a new ChatMessage for the received message and add it to the chatAdapter's data list
                    ChatMessage receivedMessage = new ChatMessage("Bot", botMessage, true);
                    chatAdapter.addMessage(receivedMessage);

                    // Notify the adapter that the data set has changed
                    chatAdapter.notifyDataSetChanged();

                    listener.onResponseReceived(botMessage);
                } else {
                    listener.onResponseReceived("Failed to get a response from the chatbot.");
                }
            }

            @Override
            public void onFailure(Call<ChatbotResponse> call, Throwable t) {
                // Handle network call failure here
                String errorMessage = "Network error: " + t.getMessage();

                Log.d(TAG, errorMessage);

            }
        });
    }



    public interface ChatbotResponseListener {
        void onResponseReceived(String response);
    }
}
