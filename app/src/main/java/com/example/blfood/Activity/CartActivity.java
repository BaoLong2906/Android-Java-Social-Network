package com.example.blfood.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.blfood.Adapter.ItemAdapter;
import com.example.blfood.Connection.IPadress;
import com.example.blfood.Storage.MyCart;
import com.example.blfood.Token.MyToken;
import com.example.blfood.R;
import com.example.blfood.Storage.user;

import java.util.HashMap;
import java.util.Map;

public class CartActivity extends AppCompatActivity {
    public static TextView TongTienCart;
    Button btnThanhToan, btnTiepTucMua;
    RecyclerView recycleviewCart;
    public static ItemAdapter itemAdapter;
    public static int TongTienCartUpdate = 0;

    //public static String idhoadon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        Intent intent = getIntent();

        AnhXa();

        setBtn();


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(CartActivity.this, LinearLayoutManager.VERTICAL, false);
        itemAdapter = new ItemAdapter(CartActivity.this, MyCart.itemCartList);

        recycleviewCart.setAdapter(itemAdapter);
        recycleviewCart.setLayoutManager(linearLayoutManager);

        for (int i = 0; i < MyCart.itemCartList.size(); i++) {
            TongTienCartUpdate += MyCart.itemCartList.get(i).getTonggia();
        }

        TongTienCart.setText(String.valueOf(TongTienCartUpdate));
        TongTienCartUpdate = 0;

    }

    synchronized void insertToHoaDon() {
        String url = IPadress.ip +"Werservice/insertToHoaDon.php";

        // insert vào bảng hóa đơn
        StringRequest stringRequest1 = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                user.idhoadon = response;
                //Toast.makeText(CartActivity.this, user.idhoadon +" lần 1", Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("token", MyToken.getToken(CartActivity.this).trim());
                params.put("tonghoadon", TongTienCart.getText().toString().trim());
                return params;
            }

            @Override
            public Priority getPriority() {
                return Priority.HIGH;
            }
        };
//                RequestQueue requestQueue = Volley.newRequestQueue(CartActivity.this);
//                requestQueue.add(stringRequest1);

        stringRequest1.setRetryPolicy(new DefaultRetryPolicy(20 * 1000, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        RequestQueue requestQueue = Volley.newRequestQueue(CartActivity.this);
        requestQueue.add(stringRequest1);
    }

    synchronized void insertToChiTietHoaDon() {
        for (int i = 0; i < MyCart.itemCartList.size(); i++) {


            // gửi lên Webservice các thông tin về từng product trong giỏ hàng để Webservice tiến hành
            // insert vào bảng chi tiết hóa đơn

            String url = IPadress.ip +"Werservice/insertToChiTietHoaDon.php";

            final int finalI = i;

            StringRequest stringRequest2 = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    //Toast.makeText(CartActivity.this, response+ " lần 2", Toast.LENGTH_LONG).show();
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> prams = new HashMap<String, String>();


                    prams.put("token", MyToken.getToken(CartActivity.this).trim());

                    prams.put("idproduct", String.valueOf(MyCart.itemCartList.get(finalI).getIdproduct()));
                    // KIỂM TRA ĐÂY NỮA NÈ
                    prams.put("username", user.username);
                    prams.put("productname", MyCart.itemCartList.get(finalI).getProductname());
                    prams.put("gia", String.valueOf(MyCart.itemCartList.get(finalI).getGia()));
                    prams.put("tongchitiethoadon", String.valueOf(MyCart.itemCartList.get(finalI).getTonggia()));
                    prams.put("soluongmua", String.valueOf(MyCart.itemCartList.get(finalI).getSoluongmua()));



                    return prams;
                }

                @Override
                public Priority getPriority() {
                    return Priority.HIGH;
                }
            };
//                    RequestQueue requestQueue2 = Volley.newRequestQueue(CartActivity.this);
//                    requestQueue2.add(stringRequest2);

            stringRequest2.setRetryPolicy(new DefaultRetryPolicy(20 * 1000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

            RequestQueue requestQueue2 = Volley.newRequestQueue(CartActivity.this);
            requestQueue2.add(stringRequest2);

        }
    }

    void setBtn() {
        btnTiepTucMua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CartActivity.this, MainActivity.class));
            }
        });

        btnThanhToan.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                insertToHoaDon();
                insertToChiTietHoaDon();

                startActivity(new Intent(CartActivity.this, BillActivity.class));
                //startActivity(new Intent(ChiTietSanPhamActivity.moveContext, BillActivity.class));
                //startActivity(new Intent(CartActivity.this, BillActivity.class));
            }
        });
    }

    void AnhXa() {
        TongTienCart    = (TextView) findViewById(R.id.TongTienCart);
        btnThanhToan    = (Button) findViewById(R.id.btnThanhToan);
        btnTiepTucMua   = (Button) findViewById(R.id.btnTiepTucMua);
        recycleviewCart = (RecyclerView) findViewById(R.id.recycleviewCart);
    }

    // hàm này được gọi để reset lại tổng tiền cart, hàm này đọc lại mảng từ đầu
    // bởi vì mảng lúc này được gọi, khi trước đó đã có nhưng lệnh thay đổi mảng
    public static void TongTienCartUpdate() {
        for (int i = 0; i < MyCart.itemCartList.size(); i++) {
            TongTienCartUpdate += MyCart.itemCartList.get(i).getTonggia();
        }
        // tổng tiền cart này là một text view có cần phải để static hay ko ?
        TongTienCart.setText(String.valueOf(TongTienCartUpdate));
    }

//    String getIdHoaDon() {
//        String url = "http://192.168.1.104:81/Werservice/getIdHoaDon.php";
//
//        StringRequest stringRequest3 = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//                Toast.makeText(CartActivity.this, response, Toast.LENGTH_SHORT).show();
////                int real = Integer.parseInt(response) + 1;
////                idhoadon = String.valueOf(real);
//                idhoadon = response;
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//
//            }
//        }) {
//            @Override
//            public Priority getPriority() {
//                return Priority.HIGH;
//            }
//        };
//        RequestQueue requestQueue = Volley.newRequestQueue(CartActivity.this);
//        requestQueue.add(stringRequest3);
//
//        return idhoadon;
//    }
}