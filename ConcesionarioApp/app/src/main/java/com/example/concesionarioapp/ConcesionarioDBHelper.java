package com.example.concesionarioapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ConcesionarioDBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "concesionario.db";
    private static final int DATABASE_VERSION = 1;

    public ConcesionarioDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE = "CREATE TABLE coches (" +
                "cod_coche INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "marca TEXT, " +
                "modelo TEXT, " +
                "numCV INTEGER, " +
                "precio REAL, " +
                "color TEXT)";
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS coches");
        onCreate(db);
    }
}
