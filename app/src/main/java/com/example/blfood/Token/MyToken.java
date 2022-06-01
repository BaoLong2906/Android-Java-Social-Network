package com.example.blfood.Token;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.blfood.VolleySingleton;

import java.util.HashMap;
import java.util.Map;

import static android.content.Context.MODE_PRIVATE;

public class MyToken {
    public static SharedPreferences sharedPreferences;

    public static void saveToken(Context context, String token) {
        sharedPreferences = context.getSharedPreferences("saveSession",MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString("luuToken",token);
        editor.apply();
    }

    public static String getToken(Context context) {
        sharedPreferences = context.getSharedPreferences("saveSession",MODE_PRIVATE);
        return sharedPreferences.getString("luuToken","lỗi");
    }

    public static void removeByKey(Context context, String key) {
        sharedPreferences = context.getSharedPreferences("saveSession",MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(key);
    }

    public static void removeAll(Context context) {
        sharedPreferences = context.getSharedPreferences("saveSession",MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
    }
    //public static String GetDecodePayLoad(String jwt) {
    //    jwt = ".abc.abc.abc";
    //    String[] parts = jwt.split(".");

    //    byte[] PayloadAsByteArray = parts[1].getBytes(StandardCharsets.UTF_8);

    //    return  parts[1];
        //return new String(Base64.decode(PayloadAsByteArray, Base64.DEFAULT));
    //}

    // hàm này gửi token lên webservice
    public static void sendToken(final Context context, String url) {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
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
                params.put("token", MyToken.getToken(context).trim());

                return params;
            }

            @Override
            public Priority getPriority() {
                return Priority.HIGH;
            }
        };

        VolleySingleton.getInstance(context).getRequestQueue().add(stringRequest);
    }
}
