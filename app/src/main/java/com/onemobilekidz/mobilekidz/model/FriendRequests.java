package com.onemobilekidz.mobilekidz.model;

/**
 * Created by vfischer on 3/14/15.
 */
public class FriendRequests {

    String outOfNetworkUsers;
    String status;

    public FriendRequests() {
    }

    public FriendRequests(String outOfNetworkUsers, String status) {
        this.outOfNetworkUsers = outOfNetworkUsers;
        this.status = status;
    }

    public String getOutOfNetworkUsers() {
        return outOfNetworkUsers;
    }

    public void setOutOfNetworkUsers(String outOfNetworkUsers) {
        this.outOfNetworkUsers = outOfNetworkUsers;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


}
