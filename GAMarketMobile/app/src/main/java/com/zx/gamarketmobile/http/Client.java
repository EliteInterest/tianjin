package com.zx.gamarketmobile.http;

import org.apache.http.impl.client.DefaultHttpClient;

public class Client {
    private static DefaultHttpClient instance = null;

    private Client() {

    }

    public static DefaultHttpClient getInstance() {
        if (instance == null) {
            return instance = new DefaultHttpClient();
        } else {
            return instance;
        }
    }
}
