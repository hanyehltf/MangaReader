package com.project.mangareader.DatabaseManagment;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.net.PortUnreachableException;

public class DataBaseControler extends SQLiteOpenHelper {
    public static String DATABASE_NAME = "mangareader";
    public static String USER_T = "users";
    public static String MANGA_T = "mangas";
    public static String USERNAME = "username";
    public static String EMAIL = "email";
    public static String PASSWORD = "password";
    public static String AVATAR = "avatar";
    public static String M_NAME = "name";
    public static String M_WRITER = "writer";
    public static String M_IMAGES = "images";
    public static String M_COVER = "cover";
    public static String CREATE_USERS_T = "CREATE TABLE IF NOT EXISTS  " + USER_T + " (" +
            USERNAME + " TEXT, " +
            EMAIL + " TEXT ," +
            PASSWORD + " TEXT  , " +
            EMAIL + "  TEXT ," +
            AVATAR + "  TEXT );";

    public static String CREATE_MANGAS_T = "CREATE TABLE IF NOT EXISTS  " + MANGA_T + " (" +
            M_NAME + " TEXT, " +
            M_WRITER + " TEXT ," +
            M_COVER + " TEXT  , " +
            M_IMAGES + "  TEXT);";

    public DataBaseControler(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 2);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_USERS_T);
        db.execSQL(CREATE_MANGAS_T);
    }




public void signUp(User user){
        AddUserTast addUserTast=new AddUserTast(this,user);
        addUserTast.execute();




}

public Boolean  login (String username,String password){
        Boolean statuse=new Boolean(false);




    return statuse;
}


public void AddManga(Manga manga){





}


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
