package com.onemobilekidz.mobilekidz.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.firebase.client.Firebase;
import com.firebase.client.Query;
import com.onemobilekidz.mobilekidz.FirebaseListJoiner;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by vfischer on 3/14/15.
 */


@JsonIgnoreProperties(ignoreUnknown = true)
public class FriendRequestsModel implements FirebaseListJoiner {

    String id;
    Boolean recipient;
    Boolean sender;
    UserModel user;

    // Required default constructor for Firebase object mapping
    @SuppressWarnings("unused")
    private FriendRequestsModel() {
    }

    public Map<Query, String> joinPaths(Firebase path) {
        Map<Query, String> paths = new HashMap<>();
        paths.put(path.getRoot().child("users").child(id), "user");
        return paths;
    }

    public String getId() {
        return id;
    }

    public Boolean isRecipient() {
        return recipient;
    }

    public Boolean isSender() {
        return sender;
    }

    public UserModel getUser() {
        return user;
    }
}
