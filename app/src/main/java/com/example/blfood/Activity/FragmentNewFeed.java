package com.example.blfood.Activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.blfood.Adapter.NewFeedAdapter;
import com.example.blfood.Connection.IPadress;
import com.example.blfood.Model.NewFeedItem;
import com.example.blfood.R;
import com.example.blfood.VolleySingleton;
import com.example.blfood.Storage.user;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SuppressLint("ValidFragment")
public class FragmentNewFeed extends Fragment {
    ImageView avatarNewFeed, statusImage, statusChoseGallery, statusChoseCamera;
    EditText contentNewFeed;
    Button btnSeeStatusEdit, btnPost;
    ImageButton btnCancelStatus;
    LinearLayout linearlayoutNewFeed;

    List<NewFeedItem> newFeedItemList;
    //List<NewFeedAdapter.WasLiked> wasLikedList;
    RecyclerView RecycleViewNewFeed;
    NewFeedAdapter newFeedAdapter;
    LinearLayoutManager linearLayoutManager;

    public static final int REQUEST_CODE_CAMERA = 1;
    public static final int REQUEST_CODE_GALLERY = 0;
    public static final int PERMISSON_CODE_GALLERY = 1000;

    public static String base64String;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_newfeed, container, false);

        AnhXa(view);

        //getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        //fixbug.requestFocus();
        //showSoftKeyboard(fixbug);
        //getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        //getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
//        fixbug.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
//            @Override
//            public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
//
//            }
//        });

//        fixbug.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//            @Override
//            public void onFocusChange(View v, boolean hasFocus) {
//                Toast.makeText(getActivity(), "HELLO", Toast.LENGTH_LONG).show();
//            }
//        });

//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            fixbug.keyboardNavigationClusterSearch();
//        }
        contentNewFeed.setVisibility(View.GONE);
        linearlayoutNewFeed.setVisibility(View.GONE);
        RecycleViewNewFeed.setVisibility(View.VISIBLE);

        setAvatar();
        LoadData();

        btnSeeStatusEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                contentNewFeed.setVisibility(View.VISIBLE);
                linearlayoutNewFeed.setVisibility(View.VISIBLE);
                RecycleViewNewFeed.setVisibility(View.GONE);
                btnSeeStatusEdit.setVisibility(View.GONE);
                statusImage.setVisibility(View.GONE);
            }
        });

//        contentNewFeed.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//            @Override
//            public void onFocusChange(View v, boolean hasFocus) {
//                linearlayoutNewFeed.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
//                linearlayoutNewFeed.setVisibility(View.VISIBLE);
//                RecycleViewNewFeed.setVisibility(View.GONE);
//            }
//        });

//        contentNewFeed.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//
////                LinearLayout.LayoutParams params = linearlayoutNewFeed.getLayoutParams();
////                params.height =
//                linearlayoutNewFeed.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
//                linearlayoutNewFeed.setVisibility(View.VISIBLE);
//                // hiện to để hiện hiệu ứng
//            }
//        });

        btnCancelStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                contentNewFeed.setVisibility(View.GONE);
                linearlayoutNewFeed.setVisibility(View.GONE);
                btnSeeStatusEdit.setVisibility(View.VISIBLE);
                RecycleViewNewFeed.setVisibility(View.VISIBLE);
            }
        });

        statusChoseCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                startActivityForResult(intent, REQUEST_CODE_CAMERA);
            }
        });

        statusChoseGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (getActivity().checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
                        // nếu user chưa cấp quyền, thì xin quyền
                        String[] permissions = {Manifest.permission.READ_EXTERNAL_STORAGE};
                        requestPermissions(permissions, PERMISSON_CODE_GALLERY);
                    } else {
                        // nếu user trước đó đã cấp quyền
                        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                        startActivityForResult(intent, REQUEST_CODE_GALLERY);
                    }
                } else {
                    // nếu phiên bản andriod < 23 thì chỉ cần cấp quyền trong mainfests
                    Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                    startActivityForResult(intent, REQUEST_CODE_GALLERY);
                }
            }
        });

        setBtnPost();
        return view;
    }



    public void showSoftKeyboard(View view) {
        if (view.requestFocus()) {
            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT);
        }
    }

    // hàm trả về kết quả xin cấp quyền truy cập của user, hàm này sẽ được gọi nếu như user lần đầu cấp quyền
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case PERMISSON_CODE_GALLERY: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                    startActivityForResult(intent, REQUEST_CODE_GALLERY);
                }
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case REQUEST_CODE_CAMERA: if (resultCode == getActivity().RESULT_OK && data != null) {
                statusImage.setVisibility(View.VISIBLE);
                // lấy ảnh từ camera
                Bitmap bitmap = (Bitmap) data.getExtras().get("data");
                statusImage.setImageBitmap(bitmap);
                // convert ảnh bitmap sang base64 để gửi qua Volley đến Webservice sẽ decode chuổi base64
                // và image sẽ lưu vào folder ảnh trong Werservice
                BitmapToBase64(bitmap);
                break;
            }

            case REQUEST_CODE_GALLERY: if (resultCode == getActivity().RESULT_OK && data != null) {
                statusImage.setVisibility(View.VISIBLE);
                statusImage.setImageURI(data.getData());

                // convert URI sang ảnh bitmap
                Uri ImageCaptureUri = data.getData();
                Bitmap bitmap = null;
                if (ImageCaptureUri != null) {
                    try {
                        bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), ImageCaptureUri);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                // convert ảnh bitmap sang base64 để gửi qua Volley đến Webservice sẽ decode chuổi base64
                // và image sẽ lưu vào folder ảnh trong Werservice
                BitmapToBase64(bitmap);
                break;
            }
        }
    }

    String BitmapToBase64(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 50, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        base64String = Base64.encodeToString(byteArray, Base64.DEFAULT);
        return base64String;
    }

    void LoadData() {
        newFeedItemList = new ArrayList<NewFeedItem>();

        // lấy từ server các post status đổ vào mảng arrayList để đổ vào Recycleview
        String url = IPadress.ip + "Werservice/getStatus.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //Toast.makeText(getActivity(), response, Toast.LENGTH_LONG).show();
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("status");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        // json object này để duyệt từng object trong mảng
                        JSONObject jsonObjectByRow = jsonArray.getJSONObject(i);
                        int idstatus = Integer.parseInt(jsonObjectByRow.getString("idstatus"));
                        int iduser   = Integer.parseInt(jsonObjectByRow.getString("iduser"));
                        String avatarURL = jsonObjectByRow.getString("avatarURL");
                        String username  = jsonObjectByRow.getString("username");
                        String content   = jsonObjectByRow.getString("content");
                        int likecount = Integer.parseInt(jsonObjectByRow.getString("likecount"));
                        String imageurl  = jsonObjectByRow.getString("imageurl");

                        // ý tưởng, có thể thêm vào 1 if ở đây, để kiểm tra nếu username của json = với username của class user
                        // thì lấy ra và lưu và user.iduser
                        // đổ kết quả và mảng newFeedItemList
                        newFeedItemList.add(new NewFeedItem(idstatus, iduser, avatarURL, username, content,likecount, imageurl));
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
            public Priority getPriority() {
                return Priority.HIGH;
            }
        };
        VolleySingleton.getInstance(getActivity()).getRequestQueue().add(stringRequest);

        newFeedAdapter = new NewFeedAdapter(getActivity(), newFeedItemList);
        linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        RecycleViewNewFeed.setAdapter(newFeedAdapter);
        //RecycleViewNewFeed.setHasFixedSize(true);
        RecycleViewNewFeed.setLayoutManager(linearLayoutManager);

        // refresh
        //RecycleViewNewFeed.setVisibility(View.INVISIBLE);
        //RecycleViewNewFeed.setVisibility(View.VISIBLE);
        btnSeeStatusEdit.setVisibility(View.VISIBLE);
        RecycleViewNewFeed.setVisibility(View.VISIBLE);
    }


    void AnhXa(View view) {
        avatarNewFeed        = (ImageView) view.findViewById(R.id.avatarNewFeed);
        contentNewFeed       = (EditText) view.findViewById(R.id.contentNewFeed);
        statusImage          = (ImageView) view.findViewById(R.id.statusImage);
        statusChoseGallery   = (ImageView) view.findViewById(R.id.statusChoseGallery);
        statusChoseCamera    = (ImageView) view.findViewById(R.id.statusChoseCamera);
        btnCancelStatus      = (ImageButton) view.findViewById(R.id.btnCancelStatus);
        btnSeeStatusEdit     = (Button) view.findViewById(R.id.btnSeeStatusEdit);
        btnPost              = (Button) view.findViewById(R.id.btnPost);
        RecycleViewNewFeed   = (RecyclerView) view.findViewById(R.id.RecycleViewNewFeed);
        linearlayoutNewFeed  = (LinearLayout) view.findViewById(R.id.linearlayoutNewFeed);
    }

    void setAvatar() {
        String ofUrl = IPadress.ip + "Werservice/images/" + user.avatarURL;
        Picasso.with(getActivity()).load(ofUrl).into(avatarNewFeed);
    }

    synchronized void setBtnPost() {
        btnPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // lấy nội dung mà người dùng nhập vào
                final String takeContent = contentNewFeed.getText().toString().trim();
                final long timenow = System.currentTimeMillis();

                //newFeedItemList.add(new NewFeedItem(user.avatarURL, user.username, takeContent, 0));
                // refresh lại recycleview
//                RecycleViewNewFeed.setAdapter(newFeedAdapter);
//                RecycleViewNewFeed.setLayoutManager(linearLayoutManager);

                // insert vào bảng status
                String url1 = IPadress.ip + "Werservice/insertToStatus.php";
                StringRequest stringRequest1 = new StringRequest(Request.Method.POST, url1, new Response.Listener<String>() {
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
                        // idstatus lúc insert mysql tự thêm vào
                        params.put("iduser", String.valueOf(user.iduser));
                        params.put("username", user.username);
                        params.put("contentNewFeed", takeContent);
                        params.put("likecount", "0");
                        // có thể lấy thời gian tại server
                        params.put("timenow", String.valueOf(timenow));
                        //String filename = "idstatus"+ user.username + ".png";
                        params.put("base64String", base64String);
                        return params;
                    }

                    @Override
                    public Priority getPriority() {
                        return Priority.HIGH;
                    }
                };
                VolleySingleton.getInstance(getActivity()).getRequestQueue().add(stringRequest1);
                LoadData();
            }
        });
    }
}
