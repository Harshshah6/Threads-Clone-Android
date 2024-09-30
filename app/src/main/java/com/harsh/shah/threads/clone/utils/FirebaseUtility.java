package com.harsh.shah.threads.clone.utils;

import android.content.Context;

public class FirebaseUtility {
    static Context mContext;
    private static FirebaseUtility mInstance;

    public static FirebaseUtility getInstance(Context c) {
        if (mInstance == null) {
            mInstance = new FirebaseUtility(c);
        }
        return mInstance;
    }

    FirebaseUtility(Context c){
        mContext = c;
    }

    public void signOut(){

    }
}
