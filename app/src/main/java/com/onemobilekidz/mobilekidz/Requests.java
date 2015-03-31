package com.onemobilekidz.mobilekidz;


import android.os.Bundle;
import android.util.Log;
import android.app.Activity;
import android.widget.ListView;

public class Requests extends Activity {

    private ListView requestList;

    private RequestListAdapter requestListAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_requests);
        bindViews();

    }

    private void bindViews() {
        requestList = (ListView) findViewById(R.id.requestList);
    }


}
