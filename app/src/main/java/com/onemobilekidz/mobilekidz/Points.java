package com.onemobilekidz.mobilekidz;

import android.app.Activity;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.onemobilekidz.mobilekidz.helper.DatabaseManager;
import com.onemobilekidz.mobilekidz.model.PointsModel;


public class Points extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_points);
    }

    public void assignPoints(View view) {

        DatabaseManager dbManager = new DatabaseManager(this);
        PointsModel pointsObj = new PointsModel(5, 2);
        dbManager.addRowPoints(pointsObj);

    }


}
