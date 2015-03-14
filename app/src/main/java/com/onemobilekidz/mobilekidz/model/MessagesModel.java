package com.onemobilekidz.mobilekidz.model;

/**
 * Created by vfischer on 3/14/15.
 */
public class MessagesModel {


    int babysitterId;
    String message;
    String status;

    public MessagesModel() {

    }

    public MessagesModel(int babysitterId, String message, String status) {
        this.babysitterId = babysitterId;
        this.message = message;
        this.status = status;
    }

    public int getBabysitterId() {
        return babysitterId;
    }

    public void setBabysitterId(int babysitterId) {
        this.babysitterId = babysitterId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}



