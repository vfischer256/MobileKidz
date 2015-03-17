package com.onemobilekidz.mobilekidz;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.onemobilekidz.mobilekidz.helper.DatabaseManager;
import com.onemobilekidz.mobilekidz.model.FriendsModel;

/**
 * Created by vfischer on 3/16/15.
 */
public class FriendListAdapter extends BaseAdapter {
    DatabaseManager dm;
    ArrayList<FriendsModel> friendsModelArrayList;
    LayoutInflater inflater;
    Context _context;

    public FriendListAdapter(Context context) {

        friendsModelArrayList = new ArrayList<FriendsModel>();
        _context = context;
        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        dm = new DatabaseManager(_context);
        friendsModelArrayList = dm.getAllFriendsData();

    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
        //refetching the new data from database
        friendsModelArrayList = dm.getAllFriendsData();

    }

    @Override
    public int getCount() {
        return friendsModelArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return friendsModelArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder vHolder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.friend_list_row, null);
            vHolder = new ViewHolder();

            vHolder.friend_name = (TextView) convertView
                    .findViewById(R.id.friend_name);
            convertView.setTag(vHolder);
        } else {
            vHolder = (ViewHolder) convertView.getTag();
        }

        FriendsModel friendsObj = friendsModelArrayList.get(position);

        vHolder.friend_name.setText(friendsObj.getFriendName());

        return convertView;
    }

    class ViewHolder {
        TextView friend_name;
    }
}
