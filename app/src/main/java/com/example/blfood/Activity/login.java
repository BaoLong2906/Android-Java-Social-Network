package com.example.blfood.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
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

public class login extends AppCompatActivity {
    EditText username, password;
    Button btnLogin;
    TextView signUp;
    CheckBox checkBox;

    VolleySingleton volleySingleton;
    public static boolean access;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        AnhXa();
        setBtn();




//        btnLogin.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String url = "http://192.168.1.104:81/Werservice/login.php";
//
//                StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//                        //Toast.makeText(login.this, response, Toast.LENGTH_LONG).show();
//                        try {
//                            // lấy token mới được tạo mới ở webservice về và lưu trong SharedPreferences
//                            JSONObject jsonObject = new JSONObject(response);
//                            String token = jsonObject.getString("token");
//
//                            MyToken.saveToken(login.this, token);
//                            Toast.makeText(login.this, MyToken.getToken(login.this), Toast.LENGTH_LONG).show();
//
//                            // class user nhớ username để lấy giá trị username để insert vào table chi tiết hóa đơn
//                            user.username = username.getText().toString().trim();
//
//                            //Toast.makeText(login.this, token, Toast.LENGTH_LONG).show();
//
//                            // kiểm tra khách hàng có lưu phiên đăng nhập hay ko
//
//                            // nếu khách hàng chọn nhớ phiên đăng nhập
//                            if (checkBox.isChecked()){
//                                MyCheckBoxStatus.saveCheckBoxStatus(login.this, "1");
//                            } else {
//                                // nếu khách hàng không chọn nhớ phiên đăng nhập
//                                MyCheckBoxStatus.saveCheckBoxStatus(login.this, "0");
//                            }
//
//                            startActivity(new Intent(login.this, MainActivity.class));
//                            finish();
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//
//                        //Toast.makeText(login.this, MyToken.GetDecodePayLoad(response), Toast.LENGTH_LONG).show();
//                        //MyToken.GetDecodePayLoad(response);
//                        //Log.i("VOLLEY", response);
//                    }
//                }, new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        Toast.makeText(login.this, "lỗi", Toast.LENGTH_LONG).show();
//                        //Log.e("VOLLEY", error.toString());
//                    }
//                }){
//                    @Override
//                    protected Map<String, String> getParams() throws AuthFailureError {
//                        Map<String, String> params = new HashMap<>();
//                        params.put("username",username.getText().toString().trim());
//                        params.put("password",password.getText().toString().trim());
//                        return params;
//                    }
//
//                    @Override
//                    public Priority getPriority() {
//                        return Priority.HIGH;
//                    }
//                };
//                //RequestQueue requestQueue = Volley.newRequestQueue(login.this);
//                //requestQueue.add(stringRequest);
//                VolleySingleton.getInstance(login.this).getRequestQueue().add(stringRequest);
//
//
//
//
//            }
//        });
//
//        signUp.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });
    }

    void setBtn() {
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = IPadress.ip + "Werservice/login.php";

                StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //Toast.makeText(login.this, response, Toast.LENGTH_LONG).show();
                        try {
                            // lấy token mới được tạo mới ở webservice về và lưu trong SharedPreferences
                            JSONObject jsonObject = new JSONObject(response);
                            String token = jsonObject.getString("token");

                            MyToken.saveToken(login.this, token);
                            Toast.makeText(login.this, MyToken.getToken(login.this), Toast.LENGTH_LONG).show();

                            // class user nhớ username để lấy giá trị username để insert vào table chi tiết hóa đơn
                            user.username = username.getText().toString().trim();
                            getUserInfor();
                            //Toast.makeText(login.this, token, Toast.LENGTH_LONG).show();

                            // kiểm tra khách hàng có lưu phiên đăng nhập hay ko

                            // nếu khách hàng chọn nhớ phiên đăng nhập
                            if (checkBox.isChecked()){
                                MyCheckBoxStatus.saveCheckBoxStatus(login.this, "1");
                            } else {
                                // nếu khách hàng không chọn nhớ phiên đăng nhập
                                MyCheckBoxStatus.saveCheckBoxStatus(login.this, "0");
                            }

                            startActivity(new Intent(login.this, MainActivity.class));
                            finish();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        //Toast.makeText(login.this, MyToken.GetDecodePayLoad(response), Toast.LENGTH_LONG).show();
                        //MyToken.GetDecodePayLoad(response);
                        //Log.i("VOLLEY", response);
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(login.this, "lỗi", Toast.LENGTH_LONG).show();
                        //Log.e("VOLLEY", error.toString());
                    }
                }){
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params = new HashMap<>();
                        params.put("username",username.getText().toString().trim());
                        params.put("password",password.getText().toString().trim());
                        return params;
                    }

                    @Override
                    public Priority getPriority() {
                        return Priority.HIGH;
                    }
                };
                //RequestQueue requestQueue = Volley.newRequestQueue(login.this);
                //requestQueue.add(stringRequest);
                VolleySingleton.getInstance(login.this).getRequestQueue().add(stringRequest);
            }
        });

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // đi đến trang đăng ký
                startActivity(new Intent(login.this, SignUp.class));
            }
        });
    }

    void AnhXa() {
        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        signUp = (TextView) findViewById(R.id.signUp);
        checkBox = (CheckBox) findViewById(R.id.checkBox);
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
                        String name     = jsonObjectByRow.getString("name");
                        //int age         = Integer.parseInt(jsonObjectByRow.getString("age"));
                        //int sex         = Integer.parseInt(jsonObjectByRow.getString("sex"));
                        String avatarURL = jsonObjectByRow.getString("avatarURL");

                        // sữ dụng để set tên và set avatar
                        user.iduser     = iduser;
                        user.username   = username;
                        user.avatarURL  = avatarURL;
                        user.name       = name;
                        Toast.makeText(login.this, user.avatarURL, Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(login.this, "lỗi", Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("token", MyToken.getToken(login.this));
                return params;
            }

            @Override
            public Priority getPriority() {
                return  Priority.HIGH;
            }
        };
        VolleySingleton.getInstance(login.this).getRequestQueue().add(stringRequest);
    }
}
