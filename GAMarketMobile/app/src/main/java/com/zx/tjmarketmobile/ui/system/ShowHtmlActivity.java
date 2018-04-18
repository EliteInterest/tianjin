package com.zx.tjmarketmobile.ui.system;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.zx.tjmarketmobile.R;
import com.zx.tjmarketmobile.http.ApiData;
import com.zx.tjmarketmobile.ui.base.BaseActivity;

/**
 * Create By Xiangb On 2016/9/19
 * 功能：关于界面
 */

public class ShowHtmlActivity extends BaseActivity {

    private WebView webView;

    public static void startAction(Context context, String path, String name) {
        Intent starter = new Intent(context, ShowHtmlActivity.class);
        starter.putExtra("path", path);
        starter.putExtra("name", name);
        context.startActivity(starter);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_showhtml);
        addToolBar(true);
        hideRightImg();
        setMidText(getIntent().getStringExtra("name"));
        webView = findViewById(R.id.web_view);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setUseWideViewPort(true);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setBuiltInZoomControls(true);
        webView.getSettings().setDefaultTextEncodingName("GBK");
        webView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        webView.setInitialScale(5);
        webView.setHorizontalScrollBarEnabled(true);
        webView.setVerticalScrollBarEnabled(true);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                return false;
            }
        });
        webView.loadUrl(ApiData.baseUrl + getIntent().getStringExtra("path"));
//        webView.loadUrl("http://192.168.11.238:6020/temp_tj/CAF7EEF2D2D8498CACF935434B5AA9D4.html");
    }

    @Override
    public void onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack();
        } else {
            super.onBackPressed();
        }
    }
}
