package com.example.blfood.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.blfood.Activity.ChiTietSanPhamActivity;
import com.example.blfood.Activity.ProfileActivity;
import com.example.blfood.Connection.IPadress;
import com.example.blfood.Model.NewFeedItem;
import com.example.blfood.R;
import com.example.blfood.Storage.user;
import com.example.blfood.VolleySingleton;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RequesterAdapter extends RecyclerView.Adapter<RequesterAdapter.ViewHolder> {
    Context context;
    List<NewFeedItem> newFeedItemList;

    public RequesterAdapter(Context context, List<NewFeedItem> newFeedItemList) {
        this.context = context;
        this.newFeedItemList = newFeedItemList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.resultfindfriendrow, parent, false);
        return new RequesterAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        String ofUrl = IPadress.ip + "Werservice/images/" + newFeedItemList.get(position).getAvatarURL();
        Picasso.with(context).load(ofUrl).into(holder.resultFindFriendAvatar);

        holder.resultFindFriendName.setText(newFeedItemList.get(position).getUsername());
        // hiển thị thông tin là chưa xác nhận kết bạn, vì có lời mời kết bạn nhưng chưa xác nhận
        holder.resultFindFriendAvatarIsFriend.setText("Chưa xác nhận kết bạn");
        // hiển thị button bên phải
        holder.btnResultRemoveRequest.setVisibility(View.VISIBLE);
        // hiển thị button bên trái, với text là xác nhận kết bạn
        holder.btnResultFindFriend.setText("Xác nhận kết bạn");
        holder.btnResultFindFriend.setVisibility(View.VISIBLE);

        // code button xác nhận kết bạn, button bên trái
        holder.btnResultFindFriend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                agreeFriendRequest(newFeedItemList.get(position));

                // textview trạng thái là  đã kết bạn
                holder.resultFindFriendAvatarIsFriend.setText("Đã kết bạn");
                // button bên trái sẽ hidden, lần tìm sau nó sẽ là luôn hidden vì 2 user đã là bạn bè
                holder.btnResultFindFriend.setVisibility(View.INVISIBLE);
                // button bên phải sẽ hidden, lần tìm sau nó sẽ là luôn hidden vì 2 user đã là bạn bè
                holder.btnResultRemoveRequest.setVisibility(View.INVISIBLE);
            }
        });

        // code button không xác nhận kết bạn, button bên phải
        holder.btnResultRemoveRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RemoveFriendRequest(newFeedItemList.get(position));

                // texview trạng thái là lời mời kết bạn đã xóa
                holder.resultFindFriendAvatarIsFriend.setText("Đã xóa lời mời kết bạn");
                // do mặc định layout là text không phải bạn bè rồi nên không cần setText nữa

                // button bên trái sẽ hidden, lần tìm sau nó sẽ là button kết bạn
                holder.btnResultFindFriend.setVisibility(View.INVISIBLE);
                // button bên phải sẽ hidden, lần tìm sau nó sẽ là luôn hidden
                holder.btnResultRemoveRequest.setVisibility(View.INVISIBLE);

            }
        });

        holder.resultFindFriendAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ProfileActivity.class);
                intent.putExtra("profileuser", newFeedItemList.get(position));
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return newFeedItemList.size();
    }

    // hàm chấp nhận lời mời kết bạn
    synchronized void agreeFriendRequest(final NewFeedItem newFeedItem) {
        String url = IPadress.ip + "Werservice/agreeFriendRequest.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("requester", newFeedItem.getUsername());
                params.put("admirer", user.username);
                return params;
            }

            @Override
            public Priority getPriority() {
                return Priority.HIGH;
            }
        };
        VolleySingleton.getInstance(context).getRequestQueue().add(stringRequest);
    }

    // hàm xóa lời mời kết bạn
    synchronized void RemoveFriendRequest(final NewFeedItem newFeedItem) {
        String url = IPadress.ip + "Werservice/RemoveFriendRequest.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("requester", newFeedItem.getUsername());
                params.put("admirer", user.username);
                return params;
            }

            @Override
            public Priority getPriority() {
                return Priority.HIGH;
            }
        };
        VolleySingleton.getInstance(context).getRequestQueue().add(stringRequest);
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView resultFindFriendAvatar;
        TextView resultFindFriendName, resultFindFriendAvatarIsFriend;
        Button btnResultFindFriend, btnResultRemoveRequest;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            resultFindFriendAvatar         = (ImageView) itemView.findViewById(R.id.resultFindFriendAvatar);
            resultFindFriendName           = (TextView) itemView.findViewById(R.id.resultFindFriendName);
            resultFindFriendAvatarIsFriend = (TextView) itemView.findViewById(R.id.resultFindFriendAvatarIsFriend);
            btnResultFindFriend            = (Button) itemView.findViewById(R.id.btnResultFindFriend);
            btnResultRemoveRequest         = (Button) itemView.findViewById(R.id.btnResultRemoveRequest);
        }
    }
}
