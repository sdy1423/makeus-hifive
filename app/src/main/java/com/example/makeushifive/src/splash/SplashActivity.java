package com.example.makeushifive.src.splash;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.example.makeushifive.R;
import com.example.makeushifive.src.BaseActivity;
import com.example.makeushifive.src.login.LoginActivity;
import com.example.makeushifive.src.main.MainActivity;

public class SplashActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

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
