package com.onemobilekidz.mobilekidz.model;

import android.text.format.DateFormat;
import android.util.Log;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.firebase.client.Firebase;
import com.firebase.client.Query;
import com.onemobilekidz.mobilekidz.FirebaseListJoiner;

import java.util.HashMap;
import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
public class OutBabysittingRequestsModel implements FirebaseListJoiner {

    private static final String LOG = "OutBSRListAdapter";
    String id;

    public String getJob_start_time() {
        return job_start_time;
    }

    public String getJob_end_time() {
        return job_end_time;
    }

    String job_start_time;
    String job_end_time;
    String requestee;
    UserModel user;


    // Required default constructor for Firebase object mapping
    @SuppressWarnings("unused")
    private OutBabysittingRequestsModel() {
    }

    public Map<Query, String> joinPaths(Firebase path) {
        Map<Query, String> paths = new HashMap<>();
        if (getRequestee() != null)
            paths.put(path.getRoot().child("users").child(getRequestee()), "user");
        return paths;
    }

    public String getId() {
        return id;
    }


    public String getRequestee() {
        return requestee;
    }

    public String getRequesteeName() {
        return user == null ? null : user.getDisplayName();
    }


}
