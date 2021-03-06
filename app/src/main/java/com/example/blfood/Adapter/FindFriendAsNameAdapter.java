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
                holder.isFriendText.setText("Ch??a ph???i b???n b??, h??y g???i l???i m???i k???t b???n");
            } else if (newFeedItemList.get(position).getIsfriend() == 1) {
                holder.isFriendText.setText("???? l?? b???n b??");
            }
        } else {
            holder.isFriendText.setText("????y l?? t??i kho???n c???a b???n");
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
//        // ki???m tra xem object c?? l?? requester hay kh??ng ?
//        boolean isExistRequester = isExistRequester(newFeedItemList.get(position));
//
//        holder.resultFindFriendName.setText(newFeedItemList.get(position).getUsername());
//        // tr???ng th??i k???t b???n
//        if (isFriend(newFeedItemList.get(position)) == true) {
//            // n???u ???? k???t b???n r???i
//            // hi???n th??? th??ng tin l?? ???? k???t b???n
//            holder.resultFindFriendAvatarIsFriend.setText("???? k???t b???n");
//            holder.resultFindFriendAvatarIsFriend.setVisibility(View.VISIBLE);
//            // kh??ng hi???n th??? button kh??ng x??c nh???n k???t b???n
//            holder.btnResultRemoveRequest.setVisibility(View.INVISIBLE);
//            // kh??ng hi???n th??? button k???t b???n
//            holder.btnResultFindFriend.setVisibility(View.INVISIBLE);
//
//        } else if (isExistRequester == true) {
//            // n???u ch??a k???t b???n
//            // hi???n th??? th??ng tin l?? ch??a x??c nh???n k???t b???n, v?? c?? l???i m???i k???t b???n nh??ng ch??a x??c nh???n
//            holder.resultFindFriendAvatarIsFriend.setText("Ch??a x??c nh???n k???t b???n");
//            // hi???n th??? button b??n ph???i
//            holder.btnResultRemoveRequest.setVisibility(View.VISIBLE);
//            // hi???n th??? button b??n tr??i, v???i text l?? x??c nh???n k???t b???n
//            holder.btnResultFindFriend.setText("X??c nh???n k???t b???n");
//            holder.btnResultFindFriend.setVisibility(View.VISIBLE);
//
//            // code button x??c nh???n k???t b???n, button b??n tr??i
//            holder.btnResultFindFriend.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    agreeFriendRequest(newFeedItemList.get(position));
//
//                    // textview tr???ng th??i l??  ???? k???t b???n
//                    holder.resultFindFriendAvatarIsFriend.setText("???? k???t b???n");
//                    // button b??n tr??i s??? hidden, l???n t??m sau n?? s??? l?? lu??n hidden v?? 2 user ???? l?? b???n b??
//                    holder.btnResultFindFriend.setVisibility(View.INVISIBLE);
//                    // button b??n ph???i s??? hidden, l???n t??m sau n?? s??? l?? lu??n hidden v?? 2 user ???? l?? b???n b??
//                    holder.btnResultRemoveRequest.setVisibility(View.INVISIBLE);
//                }
//            });
//
//            // code button kh??ng x??c nh???n k???t b???n, button b??n ph???i
//            holder.btnResultRemoveRequest.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    RemoveFriendRequest(newFeedItemList.get(position));
//
//                    // texview tr???ng th??i l?? kh??ng ph???i b???n b??
//                    // do m???c ?????nh layout l?? text kh??ng ph???i b???n b?? r???i n??n kh??ng c???n setText n???a
//
//                    // button b??n tr??i s??? hidden, l???n t??m sau n?? s??? l?? button k???t b???n
//                    holder.btnResultFindFriend.setVisibility(View.INVISIBLE);
//                    // button b??n ph???i s??? hidden, l???n t??m sau n?? s??? l?? lu??n hidden
//                    holder.btnResultRemoveRequest.setVisibility(View.INVISIBLE);
//
//                }
//            });
//
//        } else if (isExistRequester == false) {
//            // n???u gi???a 2 user kh??ng c?? y??u c???u k???t b???n
//            // hi???n th??? th??ng tin k???t b???n l?? null
//            holder.resultFindFriendAvatarIsFriend.setText(" ");
//            // kh??ng hi???n th??? button b??n ph???i
//            holder.btnResultRemoveRequest.setVisibility(View.INVISIBLE);
//            // hi???n th??? button k???t b???n, button b??n tr??i, v???i text l?? k???t b???n
//            holder.btnResultFindFriend.setText("K???t b???n");
//            holder.btnResultFindFriend.setVisibility(View.VISIBLE);
//
//            // code button k???t b???n, button b??n tr??i
//            holder.btnResultFindFriend.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    sendFriendRequest(newFeedItemList.get(position));
//                    // text tr???ng th??i b???n b??, setText l?? ???? g???i k???t b???n
//                    holder.resultFindFriendAvatarIsFriend.setText("???? g???i l???i m???i k???t b???n");
//                    // button b??n tr??i hidden, l???n t??m sau button b??n tr??i s??? l?? button x??c nh???n b???n b??
//                    holder.btnResultFindFriend.setVisibility(View.INVISIBLE);
//                    // button b??n ph???i hidden, l???n t??m sau button b??n ph???i s??? l?? button t??? ch???i x??c nh???n b?? b??
//                    // button b??n ph???i ???? hidden
//                }
//            });
//        }

    }

    @Override
    public int getItemCount() {
        return newFeedItemList.size();
    }

    // h??m ki???m tra xem object c?? ph???i requester hay kh??ng ?
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
//                        Toast.makeText(context, "kh??ng c?? requester", Toast.LENGTH_LONG).show();
//                    }
//                    if (check == 1) {
//                        resultIsRequester = true;
//                        Toast.makeText(context, "c?? requester", Toast.LENGTH_LONG).show();
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
