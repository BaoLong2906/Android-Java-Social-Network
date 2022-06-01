package com.example.blfood.Activity;


import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import androidx.annotation.Nullable;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.blfood.Connection.IPadress;
import com.example.blfood.Token.MyToken;
import com.example.blfood.R;
import com.example.blfood.VolleySingleton;
import com.example.blfood.Storage.user;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class FragmentHome extends Fragment {
    ImageView menuAnSangBtn, menuAnTruaBtn, menuAnToiBtn, menuGoldTime, btnGoToCart, avatarFragmentHome;
    Button btnTimKiemMonAn;
    EditText editTextFoodName;
    ViewFlipper ViewFliperQuangCao;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        AnhXa(view);
        editTextFoodName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editTextFoodName.getText().clear();
            }
        });
        //getUserInfor();
        setData();
        ActionViewFliper();
        GoToMenu();
        return view;
    }

    void AnhXa(View view) {
        menuAnSangBtn      = (ImageView) view.findViewById(R.id.menuAnSangBtn);
        menuAnTruaBtn      = (ImageView) view.findViewById(R.id.menuAnTruaBtn);
        menuAnToiBtn       = (ImageView) view.findViewById(R.id.menuAnToiBtn);
        menuGoldTime       = (ImageView) view.findViewById(R.id.menuGoldTimeBtn);
        btnGoToCart        = (ImageView) view.findViewById(R.id.btnGoToCart);
        btnTimKiemMonAn    = (Button) view.findViewById(R.id.btnTimKiemMonAn);
        editTextFoodName   = (EditText) view.findViewById(R.id.editTextFoodName);
        avatarFragmentHome = (ImageView) view.findViewById(R.id.avatarFragmentHome);
        ViewFliperQuangCao = (ViewFlipper) view.findViewById(R.id.ViewFliperQuangCao);
    }

    void setData() {
        String ofUrl = IPadress.ip + "Werservice/images/" + user.avatarURL;
        Picasso.with(getActivity()).load(ofUrl).into(avatarFragmentHome);
    }

    void ActionViewFliper() {
        Animation in = AnimationUtils.loadAnimation(getActivity(), android.R.anim.slide_in_left);
        Animation out = AnimationUtils.loadAnimation(getActivity(), android.R.anim.slide_out_right);

        ViewFliperQuangCao.setInAnimation(in);
        ViewFliperQuangCao.setOutAnimation(out);

        ViewFliperQuangCao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ViewFliperQuangCao.showNext();
            }
        });
    }

    public void GoToMenu () {
        menuAnSangBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().startActivity(new Intent(getActivity(), MenuAnSangActivity.class));
            }
        });
        menuAnTruaBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        menuAnToiBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        menuGoldTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        btnGoToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().startActivity(new Intent(getActivity(), CartActivity.class));
            }
        });
        btnTimKiemMonAn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), FindFoodAsNameActivity.class);
                intent.putExtra("FoodName", editTextFoodName.getText().toString().trim());
                startActivity(intent);
            }
        });
    }

    void getUserInfor() {
        String url = IPadress.ip + "Werservice/SessionID.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                // và nhận về id user và username để lưu vào class User

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray   = jsonObject.getJSONArray("userInfor");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        // json object này để duyệt từng object trong mảng
                        JSONObject jsonObjectByRow = jsonArray.getJSONObject(i);
                        int iduser      = Integer.parseInt(jsonObjectByRow.getString("iduser"));
                        String username = jsonObjectByRow.getString("username");
                        //String password = jsonObjectByRow.getString("password");
                        //String name     = jsonObjectByRow.getString("name");
                        //int age         = Integer.parseInt(jsonObjectByRow.getString("age"));
                        //int sex         = Integer.parseInt(jsonObjectByRow.getString("sex"));
                        String avatarURL = jsonObjectByRow.getString("avatarURL");

                        // sữ dụng để set tên và set avatar
                        user.iduser     = iduser;
                        user.username   = username;
                        user.avatarURL  = avatarURL;
                        Toast.makeText(getActivity(), user.avatarURL, Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), "lỗi", Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("token", MyToken.getToken(getActivity()));
                return params;
            }

            @Override
            public Priority getPriority() {
                return  Priority.HIGH;
            }
        };
        VolleySingleton.getInstance(getActivity()).getRequestQueue().add(stringRequest);
    }
}