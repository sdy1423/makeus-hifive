package com.song.makeushifive.src.main.notification.interfaces;

import com.song.makeushifive.src.main.notification.models.NotificationResponse;

import retrofit2.Call;
import retrofit2.http.GET;

public interface NotificationRetrofitInterface {

    @GET("/notice?type=share")
    Call<NotificationResponse> getNotification();

}