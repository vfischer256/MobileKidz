package com.onemobilekidz.mobilekidz;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.firebase.client.Query;
import com.onemobilekidz.mobilekidz.model.FriendsModel;
import com.onemobilekidz.mobilekidz.model.MessagesModel;
import com.onemobilekidz.mobilekidz.model.UserModel;

import org.w3c.dom.Text;

/**
 * Created by vfischer on 3/30/15.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class MessageListAdapter extends FirebaseListAdapter<MessagesModel> {


    private static final String LOG = "MListAdapter";

    private static final String FIREBASE_URL = "https://crackling-heat-9656.firebaseio.com/";


    private Context mContext;


    public MessageListAdapter(Context context, Query ref, Activity activity, int layout) {
        super(context, ref, MessagesModel.class, layout, activity);
        this.mContext = context;
    }

    /**
     * Bind an instance of the <code>FriendRequestsModel</code> class to our view. This method is called by <code>FirebaseListAdapter</code>
     * when there is a data change, and we are given an instance of a View that corresponds to the layout that we passed
     * to the constructor, as well as a single <code>FriendRequestsModel</code> instance that represents the current data to bind.
     *
     * @param view       A view instance corresponding to the layout we passed to the constructor.
     * @param messageObj An instance representing the current state of a chat message
     */
    @Override
    protected void populateView(View view, final MessagesModel messageObj, final int i) {
        final TextView friendIdText = (TextView) view.findViewById(R.id.messageSender);
        TextView messageIdText = (TextView) view.findViewById(R.id.message);

        if (messageObj.getFriend() != null) {

            String friendName = messageObj.getSenderName();
            friendIdText.setText("From: " + friendName);
            String message = messageObj.getMessage();
            messageIdText.setText("Message: " + message);

            Log.v(LOG, "This is the sender's name: " + friendName + " message: " + message);


        }


    }

}
