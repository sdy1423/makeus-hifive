package com.example.makeushifive.src.main.notification.interfaces;

import com.example.makeushifive.src.main.notification.models.NotificationResponse;

import java.util.ArrayList;

public interface NotificationView {
    void getNotificationSuccess(ArrayList<NotificationResponse.Result> results);
    void getNotificationFail();
}
