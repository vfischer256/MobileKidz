package com.onemobilekidz.mobilekidz.model;

/**
 * Created by vfischer on 3/11/15.
 */
public class RequestsModel {


    int requestId;
    int babysitterId;
    String requestDate;
    String requestStatus;
    String requestSentReceived;

    public RequestsModel() {

    }

    public RequestsModel(int babysitterId, String requestDate, String requestStatus, String requestSentReceived) {
        this.babysitterId = babysitterId;
        this.requestDate = requestDate;
        this.requestStatus = requestStatus;
        this.requestSentReceived = requestSentReceived;
    }


    public int getRequestId() {
        return requestId;
    }

    public void setRequestId(int requestId) {
        this.requestId = requestId;
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


    public String getRequestSentReceived() {
        return requestSentReceived;
    }

    public void setRequestSentReceived(String requestSentReceived) {
        this.requestSentReceived = requestSentReceived;
    }

}
