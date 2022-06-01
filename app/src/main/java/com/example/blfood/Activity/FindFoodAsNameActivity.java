package com.example.blfood.Activity;

import androidx.appcompat.app.AppCompatActivity;
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
import com.example.blfood.R;
import com.example.blfood.Token.MyToken;
import com.example.blfood.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FindFoodAsNameActivity extends AppCompatActivity {
    RecyclerView RecycleViewFindFoodAsName;
    List<HorizontalItem> horizontalItemList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_food_as_name);
        Intent intent = getIntent();
        String FoodName = intent.getStringExtra("FoodName");
        AnhXa();

        horizontalItemList = new ArrayList<HorizontalItem>();

        getData(FoodName);
        RecycleViewFindFoodAsName = (RecyclerView) findViewById(R.id.RecycleViewFindFoodAsName);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(FindFoodAsNameActivity.this, LinearLayoutManager.HORIZONTAL, false);
        HorizontalItemAdapter horizontalItemAdapter = new HorizontalItemAdapter(FindFoodAsNameActivity.this, horizontalItemList);
        RecycleViewFindFoodAsName.setLayoutManager(linearLayoutManager);
        RecycleViewFindFoodAsName.setAdapter(horizontalItemAdapter);
    }

    void AnhXa() {
        RecycleViewFindFoodAsName = (RecyclerView) findViewById(R.id.RecycleViewFindFoodAsName);
    }

    synchronized void getData(final String FoodName) {
        String url = IPadress.ip + "Werservice/findDataMonAnSang.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(FindFoodAsNameActivity.this, response, Toast.LENGTH_LONG).show();
                // dữ liệu lấy từ bảng product
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("ketquaMonAnTimKiem");
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
                Toast.makeText(FindFoodAsNameActivity.this, error.toString(), Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> prams = new HashMap<>();
                prams.put("FoodName", FoodName);
                return prams;
            }

            @Override
            public Priority getPriority() {
                return  Priority.IMMEDIATE;
            }
        };
        //RequestQueue requestQueue = Volley.newRequestQueue(MenuAnSangActivity.this);
        //requestQueue.add(stringRequest);
        VolleySingleton.getInstance(FindFoodAsNameActivity.this).getRequestQueue().add(stringRequest);
    }
}