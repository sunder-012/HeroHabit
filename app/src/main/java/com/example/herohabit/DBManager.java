package com.example.herohabit;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBManager extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "UserDatabase.db";
    private static final int DATABASE_VERSION = 1;

    private static final String USERS = "users";
    private static final String ID = "id";
    private static final String USERNAME = "username";
    private static final String PASSWORD = "password";
    private static final String EXPERIENCE = "xp";

    private static final String CREATE_TABLE_USERS = "CREATE TABLE IF NOT EXISTS " + USERS + " ("
            + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + USERNAME + " TEXT, "
            + PASSWORD + " TEXT, "
            + EXPERIENCE + "INTEGER);";

    public DBManager(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_USERS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + USERS);
        onCreate(db);
    }

    public static String getUSERS() {
        return USERS;
    }

    public static String getID() {
        return ID;
    }

    public static String getUSERNAME() {
        return USERNAME;
    }

    public static String getPASSWORD() {
        return PASSWORD;
    }
}