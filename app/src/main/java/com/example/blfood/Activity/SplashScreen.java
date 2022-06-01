package com.example.blfood.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.blfood.Connection.IPadress;
import com.example.blfood.Token.MyCheckBoxStatus;
import com.example.blfood.Token.MyToken;
import com.example.blfood.R;
import com.example.blfood.VolleySingleton;
import com.example.blfood.Storage.user;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class SplashScreen extends AppCompatActivity {
//    static int ok;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
//        int hihi = funny();
//        Toast.makeText(SplashScreen.this, String.valueOf(hihi),Toast.LENGTH_SHORT).show();

        int ok = Check();
        //Toast.makeText(SplashScreen.this, MyCheckBoxStatus.getCheckBoxStatus(SplashScreen.this).trim(), Toast.LENGTH_SHORT).show();
        //Toast.makeText(SplashScreen.this, String.valueOf(ok),Toast.LENGTH_SHORT).show();

        if (ok == 0) {
            LoadingLogin loadingLogin = new LoadingLogin();
            loadingLogin.execute();
        } else {
            getUserInfor();
            LoadingMainActivity loadingMainActivity = new LoadingMainActivity();
            loadingMainActivity.execute();
        }
    }

    void getUserInfor() {
        String url = IPadress.ip + "Werservice/SessionID.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                // và nhận về id user và username để lưu vào class User

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray   = jsonObject.getJSONArray("userInfor");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        // json object này để duyệt từng object trong mảng
                        JSONObject jsonObjectByRow = jsonArray.getJSONObject(i);
                        int iduser      = Integer.parseInt(jsonObjectByRow.getString("iduser"));
                        String username = jsonObjectByRow.getString("username");
                        //String password = jsonObjectByRow.getString("password");
                        //String name     = jsonObjectByRow.getString("name");
                        //int age         = Integer.parseInt(jsonObjectByRow.getString("age"));
                        //int sex         = Integer.parseInt(jsonObjectByRow.getString("sex"));
                        String avatarURL = jsonObjectByRow.getString("avatarURL");

                        // sữ dụng để set tên và set avatar
                        user.iduser     = iduser;
                        user.username   = username;
                        user.avatarURL  = avatarURL;
                        //Toast.makeText(SplashScreen.this, user.avatarURL, Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(SplashScreen.this, "lỗi", Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("token", MyToken.getToken(SplashScreen.this));
                return params;
            }

            @Override
            public Priority getPriority() {
                return  Priority.HIGH;
            }
        };
        VolleySingleton.getInstance(SplashScreen.this).getRequestQueue().add(stringRequest);
    }

    int Check() {
        int kq = 0;
//        Toast.makeText(this, MyCheckBoxStatus.getCheckBoxStatus(SplashScreen.this), Toast.LENGTH_LONG).show();
//        Toast.makeText(this, MyToken.getToken(SplashScreen.this), Toast.LENGTH_LONG).show();

        String mToken = MyToken.getToken(SplashScreen.this).trim();
        int mCheck = Integer.parseInt(MyCheckBoxStatus.getCheckBoxStatus(SplashScreen.this).trim());

        // Trường hợp không có token, cũng sẽ không cớ check bởi vì khi đăng xuất đã set token và check = empty
        if (mToken == "empty") {
            // đi đến trang login
            kq = 0;
        }
        else {
            // trường hợp có token và có check
            if (mCheck == 1) {
                kq = 1;
            }
            // trường hợp có token và không có check
            if (mCheck == 0) {
                kq = 0;
                // xóa token lưu trong máy
                MyToken.saveToken(SplashScreen.this, "empty");
            }
        }


        return kq;
    };

    public class LoadingMainActivity extends AsyncTask<Void, Void, Void> {


        @Override
        protected Void doInBackground(Void... voids) {
            try {
                Thread.sleep(2500);
                startActivity(new Intent(SplashScreen.this, MainActivity.class));
                finish();

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    public class LoadingLogin extends AsyncTask<Void, Void, Void> {


        @Override
        protected Void doInBackground(Void... voids) {
            try {
                Thread.sleep(2500);
                startActivity(new Intent(SplashScreen.this, login.class));
                finish();

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    int funny() {
        int haha = 0;
        String mToken = MyToken.getToken(SplashScreen.this).trim();
        String mCheck = MyCheckBoxStatus.getCheckBoxStatus(SplashScreen.this).trim();
        if (mToken == "empty") {haha =1;}
        if(mToken != "empty") {haha = 100;}
        return  haha;
    }

}
