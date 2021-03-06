package com.onemobilekidz.mobilekidz.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.firebase.client.Firebase;
import com.firebase.client.Query;
import com.onemobilekidz.mobilekidz.FirebaseListJoiner;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by vfischer on 3/14/15.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class MessagesModel implements FirebaseListJoiner {

    private static final String LOG = "MessagesModel";
    String id;
    UserModel user;
    String message;
    String sender;

    public Map<Query, String> joinPaths(Firebase path) {
        Map<Query, String> paths = new HashMap<>();
        paths.put(path.getRoot().child("users").child(sender), "user");
        return paths;
    }

    public String getId() {
        return id;
    }

    public String getSender() {
        return sender;
    }


    public UserModel getFriend() {
        return user;
    }

    public String getSenderName() {
        return user == null ? null : user.getDisplayName();
    }

    public String getMessage() {
        return message;
    }


}



