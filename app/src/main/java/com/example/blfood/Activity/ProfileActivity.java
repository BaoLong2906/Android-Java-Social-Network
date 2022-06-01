package com.example.blfood.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.blfood.Connection.IPadress;
import com.example.blfood.Model.HorizontalItem;
import com.example.blfood.Model.NewFeedItem;
import com.example.blfood.R;
import com.example.blfood.Storage.user;
import com.example.blfood.VolleySingleton;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;

public class ProfileActivity extends AppCompatActivity {
    ImageView profileavatarImageView, btnAddfriend, btnMessenger;
    TextView profilename;

    NewFeedItem userItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        // get object được truyền qua intent
        userItem = (NewFeedItem) getIntent().getSerializableExtra("profileuser");
        anhXa();

        setProfileDataUser(userItem);
        setBtn(userItem);
    }

    void anhXa() {
        profileavatarImageView = (ImageView) findViewById(R.id.profileavatarImageView);
        btnAddfriend           = (ImageView) findViewById(R.id.btnAddfriend);
        btnMessenger           = (ImageView) findViewById(R.id.btnMessenger);
        profilename            = (TextView) findViewById(R.id.profilename);
    }

    void setProfileDataUser(NewFeedItem userItem) {
        profilename.setText(userItem.getUsername());
        String ofUrl = IPadress.ip + "Werservice/images/" + userItem.getAvatarURL();
        Picasso.with(ProfileActivity.this).load(ofUrl).into(profileavatarImageView);
    }

    void setBtn(final NewFeedItem userItem) {
        btnAddfriend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ProfileActivity.this, "đã gửi lời mời kết bạn", Toast.LENGTH_SHORT).show();
                sendFriendRequest(userItem);
            }
        });

        btnMessenger.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileActivity.this, ChatMessengerActivity.class);
                String url = IPadress.ip + "Werservice/ChatService/index.php?roomboss=" + userItem.getUsername() +"&avatarURL=" + userItem.getAvatarURL();
                intent.putExtra("GoToThisURL", url);
                startActivity(intent);
            }
        });
    }

    // hàm gửi lời mời kết bạn
    synchronized void sendFriendRequest(final NewFeedItem userItem) {
        String url = IPadress.ip + "Werservice/insertToFriend.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //Toast.makeText(ProfileActivity.this, response, Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("requester", user.username);
                params.put("admirer", userItem.getUsername());
                return params;
            }

            @Override
            public Priority getPriority() {
                return Priority.HIGH;
            }
        };
        VolleySingleton.getInstance(ProfileActivity.this).getRequestQueue().add(stringRequest);
    }
}