package com.example.thomas.dijoncity.Helpers;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Thomas on 20/09/2017.
 */

public class DataBaseHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "DijonCity.db";

    private static final String SQL_CREATE_TABLE_PATH =
            "CREATE TABLE IF NOT EXISTS Trip ( " +
                "id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                "cinemaId TEXT NOT NULL," +
                "restaurantId TEXT NOT NULL," +
                "creationDate DATE NOT NULL," +
                "predictedDate DATE NOT NULL," +
                "comment TEXT NOT NULL," +
                "status TEXT NOT NULL);";

    public DataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_TABLE_PATH);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {

    }
}
