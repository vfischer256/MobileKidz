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

    @Override
    protected void populateView(View view, final OutBabysittingRequestsModel babysittingRequestObj, final int i) {
        final String requestee;
        final String requesteeName;
        final String jobStartTime;
        final int duration;
        Log.v(LOG, "I'm here still");
        if (babysittingRequestObj != null) {
            requestee = babysittingRequestObj.getRequestee();
            requesteeName = babysittingRequestObj.getRequesteeName();
            jobStartTime = babysittingRequestObj.getJob_start_time();
            duration = babysittingRequestObj.getDuration();
            final TextView babysitterNameText = (TextView) view.findViewById(R.id.babySitterName);
            babysitterNameText.setText(requesteeName);

            final TextView jobStartTimeText = (TextView) view.findViewById(R.id.requestDate);
            jobStartTimeText.setText(jobStartTime);

            final TextView jobEndTimeText = (TextView) view.findViewById(R.id.requestEndTime);
            jobEndTimeText.setText(duration + " hours ");

            Log.v(LOG, "requestor: " + requestee);
            Log.v(LOG, " requestorName: " + requesteeName + " jobstartTime: " + jobStartTime + " duration: " + duration);
        }

    }


}