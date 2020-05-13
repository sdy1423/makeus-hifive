package com.example.makeushifive.src.main.setting.change.interfaces;

import com.example.makeushifive.src.main.setting.change.models.ChangeResponse;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.PATCH;

public interface ChangeRetrofitInterface {

    @FormUrlEncoded
    @PATCH("/posts/1")
    Call<ChangeResponse>
    PatchChangeInfo(
            @Field("email") String email,
            @Field("pw") String pw,
            @Field("profileUrl") String profileUrl,
            @Field("nickname") String nickname
            );
}
