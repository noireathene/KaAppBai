package com.example.kaappbai;

import com.example.kaappbai.EmailData;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface EmailApi {
    @POST("your-server-endpoint")
    Call<Void> sendEmail(@Body EmailData emailData);
}
