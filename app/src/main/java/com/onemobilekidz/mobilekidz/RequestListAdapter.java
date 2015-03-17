package com.onemobilekidz.mobilekidz;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.onemobilekidz.mobilekidz.helper.DatabaseManager;
import com.onemobilekidz.mobilekidz.model.RequestsModel;

/**
 * Created by vfischer on 3/16/15.
 */
public class RequestListAdapter extends BaseAdapter {
    DatabaseManager dm;
    ArrayList<RequestsModel> requestsModelArrayList;
    LayoutInflater inflater;
    Context _context;

    public RequestListAdapter(Context context) {

        requestsModelArrayList = new ArrayList<RequestsModel>();
        _context = context;
        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        dm = new DatabaseManager(_context);
        requestsModelArrayList = dm.getAllRequestsData();

    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
        //refetching the new data from database
        requestsModelArrayList = dm.getAllRequestsData();

    }

    @Override
    public int getCount() {
        return requestsModelArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return requestsModelArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder vHolder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.request_list_row, null);
            vHolder = new ViewHolder();

            vHolder.babysitterId = (TextView) convertView
                    .findViewById(R.id.babySitterId);
            vHolder.requestDate = (TextView) convertView
                    .findViewById(R.id.requestDate);
            vHolder.requestSentReceived = (TextView) convertView
                    .findViewById(R.id.requestSentReceived);
            convertView.setTag(vHolder);
        } else {
            vHolder = (ViewHolder) convertView.getTag();
        }

        RequestsModel requestsObj = requestsModelArrayList.get(position);


        vHolder.babysitterId.setText(Integer.toString(requestsObj.getBabysitterId()));
        vHolder.requestDate.setText(requestsObj.getRequestDate());
        vHolder.requestSentReceived.setText(requestsObj.getRequestSentReceived());

        return convertView;
    }

    class ViewHolder {
        TextView babysitterId;
        TextView requestDate;
        TextView requestSentReceived;
    }
}
