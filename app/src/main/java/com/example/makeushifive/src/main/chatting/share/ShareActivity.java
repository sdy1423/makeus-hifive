package com.example.makeushifive.src.main.chatting.share;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.makeushifive.R;
import com.example.makeushifive.src.BaseActivity;
import com.example.makeushifive.src.main.chatting.share.interfaces.ShareActivityView;
import com.example.makeushifive.src.main.chatting.share.models.SearchUserResponse;
import com.example.makeushifive.src.main.chatting.share.models.SharedUserResponse;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.Objects;


//TODO ACTIVITY로 진입하면 최근공유 친구들 보여준다, 검색하면 검색인원 보여준다. , 클릭하면 추가 리스트에 추가된다. (한명이라도 추가 되면 빨간색으로 바뀐다) 추가 버튼 누르면 친구가 추가된다.

public class ShareActivity extends BaseActivity implements ShareActivityView {

    ArrayList<SharedUser> users = new ArrayList<>(); //세로 리사이클러 아이템들
    ArrayList<SharedUser> shareUsersCandi = new ArrayList<>(); //가로 리사이클러 아이템들

    ImageView mIvShare;
    FrameLayout mFlShare;
    TextView mTvShareBlack,mTvShareRed,mTvPlusFriend;
    boolean ShareFlag=false; //TODO 한명이라도 추가되면 true로 바뀐다.
    EditText mEdtSearch;
    String searchMessage;
    int taskNo = 0;
    RecyclerView mRecyclerShared,mRecyclerPicked;
    RecentSharedRecyclerAdapter adapter;
    PickedRecyclerAdapter pickedRecyclerAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share);

        Intent intent = getIntent();
        taskNo = Objects.requireNonNull(intent.getExtras()).getInt("taskNo");

        mRecyclerPicked = findViewById(R.id.share_recycler_picked);
        mRecyclerShared = findViewById(R.id.share_recyclerview);
        mTvPlusFriend = findViewById(R.id.chatting_drawer_tv_share_schedule);
        mFlShare = findViewById(R.id.share_fl_complete);
        mTvShareBlack = findViewById(R.id.share_tv_complete_black);
        mTvShareRed = findViewById(R.id.share_tv_complete_red);
        mTvShareBlack.setVisibility(View.VISIBLE);
        mTvShareRed.setVisibility(View.INVISIBLE);
        mEdtSearch = findViewById(R.id.share_edt_search);
        mIvShare = findViewById(R.id.share_iv_close);
        mIvShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        mEdtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                searchMessage = mEdtSearch.getText().toString();
                //TODO 검색
                SearchUser();
            }
        });

        //최근 공유 유저
        ShareService shareService = new ShareService(this);
        shareService.getRecentSharedUser();

        //TODO 완료버튼 누르면 postTaskShare
        mFlShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if(ShareFlag){
                    JsonObject SendObject = new JsonObject();
                    SendObject.addProperty("taskNo",taskNo);
                    JsonArray SendArray = new JsonArray();
                    JsonObject ArrayObject = new JsonObject();
                    try {
                        for(int i=0;i<shareUsersCandi.size();i++){
                            int userNo = shareUsersCandi.get(i).getSharedUserNo();
                            ArrayObject.addProperty("shareduserNo",userNo);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    SendArray.add(ArrayObject);
                    SendObject.add("shareduserNoList",SendArray);

                    try {
                        ShareTask(SendObject);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

//                }
            }
        });


    }

    private void ShareTask(JsonObject sendObject) throws JSONException {
        ShareService shareService = new ShareService(this);
        shareService.postTaskShare(sendObject);

    }

    public void SearchUser(){
        ShareService shareService = new ShareService(this);
        shareService.getSearchUser(searchMessage);

    }

    @Override
    public void getSearchUserSuccess(ArrayList<SearchUserResponse.Result> result) {
        //TODO 유저 검색
        //TODO 최근 유저검색, 리사이클러뷰
        //TODO SharedUser는 아니지만 재활용 ㄱㄱㄱㄱ
        users.clear();
        try{
            int userNo;
            String profileUrl,nickname;
            for(int i=0;i<result.size();i++){
                userNo = result.get(i).getUserNo();
                profileUrl = result.get(i).getProfileUrl();
                nickname =result.get(i).getNickname();
                SharedUser sharedUser= new SharedUser(userNo,profileUrl,nickname);
                users.add(sharedUser);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        showFriendsList();


    }

    @Override
    public void getSearchUserFail() {

    }

    @Override
    public void getRecentShareSuccess(ArrayList<SharedUserResponse.Result> result) {
        //TODO 최근 유저검색, 리사이클러뷰
        users.clear();
        try{
            int shareduserNo;
            String profileUrl,nickname;
            for(int i=0;i<result.size();i++){
                shareduserNo = result.get(i).getShareduserNo();
                profileUrl = result.get(i).getProfileUrl();
                nickname =result.get(i).getNickname();
                SharedUser sharedUser= new SharedUser(shareduserNo,profileUrl,nickname);
                users.add(sharedUser);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        showFriendsList();
    }

    @Override
    public void getRecentShareFail() {

    }


    @Override
    public void postShareSuccess(int code,String message) {
        //TODO 일정 공유 성공
        if(code==100){
            onBackPressed();
        }else if(code==201){
            showCustomToast("이미 공유 했습니다.");
        }else if(code==202){
            showCustomToast(message);
        }
    }

    @Override
    public void postShareFail() {
        showCustomToast("일정 공유에 실패 했습니다.");

    }

    public void showFriendsList(){
        mRecyclerShared.setLayoutManager(new LinearLayoutManager(this));
        adapter = new RecentSharedRecyclerAdapter(users,this);
        mRecyclerShared.setAdapter(adapter);
        adapter.setOnItemClickListener(
                new RecentSharedRecyclerAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(View v, int pos,boolean flag) {
                        //TODO 아이템 클릭 이벤트를 여기서 처리 ㄱㄱ
                        //TODO 추가 ㄱㄱㄱ
                        //TODO 가로 리사이클러에 보여줄 것들

                        //true면 추가, false면 삭제

                        if(flag){
                            //TODO 추가
                            shareUsersCandi.add(users.get(pos));
                        }else{
                            //TODO 삭제
                            shareUsersCandi.remove(users.get(pos));
                        }
                        showPickedFriendsList();
                    }
                }
        );
    }
    public void showPickedFriendsList(){
        //가로 리사이클러
        mRecyclerPicked.setLayoutManager(new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.HORIZONTAL,false));
        pickedRecyclerAdapter = new PickedRecyclerAdapter(shareUsersCandi,getApplicationContext());
        mRecyclerPicked.setAdapter(pickedRecyclerAdapter);

        pickedRecyclerAdapter.setOnItemClickListener(new PickedRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int pos,String deletedName) {

            }
        });
    }
}
