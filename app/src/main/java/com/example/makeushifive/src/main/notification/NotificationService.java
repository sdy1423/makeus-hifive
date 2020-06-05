package com.example.makeushifive.src.main.notification;

import com.example.makeushifive.src.main.notification.interfaces.NotificationRetrofitInterface;
import com.example.makeushifive.src.main.notification.interfaces.NotificationView;
import com.example.makeushifive.src.main.notification.models.NotificationResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.makeushifive.src.ApplicationClass.getRetrofit;

public class NotificationService {
    NotificationView notificationView;
    public NotificationService(NotificationView notificationView) {
        this.notificationView = notificationView;
    }

    public void getNotification(){
        NotificationRetrofitInterface notificationRetrofitInterface = getRetrofit().create(NotificationRetrofitInterface.class);
        notificationRetrofitInterface.getNotification().enqueue(new Callback<NotificationResponse>() {
            @Override
            public void onResponse(Call<NotificationResponse> call, Response<NotificationResponse> response) {
                try {
                    assert response.body() != null;
                    if(response.body().getCode()==100){
                        notificationView.getNotificationSuccess(response.body().getResults());
                    }else{
                        notificationView.getNotificationFail();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<NotificationResponse> call, Throwable t) {
                notificationView.getNotificationFail();
            }
        });
    }
}
