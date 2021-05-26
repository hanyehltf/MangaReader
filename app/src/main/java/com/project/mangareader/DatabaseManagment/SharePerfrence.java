package com.project.mangareader.DatabaseManagment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.project.mangareader.Login;

public class SharePerfrence {
    private static final String SHARED_PREF_NAME = "volleyregisterlogin";
    private static final String KEY_USERNAME = "keyusername";
    private static final String KEY_EMAIL = "keyemail";
    private static final String KEY_PASSWORD = "keypassword";
    private static final String KEY_IMAGE = "keyimage";
    private static SharePerfrence mInstance;
    private static Context ctx;

     SharePerfrence(Context context) {
        ctx = context;
    }
    public static synchronized SharePerfrence getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new SharePerfrence(context);
        }
        return mInstance;
    }

    //this method will store the user data in shared preferences
    public void userLogin(User user) {
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_IMAGE, user.getImage());
        editor.putString(KEY_USERNAME, user.getId());
        editor.putString(KEY_EMAIL, user.getEmail());
        editor.putString(KEY_PASSWORD, (user.getPassword()));
        editor.apply();
    }

    //this method will checker whether user is already logged in or not
    public boolean isLoggedIn() {
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_USERNAME, null) != null;
    }

    //this method will give the logged in user
    public User getUser() {
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        User user=new User();
        user.setImage(sharedPreferences.getString(KEY_IMAGE,null));
        user.setId(sharedPreferences.getString(KEY_USERNAME,null));
        user.setEmail(sharedPreferences.getString(KEY_EMAIL,null));
        user.setPassword(sharedPreferences.getString(KEY_PASSWORD,null));
        return user;
    }

    //this method will logout the user
    public void logout() {
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
        ctx.startActivity(new Intent(ctx, Login.class));
    }
}
