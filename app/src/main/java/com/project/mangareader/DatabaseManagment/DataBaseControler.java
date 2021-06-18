package com.project.mangareader.DatabaseManagment;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.AsyncTask;
import android.util.Log;

import androidx.annotation.Nullable;

import com.google.android.material.internal.ParcelableSparseArray;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.project.mangareader.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
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
    private Context context;

    public DataBaseControler(Context context) {
        super(context, DATABASE_NAME, null, 2);
        this.context = context;
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

    public List<Manga> getMangaFromString() {
        List<Manga> mangaList = new ArrayList<>();
        String json = getMangaFromJson();
Runnable runnable=new Runnable() {
    @Override
    public void run() {
        try {
            JSONArray jsonArray = new JSONArray(json);
            for (int i = 0; i < jsonArray.length(); i++) {
                for (int j = 0; j <= 3; j++) {
                    Manga manga = new Manga();
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    manga.setName(jsonObject.getString("name"));
                    manga.setGenera(jsonObject.getString("genera"));
                    manga.setWriter(jsonObject.getString("writer"));
                    manga.setCover(jsonObject.getString("cover"));
                    manga.setImages(getImages());
                    mangaList.add(manga);
                }

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
};runnable.run();

        return mangaList;
    }


    public List<String> getImages() {
        List<String> stringList = new ArrayList<>();
        String json = getImagesFromJson();

Runnable runnable=new Runnable() {
    @Override
    public void run() {
        try {
            JSONArray jsonArray = new JSONArray(json);

            for (int i = 0; i < jsonArray.length(); i++) {
                String image = new String();
                image = jsonArray.getString(i);
                stringList.add(image);


            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
};runnable.run();







        return stringList;
    }

    public String getMangaFromJson() {

        InputStream inputStream = context.getResources().openRawResource(R.raw.jsonfile);
        Writer writer = new StringWriter();
        char[] buffer = new char[1024];
     Thread thread=new Thread();

        Runnable runnable=new Runnable() {
            @Override
            public void run() {
                try {
                    Reader reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
                    int n;
                    while ((n = reader.read(buffer)) != -1) {
                        writer.write(buffer, 0, n);
                    }

                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    try {
                        inputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        };runnable.run();

        String jsonString = writer.toString();


        return jsonString;


    }

    public String getImagesFromJson() {
        InputStream inputStream = context.getResources().openRawResource(R.raw.images);
        Writer writer = new StringWriter();
        char[] buffer = new char[1024];
        Runnable runnable=new Runnable() {
            @Override
            public void run() {
                try {

                    Reader reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
                    int n;
                    while ((n = reader.read(buffer)) != -1) {
                        writer.write(buffer, 0, n);
                    }

                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    try {
                        inputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        };runnable.run();

        String jsonString = writer.toString();


        return jsonString;
    }

    public List<Manga> getMangas() {
        Gson gson = new Gson();
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        List<Manga> mangaList = new ArrayList<>();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM  " + MANGA_T, null);

        if (cursor.getCount() > 0 && cursor.moveToNext()) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                Manga manga = new Manga();
                manga.setName(cursor.getString(0));
                manga.setWriter(cursor.getString(1));
                manga.setCover(cursor.getString(2));
                String images = cursor.getString(3);
                manga.setGenera(cursor.getString(4));
                Type type = new TypeToken<List<String>>() {
                }.getType();
                Log.i("input array", String.valueOf(images.getBytes()));
                List<String> finalOutputString = gson.fromJson(images, type);
                manga.setImages(finalOutputString);
                mangaList.add(manga);


                cursor.moveToNext();

            }

        }
        cursor.close();
        sqLiteDatabase.close();
        return mangaList;
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
