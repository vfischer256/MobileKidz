package com.onemobilekidz.mobilekidz.model;

import android.text.format.DateFormat;
import android.util.Log;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.firebase.client.Firebase;
import com.firebase.client.Query;
import com.onemobilekidz.mobilekidz.FirebaseListJoiner;

import java.util.HashMap;
import java.util.Map;

//@JsonIgnoreProperties(ignoreUnknown = true)
public class InBabysittingRequestsModel implements FirebaseListJoiner {

    private static final String LOG = "BSRListAdapter";
    String id;
    String job_start_time;
    String job_end_time;
    String requestor;
    UserModel user;


    // Required default constructor for Firebase object mapping
    @SuppressWarnings("unused")
    private InBabysittingRequestsModel() {
    }

    public Map<Query, String> joinPaths(Firebase path) {
        Map<Query, String> paths = new HashMap<>();
        paths.put(path.getRoot().child("users").child(requestor), "user");
        return paths;
    }

    public String getId() {
        return id;
    }


    public String getRequestor() {
        return requestor;
    }


    public String getJob_start_time() {
        return job_start_time;
    }

    public String getJob_end_time() {
        return job_end_time;
    }


    public String getRequestorName() {
        return user == null ? null : user.getDisplayName();
    }


}
