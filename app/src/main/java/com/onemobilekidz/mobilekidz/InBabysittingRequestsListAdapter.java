package com.onemobilekidz.mobilekidz;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.View;

import com.firebase.client.Query;
import com.onemobilekidz.mobilekidz.model.InBabysittingRequestsModel;

/**
 * /**
 * Created by vfischer on 3/16/15.
 * <p/>
 * <p/>
 * This class is an example of how to use FirebaseListAdapter. It uses the <code>Chat</code> class to encapsulate the
 * data for each individual chat message
 */
public class InBabysittingRequestsListAdapter extends FirebaseListAdapter<InBabysittingRequestsModel> {

    private static final String LOG = "BSRListAdapter";
    private static final String FIREBASE_URL = "https://crackling-heat-9656.firebaseio.com/";

    public InBabysittingRequestsListAdapter(Context context, Query ref, Activity activity, int layout) {
        super(context, ref, InBabysittingRequestsModel.class, layout, activity);
        Log.v(LOG, "I'm here");
        System.out.println(InBabysittingRequestsModel.class);
    }

    /**
     * Bind an instance of the <code>FriendRequestsModel</code> class to our view. This method is called by <code>FirebaseListAdapter</code>
     * when there is a data change, and we are given an instance of a View that corresponds to the layout that we passed
     * to the constructor, as well as a single <code>FriendRequestsModel</code> instance that represents the current data to bind.
     *
     * @param view                  A view instance corresponding to the layout we passed to the constructor.
     * @param babysittingRequestObj An instance representing the current state of a chat message
     */


    @Override
    protected void populateView(View view, final InBabysittingRequestsModel babysittingRequestObj, final int i) {/*
        final String requestor;
        final String jobStartTime;
        final String jobEndTime;
        Log.v(LOG, "I'm here still");
        if ( babysittingRequestObj != null) {
            requestor = babysittingRequestObj.getRequestor();
            jobStartTime = babysittingRequestObj.getJobStartTime();
            jobEndTime = babysittingRequestObj.getJobEndTime();
            final TextView babysitterNameText = (TextView) view.findViewById(R.id.babySitterName);
            babysitterNameText.setText(requestor);


            Log.v(LOG, "requestor: " + babysittingRequestObj.getBabysitter().getDisplayName() + " jobstartTime: " + jobStartTime + " jobEndTime: " + jobEndTime);
        }
*/
    }


}