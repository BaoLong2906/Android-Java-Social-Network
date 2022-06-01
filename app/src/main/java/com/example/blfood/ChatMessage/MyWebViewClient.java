package com.example.blfood.ChatMessage;

import android.graphics.Bitmap;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class MyWebViewClient extends WebViewClient {
    String url;

    public MyWebViewClient(String url) {
        this.url = url;
    }

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        Log.i("MyLog", "Click on any interlink on webview that time you got url: " + url);

        return super.shouldOverrideUrlLoading(view, url);
    }

    @Override
    public void onPageStarted(WebView view, String url, Bitmap favicon) {
        super.onPageStarted(view, url, favicon);
    }

    @Override
    public void onPageFinished(WebView view, String url) {
        super.onPageFinished(view, url);
    }
}
