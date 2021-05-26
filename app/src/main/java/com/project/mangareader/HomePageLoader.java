package com.project.mangareader;

import android.content.Context;
import android.content.Intent;

import com.project.mangareader.Home.MainActivity;

public class HomePageLoader {
    private Context context;
    public HomePageLoader(Context context){
        this.context=context;
        Intent intent=new Intent(context, MainActivity.class);
        context.startActivity(intent);
    }
}
