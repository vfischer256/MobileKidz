package com.onemobilekidz.mobilekidz;


import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.ViewGroup;
import android.view.View;
import android.view.LayoutInflater;
import android.widget.Button;
import android.widget.Toast;

import static com.onemobilekidz.mobilekidz.R.*;


/**
 * Created by vfischer on 3/2/15.
 */
public class BottomLinks extends Fragment implements View.OnClickListener {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        View view = inflater.inflate(layout.fragment_bottom_section, container, false);

        Button homeButton = (Button) view.findViewById(R.id.homeButton);
        Button friendsButton = (Button) view.findViewById(id.friendsButton);
        Button profileButton = (Button) view.findViewById(id.profileButton);

        homeButton.setOnClickListener(this);
        friendsButton.setOnClickListener(this);
        profileButton.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.homeButton:
                Toast.makeText(v.getContext(), "Im Clicked!", Toast.LENGTH_SHORT).show();
                intent = new Intent(getActivity(), Home.class);
                startActivity(intent);
                break;
            case R.id.friendsButton:
                Toast.makeText(v.getContext(), "Im Clicked!", Toast.LENGTH_SHORT).show();
                intent = new Intent(getActivity(), Friends.class);
                startActivity(intent);
                break;
            case id.profileButton:
                Toast.makeText(v.getContext(), "Im Clicked!", Toast.LENGTH_SHORT).show();
                intent = new Intent(getActivity(), Profile.class);
                startActivity(intent);
                break;
        }
    }


}

