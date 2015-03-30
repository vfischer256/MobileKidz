package com.onemobilekidz.mobilekidz;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;
import com.onemobilekidz.mobilekidz.helper.DatabaseManager;
import com.onemobilekidz.mobilekidz.model.FriendRequestsModel;
import com.onemobilekidz.mobilekidz.model.FriendsModel;
import com.onemobilekidz.mobilekidz.model.UserModel;

/**
 * Created by vfischer on 3/16/15.
 */
public class FriendListAdapter extends FirebaseListAdapter<FriendsModel> {
    private static final String LOG = "FListAdapter";

    private static final String FIREBASE_URL = "https://crackling-heat-9656.firebaseio.com/";


    private String friendId;
    private String friendName;

    public FriendListAdapter(Query ref, Activity activity, int layout) {
        super(ref, FriendsModel.class, layout, activity);
    }

    /**
     * Bind an instance of the <code>FriendRequestsModel</code> class to our view. This method is called by <code>FirebaseListAdapter</code>
     * when there is a data change, and we are given an instance of a View that corresponds to the layout that we passed
     * to the constructor, as well as a single <code>FriendRequestsModel</code> instance that represents the current data to bind.
     *
     * @param view      A view instance corresponding to the layout we passed to the constructor.
     * @param friendObj An instance representing the current state of a chat message
     */
    @Override
    protected void populateView(View view, final FriendsModel friendObj, final int i) {
        final TextView friendIdText = (TextView) view.findViewById(R.id.friend_name);
        if (friendObj.getFriend() != null) {
            friendName = friendObj.getFriend().getDisplayName();
            friendIdText.setText(friendName);
        }
    }
}