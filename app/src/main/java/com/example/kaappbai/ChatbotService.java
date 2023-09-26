package com.example.kaappbai;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ChatbotService {
    String BASE_URL = "https://your-chatbot-api-url.com"; // Replace with your Azure Web App URL

    @POST("/api/chat") // Replace with the actual API endpoint on your chatbot
    Call<ChatbotResponse> sendMessage(@Body ChatbotRequest request);
    Call<ChatbotResponse> sendUserResponse(@Body UserResponse userResponse);
}



