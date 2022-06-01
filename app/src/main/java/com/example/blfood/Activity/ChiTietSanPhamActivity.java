package com.example.blfood.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

//import com.example.blfood.Model.HorizontalItem;
import com.example.blfood.Connection.IPadress;
import com.example.blfood.Model.HorizontalItem;
import com.example.blfood.Storage.MyCart;
import com.example.blfood.R;
import com.squareup.picasso.Picasso;

public class ChiTietSanPhamActivity extends AppCompatActivity {
    HorizontalItem horizontalItem;
    ImageView avatarChiTietSanPham;
    TextView productnameChiTietSanPham, priceChiTietSanPham, motasanphamChiTietSanPham;
    Button btnGioHangChiTietSanPham;
    Context context;

    public static Context moveContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet_san_pham);
        // get object được truyền qua intent
        horizontalItem = (HorizontalItem) getIntent().getSerializableExtra("horizontalItemList");

        AnhXa();
        SetData(horizontalItem);

    }

    void AnhXa() {
        avatarChiTietSanPham      = (ImageView) findViewById(R.id.avatarChiTietSanPham);
        productnameChiTietSanPham = (TextView)  findViewById(R.id.productnameChiTietSanPham);
        priceChiTietSanPham       = (TextView)  findViewById(R.id.priceChiTietSanPham);
        motasanphamChiTietSanPham = (TextView)  findViewById(R.id.motasanphamChiTietSanPham);
        btnGioHangChiTietSanPham  = (Button)    findViewById(R.id.btnGioHangChiTietSanPham);
    }

    void SetData(final HorizontalItem horizontalItem) {
        String ofUrl = IPadress.ip + "Werservice/images/" + horizontalItem.getItemImageUrl();
        Picasso.with(ChiTietSanPhamActivity.this).load(ofUrl).into(avatarChiTietSanPham);
        productnameChiTietSanPham.setText(horizontalItem.getProductname());
        priceChiTietSanPham.setText(String.valueOf(horizontalItem.getPrice()));
        motasanphamChiTietSanPham.setText(horizontalItem.getMotasanpham());

        // đến trang giỏ hàng
        btnGioHangChiTietSanPham.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                MyCart.InsertItemCartList(horizontalItem);

                // lưu vào mảng cart để đổ ra recycleview của cart activity
                startActivity(new Intent(ChiTietSanPhamActivity.this, CartActivity.class));

            }
        });
    }

}