package com.example.herohabit;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class UserRegister {

    private SQLiteOpenHelper dbHelper;

    public UserRegister(Context context) {
        dbHelper = new DBManager(context);
    }

    public long addUser(String username, String password) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(DBManager.getID(), username);
        values.put(DBManager.getPASSWORD(), password);

        //long newRowId = db.insert(DBManager.getUSERS(), null, values);
        db.execSQL("INSERT INTO "+DBManager.getUSERS()+" VALUES (username, password)");
        db.close();

        return 0;
    }
}

