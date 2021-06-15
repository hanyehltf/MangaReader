package com.project.mangareader.DatabaseManagment;

import android.content.Context;
import android.util.Log;

import com.google.gson.JsonObject;
import com.google.gson.internal.$Gson$Preconditions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class AddToJsonFile {


    Context context;
    private Manga manga;


    public AddToJsonFile(Context context) {


        this.context = context;

    }

    public void addMangaToJsonFile(Manga manga, final responce responce) {

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("name", manga.getName());
            jsonObject.put("writer", manga.getWriter());
            jsonObject.put("cover", manga.getCover());
            jsonObject.put("genera", manga.getGenera());


        } catch (JSONException e) {
            e.printStackTrace();
        }


        responce.responce(jsonObject);


    }


    public interface responce {
        void responce(JSONObject jsonObject);


    }
}
