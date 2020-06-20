package com.example.btth02;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper
{
    private static final String DATABASE_NAME = "company.db";
    private static final int DATABASE_VERSION = 1;

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query1 = "CREATE TABLE author (id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                "name TEXT NOT NULL);";

        String query2 = "CREATE TABLE book (isbn TEXT NOT NULL, " +
                "title TEXT NOT NULL," +
                "aid INTEGER NOT NULL," +
                "language INTEGER," +
                "price FLOAT," +
                "quantity INTEGER," +
                "daterelease TEXT," +
                "FOREIGN KEY (aid) REFERENCES author(id));";

        db.execSQL(query1);
        db.execSQL(query2);

        ContentValues cont = new ContentValues();
        cont.put("name", "Author Name TMP");
        db.insert("author", null, cont);

        ContentValues cont1 = new ContentValues();
        cont1.put("isbn", "MH001");
        cont1.put("title", "Title TMP");
        cont1.put("aid", 1);
        cont1.put("language", 0);
        cont1.put("price", 50.5);
        cont1.put("quantity", 50);
        cont1.put("daterelease", "19-06-2020");
        db.insert("book", null, cont1);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
