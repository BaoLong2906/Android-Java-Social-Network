package com.example.blfood.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.blfood.Adapter.HorizontalItemAdapter;
import com.example.blfood.Connection.IPadress;
import com.example.blfood.Model.HorizontalItem;
import com.example.blfood.Token.MyToken;
import com.example.blfood.R;
import com.example.blfood.VolleySingleton;
//import com.example.blfood.Model.HorizontalItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MenuAnSangActivity extends AppCompatActivity {
    List<HorizontalItem> horizontalItemList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_an_sang);

        Intent intent = getIntent();
        //Toast.makeText(MenuAnSangActivity.this, MyToken.getToken(MenuAnSangActivity.this), Toast.LENGTH_LONG).show();

        horizontalItemList = new ArrayList<>();

        getData();
        // recycle view horizontal của các món ăn sáng thịnh hành nhất
        RecyclerView recyclerViewMonAnSangThinhHanh = (RecyclerView) findViewById(R.id.recycleviewMonAnSangThinhHanh);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(MenuAnSangActivity.this, LinearLayoutManager.HORIZONTAL, false);
        HorizontalItemAdapter horizontalItemAdapter = new HorizontalItemAdapter(MenuAnSangActivity.this, horizontalItemList);
        recyclerViewMonAnSangThinhHanh.setLayoutManager(linearLayoutManager);
        recyclerViewMonAnSangThinhHanh.setAdapter(horizontalItemAdapter);

        // recycle view girdlayout của các món ăn sáng
        RecyclerView recycleviewMonAnSang = (RecyclerView) findViewById(R.id.recycleviewMonAnSang);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(MenuAnSangActivity.this, 2, GridLayoutManager.VERTICAL, false);
        recycleviewMonAnSang.setLayoutManager(gridLayoutManager);
        recycleviewMonAnSang.setAdapter(horizontalItemAdapter);
    }

    synchronized void getData() {
        String url = IPadress.ip + "Werservice/getDataMonAnSang.php";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //Toast.makeText(MenuAnSangActivity.this, response, Toast.LENGTH_LONG).show();
                // dữ liệu lấy từ bảng product
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("dataMonAnSangThinhHanh");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        // json object này để duyệt từng object trong mảng
                        JSONObject jsonObjectByRow = jsonArray.getJSONObject(i);
                        int idproduct      =  Integer.parseInt(jsonObjectByRow.getString("idproduct"));
                        String productname = jsonObjectByRow.getString("productname");
                        int price          = Integer.parseInt(jsonObjectByRow.getString("price"));
                        String motasanpham = jsonObjectByRow.getString("motasanpham");
                        //int soluong        = Integer.parseInt(jsonObjectByRow.getString("soluong"));
                        String urlImage    = jsonObjectByRow.getString("url");
                        //String tag         = jsonObjectByRow.getString("tag");

                        // sau khi volley đọc json thì nó sẽ đưa các giá trị đó vào horizontalItemList
                        // để tiến hành đổ ra các recycleview của menu món ăn
                        horizontalItemList.add(new HorizontalItem(idproduct, productname, price, motasanpham, urlImage));
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MenuAnSangActivity.this, error.toString(), Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> prams = new HashMap<>();
                // gửi token lên server để xác thực
                prams.put("IDsession", MyToken.getToken(MenuAnSangActivity.this).trim());

                return prams;
            }

            @Override
            public Priority getPriority() {
                return  Priority.IMMEDIATE;
            }
        };
        //RequestQueue requestQueue = Volley.newRequestQueue(MenuAnSangActivity.this);
        //requestQueue.add(stringRequest);
        VolleySingleton.getInstance(MenuAnSangActivity.this).getRequestQueue().add(stringRequest);
    }
}