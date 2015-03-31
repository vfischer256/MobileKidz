package com.onemobilekidz.mobilekidz.model;

import com.firebase.client.Firebase;
import com.firebase.client.Query;
import com.onemobilekidz.mobilekidz.FirebaseListJoiner;
import com.onemobilekidz.mobilekidz.Messages;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by vfischer on 3/14/15.
 */
public class MessagesModel implements FirebaseListJoiner {

    String id;
    UserModel user;
    String message;

    private static final String LOG = "MessagesModel";

    public Map<Query, String> joinPaths(Firebase path) {
        Map<Query, String> paths = new HashMap<>();
        paths.put(path.getRoot().child("users").child(id), "user");
        return paths;
    }

    public String getId() {
        return id;
    }

    public UserModel getFriend() {
        return user;
    }


    public String getMessage() {
        return message;
    }


}



