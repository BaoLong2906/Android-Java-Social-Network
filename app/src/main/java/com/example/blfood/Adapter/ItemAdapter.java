package com.example.blfood.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

//import com.example.blfood.Model.ItemCart;
import com.example.blfood.Activity.CartActivity;
import com.example.blfood.Connection.IPadress;
import com.example.blfood.Model.ItemCart;
import com.example.blfood.Storage.MyCart;
import com.example.blfood.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ViewHolder> {
    Context context;
    List<ItemCart> itemCartList;

    public ItemAdapter(Context context, List<ItemCart> itemCartList) {
        this.context = context;
        this.itemCartList = itemCartList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.itemcart, parent, false);

        return new ItemAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        String ofUrl = IPadress.ip + "Werservice/images/" + itemCartList.get(position).getOfUrl();
        Picasso.with(context).load(ofUrl).into(holder.avatarCart);
        holder.productnameCart.setText(itemCartList.get(position).getProductname());
        holder.fieldSoLuong.setText(String.valueOf(itemCartList.get(position).getSoluongmua()));
        holder.priceOneProduct.setText(String.valueOf(itemCartList.get(position).getGia()));
        holder.priceTotalOfProduct.setText(String.valueOf(itemCartList.get(position).getTonggia()));

        holder.btnPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int soluongmua = 0;
                int tonggia = 0;
                for (int i = 0; i < MyCart.itemCartList.size(); i++) {
                    if (MyCart.itemCartList.get(i).getIdproduct() == itemCartList.get(position).getIdproduct()) {
                        soluongmua = itemCartList.get(position).getSoluongmua() + 1;
                        tonggia    = itemCartList.get(position).getGia() * soluongmua;

                        MyCart.itemCartList.get(i).setSoluongmua(soluongmua);
                        MyCart.itemCartList.get(i).setTonggia(tonggia);

                        // refresh lại field số lượng mua
                        holder.fieldSoLuong.setText(String.valueOf(itemCartList.get(position).getSoluongmua()));
                        // refresh lại tổng tiền sản phẩm theo số lượng mua
                        holder.priceTotalOfProduct.setText(String.valueOf(itemCartList.get(position).getTonggia()));
                        // update reset lại tổng tiền cart khi có thay đổi trong mảng cart
                        CartActivity.TongTienCartUpdate();
                        CartActivity.TongTienCartUpdate = 0;
                    }
                }
            }
        });

        holder.btnMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int soluongmua = 0;
                int tonggia    = 0;
                for (int i = 0; i < MyCart.itemCartList.size(); i++) {
                    if (MyCart.itemCartList.get(i).getIdproduct() == itemCartList.get(position).getIdproduct()) {
                        soluongmua = itemCartList.get(position).getSoluongmua() - 1;
                        if (soluongmua == 0) {
                            // refresh lại field số lượng mua
                            holder.fieldSoLuong.setText(String.valueOf(itemCartList.get(position).getSoluongmua()));
                            // refresh lại tổng tiền sản phẩm theo số lượng mua
                            holder.priceTotalOfProduct.setText(String.valueOf(itemCartList.get(position).getTonggia()));

                            Toast.makeText(context.getApplicationContext(), String.valueOf(position), Toast.LENGTH_SHORT).show();

                            MyCart.itemCartList.remove(position);

                            // update reset lại tổng tiền cart khi có thay đổi trong mảng cart
                            CartActivity.TongTienCartUpdate();
                            CartActivity.TongTienCartUpdate = 0;

                            CartActivity.itemAdapter.notifyDataSetChanged();

                        } else {
                            tonggia    = itemCartList.get(position).getGia() * soluongmua;

                            MyCart.itemCartList.get(i).setSoluongmua(soluongmua);
                            MyCart.itemCartList.get(i).setTonggia(tonggia);

                            // refresh lại field số lượng mua
                            holder.fieldSoLuong.setText(String.valueOf(itemCartList.get(position).getSoluongmua()));
                            // refresh lại tổng tiền sản phẩm theo số lượng mua
                            holder.priceTotalOfProduct.setText(String.valueOf(itemCartList.get(position).getTonggia()));
                            // update reset lại tổng tiền cart khi có thay đổi trong mảng cart
                            CartActivity.TongTienCartUpdate();
                            CartActivity.TongTienCartUpdate = 0;
                        }
                    }

                }
            }
        });

        holder.btnRemoveItemCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                MyCart.itemCartList.remove(position);

                // update reset lại tổng tiền cart khi có thay đổi trong mảng cart
                CartActivity.TongTienCartUpdate();
                CartActivity.TongTienCartUpdate = 0;

                CartActivity.itemAdapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return itemCartList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView avatarCart, btnRemoveItemCart;
        TextView productnameCart, priceOneProduct, priceTotalOfProduct;
        EditText fieldSoLuong;
        Button btnPlus, btnMinus;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            avatarCart          = (ImageView) itemView.findViewById(R.id.avatarCart);
            productnameCart     = (TextView) itemView.findViewById(R.id.productnameCart);
            priceOneProduct     = (TextView) itemView.findViewById(R.id.priceOneProduct);
            priceTotalOfProduct = (TextView) itemView.findViewById(R.id.priceTotalOfProduct);
            fieldSoLuong        = (EditText) itemView.findViewById(R.id.fieldSoLuong);
            btnRemoveItemCart   = (ImageView) itemView.findViewById(R.id.btnRemoveItemCart);
            btnMinus            = (Button)   itemView.findViewById(R.id.btnMinus);
            btnPlus             = (Button)   itemView.findViewById(R.id.btnPlus);
        }
    }
}
