package com.onemobilekidz.mobilekidz.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.firebase.client.Firebase;
import com.firebase.client.Query;
import com.onemobilekidz.mobilekidz.FirebaseListJoiner;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by vfischer on 3/11/15.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class FriendsModel implements FirebaseListJoiner {

    String id;
    UserModel user;
    Boolean friend;

    private FriendsModel() {

    }

    public Boolean isFriendId() {
        return friend;
    }

    public String getId() {
        return id;
    }


    public Map<Query, String> joinPaths(Firebase path) {
        Map<Query, String> paths = new HashMap<>();
        paths.put(path.getRoot().child("users").child(id), "user");
        return paths;
    }

    public UserModel getFriend() {
        return user;
    }

}
