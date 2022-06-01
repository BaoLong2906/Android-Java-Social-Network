package com.example.blfood.Activity;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.blfood.Adapter.HistoryOrderAdapter;
import com.example.blfood.Connection.IPadress;
import com.example.blfood.Model.HistoryOrderItem;
import com.example.blfood.R;
import com.example.blfood.Storage.user;
import com.example.blfood.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FragmentMyOrders extends Fragment {
    RecyclerView RecycleViewHistoryOrder;
    List<HistoryOrderItem> historyOrderItemList;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_myorders, container, false);
//        Fragment frg = null;
//        frg = getFragmentManager().findFragmentByTag("refresh");
//        final FragmentTransaction ft = getFragmentManager().beginTransaction();
//        ft.detach(frg);
//        ft.attach(frg);
//        ft.commit();

        AnhXa(view);
        // tạo arraylist
        historyOrderItemList = new ArrayList<HistoryOrderItem>();
        // gọi hàm lấy dữ liệu đổ vào arraylist
        getHoaDonAtUsername(getActivity());

        HistoryOrderAdapter historyOrderAdapter = new HistoryOrderAdapter(getActivity(), historyOrderItemList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);

        RecycleViewHistoryOrder.setAdapter(historyOrderAdapter);
        RecycleViewHistoryOrder.setLayoutManager(linearLayoutManager);

        return view;
    }

    void AnhXa(View view) {
        RecycleViewHistoryOrder = (RecyclerView) view.findViewById(R.id.RecycleViewHistoryOrder);
    };

     void getHoaDonAtUsername(Context context) {
        String url = IPadress.ip + "Werservice/getHoaDonAtUsername.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //Toast.makeText(getActivity(), response, Toast.LENGTH_LONG).show();
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("HoaDon");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        // json object này để duyệt từng object trong mảng
                        JSONObject jsonObjectByRow = jsonArray.getJSONObject(i);
                        int idhoadon = Integer.parseInt(jsonObjectByRow.getString("idhoadon"));
                        String ngaymua = jsonObjectByRow.getString("ngaymua");
                        int tonghoadon = Integer.parseInt(jsonObjectByRow.getString("tonghoadon"));

                        // đổ dữ liệu từ json và arraylist
                        historyOrderItemList.add(new HistoryOrderItem(idhoadon, ngaymua, tonghoadon));
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
                params.put("username", user.username);
                return params;
            }

            @Override
            public Priority getPriority() {
                return  Priority.HIGH;
            }
        };
        VolleySingleton.getInstance(context).getRequestQueue().add(stringRequest);
    }
}
