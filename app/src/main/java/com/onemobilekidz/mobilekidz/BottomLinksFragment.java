package com.onemobilekidz.mobilekidz;


import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.ViewGroup;
import android.view.View;
import android.view.LayoutInflater;
import android.widget.Button;

import static com.onemobilekidz.mobilekidz.R.*;


/**
 * Created by vfischer on 3/2/15.
 */
public class BottomLinksFragment extends Fragment implements View.OnClickListener {

    private static final String LOG = "BottomLinksFragment";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        View view = inflater.inflate(layout.fragment_bottom_section, container, false);

        Button homeButton = (Button) view.findViewById(R.id.homeButton);
        Button friendsButton = (Button) view.findViewById(id.friendsButton);
        Button babysittingRequestButton = (Button) view.findViewById(id.babysittingRequestButton);

        homeButton.setOnClickListener(this);
        friendsButton.setOnClickListener(this);
        babysittingRequestButton.setOnClickListener(this);

        Log.v(LOG, "Loading the Bottom Links");

        return view;
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.homeButton:
                intent = new Intent(getActivity(), Home.class);
                startActivity(intent);
                break;

            case R.id.friendsButton:
                intent = new Intent(getActivity(), Friends.class);
                startActivity(intent);
                break;

            case R.id.babysittingRequestButton:
                intent = new Intent(getActivity(), BabysittingRequests.class);
                startActivity(intent);
                break;
        }
    }
}

