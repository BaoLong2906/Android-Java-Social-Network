package com.example.blfood.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

//import com.example.blfood.Model.HorizontalItem;
import com.example.blfood.Activity.ChiTietSanPhamActivity;
import com.example.blfood.Connection.IPadress;
import com.example.blfood.Model.HorizontalItem;
import com.example.blfood.Storage.MyCart;
import com.example.blfood.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class HorizontalItemAdapter extends RecyclerView.Adapter<HorizontalItemAdapter.ViewHolder> {
    Context context;
    List<HorizontalItem> horizontalItemList;

    public HorizontalItemAdapter(Context context, List<HorizontalItem> horizontalItemList) {
        this.context = context;
        this.horizontalItemList = horizontalItemList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater =(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.itemansangmonanthinhhanh, parent, false);
        return new HorizontalItemAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        String ofUrl = IPadress.ip + "Werservice/images/" + horizontalItemList.get(position).getItemImageUrl();
        Picasso.with(context).load(ofUrl).into(holder.itemImage);
        holder.itemImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(context.getApplicationContext(), "Đã vào trang chi tiết", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(context, ChiTietSanPhamActivity.class);
                intent.putExtra("horizontalItemList", horizontalItemList.get(position));
                context.startActivity(intent);
            }
        });
        holder.nameItem.setText(horizontalItemList.get(position).getProductname());
        holder.giaTien.setText(String.valueOf(horizontalItemList.get(position).getPrice()));
        holder.btnInsertCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context.getApplicationContext(), "Đã thêm vào giỏ hàng", Toast.LENGTH_SHORT).show();

                MyCart.InsertItemCartList(horizontalItemList.get(position));
            }
        });

    }


    @Override
    public int getItemCount() {
        return horizontalItemList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView itemImage;
        TextView nameItem, giaTien;
        ImageView btnInsertCart;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemImage = itemView.findViewById(R.id.itemImage);
            nameItem  = itemView.findViewById(R.id.nameItem);
            giaTien   = itemView.findViewById(R.id.giaTien);
            btnInsertCart = itemView.findViewById(R.id.btnInsertCart);
        }
    }
}
