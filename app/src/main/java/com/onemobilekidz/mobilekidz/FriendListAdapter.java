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
    private Context mContext;


    public FriendListAdapter(Context context, Query ref, Activity activity, int layout) {
        super(context, ref, FriendsModel.class, layout, activity);
        this.mContext = context;
    }

    @Override
    protected void populateView(View view, final FriendsModel friendObj, final int i) {
        final TextView friendIdText = (TextView) view.findViewById(R.id.friend_name);
        final String friendName;
        final String friendId;
        if (friendObj.getFriend() != null) {
            friendName = friendObj.getFriend().getDisplayName();
            friendIdText.setText(friendName);
            friendId = friendObj.getId();

            try {
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
            } catch (Exception e) {
                Log.e(LOG, e.toString());
            }

        }
    }


}