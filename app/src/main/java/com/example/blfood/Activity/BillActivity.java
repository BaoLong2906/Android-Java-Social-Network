package com.example.blfood.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.blfood.Adapter.BillAdapter;
import com.example.blfood.Connection.IPadress;
import com.example.blfood.Storage.MyCart;
import com.example.blfood.R;
import com.example.blfood.VolleySingleton;
import com.example.blfood.Storage.user;

public class BillActivity extends AppCompatActivity {
    TextView Billusername, Billidhoadon, Billtongtien;
    RecyclerView BillRecycleView;
    public static int idhoadon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill);

        Intent intent = getIntent();


        AnhXa();
        setData();

        BillAdapter billAdapter = new BillAdapter(BillActivity.this, MyCart.itemCartList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(BillActivity.this, LinearLayoutManager.VERTICAL, false);

        BillRecycleView.setAdapter(billAdapter);
        BillRecycleView.setLayoutManager(linearLayoutManager);


    }

    @Override
    public void onBackPressed() {
        ClearCart();
        CartActivity.itemAdapter.notifyDataSetChanged();
        CartActivity.TongTienCartUpdate = 0;
        CartActivity.TongTienCart.setText(String.valueOf(0));
        super.onBackPressed();
    }

    void ClearCart() {
        MyCart.DeleteAllCart();
    }

    void AnhXa() {
        Billusername    = (TextView)     findViewById(R.id.Billusername);
        Billidhoadon    = (TextView)     findViewById(R.id.Billidhoadon);
        Billtongtien    = (TextView)     findViewById(R.id.Billtongtien);
        BillRecycleView = (RecyclerView) findViewById(R.id.BillRecycleView);
    }

    void setData() {
        // c?? 2 tr?????ng h???p, 1 l?? l???y username trong share Preference n???u kh??ch h??ng tick v??o ?? nh??? ????ng nh???p
        // 2 l?? l???y username trong bi???n static username n???u kh??ch h??ng kh??ng tick v??o ?? nh??? ????ng nh???p
        Billusername.setText(user.username);
        Billidhoadon.setText(String.valueOf(getIdHoaDon()));
        for (int i = 0; i < MyCart.itemCartList.size(); i++) {
            CartActivity.TongTienCartUpdate += MyCart.itemCartList.get(i).getTonggia();
        }
        int temp = CartActivity.TongTienCartUpdate;
        CartActivity.TongTienCartUpdate = 0;

        Billtongtien.setText(String.valueOf(temp));
    }

    int getIdHoaDon() {
        String url = IPadress.ip + "Werservice/getIdHoaDon.php";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                idhoadon = Integer.parseInt(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        VolleySingleton.getInstance(BillActivity.this).getRequestQueue().add(stringRequest);

        return idhoadon;
    }
}