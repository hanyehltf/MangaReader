package com.project.mangareader.DatabaseManagment;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;

public class AddUserTast extends AsyncTask<Void, Void, Void> {
    private DataBaseControler dataBaseControler;
    private User user;

    @Override
    protected Void doInBackground(Void... voids) {

        ContentValues contentValues = new ContentValues();
        contentValues.put(dataBaseControler.USERNAME, user.getId());
        contentValues.put(dataBaseControler.EMAIL, user.getEmail());
        contentValues.put(dataBaseControler.AVATAR, user.getImage());
        contentValues.put(dataBaseControler.PASSWORD, user.getPassword());
        SQLiteDatabase sqLiteDatabase = dataBaseControler.getWritableDatabase();
        long isInstence = sqLiteDatabase.insert(dataBaseControler.USER_T, null, contentValues);


        return null;
    }


    public AddUserTast(DataBaseControler dataBaseControler, User user) {
        this.dataBaseControler = dataBaseControler;
        this.user = user;
    }
}
