package com.example.kaappbai;

import static com.example.kaappbai.RetrofitClient.BASE_URL;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ChatbotManager {
    private ChatbotService chatbotService;

    public ChatbotManager() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://your-azure-app-url.com/rasa-api-endpoint/") // Replace with your API endpoint
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        chatbotService = retrofit.create(ChatbotService.class);
    }

    public void sendMessageToChatbot(String userMessage, final ChatbotResponseListener listener) {
        ChatbotRequest message = new ChatbotRequest(userMessage);

        Call<ChatbotResponse> call = chatbotService.sendMessage(BASE_URL, message);
        call.enqueue(new Callback<ChatbotResponse>() {


            @Override
            public void onResponse(Call<ChatbotResponse> call, Response<ChatbotResponse> response) {
                if (response.isSuccessful()) {
                    ChatbotResponse botResponse = response.body();
                    String botMessage = botResponse != null ? botResponse.getReply() : "No response from bot.";
                    listener.onResponseReceived(botMessage);
                } else {
                    listener.onResponseReceived("Failed to get a response from the chatbot.");
                }
            }

            @Override
            public void onFailure(Call<ChatbotResponse> call, Throwable t) {

            }

        });
    }

    public interface ChatbotResponseListener {
        void onResponseReceived(String response);
    }
}
