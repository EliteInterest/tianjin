package com.zx.tjmarketmobile.http;

/**
 * 请求 参数 JavaBean
 *
 * @author zsx
 * @date 2014-06-16
 */
public abstract class BaseHttpParams<Param> {
    private String apiUrl;
    private int port = -1;
    private HTTP_MOTHOD mothod = HTTP_MOTHOD.POST;
    private boolean isRetry = true;//是否请求第二次 默认为true

    public boolean isRetry() {
        return isRetry;
    }

    public void setRetry(boolean retry) {
        isRetry = retry;
    }

    public static enum HTTP_MOTHOD {
        POST, GET
    }

    /**
     * @return 请求URL
     */
    final String getRequestUrl(int id) {
        return apiUrl;
    }

    /**
     * 设置请求URL
     *
     * @param apiUrl
     */
    public void setApiUrl(String apiUrl) {
        this.apiUrl = apiUrl;
        if (port != -1) {
            this.apiUrl += ":";
            this.apiUrl += port;
        }
    }

    public void setPort(int port) {
        this.port = port;
    }

    public int getPort() {
        return this.port;
    }

    /**
     * 设置请求方式
     *
     * @param mothod
     */
    public final void setRequestMothod(HTTP_MOTHOD mothod) {
        this.mothod = mothod;
    }

    /**
     * @return 拿到请求参数
     */
    protected abstract Param getParams(int id);

    /**
     * 拿到请求方式
     *
     * @return
     */
    public final HTTP_MOTHOD getRequestMothod() {
        return mothod;
    }
}
