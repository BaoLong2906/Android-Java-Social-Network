package com.example.blfood.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.example.blfood.Activity.FragmentHome;
import com.example.blfood.Activity.FragmentMyOrders;
import com.example.blfood.Activity.FragmentNewFeed;
import com.example.blfood.Activity.FragmentUserProfile;
import com.example.blfood.R;

public class MainActivity extends AppCompatActivity {
    ImageView homebtn, myorderbtn, earthbtn, profilebtn;
    FrameLayout frameContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent = getIntent();

        AnhXa();

        // vào trước tiên là fragmentHome sẽ hiện trước
        homebtn.setImageResource(R.drawable.iconhomeon);

        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameContent, new FragmentHome());
        fragmentTransaction.commit();
    }

    public void AnhXa() {
        frameContent = (FrameLayout) findViewById(R.id.frameContent);
        homebtn      = (ImageView) findViewById(R.id.homebtn);
        myorderbtn   = (ImageView) findViewById(R.id.myorderbtn);
        earthbtn     = (ImageView) findViewById(R.id.earthbtn);
        profilebtn   = (ImageView) findViewById(R.id.profilebtn);
    }

    public void ChangeFragment(View view) {
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        // các fragmentHome, fragmentMyOrder được extends từ fragment
        // một ví dụ về ứng dụng của casting trong lập trình
        // replace chỉ nhận một đầu vào, tuy nhiên có nhiều case có thể xảy ra, vậy với casting
        // chúng ta đã ép kiểu các case đó thành 1 biến của class cha
        // sau đó chỉ việc nhét class cha này vào replace() là được
        Fragment whichFragment = null;
        switch (view.getId()) {
            case R.id.homebtn:
                homebtn.setImageResource(R.drawable.iconhomeon);
                myorderbtn.setImageResource(R.drawable.iconmyorderoff);
                earthbtn.setImageResource(R.drawable.iconearthoff);
                profilebtn.setImageResource(R.drawable.profileoff);
                whichFragment = new FragmentHome();
                break;
            case R.id.myorderbtn:
                myorderbtn.setImageResource(R.drawable.iconmyorderon);
                homebtn.setImageResource(R.drawable.iconhomeoff);
                earthbtn.setImageResource(R.drawable.iconearthoff);
                profilebtn.setImageResource(R.drawable.profileoff);
                whichFragment = new FragmentMyOrders();
                break;
            case R.id.earthbtn:
                earthbtn.setImageResource(R.drawable.iconearthon);
                homebtn.setImageResource(R.drawable.iconhomeoff);
                myorderbtn.setImageResource(R.drawable.iconmyorderoff);
                profilebtn.setImageResource(R.drawable.profileoff);
                whichFragment = new FragmentNewFeed();
                break;
            case  R.id.profilebtn:
                profilebtn.setImageResource(R.drawable.profileon);
                homebtn.setImageResource(R.drawable.iconhomeoff);
                myorderbtn.setImageResource(R.drawable.iconmyorderoff);
                earthbtn.setImageResource(R.drawable.iconearthoff);
                whichFragment = new FragmentUserProfile();
                break;

        }
        fragmentTransaction.replace(R.id.frameContent, whichFragment, "refresh");
        fragmentTransaction.commit();

    }
}