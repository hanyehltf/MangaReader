package com.project.mangareader.DatabaseManagment;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;

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
        sqLiteDatabase.insert(dataBaseControler.MANGA_T, null, contentValues);

        return null;
    }


    public AddMangaTask(DataBaseControler dataBaseControler, Manga manga) {
        this.dataBaseControler = dataBaseControler;
        this.manga = manga;
    }

    public AddMangaTask(DataBaseControler dataBaseControler) {


        this.dataBaseControler = dataBaseControler;
    }


    public List<Manga> getMangaList() {
        SQLiteDatabase sqLiteDatabase = dataBaseControler.getReadableDatabase();
        List<Manga> mangaList = new ArrayList<>();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM  " + dataBaseControler.MANGA_T, null);
        cursor.moveToFirst();
        if (cursor.getCount() > 0) {
            while (!cursor.isAfterLast()) {
                Manga manga = new Manga();
                manga.setName(cursor.getColumnName(0));
                manga.setWriter(cursor.getColumnName(1));
                manga.setCover(cursor.getColumnName(2));
                String images = cursor.getColumnName(3);
                manga.setGenera(cursor.getColumnName(4));
                Type type = new TypeToken<ArrayList<String>>() {
                }.getType();

                ArrayList<String> finalOutputString = gson.fromJson(images, type);
                manga.setImages(finalOutputString);
                mangaList.add(manga);


                cursor.moveToNext();

            }

        }
        return mangaList;
    }


}
