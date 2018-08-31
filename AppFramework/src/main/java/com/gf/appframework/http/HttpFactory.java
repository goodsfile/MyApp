package com.gf.appframework.http;

import android.content.Context;

import java.util.HashMap;
import java.util.Map;

/**
 * @author: gaofei
 * @date: 2017/11/22
 * @desc: 服务提供类，通过retrofit创建服务,并且请求http
 * @version:
 * @update:
 */

public class HttpFactory {
    private static Map<Class, Object> m_service = new HashMap<>();
    private String url = "";
    private boolean isDebug = false;

    private static class HttpHolder {
        private static HttpFactory instance = new HttpFactory();
    }

    private HttpFactory() {

    }

    public static HttpFactory getInstance() {
        return HttpHolder.instance;
    }

    public <T> T provideService(Class cls, Context context) {
        Object serv = m_service.get(cls);
        if (serv == null) {
            synchronized (cls) {
                serv = m_service.get(cls);
                if (serv == null) {
                    if (!getUrl().isEmpty()) {
                        serv = HttpClient.getIns(getUrl(), isDebug(),context).createService(cls);
                        m_service.put(cls, serv);
                    }
                }
            }
        }
        return (T) serv;
    }


    /**
     * 当应用退出时清空所有
     */
    public void clearServiceMapCache() {
        m_service.clear();
    }


    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public boolean isDebug() {
        return isDebug;
    }

    public void setDebug(boolean debug) {
        isDebug = debug;
    }
}
