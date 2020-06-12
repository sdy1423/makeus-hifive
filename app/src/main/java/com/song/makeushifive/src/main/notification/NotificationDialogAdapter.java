package com.song.makeushifive.src.main.notification;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.song.makeushifive.R;
import com.song.makeushifive.src.main.notification.interfaces.NotificationView;
import com.song.makeushifive.src.main.notification.models.NotificationResponse;

import java.util.ArrayList;
import java.util.Objects;

public class NotificationDialogAdapter extends DialogFragment implements NotificationView {

    Context context;
    RecyclerView mRecycler;
    ArrayList<NotificationInfo> notificationInfos = new ArrayList<>();

    public NotificationDialogAdapter(Context context) {
        this.context = context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Objects.requireNonNull(Objects.requireNonNull(getDialog()).getWindow()).requestFeature(Window.FEATURE_NO_TITLE);
        Objects.requireNonNull(getDialog().getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        View rootview = inflater.inflate(R.layout.item_dialog_notification,container,false);
//        Dialog dialog = super.onCreateDialog(savedInstanceState);
//        dialog.setTitle("My Title");

        mRecycler = rootview.findViewById(R.id.notification_recycler);
        NotificationService notificationService = new NotificationService(this);
        notificationService.getNotification();

        ImageView mIvClose = rootview.findViewById(R.id.noti_iv_close);
        mIvClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        return rootview;
//        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Objects.requireNonNull(Objects.requireNonNull(getDialog()).getWindow())
                .getAttributes().windowAnimations = R.style.DialogAnimation;

    }

//    public void onResume() {
//        super.onResume();
//        int width = getResources().getDimensionPixelSize(R.dimen.year_month_pick_dialog_width);
//        int height = getResources().getDimensionPixelSize(R.dimen.year_month_pick_dialog_height);
//        Objects.requireNonNull(Objects.requireNonNull(getDialog()).getWindow()).setLayout(width,height);
//    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null) {
            Objects.requireNonNull(dialog.getWindow()).setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }
    }

    @Override
    public void getNotificationSuccess(ArrayList<NotificationResponse.Result> results) {
        Log.e("진입","알림만들것");
        notificationInfos.clear();
        try {
            if(!results.isEmpty()) {
                Log.e("result존재", "알림만들것");
                for (int i = 0; i < results.size(); i++) {
                    Log.e("result for문", "알림만들것");
                    int taskNo = results.get(i).getTaskNo();
                    String title = results.get(i).getTitle();
                    int color = results.get(i).getColor();
                    String day = results.get(i).getDay();
                    int week = results.get(i).getWeek();
                    String time = results.get(i).getTime();
                    String nickname = results.get(i).getNickname();
                    NotificationInfo info = new NotificationInfo(taskNo, title, color, day, week, time, nickname);
                    notificationInfos.add(info);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.e("알림", "notificationInfos" + notificationInfos);
        mRecycler.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        NotificationRecyclerAdapter adapter = new NotificationRecyclerAdapter(notificationInfos, context);
        mRecycler.setAdapter(adapter);


    }

    @Override
    public void getNotificationFail() {
        Log.e("알림정보GET실패","실패");

    }
}
