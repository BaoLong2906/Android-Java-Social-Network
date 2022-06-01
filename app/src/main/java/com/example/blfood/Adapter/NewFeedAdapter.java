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

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.blfood.Activity.ChiTietSanPhamActivity;
import com.example.blfood.Activity.commentActivity;
import com.example.blfood.Connection.IPadress;
import com.example.blfood.Model.NewFeedItem;
import com.example.blfood.R;
import com.example.blfood.VolleySingleton;
import com.example.blfood.Storage.user;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NewFeedAdapter extends RecyclerView.Adapter<NewFeedAdapter.ViewHolder> {
    Context context;
    List<NewFeedItem> newFeedItemList;

    //static int wasLiked;
    static int[] isClick;
    List<WasLiked> wasLikedList;

    static int count = 0;

    public NewFeedAdapter(Context context, List<NewFeedItem> newFeedItemList) {
        this.context = context;
        this.newFeedItemList = newFeedItemList;
        this.wasLikedList = new ArrayList<>();

        //this.wasLikedList = wasLikedList;
        //this.isClick = new int[getItemCount()];
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.postitem, parent, false);
        return new NewFeedAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        String ofUrl = IPadress.ip + "Werservice/images/" + newFeedItemList.get(position).getAvatarURL();
        Picasso.with(context).load(ofUrl).into(holder.PostItemAvatar);

        String ofUrl2 = IPadress.ip + "Werservice/images/" + newFeedItemList.get(position).getImageurl();
        Picasso.with(context).load(ofUrl2).into(holder.PostItemImageStatus);

        holder.PostItemUsername.setText(newFeedItemList.get(position).getUsername());
        holder.PostitemContent.setText(newFeedItemList.get(position).getContentNewFeed());
        holder.PostItemLikeCount.setText(String.valueOf(newFeedItemList.get(position).getLikecount()));

        //int kq = PreCheckLikeStatusVer2(context, position);

        //Toast.makeText(context, newFeedItemList.get(position).getAvatarURL() +" "+ newFeedItemList.get(position).getUsername()+" "
        //        +newFeedItemList.get(position).getContentNewFeed()+" "+newFeedItemList.get(position).getLikecount(), Toast.LENGTH_LONG).show();


//        PreCheckLikeStatus(context, position);
//        int kq = isLike(position);
//        Toast.makeText(context, String.valueOf(kq), Toast.LENGTH_LONG).show();

        // kiểm tra database để xem nên set icon Like on hay off
        // gọi hàm kiểm tra trạng thái của like
///////////////////////////////////////////////////////////////////////////////////
//        final int[] kq = {PreCheckLikeStatus(context, position)};
//        if (kq[0] == 1) {
//            // đã like status này, kết quả lấy từ database
//
//            holder.PostItemLike.setImageResource(R.drawable.likeon);
//
//            // set chức năng hủy like cho button
//            holder.PostItemLike.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    // nếu isClick = 1, để lưu là user đã click hủy like
////                    if (isClick == 1) {
////
////                    } else if (isClick == 0 && kq[0] == 0){
////
////                    } else if (isClick == 0 && kq[0] == 1) {
////
////                    }
//                    wasLikedList.set(position, new WasLiked(position, 0));
//
//                    // đổi icon thành like-off
//                    holder.PostItemLike.setImageResource(R.drawable.likeoff);
//
//                    // giảm likecount
//                    int newLikeCount = Integer.valueOf(holder.PostItemLikeCount.getText().toString().trim()) - 1;
//                    holder.PostItemLikeCount.setText(newLikeCount);
//
//                    // cập nhật xóa like đó trong bảng like
//                    deleteLike(context, position);
//
//                    // cập nhật số lượng likecount mới trong bảng status
//                    insertNewLikeCount(context, position, newLikeCount);
//
//                    //checkLikeStatus(context, holder, position);
//
//                }
//            });
//
//        } else if (kq[0] == 0) {
//            // chưa like status này
//
//            wasLikedList.set(position, new WasLiked(position, 1));
//
//            // vẫn để icon like-off vì chưa biết được user sẽ like status này hay không
//
//            // set chức năng like cho button này
//            holder.PostItemLike.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    // yêu cầu: mỗi post mỗi acc like được 1 lần, nếu nhấn like lần 2 thì sẽ hủy like
//
//                    // tăng likecount
//                    int newLikeCount = Integer.valueOf(holder.PostItemLikeCount.getText().toString().trim()) + 1;
//                    holder.PostItemLikeCount.setText(newLikeCount);
//
//                    // insert vào bảng like
//                    insertToLike(context, holder, position);
//
//                    // cập nhật số lượng likecount mới trong bảng status
//                    insertNewLikeCount(context, position, newLikeCount);
//
//                    //int likecount = newFeedItemList.get(position).getLikecount() + 1;
//                    //checkLikeStatus(context, holder, position);
//
//                }
//            });
//        }

        //PreCheckLikeStatus(context, position);
        // test:
        //int check = PreCheckLikeStatus(context, position);
        //Toast.makeText(context, String.valueOf(check), Toast.LENGTH_LONG).show();


        PreCheckLikeStatusVer2(context, position, holder);


        // icon like-on là btnRemoveLike
        holder.PostItemRemoveLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //giảm likecount
                int newLikeCount;
                if (Integer.valueOf(holder.PostItemLikeCount.getText().toString().trim()) == 0) {
                    // trường hợp đặc biệt nếu status này chưa user nào like, thì đây là like đầu tiên của status
                    newLikeCount = 1;
                    holder.PostItemLikeCount.setText(String.valueOf(newLikeCount));

                } else {
                    newLikeCount = Integer.valueOf(holder.PostItemLikeCount.getText().toString().trim()) - 1;
                    holder.PostItemLikeCount.setText(String.valueOf(newLikeCount));
                }

                // cập nhật xóa like đó trong bảng like
                deleteLike(context, position);

                // cập nhật số lượng likecount mới trong bảng status
                insertNewLikeCount(context, position, newLikeCount);

                // hiển thị icon like - off, button lúc này trở thành btnLike
                holder.PostItemLike.setVisibility(View.VISIBLE);
                holder.PostItemRemoveLike.setVisibility(View.GONE);
            }
        });

        // icon like-off chính là btnLike
        holder.PostItemLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //tăng likecount
                int newLikeCount = Integer.valueOf(holder.PostItemLikeCount.getText().toString().trim()) + 1;
                holder.PostItemLikeCount.setText(String.valueOf(newLikeCount));

                // insert vào bảng like
                insertToLike(context, position);

                // cập nhật số lượng likecount mới trong bảng status
                insertNewLikeCount(context, position, newLikeCount);

                // hiển thị icon like - on, button lúc này trở thành btnRemoveLike
                holder.PostItemLike.setVisibility(View.GONE);
                holder.PostItemRemoveLike.setVisibility(View.VISIBLE);
            }
        });

        // btnComment
        holder.PostItemComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // send object
                Intent intent = new Intent(context, commentActivity.class);
                intent.putExtra("newFeedItemList", newFeedItemList.get(position));
                context.startActivity(intent);
            }
        });
    }

//    synchronized void PreCheckLikeStatusVer2(final Context context, final int pos) {
//        String url = IPadress.ip + "Werservice/preCheckLikeStatus.php";
//        StringRequest PrestringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//                Toast.makeText(context, "preCheck" + response, Toast.LENGTH_LONG).show();
//                // nếu server trả về là user trước chưa like status này
//                int kq = Integer.valueOf(response);
//
//                if (kq == 1) {
//
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
//                params.put("idstatus", String.valueOf(newFeedItemList.get(pos).getIdstatus()));
//                params.put("username", user.username);
//                return params;
//            }
//
//            @Override
//            public Priority getPriority() {
//                return Priority.HIGH;
//            }
//        };
//        VolleySingleton.getInstance(context).getRequestQueue().add(PrestringRequest);
//    }

/////////////////////////////////////////////////////////////////////////////////////////////////
//    int isLike(int pos) {
//        return wasLikedList.get(pos).getWasLiked();
//    }
//
//    synchronized int PreCheckLikeStatus(final Context context, final int pos) {
//        String url = IPadress.ip + "Werservice/preCheckLikeStatus.php";
//        StringRequest PrestringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//                Toast.makeText(context, "preCheck" + response, Toast.LENGTH_LONG).show();
//                // nếu server trả về là user trước chưa like status này
//                int kq = Integer.valueOf(response);
//                if (kq == 0) {
//                    // chưa like status này
//                    wasLikedList.add(new WasLiked(pos, 0));
//                    //isClick.[pos] = 0;
//                    Toast.makeText(context, "hihi" + String.valueOf(wasLikedList.get(pos).getWasLiked()), Toast.LENGTH_LONG).show();
//                }
//                if (kq == 1){
//                    // nếu server trả về là user trước đã like status này
//
//                    wasLikedList.add(new WasLiked(pos, 1));
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
//                params.put("idstatus", String.valueOf(newFeedItemList.get(pos).getIdstatus()));
//                params.put("username", user.username);
//                return params;
//            }
//
//            @Override
//            public Priority getPriority() {
//                return Priority.HIGH;
//            }
//        };
//        VolleySingleton.getInstance(context).getRequestQueue().add(PrestringRequest);
//        return wasLikedList.get(pos).getWasLiked();
//    }

    synchronized void checkLikeStatus(final Context context, final ViewHolder holder, final int pos) {
        String url = IPadress.ip + "Werservice/checkLikeStatus.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                // lấy ra số lượng likecount hiện tại
                int likecount = Integer.parseInt(holder.PostItemLikeCount.getText().toString().trim());
                // nếu server trả về là user trước chưa like status này
                if (response.equals("0")) {
                    // thì set lại icon like off thành like on
                    holder.PostItemLike.setImageResource(R.drawable.likeon);
                    // tăng tạm thời textview likecount lên thêm 1
                    likecount++;
                    holder.PostItemLikeCount.setText(String.valueOf(likecount));
                    // update lên server tăng likecount lên thêm 1
                    insertNewLikeCount(context, pos, likecount);
                    // insert vào bảng Like giá trị idstatus và username
                    insertToLike(context, pos);

                } else {
                    // nếu server trả về là user trước đã like status này
                    // thì set lại icon like on thành like off
                    holder.PostItemLike.setImageResource(R.drawable.likeoff);
                    // giữ nguyên số lượng like đã có
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("idstatus", String.valueOf(newFeedItemList.get(pos).getIdstatus()));
                params.put("username", user.username);
                return params;
            }

            @Override
            public Priority getPriority() {
                return Priority.HIGH;
            }
        };
        VolleySingleton.getInstance(context).getRequestQueue().add(stringRequest);
    }

    // update số lượng like
    synchronized void insertNewLikeCount(Context context, final int pos, final int likecount) {
        String url3 = IPadress.ip + "Werservice/updateLikeCount.php";
        StringRequest stringRequest3 = new StringRequest(Request.Method.POST, url3, new Response.Listener<String>() {
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
                // idstatus tại status đó gửi lên server
                params.put("idstatus", String.valueOf(newFeedItemList.get(pos).getIdstatus()));
                // số lượng likecount muốn update gửi lên server
                params.put("likecount", String.valueOf(likecount));
                return params;
            }

            @Override
            public Priority getPriority() {
                return Priority.HIGH;
            }
        };
        VolleySingleton.getInstance(context).getRequestQueue().add(stringRequest3);
    }

    // insert 1 row vào bảng like
    synchronized void insertToLike(Context context, final int pos) {
        String url6 = IPadress.ip+"/Werservice/insertToLike.php";
        StringRequest stringRequest6 = new StringRequest(Request.Method.POST, url6, new Response.Listener<String>() {
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
                params.put("idstatus", String.valueOf(newFeedItemList.get(pos).getIdstatus()));
                params.put("username", user.username);
                return params;
            }

            @Override
            public Priority getPriority() {
                return Priority.HIGH;
            }
        };
        VolleySingleton.getInstance(context).getRequestQueue().add(stringRequest6);
    }

    // hàm này xóa 1 row trong bảng like
    synchronized void deleteLike(final Context context, final int pos) {
        String url7 = IPadress.ip+"/Werservice/deleteLike.php";
        StringRequest stringRequest7 = new StringRequest(Request.Method.POST, url7, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(context, response, Toast.LENGTH_LONG).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("idstatus", String.valueOf(newFeedItemList.get(pos).getIdstatus()));
                params.put("username", user.username);
                return params;
            }

            @Override
            public Priority getPriority() {
                return Priority.HIGH;
            }
        };
        VolleySingleton.getInstance(context).getRequestQueue().add(stringRequest7);
    }

    @Override
    public int getItemCount() {
        return newFeedItemList.size();
    }

    String likeStatusToJsonString(List<WasLiked> wasLikedList) {
        String jsonString = new Gson().toJson(wasLikedList);
        return jsonString;
    }

    synchronized void PreCheckLikeStatusVer2(final Context context, final int pos, final ViewHolder holder) {
        String url = IPadress.ip + "Werservice/preCheckLikeStatusVer2.php";
        StringRequest PrestringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //Toast.makeText(context, response, Toast.LENGTH_LONG).show();

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("like");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        // json object này để duyệt từng object trong mảng
                        JSONObject jsonObjectByRow = jsonArray.getJSONObject(i);
                        int idstatus = Integer.parseInt(jsonObjectByRow.getString("idstatus"));
                        int iduser   = Integer.parseInt(jsonObjectByRow.getString("iduser"));
                        int likecount = Integer.parseInt(jsonObjectByRow.getString("likecount"));
                        String username  = jsonObjectByRow.getString("username");
                        int idlike = Integer.parseInt(jsonObjectByRow.getString("idlike"));

                        //wasLikedList.add(new WasLiked(idstatus, iduser, likecount, username, idlike));
                        //likeStatusToJsonString(wasLikedList);
                        if (i == pos) {
                            if (username.equals("empty") && idlike == 100) {
                                // nếu pre kiểm tra cho kết quả là chưa like
                                // thì set icon like - off, hidden icon like - on

                                holder.PostItemLike.setVisibility(View.VISIBLE);
                                holder.PostItemRemoveLike.setVisibility(View.GONE);

                            } else {
                                // nếu pre kiểm tra cho kết quả là đã like
                                // thì set icon like - on, hidden icon like - off

                                holder.PostItemLike.setVisibility(View.GONE);
                                holder.PostItemRemoveLike.setVisibility(View.VISIBLE);
                            }
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("idstatus", String.valueOf(newFeedItemList.get(pos).getIdstatus()));
                params.put("username", user.username);
                return params;
            }

            @Override
            public Priority getPriority() {
                return Priority.HIGH;
            }
        };
        VolleySingleton.getInstance(context).getRequestQueue().add(PrestringRequest);
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView PostItemAvatar, PostItemLike, PostItemRemoveLike, PostItemComment, PostItemImageStatus;
        TextView PostItemUsername, PostitemContent, PostItemLikeCount;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            PostItemUsername    = (TextView) itemView.findViewById(R.id.PostItemUsername);
            PostitemContent     = (TextView) itemView.findViewById(R.id.PostitemContent);
            PostItemAvatar      = (ImageView) itemView.findViewById(R.id.PostItemAvatar);
            PostItemImageStatus = (ImageView) itemView.findViewById(R.id.PostItemImageStatus);
            PostItemLike        = (ImageView) itemView.findViewById(R.id.PostItemLike);
            PostItemRemoveLike  = (ImageView) itemView.findViewById(R.id.PostItemRemoveLike);
            PostItemComment     = (ImageView) itemView.findViewById(R.id.PostItemComment);
            PostItemLikeCount   = (TextView)  itemView.findViewById(R.id.PostItemLikeCount);
        }
    }

    public class WasLiked {
        int idstatus;
        int iduser;
        int liekcount;
        String username;
        int idlike;

        public WasLiked(int idstatus, int iduser, int liekcount, String username, int idlike) {
            this.idstatus = idstatus;
            this.iduser = iduser;
            this.liekcount = liekcount;
            this.username = username;
            this.idlike = idlike;
        }

        public int getIdstatus() {
            return idstatus;
        }

        public void setIdstatus(int idstatus) {
            this.idstatus = idstatus;
        }

        public int getIduser() {
            return iduser;
        }

        public void setIduser(int iduser) {
            this.iduser = iduser;
        }
        
        public int getLiekcount() {
            return liekcount;
        }

        public void setLiekcount(int liekcount) {
            this.liekcount = liekcount;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public int getIdlike() {
            return idlike;
        }

        public void setIdlike(int idlike) {
            this.idlike = idlike;
        }
    }
}
