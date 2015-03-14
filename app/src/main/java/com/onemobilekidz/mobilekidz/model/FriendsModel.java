package com.onemobilekidz.mobilekidz.model;

/**
 * Created by vfischer on 3/11/15.
 */
public class FriendsModel {

    int babysitterId;
    String friendName;

    public FriendsModel() {

    }

    public FriendsModel(int babysitterId, String friendName) {
        this.babysitterId = babysitterId;
        this.friendName = friendName;
    }

    public int getBabysitterId() {
        return babysitterId;
    }

    public void setBabysitterId(int babysitterId) {
        this.babysitterId = babysitterId;
    }

    public String getFriendName() {
        return friendName;
    }

    public void setFriendName(String friendName) {
        this.friendName = friendName;
    }


}
