package com.example.blfood.Token;

import android.content.Context;
import android.content.SharedPreferences;

import static android.content.Context.MODE_PRIVATE;

public class MyCheckBoxStatus {
    public static SharedPreferences sharedPreferences;

    public static void saveCheckBoxStatus(Context context, String status) {
        sharedPreferences = context.getSharedPreferences("saveSession",MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString("checkBoxStatus", status);
        editor.commit();
    }

    public static String getCheckBoxStatus(Context context) {
        sharedPreferences = context.getSharedPreferences("saveSession",MODE_PRIVATE);
        return sharedPreferences.getString("checkBoxStatus","lỗi");
    }
}
