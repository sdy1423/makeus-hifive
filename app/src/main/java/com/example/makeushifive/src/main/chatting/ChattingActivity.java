package com.example.makeushifive.src.main.chatting;

import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.makeushifive.R;
import com.example.makeushifive.src.BaseActivity;
import com.example.makeushifive.src.main.chatting.interfaces.ChattingActivityView;
import com.example.makeushifive.src.main.chatting.models.ChatUserResponse;
import com.example.makeushifive.src.main.chatting.models.ChattingHistoryResponse;
import com.example.makeushifive.src.main.chatting.models.ChattingResponse;
import com.example.makeushifive.src.main.chatting.share.ShareActivity;
import com.example.makeushifive.src.main.taskchange.TaskChangeActivity;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Objects;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

import static com.example.makeushifive.src.ApplicationClass.Chatting;
import static com.example.makeushifive.src.ApplicationClass.DATE_FORMAT;
import static com.example.makeushifive.src.ApplicationClass.TIME;
import static com.example.makeushifive.src.ApplicationClass.TIME_KOREAN;
import static com.example.makeushifive.src.ApplicationClass.sSharedPreferences;

public class ChattingActivity extends BaseActivity implements ChattingActivityView {

    DrawerLayout drawerLayout;
    int taskNo=0,color=0;
    String day,location,time,title;
    TextView mTvLocation,mTvDay,mTvTime,mTvTitle,mTvShareFriend;
    ImageView[] imageView=new ImageView[8];
    ImageView mIvColor1,mIvColor2,mIvColor3,mIvColor4,mIvColor5,mIvColor6,mIvColor7,mIvColor8,mIvShare,mIvChange;
    int Colors[] = {R.id.chatting_iv_one,R.id.chatting_iv_two,R.id.chatting_iv_three,R.id.chatting_iv_four,R.id.chatting_iv_five,R.id.chatting_iv_six,R.id.chatting_iv_seven,R.id.chatting_iv_eight};


    //채팅 시도
    Socket mSocket;
    String userName,roomName;
    EditText mEdtMessage;
    ArrayList<Message> chatList = new ArrayList<>();
    ChattingAdapter chattingAdapter;
    ImageView mIvSend,mIvTaskChange;
    RecyclerView chatRecyclerView;
    String MyName;

    ArrayList<ChatUser> chatUsers = new ArrayList<>(); //채팅방 인원들
    RecyclerView mChatUserRecycler;

    ChattingService chattingService;
    Bundle mbundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mbundle = savedInstanceState;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatting);

        Intent intent = getIntent();
        taskNo = Objects.requireNonNull(intent.getExtras()).getInt("taskNo");
        color = intent.getExtras().getInt("color");
        Log.e("받는taskNo",""+taskNo);
        Log.e("받는color",""+color);


        mIvTaskChange=findViewById(R.id.chatting_iv_change);
        mIvTaskChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), TaskChangeActivity.class);
                intent.putExtra("taskNo",taskNo);
                startActivity(intent);
            }
        });

        drawerLayout=findViewById(R.id.chatting_drawer);

        //드로어 고정시키기
//        if(drawerLayout.isDrawerOpen(Gravity.RIGHT)){
//            drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_OPEN);
//        }else{
//            drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
//        }

        mTvShareFriend = findViewById(R.id.chatting_drawer_tv_share_schedule);
        mTvShareFriend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShareSchedule();
            }
        });
        //TODO 클릭하면 일정공유

        chattingService = new ChattingService(this);
        chattingService.getDetailSchedule(taskNo);

        mTvLocation= findViewById(R.id.chatting_tv_location);
        mTvDay=findViewById(R.id.chatting_tv_day);
        mTvTime=findViewById(R.id.chatting_tv_time);
        mTvTitle=findViewById(R.id.chatting_tv_title);

        imageView= new ImageView[]{mIvColor1, mIvColor2, mIvColor3, mIvColor4, mIvColor5, mIvColor6, mIvColor7, mIvColor8};
        for(int i=0;i<8;i++){
            imageView[i] = findViewById(Colors[i]);
            if(i==color-1){
                imageView[i].setVisibility(View.VISIBLE);
            }else{
                imageView[i].setVisibility(View.INVISIBLE);
            }
        }

        mIvShare = findViewById(R.id.chatting_iv_share);
        mIvShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShareSchedule();

            }
        });



        //leave는 일단 안만듬

        //받아올것
//        userName = "deokyong";
//        roomName = "소퀘";

        //set chatting room adapter
        chatRecyclerView=findViewById(R.id.chatting_recycler);
        chattingAdapter = new ChattingAdapter(this,chatList);
        chatRecyclerView.setAdapter(chattingAdapter);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        chatRecyclerView.setLayoutManager(linearLayoutManager);


        //채팅!
        mEdtMessage=findViewById(R.id.chatting_edt_message);
        mIvSend = findViewById(R.id.chatting_iv_send);

        mIvSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMessage();
            }
        });

        try {
            mSocket = IO.socket("http://15.165.78.22:8080");
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        mSocket.connect();

        mSocket.on(Socket.EVENT_CONNECT,onConnect);//리스너임
//////////////////////////////////////////////////////////////////

        //TODO 새로운 유저입장
        mSocket.on("newUserToChatRoom",onNewUser);

        //TODO 채팅 업데이트
        mSocket.on("rMsg",onUpdateChat);

        //TODO 유저 떠남
        mSocket.on("userLeftChatRoom",onUserLeft);


        MyName = sSharedPreferences.getString("nickname",null);

        mChatUserRecycler = findViewById(R.id.chatting_drawer_recyclerview);


        chattingService.getChatHistory(taskNo);


        chattingService.getChatUser(taskNo);
    }

    private void ShareSchedule() {
        Intent intent1 = new Intent(getApplicationContext(), ShareActivity.class);
        intent1.putExtra("taskNo",taskNo);
        startActivity(intent1);
        //일정 추가 ㄱㄱ
        //taskNo보낸다.
    }


    private void addItemToRecyclerView(Message message){
        //Since this function is inside of the listener,
        // You need to do it on UIThread!
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                chatList.add(message);
                chattingAdapter.notifyItemInserted(chatList.size());
                mEdtMessage.setText("");
                chatRecyclerView.scrollToPosition(chatList.size()-1);//move focus on last message
            }
        });


    }


    private void sendMessage() {
        String content = mEdtMessage.getText().toString();
        String profileUrl = sSharedPreferences.getString("profileUrl",null);
        int num  = sSharedPreferences.getInt("userNo", 0);
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("taskNo",taskNo);
        jsonObject.addProperty("name",num);
        jsonObject.addProperty("msg",content);
        jsonObject.addProperty("room",taskNo);
        jsonObject.addProperty("profileUrl",profileUrl);
        Log.e("sMsg",""+jsonObject);
        mSocket.emit("sMsg",jsonObject);

        //채팅 보내면 메세지 사라지게
        mEdtMessage.getText().clear();




//        SendMessage sendMessage = new SendMessage(taskNo,num,content,taskNo);
//        mSocket.emit("sMsg",sendMessage);
//        Log.e("userNo",""+num);
//        Log.e("taskNo",""+taskNo);
//        Log.e("content",""+content);
//        Log.e("sendMessage",""+sendMessage);
        //taskNo:taskno ,name: userno, ,msg message , room taskNo
    }

    private Emitter.Listener onConnect = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
//            val data = initialData(userName, roomName)
//            val jsonData = gson.toJson(data)
//            mSocket.emit("subscribe", jsonData)

        }
    };
    private Emitter.Listener onNewUser = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            //TODO 새로운 유저 입장

//            val name = it[0] as String //This pass the userName!
//            val chat = Message(name, "", roomName, MessageType.USER_JOIN.index)
//            addItemToRecyclerView(chat)
//            Log.d(TAG, "on New User triggered.")

        }
    };
    private Emitter.Listener onUpdateChat = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            //TODO 채팅 업데이트

            Log.e("서버로부터 받은 메세지",""+ Arrays.toString(args));


            String getMessage = Arrays.toString(args);
            int taskNo = 0,roomNo = 0;
            String userName = null,message = null,profileUrl = null;

            Log.e("getMessage",""+getMessage);

            try {
                JSONArray jsonArray = new JSONArray(getMessage);
                for(int i=0;i<jsonArray.length();i++){
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    taskNo = jsonObject.getInt("taskNo");
                    userName = jsonObject.getString("name");
                    message = jsonObject.getString("msg");
                    roomNo = jsonObject.getInt("room");
                    profileUrl = jsonObject.getString("profileUrl");

//                    Log.e("userName",""+userName);
//                    Log.e("taskNo",""+taskNo);
//                    Log.e("roomNo",""+roomNo);
//                    Log.e("message",""+message);
//                    Log.e("profileUrl",""+profileUrl);

                    Message message1;
                    if(userName.equals(MyName)){
                        Log.e("나","");
                        message1 = new Message(userName,message,message,0,profileUrl);
                       addItemToRecyclerView(message1);
                    }else{
                        Log.e("너","");
                        message1 = new Message(userName,message,message,1,profileUrl);
                        addItemToRecyclerView(message1);

                    }

                }


            } catch (JSONException e) {
                e.printStackTrace();
            }



//
//


//            val chat: Message = gson.fromJson(it[0].toString(), Message::class.java)
//            chat.viewType = MessageType.CHAT_PARTNER.index
//            addItemToRecyclerView(chat)


        }
    };
    private Emitter.Listener onUserLeft = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            //TODO 유저가 떠남
//            val name = it[0] as String //This pass the userName!
//            val chat = Message(name, "", roomName, MessageType.USER_JOIN.index)
//            addItemToRecyclerView(chat)
//            Log.d(TAG, "on New User triggered.")


        }
    };



    @Override
    public void getScheduleDetailSuccess(ArrayList<ChattingResponse.Result> result) throws ParseException {
        //TODO 일정상세 조회에 taskNo, repeatweek 추가 되었으니 활용할것 (repeatweek는 일정반복)
        if(!result.isEmpty()){

            location = result.get(0).getLocation();
            time = result.get(0).getTime();
            title = result.get(0).getTitle();
            day = result.get(0).getDay();
            Date dateformat;
            dateformat = DATE_FORMAT.parse(day); //string to date

            String ShowTime="";
            try{
                ShowTime+=time.substring(0,5);
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                if(time.length()==17){
                    try{
                        ShowTime="";
                        ShowTime+=time.substring(0,5);
                        ShowTime+=time.substring(8,14);
                        Log.e("ShowTime",""+time.length());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }




            assert dateformat != null;
            String today = Chatting.format(dateformat); //date to string

            mTvLocation.setText(location);
            mTvDay.setText(today);
            mTvTime.setText(ShowTime);
            mTvTitle.setText(title);



        }
    }

    @Override
    public void getScheduleDetailFail() {

    }

    @Override
    public void getChatUserSuccess(ArrayList<ChatUserResponse.Result> result) {
        chatUsers.clear();
        try {
            for(int i =0;i<result.size();i++){
                int userno = result.get(i).getUserNo();
                String profileurl = result.get(i).getProfileUrl();
                String nickname = result.get(i).getNickname();
                ChatUser chatUser = new ChatUser(userno,profileurl,nickname);
                chatUsers.add(chatUser);
                Log.e("userno",""+userno);
                Log.e("profileurl",""+profileurl);
                Log.e("nickname",""+nickname);
            }
            Log.e("showshow","show "+chatUsers);

            mChatUserRecycler.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
            ChatUserRecyclerAdapter adapter = new ChatUserRecyclerAdapter(chatUsers,this);
            mChatUserRecycler.setAdapter(adapter);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void getChatUserFail() {

    }

    @Override
    public void getChatHistorySuccess(ArrayList<ChattingHistoryResponse.Result> result) {
        try{
            if(!result.isEmpty()){
                for(int i=0;i<result.size();i++){
                    String username = result.get(i).getNickname();
                    String msg = result.get(i).getMsg();
                    String profileurl = result.get(i).getProfileUrl();
                    if(username.equals(MyName)){
                        Message message = new Message(username,msg,msg,0,profileurl);
                        addItemToRecyclerView(message);
                    }else {
                        Message message = new Message(username,msg,msg,1,profileurl);
                        addItemToRecyclerView(message);
                    }
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void getChatHistoryFail() {

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        //TODO 채팅방 떠날때
//        initalData initalData = new initalData(userName,roomName);
//        JsonObject jsonObject = new JsonObject();
//        jsonObject.add("unsubscribe",jsonObject);
//        mSocket.disconnect();

    }

    @Override
    protected void onResume() {
        super.onResume();
        String nickname = sSharedPreferences.getString("nickname",null);
        Log.e("ChattingActivity","onResume"+nickname);
        ChattingService chattingService = new ChattingService(this);
        chattingService.getUser(nickname);
        chattingService.getChatUser(taskNo);
    }

    @Override
    public void getUserSuccess() {
        Log.e("ChattingActivity","onCreateㄱㄱ");
        ChattingService chattingService = new ChattingService(this);
        chattingService.getChatUser(taskNo);
        onCreate(mbundle);
    }

    @Override
    public void getUserFail() {

    }

    @Override
    public void onStop() {
        Log.e("ChattingActivity","onStop");
        super.onStop();
    }

    @Override
    protected void onStart() {
        Log.e("ChattingActivity","onStart");
        super.onStart();
    }

    @Override
    protected void onPause() {
        Log.e("ChattingActivity","onPause");
        super.onPause();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.e("ChattingActivity","onRestart");

    }

}
