package com.example.makeushifive.src.main.chatting.share;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
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

    ImageView mIvClose;
    FrameLayout mFlShare;
    TextView mTvShareBlack,mTvShareRed,mTvPlusFriend,mTvRecentFriends;
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
        mIvClose = findViewById(R.id.share_iv_close);
        mTvRecentFriends = findViewById(R.id.share_tv_recent_friend);

        mRecyclerShared.setLayoutManager(new LinearLayoutManager(this));


        mIvClose.setOnClickListener(new View.OnClickListener() {
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


                Log.e("검색내용: ",""+searchMessage);
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
        mTvRecentFriends.setText("검색 결과");
        try{
            int userNo;
            String profileUrl,nickname;
            for(int i=0;i<result.size();i++){
                userNo = result.get(i).getUserNo();
                profileUrl = result.get(i).getProfileUrl();
                nickname =result.get(i).getNickname();
                SharedUser sharedUser = null;
                //shareUsersCandi
                if(!shareUsersCandi.isEmpty()){
                    boolean sign = false;
                    for(int j=0;j<shareUsersCandi.size();j++){
                        if(userNo==shareUsersCandi.get(j).getSharedUserNo()){
                            sharedUser= new SharedUser(userNo,profileUrl,nickname,true);
                            sign=true;
                            break;
                        }
                    }
                    if(!sign){
                        sharedUser= new SharedUser(userNo,profileUrl,nickname,false);
                    }
                    users.add(sharedUser);

                }else{
                    sharedUser= new SharedUser(userNo,profileUrl,nickname,false);
                    users.add(sharedUser);
                }
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
        mTvRecentFriends.setText("최근 공유 친구");

        users.clear();
        try{
            int shareduserNo;
            String profileUrl,nickname;
            for(int i=0;i<result.size();i++){
                shareduserNo = result.get(i).getShareduserNo();
                profileUrl = result.get(i).getProfileUrl();
                nickname =result.get(i).getNickname();
                SharedUser sharedUser= new SharedUser(shareduserNo,profileUrl,nickname,false);
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
        adapter = new RecentSharedRecyclerAdapter(users,this);
        mRecyclerShared.setAdapter(adapter);
        adapter.setOnItemClickListener(
                new RecentSharedRecyclerAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(View v, int pos,boolean flag) {
                        //true면 추가, false면 삭제
                        if(flag){
                            //TODO 추가
                            Log.e("변경전",""+users.get(pos).isPicked());
                            users.get(pos).picked=true;
                            Log.e("변경후",""+users.get(pos).isPicked());
                            shareUsersCandi.add(users.get(pos));
                        }else{
                            //TODO 삭제
                            Log.e("변경전",""+users.get(pos).isPicked());
                            users.get(pos).picked=false;
                            Log.e("변경후",""+users.get(pos).isPicked());
                            if(!shareUsersCandi.isEmpty()){
                                int what = 0;
                                for(int i=0;i<shareUsersCandi.size();i++){
                                    if(shareUsersCandi.get(i).getSharedUserNo()==users.get(pos).getSharedUserNo()){
                                        what=i;
                                    }
                                }
                                shareUsersCandi.remove(what);
                            }
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
            public void onItemClick(View v, int pos, int userNo) {

                if(!users.isEmpty()){
                    for(int i=0;i<users.size();i++){
                        if(users.get(i).getSharedUserNo()==userNo){
                            users.get(i).picked=false;
                        }
                    }
                    showFriendsList();
                }
            }
        });
    }
}
