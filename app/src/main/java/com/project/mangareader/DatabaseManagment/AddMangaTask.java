package com.project.mangareader.DatabaseManagment;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class AddMangaTask extends AsyncTask<Void, Void, Void> {
    private final DataBaseControler dataBaseControler;
    private Manga manga;
    Gson gson = new Gson();


    @Override
    protected Void doInBackground(Void... voids) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(dataBaseControler.M_NAME, manga.getName());
        contentValues.put(dataBaseControler.M_WRITER, manga.getWriter());
        contentValues.put(dataBaseControler.M_COVER, manga.getCover());
        List<String> imagelist = manga.getImages();
        String inputArray = gson.toJson(imagelist);
        contentValues.put(dataBaseControler.M_IMAGES, inputArray);
        contentValues.put(dataBaseControler.GENERA, manga.getGenera());

        SQLiteDatabase sqLiteDatabase = dataBaseControler.getWritableDatabase();
        Long s = sqLiteDatabase.insert(dataBaseControler.MANGA_T, null, contentValues);

        Log.i("database ","   "+s);
        return null;
    }


    public AddMangaTask(DataBaseControler dataBaseControler, Manga manga) {
        this.dataBaseControler = dataBaseControler;
        this.manga = manga;
    }




}
