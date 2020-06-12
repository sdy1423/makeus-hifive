package com.song.makeushifive.src.splash;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.song.makeushifive.R;
import com.song.makeushifive.src.BaseActivity;
import com.song.makeushifive.src.login.LoginActivity;
import com.song.makeushifive.src.main.MainActivity;
import com.song.makeushifive.src.main.MainService;
import com.song.makeushifive.src.main.interfaces.MainActivityView;

import static com.song.makeushifive.src.ApplicationClass.sSharedPreferences;

public class SplashActivity extends BaseActivity implements MainActivityView {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        String nickname = sSharedPreferences.getString("nickname",null);
        MainService mainService = new MainService(this);
        mainService.getUser(nickname);



    }

    @Override
    public void ChangeProfileSuccess() {

    }

    @Override
    public void ChangeProfileFail() {

    }

    @Override
    public void getUserSuccess() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        SplashActivity.this.finish(); //로딩페이지를 액티비티 스택에서 제거거
    }

    @Override
    public void getUserFail() {
        Handler handler = new Handler();
        handler.postDelayed(new SplashHandler(),1000);
        //3000ms = 3초

    }

    private class SplashHandler implements Runnable{
        @Override
        public void run() {
            startActivity(new Intent(getApplication(), LoginActivity.class));
            SplashActivity.this.finish(); //로딩페이지를 액티비티 스택에서 제거거
        }
    }

    @Override
    public void onBackPressed(){
        //초반 플래시 화면에서 넘어갈때 뒤로가게 버튼 못누르게
    }
}
