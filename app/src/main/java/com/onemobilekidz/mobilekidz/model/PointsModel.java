package com.onemobilekidz.mobilekidz.model;

/**
 * Created by vfischer on 3/14/15.
 */
public class PointsModel {

    int points;
    int requestId;

    public PointsModel() {

    }

    public PointsModel(int points, int requestId) {
        this.points = points;
        this.requestId = requestId;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public int getRequestId() {
        return requestId;
    }

    public void setRequestId(int requestId) {
        this.requestId = requestId;
    }


}
