package com.project.mangareader;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.project.mangareader.Home.MainActivity;

public class HomePageLoader {
    private Context context;
    public HomePageLoader(Context context){
        this.context=context;
        Intent intent=new Intent(context, MainActivity.class);
        ((Activity)context).finish();
        context.startActivity(intent);
    }
}
