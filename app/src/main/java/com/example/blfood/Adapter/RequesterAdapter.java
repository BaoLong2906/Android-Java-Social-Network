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
        // hi???n th??? th??ng tin l?? ch??a x??c nh???n k???t b???n, v?? c?? l???i m???i k???t b???n nh??ng ch??a x??c nh???n
        holder.resultFindFriendAvatarIsFriend.setText("Ch??a x??c nh???n k???t b???n");
        // hi???n th??? button b??n ph???i
        holder.btnResultRemoveRequest.setVisibility(View.VISIBLE);
        // hi???n th??? button b??n tr??i, v???i text l?? x??c nh???n k???t b???n
        holder.btnResultFindFriend.setText("X??c nh???n k???t b???n");
        holder.btnResultFindFriend.setVisibility(View.VISIBLE);

        // code button x??c nh???n k???t b???n, button b??n tr??i
        holder.btnResultFindFriend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                agreeFriendRequest(newFeedItemList.get(position));

                // textview tr???ng th??i l??  ???? k???t b???n
                holder.resultFindFriendAvatarIsFriend.setText("???? k???t b???n");
                // button b??n tr??i s??? hidden, l???n t??m sau n?? s??? l?? lu??n hidden v?? 2 user ???? l?? b???n b??
                holder.btnResultFindFriend.setVisibility(View.INVISIBLE);
                // button b??n ph???i s??? hidden, l???n t??m sau n?? s??? l?? lu??n hidden v?? 2 user ???? l?? b???n b??
                holder.btnResultRemoveRequest.setVisibility(View.INVISIBLE);
            }
        });

        // code button kh??ng x??c nh???n k???t b???n, button b??n ph???i
        holder.btnResultRemoveRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RemoveFriendRequest(newFeedItemList.get(position));

                // texview tr???ng th??i l?? l???i m???i k???t b???n ???? x??a
                holder.resultFindFriendAvatarIsFriend.setText("???? x??a l???i m???i k???t b???n");
                // do m???c ?????nh layout l?? text kh??ng ph???i b???n b?? r???i n??n kh??ng c???n setText n???a

                // button b??n tr??i s??? hidden, l???n t??m sau n?? s??? l?? button k???t b???n
                holder.btnResultFindFriend.setVisibility(View.INVISIBLE);
                // button b??n ph???i s??? hidden, l???n t??m sau n?? s??? l?? lu??n hidden
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

    // h??m ch???p nh???n l???i m???i k???t b???n
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

    // h??m x??a l???i m???i k???t b???n
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
