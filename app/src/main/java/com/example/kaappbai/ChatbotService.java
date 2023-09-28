package com.example.kaappbai;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Url;

public interface ChatbotService {


        @POST
        Call<ChatbotResponse> sendMessage(@Url String url, @Body ChatbotRequest message);

}



