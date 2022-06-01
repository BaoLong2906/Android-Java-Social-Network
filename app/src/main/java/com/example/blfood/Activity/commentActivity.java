package com.example.blfood.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.blfood.Adapter.CommentAdapter;
import com.example.blfood.Connection.IPadress;
import com.example.blfood.Model.NewFeedItem;
import com.example.blfood.R;
import com.example.blfood.Storage.user;
import com.example.blfood.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class commentActivity extends AppCompatActivity {
    RecyclerView RecyclerViewComment;
    EditText editTextComment;
    Button btnPostComment;

    NewFeedItem newFeedItemIntent;
    List<NewFeedItem> newFeedItemList;

    CommentAdapter commentAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);
        newFeedItemIntent = (NewFeedItem) getIntent().getSerializableExtra("newFeedItemList");
        AnhXa();

        newFeedItemList = new ArrayList<NewFeedItem>();
        // lấy ra các comment của status được send từ FragmentNewFeed
        getCommentOfStatus(newFeedItemIntent);

        commentAdapter = new CommentAdapter(commentActivity.this, newFeedItemList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(commentActivity.this, LinearLayoutManager.VERTICAL, false);

        RecyclerViewComment.setLayoutManager(linearLayoutManager);
        RecyclerViewComment.setAdapter(commentAdapter);


        //newFeedItemList.add();
        setBtn(newFeedItemIntent, commentAdapter, linearLayoutManager);
    }

    void AnhXa() {
        RecyclerViewComment = (RecyclerView) findViewById(R.id.RecyclerViewComment);
        editTextComment     = (EditText) findViewById(R.id.editTextComment);
        btnPostComment      = (Button) findViewById(R.id.btnPostComment);
    }

    void setBtn(final NewFeedItem newFeedItemIntent, final CommentAdapter commentAdapter, final LinearLayoutManager linearLayoutManager) {
        btnPostComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // insert vào bảng comment
                insertToComment(newFeedItemIntent);
                // reset bảng
//                // xóa các element đang có trong mảng
//                newFeedItemList = new ArrayList<NewFeedItem>();
//                getCommentOfStatus(newFeedItemIntent);
//                CommentAdapter commentAdapter = new CommentAdapter(commentActivity.this, newFeedItemList);
//                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(commentActivity.this, LinearLayoutManager.VERTICAL, false);
//                RecyclerViewComment.setAdapter(commentAdapter);
//                RecyclerViewComment.setLayoutManager(linearLayoutManager);
//                //commentAdapter.notifyDataSetChanged();
                // thêm vào 1 row
                addOneComment();
            }
        });
    }

    void addOneComment() {
        newFeedItemList.add(new NewFeedItem(newFeedItemIntent.getIdstatus() ,user.iduser, user.avatarURL, user.username, editTextComment.getText().toString().trim()));
        commentAdapter.notifyItemInserted(newFeedItemList.size() - 1);
    }

    synchronized void getCommentOfStatus(final NewFeedItem newFeedItemIntent) {
        String url1 = IPadress.ip + "Werservice/getCommentOfStatus.php";
        StringRequest stringRequest1 = new StringRequest(Request.Method.POST, url1, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //Toast.makeText(commentActivity.this, response, Toast.LENGTH_LONG).show();
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("comment");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObjectByRow = jsonArray.getJSONObject(i);
                        int idcomment         = Integer.parseInt(jsonObjectByRow.getString("idcomment"));
                        int idstatus          = Integer.parseInt(jsonObjectByRow.getString("idstatus"));
                        int iduser            = Integer.parseInt(jsonObjectByRow.getString("iduser"));
                        String avatarURL      = jsonObjectByRow.getString("avatarURL").trim();
                        String username       = jsonObjectByRow.getString("username").trim();
                        String contentNewFeed = jsonObjectByRow.getString("content").trim();

                        newFeedItemList.add(new NewFeedItem(idcomment, idstatus, iduser, avatarURL, username, contentNewFeed));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                // gửi các id và username để tìm kiếm các comment của status
                Map<String, String> params = new HashMap<String, String>();
                params.put("idstatus", String.valueOf(newFeedItemIntent.getIdstatus()));
                //params.put("iduser", String.valueOf(user.iduser));
                //params.put("username", user.username);
                return params;
            }

            @Override
            public Priority getPriority() {
                return  Priority.HIGH;
            }
        };
        VolleySingleton.getInstance(commentActivity.this).getRequestQueue().add(stringRequest1);
    }

    synchronized void insertToComment(final NewFeedItem newFeedItemIntent) {
        String url2 = IPadress.ip + "Werservice/insertToComment.php";
        StringRequest stringRequest2 = new StringRequest(Request.Method.POST, url2, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //Toast.makeText(commentActivity.this, response, Toast.LENGTH_LONG).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("idstatus", String.valueOf(newFeedItemIntent.getIdstatus()));
                params.put("iduser", String.valueOf(user.iduser));
                params.put("avatarURL", user.avatarURL);
                params.put("username", user.username);
                params.put("content", editTextComment.getText().toString().trim());
                return params;
            }

            @Override
            public Priority getPriority() {
                return  Priority.HIGH;
            }
        };
        VolleySingleton.getInstance(commentActivity.this).getRequestQueue().add(stringRequest2);
    }
}