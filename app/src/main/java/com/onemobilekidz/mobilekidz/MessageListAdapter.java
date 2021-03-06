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

    private Context mContext;


    public MessageListAdapter(Context context, Query ref, Activity activity, int layout) {
        super(context, ref, MessagesModel.class, layout, activity);
        this.mContext = context;
    }

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
