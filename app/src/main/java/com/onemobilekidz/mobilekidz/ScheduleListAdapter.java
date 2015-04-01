package com.onemobilekidz.mobilekidz;


/**
 * Created by vfischer on 3/31/15.
 */


import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.firebase.client.Query;
import com.onemobilekidz.mobilekidz.model.InBabysittingRequestsModel;
import com.onemobilekidz.mobilekidz.model.OutBabysittingRequestsModel;
import com.onemobilekidz.mobilekidz.model.ScheduleModel;

import org.w3c.dom.Text;

/**
 * Created by vfischer on 3/31/15.
 */

public class ScheduleListAdapter extends FirebaseListAdapter<ScheduleModel> {

    private static final String LOG = "SchedListAdapter";
    private static final String FIREBASE_URL = "https://crackling-heat-9656.firebaseio.com/";

    public ScheduleListAdapter(Context context, Query ref, Activity activity, int layout) {
        super(context, ref, ScheduleModel.class, layout, activity);
        Log.v(LOG, "I'm here");
    }

    /**
     * Bind an instance of the <code>FriendRequestsModel</code> class to our view. This method is called by <code>FirebaseListAdapter</code>
     * when there is a data change, and we are given an instance of a View that corresponds to the layout that we passed
     * to the constructor, as well as a single <code>FriendRequestsModel</code> instance that represents the current data to bind.
     *
     * @param view                  A view instance corresponding to the layout we passed to the constructor.
     * @param scheduleObj An instance representing the current state of a chat message
     */


    @Override
    protected void populateView(View view, final ScheduleModel scheduleObj, final int i) {
        final String requestor;
        final String requestorName;
        final String jobStartTime;
        final int duration;
        Log.v(LOG, "I'm here still");
        if (scheduleObj != null) {
            requestor = scheduleObj.getRequestor();
            requestorName = scheduleObj.getRequestorName();
            jobStartTime = scheduleObj.getJob_start_time();
            duration = scheduleObj.getDuration();
            final TextView babysitterNameText = (TextView) view.findViewById(R.id.babySitterName);
            babysitterNameText.setText(requestorName);

            final TextView jobStartTimeText = (TextView) view.findViewById(R.id.requestDate);
            jobStartTimeText.setText(jobStartTime);

            final TextView jobEndTimeText = (TextView) view.findViewById(R.id.requestEndTime);
            jobEndTimeText.setText(duration);

            Log.v(LOG, "requestor: " + requestor);
            Log.v(LOG, " requestorName: " + requestorName + " jobstartTime: " + jobStartTime + " jobEndTime: " + duration);


        }
    }

}