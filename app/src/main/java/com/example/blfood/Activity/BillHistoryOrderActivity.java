package com.example.blfood.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.blfood.Adapter.BillHistoryOrderAdapter;
import com.example.blfood.Connection.IPadress;
import com.example.blfood.Model.HistoryOrderItem;
import com.example.blfood.Model.ItemCart;
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

public class BillHistoryOrderActivity extends AppCompatActivity {
    TextView Billusername, Billidhoadon, Billtongtien;
    RecyclerView BillRecycleView;

    HistoryOrderItem historyOrderItem;

    // dùng hàm contruction thứ 2 của ItemCart ít intance hơn
    List<ItemCart> itemCartList;

    public static int idhoadon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill);

        historyOrderItem = (HistoryOrderItem) getIntent().getSerializableExtra("historyOrderItemList");
        AnhXa();
        SetData(historyOrderItem);

        itemCartList = new ArrayList<ItemCart>();

        getChiTietHoaDonAtIdhoadonAndUsername(historyOrderItem);

        BillHistoryOrderAdapter billHistoryOrderAdapter = new BillHistoryOrderAdapter(BillHistoryOrderActivity.this, itemCartList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(BillHistoryOrderActivity.this, LinearLayoutManager.VERTICAL, false);

        BillRecycleView.setAdapter(billHistoryOrderAdapter);
        BillRecycleView.setLayoutManager(linearLayoutManager);
    }

    void AnhXa() {
        Billusername    = (TextView)     findViewById(R.id.Billusername);
        Billidhoadon    = (TextView)     findViewById(R.id.Billidhoadon);
        Billtongtien    = (TextView)     findViewById(R.id.Billtongtien);
        BillRecycleView = (RecyclerView) findViewById(R.id.BillRecycleView);
    }

    void SetData(HistoryOrderItem historyOrderItem) {
        Billusername.setText(user.username);
        Billidhoadon.setText(String.valueOf(historyOrderItem.getIdhoadon()));
        Billtongtien.setText(String.valueOf(historyOrderItem.getTonghoadon()));
    }

    synchronized void getChiTietHoaDonAtIdhoadonAndUsername(final HistoryOrderItem historyOrderItem) {
        String url = IPadress.ip + "Werservice/getChiTietHoaDonAtIdhoadonAndUsername.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    //Toast.makeText(BillHistoryOrderActivity.this, response, Toast.LENGTH_LONG).show();
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("ChiTietHoaDon");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        // json object này để duyệt từng object trong mảng
                        JSONObject jsonObjectByRow = jsonArray.getJSONObject(i);
                        String productname = jsonObjectByRow.getString("productname");
                        int gia         = Integer.parseInt(jsonObjectByRow.getString("gia"));
                        int soluongmua  = Integer.parseInt(jsonObjectByRow.getString("soluongmua"));
                        int tongchitiethoadon = Integer.parseInt(jsonObjectByRow.getString("tongchitiethoadon"));

                        // đổ dữ liệu từ json vào arraylist
                        itemCartList.add(new ItemCart(productname, gia, soluongmua, tongchitiethoadon));
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
                params.put("idhoadon", String.valueOf(historyOrderItem.getIdhoadon()));
                params.put("username", user.username);
                return params;
            }

            @Override
            public Priority getPriority() {
                return Priority.HIGH;
            }
        };
        VolleySingleton.getInstance(BillHistoryOrderActivity.this).getRequestQueue().add(stringRequest);
    }
}