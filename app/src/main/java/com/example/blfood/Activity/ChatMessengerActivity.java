package com.example.blfood.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebView;

import com.example.blfood.ChatMessage.MyWebViewClient;
import com.example.blfood.Connection.IPadress;
import com.example.blfood.R;
import com.example.blfood.Storage.user;

public class ChatMessengerActivity extends AppCompatActivity {
    WebView webview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_messenger);
        Intent intent = getIntent();
        String url = intent.getStringExtra("GoToThisURL");

        anhXa();

        if (url != null) {
            goUrl(url);
        } else {
            url = IPadress.ip + "Werservice/ChatService/listfriendpage.php?requester=" + user.username + "&admirer=" + user.username + "&user=" + user.username;
            //String url = "https://www.google.com/";
            webview.setWebViewClient(new MyWebViewClient(url));
            goUrl(url);
        }
        // https://meet.google.com/wso-vdsq-qsg
        // http://localhost:81/Werservice/ChatService/listfriendpage.php?requester=John&admirer=John&user=John
    }

    void anhXa() {
        webview = (WebView) findViewById(R.id.webview);
    }

    void goUrl(String url) {
        webview.getSettings().setLoadsImagesAutomatically(true);
        webview.getSettings().setJavaScriptEnabled(true);
        webview.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        webview.loadUrl(url);
    }

    // nếu đang ở page index mà user nhấn nút back thì finish() activity này
    // nếu không phải thì lui lại một page
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_DOWN) {
            switch (keyCode) {
                case KeyEvent.KEYCODE_BACK:
                    if (webview.canGoBack()) {
                        webview.goBack();
                    } else {
                        finish();
                    }
                    return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }
}