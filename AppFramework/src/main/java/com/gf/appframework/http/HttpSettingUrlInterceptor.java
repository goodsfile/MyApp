package com.gf.appframework.http;

import java.io.IOException;
import java.util.List;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @author: gaofei
 * @date: 2018/1/6
 * @desc: 用于项目中出现多个BaseURL, 动态替换BaseURL
 * @version:
 * @update:
 */

public class HttpSettingUrlInterceptor implements Interceptor {
    /**
     * 默认的url即全局url
     */
    public String defaultUrl;

    /**
     * 存放http header key
     */
    public final static String URL_NAME = "url_name";


    public HttpSettingUrlInterceptor(String url) {
        this.defaultUrl = url;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        //从request中获取原有的HttpUrl实例oldHttpUrl
        HttpUrl oldHttpUrl = request.url();
        Request.Builder builder = request.newBuilder();
        List<String> headerValue = request.headers(URL_NAME);
        if (headerValue != null && headerValue.size() > 0) {
            builder.removeHeader(URL_NAME);
            // 获取最新的URL
            String newUrl = headerValue.get(0);
            HttpUrl newBaseUrl = null;
            if (!defaultUrl.equals(newUrl)) {
                // 新url与默认url不同时，则设置新的url
                newBaseUrl = HttpUrl.parse(newUrl);
            } else {
                newBaseUrl = oldHttpUrl;
            }
            // 重新拼接url
            HttpUrl newHttpUrl = oldHttpUrl.newBuilder()
                    .scheme(newBaseUrl.scheme())
                    .host(newBaseUrl.host())
                    .port(newBaseUrl.port())
                    .removePathSegment(0)
                    .build();

            return chain.proceed(builder.url(newHttpUrl).build());
        }
        return chain.proceed(request);
    }
}
