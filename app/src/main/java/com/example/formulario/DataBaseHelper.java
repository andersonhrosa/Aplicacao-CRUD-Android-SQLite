package com.example.formulario;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.File;

class DatabaseHelper extends SQLiteOpenHelper {

    private static final String TAG = "DatabaseHelper";
    private static final String DB_NAME = "People.db";
    private static final String TABLE_NAME = "people_table";
    private static final String COL1 = "ID";
    private static final String COL2 = "cod";
    private static final String COL3 = "name";
    private static final String COL4 = "address";
    private static final String COL5 = "phone";

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, 1);
        //context.deleteDatabase(DB_NAME);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_NAME + " (" +
                COL1 + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL2 + " TEXT," +
                COL3 + " TEXT," +
                COL4 + " TEXT," +
                COL5 + " TEXT)";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public boolean addData(String cod, String name, String address, String phone) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL2, cod);
        contentValues.put(COL3, name);
        contentValues.put(COL4, address);
        contentValues.put(COL5, phone);

        Log.d(TAG, "addDAta: Adding " + cod + " " + name + " " + address + " " + phone + " " + "to " + TABLE_NAME);
        long result = db.insert(TABLE_NAME, null, contentValues);

        return result != -1;
    }

    public Cursor getAllData() {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME;
        return db.rawQuery(query, null);
    }

    public Cursor getData(String codigo) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE " + COL2 + " = " + codigo + ";";
        return db.rawQuery(query, null);
    }

    public int updateData(String[] codigo, String cod, String name, String address, String phone) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL2, cod);
        contentValues.put(COL3, name);
        contentValues.put(COL4, address);
        contentValues.put(COL5, phone);
        String selection = COL2 + " LIKE ?";
        return db.update(TABLE_NAME, contentValues, selection, codigo);
    }

    public int removeData(String[] codigo) {
        SQLiteDatabase db = this.getWritableDatabase();
        String selection = COL2 + " LIKE ?";
        return db.delete(TABLE_NAME, selection, codigo);
    }
}