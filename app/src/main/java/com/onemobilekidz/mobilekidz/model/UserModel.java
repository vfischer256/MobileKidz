package com.onemobilekidz.mobilekidz.model;


import android.util.Log;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;
import com.firebase.client.ValueEventListener;


import java.util.HashMap;
import java.util.Map;

/**
 * Created by vfischer on 3/26/15.
 */
public class UserModel {
    private static final String LOG = "UserModel";
    private static UserModel currentUser = new UserModel();
    private String email;
    private String displayName;
    private String userId;

    public UserModel() {
    }

    public UserModel(String email, String displayName) {
        this.email = email;
        this.displayName = displayName;
    }

    public static void getOrCreateUserByEmail(final String email, final ValueEventListener onComplete) {
        getUserByEmail(email, new ValueEventListener() {
            public void onDataChange(DataSnapshot snapshot) {
                if (snapshot.getValue() == null) {
                    createUser(email, "Unknown User").getKey();
                    getUserByEmail(email, onComplete);
                } else {
                    onComplete.onDataChange(snapshot);
                }
            }

            public void onCancelled(FirebaseError firebaseError) {
            }
        });
    }

    public static void getUserByEmail(final String email, final ValueEventListener onComplete) {
        Query query = new Firebase("https://crackling-heat-9656.firebaseio.com/users").equalTo(email);

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            public void onDataChange(DataSnapshot snapshot) {
                onComplete.onDataChange(snapshot);
            }

            public void onCancelled(FirebaseError firebaseError) {
                Log.v(LOG, "The read failed: " + firebaseError.getMessage());
            }
        });
    }

    public static Firebase createUser(String email, String displayName) {
        Firebase postRef = new Firebase("https://crackling-heat-9656.firebaseio.com/users");
        Map<String, String> user = new HashMap<String, String>();
        user.put("email", email);
        user.put("displayName", displayName);
        // Set the priority so we can easily search for users by email.
        user.put(".priority", email);
        Firebase ref = postRef.push();
        ref.setValue(user);
        return ref;
    }

    public static UserModel getCurrentUser() {
        return currentUser;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }
}

