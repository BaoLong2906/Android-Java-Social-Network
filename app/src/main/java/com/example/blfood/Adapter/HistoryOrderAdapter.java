package com.example.blfood.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.blfood.Activity.BillHistoryOrderActivity;
import com.example.blfood.Activity.ChiTietSanPhamActivity;
import com.example.blfood.Model.HistoryOrderItem;
import com.example.blfood.R;
import com.example.blfood.Storage.user;

import java.util.List;

public class HistoryOrderAdapter extends RecyclerView.Adapter<HistoryOrderAdapter.ViewHolder> {
    Context context;
    List<HistoryOrderItem> historyOrderItemList;

    public HistoryOrderAdapter(Context context, List<HistoryOrderItem> historyOrderItemList) {
        this.context = context;
        this.historyOrderItemList = historyOrderItemList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.historyorderitem, parent, false);
        return new HistoryOrderAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.historyorderitemIDhoadon.setText(String.valueOf(historyOrderItemList.get(position).getIdhoadon()));
        holder.historyorderitemTime.setText(historyOrderItemList.get(position).getNgaymua());
        holder.historyorderitemUsername.setText(user.username);
        holder.historyorderitemTongTien.setText(String.valueOf(historyOrderItemList.get(position).getTonghoadon()));
        holder.btnXemChiTietHoaDon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, BillHistoryOrderActivity.class);
                intent.putExtra("historyOrderItemList", historyOrderItemList.get(position));
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return historyOrderItemList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView historyorderitemIDhoadon, historyorderitemTime, historyorderitemUsername, historyorderitemTongTien;
        Button btnXemChiTietHoaDon;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            historyorderitemIDhoadon = (TextView) itemView.findViewById(R.id.historyorderitemIDhoadon);
            historyorderitemTime     = (TextView) itemView.findViewById(R.id.historyorderitemTime);
            historyorderitemUsername = (TextView) itemView.findViewById(R.id.historyorderitemUsername);
            historyorderitemTongTien = (TextView) itemView.findViewById(R.id.historyorderitemTongTien);
            btnXemChiTietHoaDon      = (Button)   itemView.findViewById(R.id.btnXemChiTietHoaDon);
        }
    }
}
