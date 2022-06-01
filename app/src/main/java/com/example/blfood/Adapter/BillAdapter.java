package com.example.blfood.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.blfood.Model.ItemCart;
import com.example.blfood.R;

import java.util.List;

public class BillAdapter extends RecyclerView.Adapter<BillAdapter.ViewHolder> {
    Context context;
    List<ItemCart> itemCartList;

    public BillAdapter(Context context, List<ItemCart> itemCartList) {
        this.context = context;
        this.itemCartList = itemCartList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.billrow, parent, false);
        return new BillAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.BillRowProductname.setText(itemCartList.get(position).getProductname());
        holder.BillRowPrice.setText(String.valueOf(itemCartList.get(position).getGia()));
        holder.BillRowSoLuong.setText(String.valueOf(itemCartList.get(position).getSoluongmua()));
        holder.BillRowTongTien.setText(String.valueOf(itemCartList.get(position).getTonggia()));
    }

    @Override
    public int getItemCount() {
        return itemCartList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView BillRowProductname, BillRowPrice, BillRowSoLuong, BillRowTongTien;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            BillRowProductname = (TextView) itemView.findViewById(R.id.BillRowProductname);
            BillRowPrice       = (TextView) itemView.findViewById(R.id.BillRowPrice);
            BillRowSoLuong     = (TextView) itemView.findViewById(R.id.BillRowSoLuong);
            BillRowTongTien    = (TextView) itemView.findViewById(R.id.BillRowTongTien);
        }
    }
}
