package com.onemobilekidz.mobilekidz;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.firebase.client.Query;
import com.onemobilekidz.mobilekidz.model.FriendsModel;
import com.onemobilekidz.mobilekidz.model.UserModel;

/**
 * Created by vfischer on 3/16/15.
 */
public class FriendListAdapter extends FirebaseListAdapter<FriendsModel> {

    private static final String LOG = "FListAdapter";

    private static final String FIREBASE_URL = "https://crackling-heat-9656.firebaseio.com/";


    private Context mContext;


    public FriendListAdapter(Context context, Query ref, Activity activity, int layout) {
        super(context, ref, FriendsModel.class, layout, activity);
        this.mContext = context;
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
        final String friendName;
        final String friendId;
        if (friendObj.getFriend() != null) {
            friendName = friendObj.getFriend().getDisplayName();
            friendIdText.setText(friendName);
            friendId = friendObj.getId();
            ImageButton sendMessageButton = (ImageButton) view.findViewById(R.id.sendMessageButton);
            sendMessageButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View v) {

                    Log.v(LOG, "Sending message from " + UserModel.getCurrentUser().getEmail() + " to: " + friendId + " position: " + i);
                    Intent intent = new Intent(mContext, SendMessage.class);
                    intent.putExtra("friendId", friendId);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    mContext.startActivity(intent);
                }

            });

        }


    }


}