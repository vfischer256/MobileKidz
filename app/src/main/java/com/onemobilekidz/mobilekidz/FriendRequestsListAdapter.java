package com.onemobilekidz.mobilekidz;

import android.app.Activity;
import android.graphics.Color;
import android.view.View;
import android.widget.TextView;

import com.firebase.client.Query;
import com.onemobilekidz.mobilekidz.model.FriendRequestsModel;

/**
 * @author greg
 * @since 6/21/13
 * <p/>
 * This class is an example of how to use FirebaseListAdapter. It uses the <code>Chat</code> class to encapsulate the
 * data for each individual chat message
 */
public class FriendRequestsListAdapter extends FirebaseListAdapter<FriendRequestsModel> {
    public FriendRequestsListAdapter(Query ref, Activity activity, int layout) {
        super(ref, FriendRequestsModel.class, layout, activity);

    }

    /**
     * Bind an instance of the <code>Chat</code> class to our view. This method is called by <code>FirebaseListAdapter</code>
     * when there is a data change, and we are given an instance of a View that corresponds to the layout that we passed
     * to the constructor, as well as a single <code>Chat</code> instance that represents the current data to bind.
     *
     * @param view              A view instance corresponding to the layout we passed to the constructor.
     * @param friendRequestsObj An instance representing the current state of a chat message
     */
    @Override
    protected void populateView(View view, FriendRequestsModel friendRequestsObj) {
// Map a Chat object to an entry in our listview
        //   String recipientId = (String)friendRequestsObj.getSent_to().toArray()[0];

        String recipientId = friendRequestsObj.getId();
        if (friendRequestsObj.getUser() != null) {
            recipientId = friendRequestsObj.getUser().getEmail();
        }
        TextView recipientIdText = (TextView) view.findViewById(R.id.friendRequestName);
        recipientIdText.setText(recipientId + ": ");
    }
}