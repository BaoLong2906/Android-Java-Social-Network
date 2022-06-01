package com.example.blfood.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import com.example.blfood.Adapter.FindFriendAsNameAdapter;
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

public class FindFriendAsNameActivity extends AppCompatActivity {
    RecyclerView RecyclerViewFindFriendAsName;
    List<NewFeedItem> newFeedItemList = new ArrayList<NewFeedItem>();

    EditText editTextFindFriendAsName;
    Button btnFindFriendAsName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_friend_as_name);

        AnhXa();

        editTextFindFriendAsName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editTextFindFriendAsName.getText().clear();
            }
        });

        setBtn();
    }

    void AnhXa() {
        RecyclerViewFindFriendAsName = (RecyclerView) findViewById(R.id.RecyclerViewFindFriendAsName);
        editTextFindFriendAsName     = (EditText) findViewById(R.id.editTextFindFriendAsName);
        btnFindFriendAsName          = (Button) findViewById(R.id.btnFindFriendAsName);
    }

    void setBtn() {
        btnFindFriendAsName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FindFriendByName();

                FindFriendAsNameAdapter findFriendAsNameAdapter = new FindFriendAsNameAdapter(FindFriendAsNameActivity.this, newFeedItemList);
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(FindFriendAsNameActivity.this, LinearLayoutManager.VERTICAL, false);

                RecyclerViewFindFriendAsName.setAdapter(findFriendAsNameAdapter);
                RecyclerViewFindFriendAsName.setLayoutManager(linearLayoutManager);

                newFeedItemList.clear();
            }
        });
    }

    synchronized void FindFriendByName() {
        String url = IPadress.ip + "Werservice/FindFriendByName.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //Toast.makeText(FindFriendAsNameActivity.this, response, Toast.LENGTH_LONG).show();
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("findthisname");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        // json object này để duyệt từng object trong mảng
                        JSONObject jsonObjectByRow = jsonArray.getJSONObject(i);
                        int iduser   = Integer.parseInt(jsonObjectByRow.getString("iduser"));
                        String username = jsonObjectByRow.getString("username");
                        String name = jsonObjectByRow.getString("name");
                        String avatarURL = jsonObjectByRow.getString("avatarURL");
//                        int idfriend = Integer.parseInt(jsonObjectByRow.getString("idfriend"));
//                        String requester = jsonObjectByRow.getString("requester");
//                        String admirer = jsonObjectByRow.getString("admirer");
                        int isfriend = Integer.parseInt(jsonObjectByRow.getString("isfriend"));

                        newFeedItemList.add(new NewFeedItem(iduser, avatarURL, username, name, isfriend));
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
                Map<String, String> params = new HashMap<String, String>();
                params.put("findthisname", editTextFindFriendAsName.getText().toString().trim());
                params.put("clientboss", user.username);
                return params;
            }

            @Override
            public Priority getPriority() {
                return Priority.HIGH;
            }
        };
        VolleySingleton.getInstance(FindFriendAsNameActivity.this).getRequestQueue().add(stringRequest);
    }
}