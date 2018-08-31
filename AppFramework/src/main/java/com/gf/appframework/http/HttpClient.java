package com.gf.appframework.http;
import android.content.Context;

import com.gf.appframework.http.log.LogInterceptor;

import java.io.File;
import java.util.concurrent.TimeUnit;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author: gaofei
 * @date: 2017/11/22
 * @desc: 只有在get请求时才具备http的缓存功能，post没有
 * @version:
 * @update:
 */

public class HttpClient {
    private static HttpClient mInstance;
    private Retrofit singleton;

    public HttpClient(String url, boolean debug,Context context) {
        LogInterceptor logInterceptor = new LogInterceptor();
        logInterceptor.setLevel(debug ? LogInterceptor.Level.ALL : LogInterceptor.Level.NONE);

        File cacheFile = new File(context.getCacheDir(), "HttpCache");
        //100Mb 缓存空间
        Cache cache = new Cache(cacheFile, 1024 * 1024 * 100);

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(10000, TimeUnit.MILLISECONDS)
                .writeTimeout(10000, TimeUnit.MILLISECONDS)
                .readTimeout(10000, TimeUnit.MILLISECONDS)
                .addNetworkInterceptor(logInterceptor)
                .addInterceptor(new HttpSettingUrlInterceptor(url))
                .cache(cache)
                .build();

        singleton = new Retrofit.Builder()
                .baseUrl(url)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();
    }

    /**
     * 获取全局唯一HttpClient对象
     * @param url
     * @param debug
     * @return
     */
    public static HttpClient getIns(String url, boolean debug, Context context) {
        if (mInstance == null) {
            synchronized (HttpClient.class) {
                if (mInstance == null) {
                    mInstance = new HttpClient(url, debug,context);

                }
            }
        }
        return mInstance;
    }

    public <T> T createService(Class<T> clz) {
        return singleton.create(clz);
    }

}
