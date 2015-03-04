package com.onemobilekidz.mobilekidz;



import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.ViewGroup;
import android.view.View;
import android.view.LayoutInflater;
import android.widget.Button;



public class FriendsLinksFragment extends Fragment implements View.OnClickListener {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_friends_links, container, false);

        Button listButton = (Button) view.findViewById(R.id.listButton);
        Button messageButton = (Button) view.findViewById(R.id.messageButton);
        Button friendRequestsButton = (Button) view.findViewById(R.id.friendRequestsButton);
        Button outOfNetworkFriendsButton = (Button) view.findViewById(R.id.outOfNetworkFriendsButton);

        listButton.setOnClickListener(this);
        messageButton.setOnClickListener(this);
        friendRequestsButton.setOnClickListener(this);
        outOfNetworkFriendsButton.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.listButton:
                intent = new Intent(getActivity(), Friends.class);
                startActivity(intent);
                break;
            case R.id.messageButton:
                intent = new Intent(getActivity(), Messages.class);
                startActivity(intent);
                break;
            case R.id.friendRequestsButton:
                intent = new Intent(getActivity(), FriendRequests.class);
                startActivity(intent);
                break;
            case R.id.outOfNetworkFriendsButton:
                intent = new Intent(getActivity(), OutOfNetWorkFriends.class);
                startActivity(intent);
                break;
        }
    }


}

