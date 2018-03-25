package com.practical.veny.venyamiliafitri_1202150243_Modul5;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Veny on 3/24/2018.
 */

public class DataHelper extends SQLiteOpenHelper {

    //Versi database yang dibuat
    private static final int DATABASE_VERSION = 2;

    //Nama database yang digunakan
    static final String DATABASE_NAME = "TODO_DB";

    //Nama tabel yang digunakan
    public static final String TABLE_SQLite = "TABLE_TODO";

    //Nama-nama kolom yang digunakan
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_TODO = "todo";
    public static final String COLUMN_DESCRIPTION = "description";
    public static final String COLUMN_PRIORITY = "priority";

    //Konstruktor datahelper dengan parameter Context
    public DataHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    //method SQLiteDatabase untuk create table beserta kolom dan tipe data yang digunakan
    @Override
    public void onCreate(SQLiteDatabase db) {
        final String SQL_CREATE_TODO_TABLE = "CREATE TABLE " + TABLE_SQLite + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY autoincrement, " +
                COLUMN_TODO + " TEXT NOT NULL, " +
                COLUMN_DESCRIPTION + " TEXT NOT NULL," +
                COLUMN_PRIORITY + " TEXT NOT NULL" +
                " )";

        //mengeksekusi SQL yang dibuat pada String SQL_CREATE_TODO_TABLE
        db.execSQL(SQL_CREATE_TODO_TABLE);
    }

    //method upgrade SQLiteDatabase untuk diganti dengan table yang baru
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SQLite);
        //onCreate SQLiteDatabase
        onCreate(db);
    }

    //ArrayList getAllData
    public ArrayList<HashMap<String, String>> getAllData() {
        ArrayList<HashMap<String, String>> wordList;
        wordList = new ArrayList<>();
        //Query select tabel
        String selectQuery = "SELECT * FROM " + TABLE_SQLite;
        //SQLiteDatabase melakukan getWritableDatabase()
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> map = new HashMap<String, String>();
                map.put(COLUMN_ID, cursor.getString(0));
                map.put(COLUMN_TODO, cursor.getString(1));
                map.put(COLUMN_DESCRIPTION, cursor.getString(2));
                map.put(COLUMN_PRIORITY, cursor.getString(3));
                //menambahkan map ke wordlist
                wordList.add(map);
            } while (cursor.moveToNext());
        }
        //Log select wordlist
        Log.e("select sqlite ", "" + wordList);

        //database ditutup
        database.close();
        //mengembalikan nilai wordlist
        return wordList;
    }

    //method insert
    public void insert(String todo, String description, String priority) {
        //SQLiteDatabase melakukan getWritableDatabase()
        SQLiteDatabase database = this.getWritableDatabase();

        //Deklarasi query insert data ke tabel database
        String queryValues = "INSERT INTO " + TABLE_SQLite + " (todo, description, priority) " +
                "VALUES ('" + todo + "', '" + description + "','" + priority + "')";

        //log insert queryValues
        Log.e("insert sqlite ", "" + queryValues);
        //mengeksekusi SQL Query database yang dideklarasikan di queryValues
        database.execSQL(queryValues);
        //database ditutup
        database.close();
    }

    //method menghapus data pada database
    public void delete(int id) {
        //SQLiteDatabase melakukan getWritableDatabase()
        SQLiteDatabase database = this.getWritableDatabase();

        //Deklarasi query delete dari tabel database
        String deleteQuery = "DELETE FROM " + TABLE_SQLite + " WHERE " + COLUMN_ID + "=" + "'" + id + "'";

        //log update deleteQuery
        Log.e("update sqlite ", deleteQuery);
        //mengeksekusi SQL Query yang dideklarasikan di deleteQuery
        database.execSQL(deleteQuery);
        //database ditutup
        database.close();
    }
}
