package com.example.blfood.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FindFriendAsNameAdapter extends RecyclerView.Adapter<FindFriendAsNameAdapter.ViewHolder> {
    Context context;
    List<NewFeedItem> newFeedItemList;

    static boolean resultIsFriend;
    static boolean resultIsRequester;

    public FindFriendAsNameAdapter(Context context, List<NewFeedItem> newFeedItemList) {
        this.context = context;
        this.newFeedItemList = newFeedItemList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.findfriendrow, parent, false);
        return new FindFriendAsNameAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        String ofUrl = IPadress.ip + "Werservice/images/" + newFeedItemList.get(position).getAvatarURL();
        Picasso.with(context).load(ofUrl).into(holder.FindFriendAvatar);

        if (!newFeedItemList.get(position).getUsername().equals(user.username)) {
            holder.FindFriendNameText.setText(newFeedItemList.get(position).getUsername());

            if (newFeedItemList.get(position).getIsfriend() == 0) {
                holder.isFriendText.setText("Chưa phải bạn bè, hãy gửi lời mời kết bạn");
            } else if (newFeedItemList.get(position).getIsfriend() == 1) {
                holder.isFriendText.setText("Đã là bạn bè");
            }
        } else {
            holder.isFriendText.setText("Đây là tài khoản của bạn");
        }

        holder.FindFriendAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ProfileActivity.class);
                intent.putExtra("profileuser", newFeedItemList.get(position));
                context.startActivity(intent);
            }
        });

//        boolean test = isFriend(newFeedItemList.get(position));
//        if(test == true) {
//            Toast.makeText(context, "ok", Toast.LENGTH_LONG).show();
//        } else {
//            Toast.makeText(context, "no", Toast.LENGTH_LONG).show();
//        }
//        // kiểm tra xem object có là requester hay không ?
//        boolean isExistRequester = isExistRequester(newFeedItemList.get(position));
//
//        holder.resultFindFriendName.setText(newFeedItemList.get(position).getUsername());
//        // trạng thái kết bạn
//        if (isFriend(newFeedItemList.get(position)) == true) {
//            // nếu đã kết bạn rồi
//            // hiển thị thông tin là đã kết bạn
//            holder.resultFindFriendAvatarIsFriend.setText("Đã kết bạn");
//            holder.resultFindFriendAvatarIsFriend.setVisibility(View.VISIBLE);
//            // không hiển thị button không xác nhận kết bạn
//            holder.btnResultRemoveRequest.setVisibility(View.INVISIBLE);
//            // không hiển thị button kết bạn
//            holder.btnResultFindFriend.setVisibility(View.INVISIBLE);
//
//        } else if (isExistRequester == true) {
//            // nếu chưa kết bạn
//            // hiển thị thông tin là chưa xác nhận kết bạn, vì có lời mời kết bạn nhưng chưa xác nhận
//            holder.resultFindFriendAvatarIsFriend.setText("Chưa xác nhận kết bạn");
//            // hiển thị button bên phải
//            holder.btnResultRemoveRequest.setVisibility(View.VISIBLE);
//            // hiển thị button bên trái, với text là xác nhận kết bạn
//            holder.btnResultFindFriend.setText("Xác nhận kết bạn");
//            holder.btnResultFindFriend.setVisibility(View.VISIBLE);
//
//            // code button xác nhận kết bạn, button bên trái
//            holder.btnResultFindFriend.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    agreeFriendRequest(newFeedItemList.get(position));
//
//                    // textview trạng thái là  đã kết bạn
//                    holder.resultFindFriendAvatarIsFriend.setText("Đã kết bạn");
//                    // button bên trái sẽ hidden, lần tìm sau nó sẽ là luôn hidden vì 2 user đã là bạn bè
//                    holder.btnResultFindFriend.setVisibility(View.INVISIBLE);
//                    // button bên phải sẽ hidden, lần tìm sau nó sẽ là luôn hidden vì 2 user đã là bạn bè
//                    holder.btnResultRemoveRequest.setVisibility(View.INVISIBLE);
//                }
//            });
//
//            // code button không xác nhận kết bạn, button bên phải
//            holder.btnResultRemoveRequest.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    RemoveFriendRequest(newFeedItemList.get(position));
//
//                    // texview trạng thái là không phải bạn bè
//                    // do mặc định layout là text không phải bạn bè rồi nên không cần setText nữa
//
//                    // button bên trái sẽ hidden, lần tìm sau nó sẽ là button kết bạn
//                    holder.btnResultFindFriend.setVisibility(View.INVISIBLE);
//                    // button bên phải sẽ hidden, lần tìm sau nó sẽ là luôn hidden
//                    holder.btnResultRemoveRequest.setVisibility(View.INVISIBLE);
//
//                }
//            });
//
//        } else if (isExistRequester == false) {
//            // nếu giữa 2 user không có yêu cầu kết bạn
//            // hiển thị thông tin kết bạn là null
//            holder.resultFindFriendAvatarIsFriend.setText(" ");
//            // không hiển thị button bên phải
//            holder.btnResultRemoveRequest.setVisibility(View.INVISIBLE);
//            // hiển thị button kết bạn, button bên trái, với text là kết bạn
//            holder.btnResultFindFriend.setText("Kết bạn");
//            holder.btnResultFindFriend.setVisibility(View.VISIBLE);
//
//            // code button kết bạn, button bên trái
//            holder.btnResultFindFriend.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    sendFriendRequest(newFeedItemList.get(position));
//                    // text trạng thái bạn bè, setText là đã gửi kết bạn
//                    holder.resultFindFriendAvatarIsFriend.setText("Đã gửi lời mời kết bạn");
//                    // button bên trái hidden, lần tìm sau button bên trái sẽ là button xác nhận bạn bè
//                    holder.btnResultFindFriend.setVisibility(View.INVISIBLE);
//                    // button bên phải hidden, lần tìm sau button bên phải sẽ là button từ chối xác nhận bà bè
//                    // button bên phải đã hidden
//                }
//            });
//        }

    }

    @Override
    public int getItemCount() {
        return newFeedItemList.size();
    }

    // hàm kiểm tra xem object có phải requester hay không ?
//    synchronized boolean isExistRequester(final NewFeedItem newFeedItem) {
//        String url = IPadress.ip + "Werservice/isExistRequester.php";
//        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//                try {
//                    JSONObject jsonObject = new JSONObject(response);
//                    String hasValue = jsonObject.getString("isExistRequester");
//                    int check = Integer.valueOf(hasValue);
//                    if (check == 0) {
//                        resultIsRequester = false;
//                        Toast.makeText(context, "không có requester", Toast.LENGTH_LONG).show();
//                    }
//                    if (check == 1) {
//                        resultIsRequester = true;
//                        Toast.makeText(context, "có requester", Toast.LENGTH_LONG).show();
//                    }
//
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//
//            }
//        }) {
//            @Override
//            protected Map<String, String> getParams() throws AuthFailureError {
//                Map<String, String> params = new HashMap<String, String>();
//                params.put("requester", newFeedItem.getUsername());
//                params.put("admirer", user.username);
//                return params;
//            }
//
//            @Override
//            public Priority getPriority() {
//                return Priority.LOW;
//            }
//        };
//        VolleySingleton.getInstance(context).getRequestQueue().add(stringRequest);
//        return resultIsRequester;
//    }

//    synchronized boolean isFriend(final NewFeedItem newFeedItem) {
//        String url = IPadress.ip + "Werservice/isFriend.php";
//        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//                //Toast.makeText(context, response, Toast.LENGTH_LONG).show();
//                try {
//                    JSONObject jsonObject = new JSONObject(response);
//                    String isfriend = jsonObject.getString("isfriend");
//
//                    int check = Integer.valueOf(isfriend);
//                    Toast.makeText(context, isfriend, Toast.LENGTH_SHORT).show();
//                    if (check == 1) {
//                        resultIsFriend = true;
//                        Toast.makeText(context, "dung roi", Toast.LENGTH_LONG).show();
//                    }
//                    if (check == 0) {
//                        resultIsFriend = false;
//                        Toast.makeText(context, "sai roi tai 0", Toast.LENGTH_LONG).show();
//                    }
//                    if (check == 100) {
//                        resultIsFriend = false;
//                        Toast.makeText(context, "sai roi tai empty", Toast.LENGTH_LONG).show();
//                    }
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//
//            }
//        }) {
//            @Override
//            protected Map<String, String> getParams() throws AuthFailureError {
//                Map<String, String> params = new HashMap<String, String>();
//                params.put("requester", newFeedItem.getUsername());
//                params.put("admirer", user.username);
//                return params;
//            }
//
//            @Override
//            public Priority getPriority() {
//                return Priority.LOW;
//            }
//        };
//        VolleySingleton.getInstance(context).getRequestQueue().add(stringRequest);
//        if (resultIsFriend == true) {
//            Toast.makeText(context, "this is true", Toast.LENGTH_LONG).show();
//        } else {
//            Toast.makeText(context, "this is not true", Toast.LENGTH_LONG).show();
//        }
//        return resultIsFriend;
//    }

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

    synchronized void sendFriendRequest(final NewFeedItem newFeedItem) {
        String url = IPadress.ip + "Werservice/sendFriendRequest.php";
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
        ImageView FindFriendAvatar;
        TextView FindFriendNameText, isFriendText;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            FindFriendAvatar         = (ImageView) itemView.findViewById(R.id.FindFriendAvatar);
            FindFriendNameText       = (TextView) itemView.findViewById(R.id.FindFriendNameText);
            isFriendText             = (TextView) itemView.findViewById(R.id.isFriendText);
        }
    }
}
