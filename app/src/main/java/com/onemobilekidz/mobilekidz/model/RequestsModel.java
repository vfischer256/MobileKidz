package com.onemobilekidz.mobilekidz.model;

/**
 * Created by vfischer on 3/11/15.
 */
public class RequestsModel {


    int requestId;
    int babysitterId;
    String requestDate;
    String createdAt;
    String updatedAt;
    String requestStatus;

    public RequestsModel() {

    }

    public RequestsModel(int babysitterId, String requestDate, String requestStatus) {
        this.babysitterId = babysitterId;
        this.requestDate = requestDate;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.requestStatus = requestStatus;
    }

    public int getRequestId() {
        return requestId;
    }

    public int getBabysitterId() {
        return babysitterId;
    }

    public void setBabysitterId(int babysitterId) {
        this.babysitterId = babysitterId;
    }

    public String getRequestDate() {
        return requestDate;
    }

    public void setRequestDate(String requestDate) {
        this.requestDate = requestDate;
    }

    public String getRequestStatus() {

        return requestStatus;
    }

    public void setRequestStatus(String requestStatus) {

        this.requestStatus = requestStatus;
    }


}
