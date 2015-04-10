package com.onemobilekidz.mobilekidz.model;

import com.firebase.client.Firebase;
import com.firebase.client.Query;
import com.onemobilekidz.mobilekidz.FirebaseListJoiner;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by vfischer on 4/1/15.
 */
public class ScheduleModel implements FirebaseListJoiner {

    private static final String LOG = "Schedule";
    String id;
    int duration;
    String requestor;
    UserModel user;

    public String getJob_start_time() {
        return job_start_time;
    }

    String job_start_time;

    // Required default constructor for Firebase object mapping
    @SuppressWarnings("unused")
    private ScheduleModel() {
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

    public int getDuration() {
        return duration;
    }

    public String getRequestorName() {
        return user == null ? "Unknown User" : user.getDisplayName();
    }


}
