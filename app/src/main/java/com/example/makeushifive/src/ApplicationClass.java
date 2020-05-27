package com.example.makeushifive.src;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import com.example.makeushifive.config.XAccessTokenInterceptor;

import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApplicationClass extends Application {
    public static MediaType MEDIA_TYPE_JSON = MediaType.parse("application/json; charset=uft-8");
    public static MediaType MEDIA_TYPE_JPEG = MediaType.parse("image/jpeg");

    // 테스트 서버 주소
    public static String BASE_URL = "https://calendar.ydab.woobi.co.kr";
    // 실서버 주소
//    public static String BASE_URL = "https://template.softsquared.com/";


    // SharedPreferences 키 값
    public static String TAG = "TEMPLATE_APP";


    //날짜 형식
    public static SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd", Locale.KOREA);
    public static SimpleDateFormat KOREAN_FORMAT = new SimpleDateFormat("MM월 dd일",Locale.KOREA);
    public static SimpleDateFormat DOT_FORMAT = new SimpleDateFormat("MM. dd / EE",Locale.KOREA);
    public static SimpleDateFormat CALENDAR_FORMAT = new SimpleDateFormat("yyyy. MM ",Locale.KOREA);
    public static SimpleDateFormat YEAR = new SimpleDateFormat("yyyy",Locale.KOREA);
    public static SimpleDateFormat MONTH = new SimpleDateFormat("MM",Locale.KOREA);
    public static SimpleDateFormat DAY = new SimpleDateFormat("dd",Locale.KOREA);
    public static SimpleDateFormat DAYOFWEEK = new SimpleDateFormat("EE",Locale.KOREA);
    public static SimpleDateFormat Chatting = new SimpleDateFormat("MM월 dd일 / EE",Locale.KOREA);

    // Retrofit 인스턴스
    public static Retrofit retrofit;


    public static SharedPreferences sSharedPreferences = null;
    // JWT Token 값
    public static String X_ACCESS_TOKEN = "X-ACCESS-TOKEN";
    @Override
    public void onCreate() {
        super.onCreate();

        if (sSharedPreferences == null) {
            sSharedPreferences = getApplicationContext().getSharedPreferences(TAG, Context.MODE_PRIVATE);
        }
    }

    public static Retrofit getRetrofit() {
        if (retrofit == null) {
            OkHttpClient client = new OkHttpClient.Builder()
                    .readTimeout(5000, TimeUnit.MILLISECONDS)
                    .connectTimeout(5000, TimeUnit.MILLISECONDS)
                    .addNetworkInterceptor(new XAccessTokenInterceptor()) // JWT 자동 헤더 전송
                    .build();

            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }

        return retrofit;
    }


}
