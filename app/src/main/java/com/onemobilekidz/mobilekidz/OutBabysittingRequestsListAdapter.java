package com.onemobilekidz.mobilekidz;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.firebase.client.Query;
import com.onemobilekidz.mobilekidz.model.OutBabysittingRequestsModel;

import org.w3c.dom.Text;

/**
 * Created by vfischer on 3/31/15.
 */
public class OutBabysittingRequestsListAdapter extends FirebaseListAdapter<OutBabysittingRequestsModel> {

    private static final String LOG = "OutBSRListAdapter";
    private static final String FIREBASE_URL = "https://crackling-heat-9656.firebaseio.com/";

    public OutBabysittingRequestsListAdapter(Context context, Query ref, Activity activity, int layout) {
        super(context, ref, OutBabysittingRequestsModel.class, layout, activity);
        Log.v(LOG, "I'm here");
        System.out.println(OutBabysittingRequestsModel.class);
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
    protected void populateView(View view, final OutBabysittingRequestsModel babysittingRequestObj, final int i) {
        final String requestee;
        final String requesteeName;
        final String jobStartTime;
        final String jobEndTime;
        Log.v(LOG, "I'm here still");
        if ( babysittingRequestObj != null) {
            requestee = babysittingRequestObj.getRequestee();
            requesteeName = babysittingRequestObj.getRequesteeName();
            jobStartTime = babysittingRequestObj.getJob_start_time();
            jobEndTime = babysittingRequestObj.getJob_end_time();
            final TextView babysitterNameText = (TextView) view.findViewById(R.id.babySitterName);
            babysitterNameText.setText(requesteeName);

            final TextView jobStartTimeText = (TextView) view.findViewById(R.id.requestDate);
            jobStartTimeText.setText(jobStartTime);

            final TextView jobEndTimeText = (TextView) view.findViewById(R.id.requestEndTime);
            jobEndTimeText.setText(jobEndTime);

            Log.v(LOG, "requestor: " + requestee);
            Log.v(LOG, " requestorName: " + requesteeName + " jobstartTime: " + jobStartTime + " jobEndTime: " + jobEndTime);
        }

    }


}