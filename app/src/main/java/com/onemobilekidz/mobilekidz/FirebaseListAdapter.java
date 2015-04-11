package com.onemobilekidz.mobilekidz;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;
import com.firebase.client.ValueEventListener;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Copied from Firebase Android Example
 * https://github.com/firebase/AndroidChat/blob/master/app/src/main/java/com/firebase/androidchat/FirebaseListAdapter.java
 *
 * @param <T> The class type to use as a model for the data contained in the children of the given Firebase location
 *            <p/>
 *            This class is a generic way of backing an Android ListView with a Firebase location.
 *            It handles all of the child events at the given Firebase location. It marshals received data into the given
 *            class type. Extend this class and provide an implementation of <code>populateView</code>, which will be given an
 *            instance of your list item mLayout and an instance your class that holds your data. Simply populate the view however
 *            you like and this class will handle updating the list as the data changes.
 */
public abstract class FirebaseListAdapter<T extends FirebaseListJoiner> extends BaseAdapter {
    private Context context;
    private Query mRef;
    private Class<T> mModelClass;
    private int mLayout;
    private LayoutInflater mInflater;
    private List<T> mModels;
    private Map<String, T> mModelKeys;
    private ChildEventListener mListener;

    private static final String LOG = "FirebaseListAdapter";

    /**
     * @param mRef        The Firebase location to watch for data changes. Can also be a slice of a location, using some
     *                    combination of <code>limit()</code>, <code>startAt()</code>, and <code>endAt()</code>,
     * @param mModelClass Firebase will marshall the data at a location into an instance of a class that you provide
     * @param mLayout     This is the mLayout used to represent a single list item. You will be responsible for populating an
     *                    instance of the corresponding view with the data from an instance of mModelClass.
     * @param activity    The activity containing the ListView
     */
    public FirebaseListAdapter(Context context, Query mRef, Class<T> mModelClass, int mLayout, Activity activity) {
        this.context = context;
        this.mRef = mRef;
        this.mModelClass = mModelClass;
        this.mLayout = mLayout;
        mInflater = activity.getLayoutInflater();
        mModels = new ArrayList<>();
        mModelKeys = new HashMap<>();
        // Look for all child events. We will then map them to our own internal ArrayList, which backs ListView

        mListener = this.mRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String previousChildName) {
                T model = dataSnapshot.getValue(FirebaseListAdapter.this.mModelClass);
                setField(model, "id", dataSnapshot.getKey());
                insertChild(model, dataSnapshot.getKey(), previousChildName);
                Log.v(LOG, "My values " + dataSnapshot.getKey() + " " + dataSnapshot.getValue());
                populateJoins(model, dataSnapshot);
                notifyDataSetChanged();
            }

            private void populateJoins(T model, final DataSnapshot dataSnapshot) {
                Map<Query, String> joins = model.joinPaths(dataSnapshot.getRef());
                for (final Map.Entry<Query, String> join : joins.entrySet()) {
                    Log.v(LOG, join.getKey().toString() + " " + join.getValue().toString());
                    join.getKey().addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot subSnap) {
                            setField(mModelKeys.get(dataSnapshot.getKey()), join.getValue(), subSnap);
                            notifyDataSetChanged();
                        }

                        @Override
                        public void onCancelled(FirebaseError firebaseError) {

                        }
                    });
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String previousChildName) {
                // One of the mModels changed. Replace it in our list and name mapping
                String childName = dataSnapshot.getKey();
                T newModel = dataSnapshot.getValue(FirebaseListAdapter.this.mModelClass);
                setField(newModel, "id", childName);
                removeChild(mModelKeys.get(childName));
                insertChild(newModel, childName, previousChildName);
                populateJoins(newModel, dataSnapshot);
                notifyDataSetChanged();
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                // A model was removed from the list. Remove it from our list and the name mapping
                removeChild(mModelKeys.get(dataSnapshot.getKey()));
                notifyDataSetChanged();
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String previousChildName) {
                // A model changed position in the list. Update our list accordingly
                insertChild(mModelKeys.get(dataSnapshot.getKey()), dataSnapshot.getKey(), previousChildName);
                notifyDataSetChanged();
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                Log.e("FirebaseListAdapter", "Listen was cancelled, no more updates will occur");
            }

            private void removeChild(T model) {
                int index = mModels.indexOf(model);
                mModels.remove(index);
            }

            private void insertChild(T model, String childName, String previousChildName) {
                mModelKeys.put(childName, model);
                // Remove the model if it's already in the list.
                int index = mModels.indexOf(model);
                if (index >= 0) mModels.remove(index);
                // Insert into the correct location, based on previousChildName
                if (previousChildName == null) {
                    mModels.add(0, model);
                } else {
                    T previousModel = mModelKeys.get(previousChildName);
                    int previousIndex = mModels.indexOf(previousModel);
                    int nextIndex = previousIndex + 1;
                    if (nextIndex == mModels.size()) {
                        mModels.add(model);
                    } else {
                        mModels.add(nextIndex, model);
                    }
                }
            }

            private void setField(T model, String fieldName, Object value) {
                try {
                    Field field = model.getClass().getDeclaredField(fieldName);
                    field.setAccessible(true);
                    field.set(model, value);
                } catch (Exception e) {
                    // Ignore.
                }
            }

            private void setField(T model, String fieldName, DataSnapshot value) {
                Log.v(LOG, "setField(" + model + ", " + fieldName + ", " + value + ")");
                try {
                    Field field = model.getClass().getDeclaredField(fieldName);
                    field.setAccessible(true);
                    field.set(model, value.getValue(field.getType()));
                } catch (Exception e) {
                    // Ignore.
                    Log.v(LOG, e.toString());
                }
            }
        });
    }

    public void cleanup() {
        // We're being destroyed, let go of our mListener and forget about all of the mModels
        mRef.removeEventListener(mListener);
        mModels.clear();
        mModelKeys.clear();
    }

    @Override
    public int getCount() {
        return mModels.size();
    }

    @Override
    public T getItem(int i) {
        return mModels.get(i);
    }


    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = mInflater.inflate(mLayout, viewGroup, false);
        }
        T model = mModels.get(i);
        // Call out to subclass to marshall this model into the provided view
        populateView(view, model, i);
        return view;
    }

    /**
     * Each time the data at the given Firebase location changes, this method will be called for each item that needs
     * to be displayed. The arguments correspond to the mLayout and mModelClass given to the constructor of this class.
     * <p/>
     * Your implementation should populate the view using the data contained in the model.
     *
     * @param v     The view to populate
     * @param model The object containing the data used to populate the view
     */
    protected abstract void populateView(View v, T model, int i);
}