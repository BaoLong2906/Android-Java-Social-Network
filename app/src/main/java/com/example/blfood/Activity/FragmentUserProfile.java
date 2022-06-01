package com.example.blfood.Activity;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.Nullable;

import com.example.blfood.Model.NewFeedItem;
import com.example.blfood.Storage.user;
import com.example.blfood.Token.MyCheckBoxStatus;
import com.example.blfood.Token.MyToken;
import com.example.blfood.R;

@SuppressLint("ValidFragment")
public class FragmentUserProfile extends Fragment {
    ImageView btnProfile, btnSearch, btnFriendrequest, btnChatActivity, btnLogout;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragmentprofile, container, false);

        AnhXa(view);
        setBtn();

        return  view;
    }

    @SuppressLint("WrongViewCast")
    void AnhXa(View view) {
        btnProfile = (ImageView) view.findViewById(R.id.btnProfile);
        btnSearch = (ImageView) view.findViewById(R.id.btnSearch);
        btnFriendrequest = (ImageView) view.findViewById(R.id.btnFriendrequest);
        btnChatActivity = (ImageView) view.findViewById(R.id.btnChatActivity);
        btnLogout = (ImageView) view.findViewById(R.id.btnLogout);
    }

    void setBtn() {
        // Đi đến trang cá nhân của chính user của client này
        btnProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NewFeedItem userProfile = new NewFeedItem(user.avatarURL, user.username, user.name);
                Intent intent = new Intent(getActivity(), ProfileActivity.class);
                intent.putExtra("profileuser", userProfile);
                getActivity().startActivity(intent);
            }
        });

        // Tìm kiếm bạn bè
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().startActivity(new Intent(getActivity(), FindFriendAsNameActivity.class));
            }
        });

        // Lời mời kết bạn
        btnFriendrequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().startActivity(new Intent(getActivity(), RequesterActivity.class));
            }
        });

        // đi đến ChatActivity
        btnChatActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().startActivity(new Intent(getActivity(), ChatMessengerActivity.class));
            }
        });

        // đăng xuất
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // xóa token ở client
                MyToken.saveToken(getActivity(), "empty");
                // xóa checkbox ở client
                MyCheckBoxStatus.saveCheckBoxStatus(getActivity(), "0");
                // xóa mảng cart

                // đi đến trang login
                getActivity().startActivity(new Intent(getActivity(), login.class));
            }
        });
    }

}
