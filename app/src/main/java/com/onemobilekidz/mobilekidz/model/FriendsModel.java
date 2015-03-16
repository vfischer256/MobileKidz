package com.onemobilekidz.mobilekidz.model;

/**
 * Created by vfischer on 3/11/15.
 */
public class FriendsModel {

    String friendName;
    int id;

    public FriendsModel() {

    }

    public FriendsModel(String friendName) {
        this.friendName = friendName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFriendName() {
        return friendName;
    }

    public void setFriendName(String friendName) {
        this.friendName = friendName;
    }


}
