package com.example.makeushifive.config;


import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

import static com.example.makeushifive.src.ApplicationClass.X_ACCESS_TOKEN;
import static com.example.makeushifive.src.ApplicationClass.sSharedPreferences;

public class XAccessTokenInterceptor implements Interceptor {
    @NotNull
    @Override
    public Response intercept(@NotNull final Interceptor.Chain chain) throws IOException {
        final Request.Builder builder = chain.request().newBuilder();
        final String jwtToken = sSharedPreferences.getString(X_ACCESS_TOKEN, null);
        if (jwtToken != null) {
            builder.addHeader("X-ACCESS-TOKEN", jwtToken);
        }
        return chain.proceed(builder.build());
    }
}