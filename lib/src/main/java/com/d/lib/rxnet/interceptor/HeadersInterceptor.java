package com.d.lib.rxnet.interceptor;

import android.content.Context;
import android.support.annotation.NonNull;

import java.io.IOException;
import java.util.Map;
import java.util.Set;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 请求头拦截
 */
public class HeadersInterceptor implements Interceptor {
    private Map<String, String> headers;
    private String token;

    public HeadersInterceptor(Context context, Map<String, String> headers) {
        this.headers = headers;
    }

    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        Request.Builder builder = chain.request().newBuilder();

        // FIXME: @developer 2017/10/24
        builder.addHeader("token", getToken()).build();

        if (headers != null && headers.size() > 0) {
            Set<String> keys = headers.keySet();
            for (String headerKey : keys) {
                builder.addHeader(headerKey, headers.get(headerKey)).build();
            }
        }
        return chain.proceed(builder.build());
    }

    /**
     * Token maybe dynamical，you shoule override here
     */
    private String getToken() {
        return token;
    }
}