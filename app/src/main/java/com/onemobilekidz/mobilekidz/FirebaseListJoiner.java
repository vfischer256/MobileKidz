package com.onemobilekidz.mobilekidz;

import com.firebase.client.Firebase;
import com.firebase.client.Query;

import java.util.Map;

/**
 * Created by vfischer on 3/28/15.
 */
public interface FirebaseListJoiner {
    public Map<Query, String> joinPaths(Firebase path);
}