package com.example.kaappbai;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ChatbotApiClieant {
    private static final String BASE_URL = "https://your-azure-app-url.com/rasa-api-endpoint"; // Replace with your API endpoint

    private ChatbotService chatbotService;

    public ChatbotApiClieant() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        chatbotService = retrofit.create(ChatbotService.class);
    }

    public void sendMessage(String userMessage, Callback<ChatbotResponse> callback) {
        ChatbotRequest message = new ChatbotRequest(userMessage);
        Call<ChatbotResponse> call = chatbotService.sendMessage(BASE_URL, message);

        call.enqueue(callback);
    }
}

