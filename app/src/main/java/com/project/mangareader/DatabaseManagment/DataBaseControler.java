package com.project.mangareader.DatabaseManagment;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.google.android.material.internal.ParcelableSparseArray;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.lang.reflect.Type;
import java.net.PortUnreachableException;
import java.util.ArrayList;
import java.util.List;

public class DataBaseControler extends SQLiteOpenHelper {
    public static String DATABASE_NAME = "mangareader";
    public static String USER_T = "users";
    public static String MANGA_T = "mangas";
    public static String USERNAME = "username";
    public static String EMAIL = "email";
    public static String PASSWORD = "password";
    public static String AVATAR = "avatar";
    public static String M_NAME = "name";
    public static String GENERA = "genera";
    public static String M_WRITER = "writer";
    public static String M_IMAGES = "images";
    public static String M_COVER = "cover";
    public static String CREATE_USERS_T = "CREATE TABLE IF NOT EXISTS  " + USER_T + " (" +
            USERNAME + " TEXT, " +
            EMAIL + " TEXT ," +
            PASSWORD + " TEXT  , " +
            AVATAR + "  TEXT  );";

    public static String CREATE_MANGAS_T = "CREATE TABLE IF NOT EXISTS  " + MANGA_T + " (" +
            M_NAME + " TEXT, " +
            M_WRITER + " TEXT ," +
            M_COVER + " TEXT  , " +
            M_IMAGES + "  TEXT ," +
            GENERA + " TEXT );";

    public DataBaseControler(Context context) {
        super(context, DATABASE_NAME, null, 2);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_USERS_T);
        db.execSQL(CREATE_MANGAS_T);
    }


    public Boolean signUp(User user) {
        Boolean statuse;
        if (login(user.getId(), user.getPassword()) != null) {
            AddUserTast addUserTast = new AddUserTast(this, user);
            addUserTast.execute();
            statuse = true;
        } else {
            statuse = false;
        }

        return statuse;
    }

    public User login(String username, String password) {
        User user = new User();
        String[] columns = {
                USERNAME
        };
        SQLiteDatabase db = this.getReadableDatabase();
        // selection criteria
        String selection = USERNAME + " = ?" + " AND " + PASSWORD + " = ?";
        // selection arguments
        String[] selectionArgs = {username, password};
        // query user table with conditions

        Cursor cursor = db.query(USER_T, //Table to query
                columns,                    //columns to return
                selection,                  //columns for the WHERE clause
                selectionArgs,              //The values for the WHERE clause
                null,                       //group the rows
                null,                       //filter by row groups
                null);                      //The sort order
        int cursorCount = cursor.getCount();


        if (cursorCount > 0) {
            while (!cursor.isAfterLast()) {

                user.setId(cursor.getString(0));
                user.setEmail(cursor.getString(1));
                user.setPassword(cursor.getString(2));
                user.setImage(cursor.getString(3));
                user.setPassword(cursor.getString(4));
                cursor.moveToNext();


            }

        }

        cursor.close();
        db.close();
        return user;

    }


    public void AddManga(Manga manga) {
        AddMangaTask addMangaTask = new AddMangaTask(this, manga);
        addMangaTask.execute();

    }

    public List<Manga> getMangas() {

        AddMangaTask addMangaTask = new AddMangaTask(this);
        List<Manga> mangaList = addMangaTask.getMangaList();
        return mangaList;
    }



    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
