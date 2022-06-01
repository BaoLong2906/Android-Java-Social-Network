package com.example.blfood.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.blfood.Connection.IPadress;
import com.example.blfood.Model.NewFeedItem;
import com.example.blfood.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.ViewHolder> {
    Context context;
    List<NewFeedItem> newFeedItemList;

    public CommentAdapter(Context context, List<NewFeedItem> newFeedItemList) {
        this.context = context;
        this.newFeedItemList = newFeedItemList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.commentitem, parent, false);
        return new CommentAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String ofUrl = IPadress.ip + "Werservice/images/" + newFeedItemList.get(position).getAvatarURL();
        Picasso.with(context).load(ofUrl).into(holder.avatarComment);

        // getContentNewFeed() trong adapter này là return về nội dung comment không phải nội dung status, vì dùng lại code object
        // NewFeedItem của status
        holder.commentText.setText(newFeedItemList.get(position).getContentNewFeed().trim());
    }

    @Override
    public int getItemCount() {
        return newFeedItemList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView avatarComment;
        TextView commentText;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            avatarComment = (ImageView) itemView.findViewById(R.id.avatarComment);
            commentText   = (TextView) itemView.findViewById(R.id.commentText);
        }
    }
}
