package com.bernardini.danilo.convocazioniriofreddo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class DBManager {

    private static final String TAG = "DBManager";
    private static final String NAME = "name";
    private static final String PLAYERS = "players";
    private DBHelper mDbHelper;

    public DBManager(Context context) {
        mDbHelper = new DBHelper(context);
    }

    public void insertPlayer(String name){
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(NAME, name);

        db.insert(PLAYERS, null, cv);
    }

    public boolean deletePlayer(String player) {
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        if (db.delete(PLAYERS, NAME + "=?", new String[]{player}) > 0)
            return true;
        else return false;
    }

    public Cursor queryPlayers() {
        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        Cursor cursor = db.query(
                PLAYERS,
                new String[] { NAME },
                null, null, null, null, null, null);

        return cursor;
    }

    public void createTablePlayers(){
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        db.execSQL(mDbHelper.CREATE_TABLE_PLAYERS);
    }

    public void dropTablePlayers(){
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        db.execSQL(mDbHelper.DELETE_TABLE_PLAYERS);
    }
}