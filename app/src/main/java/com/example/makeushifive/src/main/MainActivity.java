package com.example.makeushifive.src.main;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.makeushifive.R;
import com.example.makeushifive.src.BaseActivity;
import com.example.makeushifive.src.main.interfaces.MainActivityView;
import com.example.makeushifive.src.main.notification.models.NotificationResponse;
import com.example.makeushifive.src.main.notification.NotificationInfo;
import com.example.makeushifive.src.main.onboarding.OnBoardingActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import org.json.JSONException;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Objects;

import static com.example.makeushifive.src.ApplicationClass.sSharedPreferences;

public class MainActivity extends BaseActivity implements MainActivityView {

    ArrayList<NotificationInfo> notificationInfos;

    FirebaseStorage storage = FirebaseStorage.getInstance("gs://hifive-d16d6.appspot.com");
    // Create a storage reference from our app
    StorageReference storageRef = storage.getReference();
    // Create a reference to "mountains.jpg"
    StorageReference spaceRef = storageRef.child("profile.png");
//    // Create a reference to 'images/mountains.jpg'

    String profileUrl;

    TabLayout mTlTabLayout;
    ViewPager mVpViewPager;

    View tabView1,tabView2,tabView3;

    Bundle mBundle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mBundle = savedInstanceState;
        // While the file names are the same, the references point to different files
//        mountainsRef.getName().equals(mountainImagesRef.getName());    // true
//        mountainsRef.getPath().equals(mountainImagesRef.getPath());    // false


        tabView1 = LayoutInflater.from(this).inflate(R.layout.custom_tab_calendar, null);
        tabView2 = LayoutInflater.from(this).inflate(R.layout.custom_tab_feed, null);
        tabView3 = LayoutInflater.from(this).inflate(R.layout.custom_tab_profile, null);


        mTlTabLayout=findViewById(R.id.main_tl_tabs);
        mVpViewPager=findViewById(R.id.main_vp_view_pager);

        Log.e("onCreate","onStart");
        mTlTabLayout.addTab(mTlTabLayout.newTab().setCustomView(changeSelectedTabView(0)));
        mTlTabLayout.addTab(mTlTabLayout.newTab().setCustomView(changeUnSelectedTabView(1)));
        mTlTabLayout.addTab(mTlTabLayout.newTab().setCustomView(changeUnSelectedTabView(2)));

//        mTlTabLayout.addTab(mTlTabLayout.newTab().setIcon(R.drawable.calendar_red_tab2));
//        mTlTabLayout.addTab(mTlTabLayout.newTab().setIcon(R.drawable.feed_tab2_black));
//        mTlTabLayout.addTab(mTlTabLayout.newTab().setIcon(R.drawable.profile_tab2_black));
        mTlTabLayout.setTabGravity(TabLayout.GRAVITY_FILL);


        MainViewPagerAdapter mainViewPagerAdapter = new MainViewPagerAdapter(getSupportFragmentManager(),mTlTabLayout.getTabCount());
        mVpViewPager.setAdapter(mainViewPagerAdapter);
        mVpViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mTlTabLayout));

        mTlTabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mVpViewPager));

        mTlTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {

            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int pos = tab.getPosition();
                changeView(pos);
                mVpViewPager.setCurrentItem(tab.getPosition());
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }
    private View changeSelectedTabView(int index) {
        if(index==0){
            tabView1.findViewById(R.id.custom_tab_iv_calendar_red).setBackgroundResource(R.drawable.calendar_red_tab2);
            tabView1.findViewById(R.id.custom_tab_iv_calendar_red).setVisibility(View.VISIBLE);
            tabView1.findViewById(R.id.custom_tab_iv_calendar_blur).setVisibility(View.INVISIBLE);
            return tabView1;
        }else if(index==1){
            tabView2.findViewById(R.id.custom_tab_iv_feed_red).setBackgroundResource(R.drawable.feed_tab2_red);
            tabView2.findViewById(R.id.custom_tab_iv_feed_red).setVisibility(View.VISIBLE);
            tabView2.findViewById(R.id.custom_tab_iv_feed_blur).setVisibility(View.INVISIBLE);
            return tabView2;
        }else if(index==2){
            tabView3.findViewById(R.id.custom_tab_iv_profile_red).setBackgroundResource(R.drawable.profile_tab2_red);
            tabView3.findViewById(R.id.custom_tab_iv_profile_red).setVisibility(View.VISIBLE);
            tabView3.findViewById(R.id.custom_tab_iv_profile_blur).setVisibility(View.INVISIBLE);
            return tabView3;
        }
        return tabView1;
    }
    private View changeUnSelectedTabView(int index) {
//        View tabView = LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        if(index==0){
            tabView1.findViewById(R.id.custom_tab_iv_calendar_blur).setBackgroundResource(R.drawable.calendar_tab2_black);
            tabView1.findViewById(R.id.custom_tab_iv_calendar_blur).setVisibility(View.VISIBLE);
            tabView1.findViewById(R.id.custom_tab_iv_calendar_red).setVisibility(View.INVISIBLE);
            return tabView1;
        }else if(index==1){
            tabView2.findViewById(R.id.custom_tab_iv_feed_blur).setBackgroundResource(R.drawable.feed_tab2_black);
            tabView2.findViewById(R.id.custom_tab_iv_feed_blur).setVisibility(View.VISIBLE);
            tabView2.findViewById(R.id.custom_tab_iv_feed_red).setVisibility(View.INVISIBLE);
            return tabView2;
        }else if(index==2){
            tabView3.findViewById(R.id.custom_tab_iv_profile_blur).setBackgroundResource(R.drawable.profile_tab2_black);
            tabView3.findViewById(R.id.custom_tab_iv_profile_blur).setVisibility(View.VISIBLE);
            tabView3.findViewById(R.id.custom_tab_iv_profile_red).setVisibility(View.INVISIBLE);
            return tabView3;
        }
        return tabView1;
    }

    public void changeView(int index){
        if(index==0){
            Objects.requireNonNull(mTlTabLayout.getTabAt(0)).setCustomView(changeSelectedTabView(0));
            Objects.requireNonNull(mTlTabLayout.getTabAt(1)).setCustomView(changeUnSelectedTabView(1));
            Objects.requireNonNull(mTlTabLayout.getTabAt(2)).setCustomView(changeUnSelectedTabView(2));
        }else if(index==1){
            Objects.requireNonNull(mTlTabLayout.getTabAt(0)).setCustomView(changeUnSelectedTabView(0));
            Objects.requireNonNull(mTlTabLayout.getTabAt(1)).setCustomView(changeSelectedTabView(1));
            Objects.requireNonNull(mTlTabLayout.getTabAt(2)).setCustomView(changeUnSelectedTabView(2));
        }else if(index==2){
            Objects.requireNonNull(mTlTabLayout.getTabAt(0)).setCustomView(changeUnSelectedTabView(0));
            Objects.requireNonNull(mTlTabLayout.getTabAt(1)).setCustomView(changeUnSelectedTabView(1));
            Objects.requireNonNull(mTlTabLayout.getTabAt(2)).setCustomView(changeSelectedTabView(2));
        }
    }

    @Override
    protected void onResume() {
        String nickname = sSharedPreferences.getString("nickname",null);
        Log.e("onResume","onResume"+nickname);
        MainService mainService = new MainService(this);
        mainService.getUser(nickname);
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();

        Log.e("onPause","onStart");

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e("onDestroy","onStart");

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.e("onDestroy","onStart");

    }

    @Override
    public void onStop() {
        super.onStop();
        Log.e("onStop","onStart");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.e("onStart","onStart");

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 200 && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri selectedImageUri = data.getData();
            //TODO 파이어베이스에 업로드하고 프로필 사진 수정 API 수행
            //이미지 업로드 성공
            try {
                UpLoadImageToFireBase(selectedImageUri);
                Log.e("보낸건가?",""+profileUrl);

            } catch (FileNotFoundException e) {
                Log.e("보내지도 못함",""+e);
                e.printStackTrace();
            }
        }

    }
    private String getRealPathFromURI(Uri contentURI) {
        String result;
        Cursor cursor = getContentResolver().query(contentURI, null, null, null, null);
        if (cursor == null) { // Source is Dropbox or other similar local file path
            result = contentURI.getPath();
        } else {
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            result = cursor.getString(idx);
            cursor.close();
        }
        return result;
    }

    public void UpLoadImageToFireBase(Uri uri) throws FileNotFoundException {
        Log.e("진입은 성공?",""+uri);
//        Uri file = Uri.fromFile(new File(profileUrl));
        UploadTask uploadTask=spaceRef.putFile(uri);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.e("업로드 실패","업로드 실패"+e);
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Log.e("업로드 성공","업로드 성공"+taskSnapshot);
                //TODO 프사 수정 API 수행
                try {
                    getProfileUrlFromFireBase();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
    private void getProfileUrlFromFireBase() throws JSONException {
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference reference = storage.getReference().child("profile.png");
        reference.getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull Task<Uri> task) {
                if (task.isSuccessful()) {
                    profileUrl= Objects.requireNonNull(task.getResult()).toString();
                    try {
                        ChangeProfile();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    //실패
                }
            }
        });
    }

    public void ChangeProfile() throws JSONException {
        MainService mainService = new MainService(this);
        mainService.patchProfile(profileUrl); //파이어베이스에 리얼패스를 업로드 했으므로 Url 보낸다.
    }


    @Override
    public void ChangeProfileSuccess() {
        ImageView imageView = findViewById(R.id.setting_iv_profile_img);
        //TODO 이미지뷰 수정
        Glide.with(MainActivity.this)
                .load(profileUrl)
                .override(1024, 980)
                .into(imageView);

        SharedPreferences.Editor editor = sSharedPreferences.edit();
        editor.remove("profileUrl");
        editor.putString("profileUrl", String.valueOf(profileUrl));
        editor.apply();
        Log.e("프사변경 성공","성공");



    }
    public void NotificationClick(View v){

    }



    @Override
    public void ChangeProfileFail() {
        showCustomToast("프로필 사진 수정에 실패했습니다.");
    }

    @Override
    public void getUserSuccess() {
        Log.e("onCreatㄱㄱ","ONON");
        onCreate(mBundle);
    }

    @Override
    public void getUserFail() {

    }


}
