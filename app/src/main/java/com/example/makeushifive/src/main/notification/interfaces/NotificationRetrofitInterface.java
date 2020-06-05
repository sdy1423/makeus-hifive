package com.example.makeushifive.src.main.notification.interfaces;

import com.example.makeushifive.src.main.notification.models.NotificationResponse;

import retrofit2.Call;
import retrofit2.http.GET;

public interface NotificationRetrofitInterface {

    @GET("/notice?type=share")
    Call<NotificationResponse> getNotification();

}
