package com.project.mangareader.DatabaseManagment;

import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;
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
import java.util.ArrayList;
import java.util.List;

public class AddMangaTask extends AsyncTask<Void, Integer, String> {
    private final DataBaseControler dataBaseControler;
    private List<Manga> mangas;
    private Context context;
    private ProgressDialog progressDialog;


    @Override
    protected String doInBackground(Void... voids) {
        ContentValues contentValues = new ContentValues();

        mangas = getMangaFromString();


        for (int i = 0; i < mangas.size(); i++) {
            Manga manga = mangas.get(i);
            contentValues.put(dataBaseControler.M_NAME, manga.getName());
            contentValues.put(dataBaseControler.M_WRITER, manga.getWriter());
            contentValues.put(dataBaseControler.M_COVER, manga.getCover());
            contentValues.put(dataBaseControler.GENERA, manga.getGenera());
            SQLiteDatabase sqLiteDatabase = dataBaseControler.getWritableDatabase();
            sqLiteDatabase.insert(dataBaseControler.MANGA_T, null, contentValues);
            publishProgress((i * 100) / mangas.size());
        }
        return null;
    }


    public AddMangaTask(DataBaseControler dataBaseControler, Context context) {
        this.dataBaseControler = dataBaseControler;
        this.context = context;
    }

    public List<Manga> getMangaFromString() {
        List<Manga> mangaList = new ArrayList<>();
        String json = getMangaFromJson();
        Runnable runnable = new Runnable() {
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
                            manga.setImages(dataBaseControler.getImages());
                            mangaList.add(manga);
                        }
                        publishProgress((i*100)/jsonArray.length());
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        };
        runnable.run();

        return mangaList;
    }

    public String getMangaFromJson() {

        InputStream inputStream = context.getResources().openRawResource(R.raw.jsonfile);
        Writer writer = new StringWriter();
        char[] buffer = new char[1024];
        Thread thread = new Thread();

        Runnable runnable = new Runnable() {
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
        };
        runnable.run();

        String jsonString = writer.toString();


        return jsonString;


    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        progressDialog.hide();
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressDialog = new ProgressDialog(context);
        progressDialog.setTitle("در حال ذخیره سازی");
        progressDialog.setMessage("مدت زمان ذخیره سازی زمانبر است لطفا شکیبا باشید...");
        progressDialog.setIndeterminate(false);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.show();
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
        progressDialog.setProgress(values[0]);
    }
}
