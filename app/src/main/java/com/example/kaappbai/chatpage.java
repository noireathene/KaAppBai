package com.example.kaappbai;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class chatpage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatpage);

        ChatbotService chatbotService = RetrofitClient.getInstance().create(ChatbotService.class);

// Create a ChatbotRequest object with the user's message
        ChatbotRequest request = new ChatbotRequest("Hello!");

// Send the request to the chatbot
        Call<ChatbotResponse> call = chatbotService.sendMessage(request);

        call.enqueue(new Callback<ChatbotResponse>() {
            @Override
            public void onResponse(Call<ChatbotResponse> call, Response<ChatbotResponse> response) {
                if (response.isSuccessful()) {
                    ChatbotResponse chatbotResponse = response.body();
                    String reply = chatbotResponse.getReply();

                    // Handle the chatbot's reply (e.g., display it in your app's UI)
                } else {
                    // Handle HTTP error (e.g., show an error message)
                }
            }

            @Override
            public void onFailure(Call<ChatbotResponse> call, Throwable t) {
                // Handle network or other errors
            }
        });

    }
}