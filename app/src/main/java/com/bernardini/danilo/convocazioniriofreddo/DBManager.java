package com.bernardini.danilo.convocazioniriofreddo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class DBManager {

    private static final String TAG = "DBManager";
    private static final String PLAYERS = "players";
    private static final String TEAMS = "teams";
    private static final String TEAM_NAME = "team_name";
    private static final String CALLS = "calls";
    private static final String NAME = "name";
    private static final String HOME = "home";
    private static final String AWAY = "away";
    private static final String DATE = "date";
    private static final String PLACE = "place";
    private static final String CALL_PLACE = "call_place";
    private static final String CALL_TIME = "call_time";
    private static final String NOTES = "notes";
    private static final String PLAYERS_CALLED = "players_called";
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
                null, null, null, null, NAME + " ASC");

        return cursor;
    }

    public void insertTeam(String team){
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(TEAM_NAME, team);

        db.insert(TEAMS, null, cv);
    }

    public boolean deleteTeam(String team) {
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        if (db.delete(TEAMS, TEAM_NAME + "=?", new String[]{team}) > 0)
            return true;
        else return false;
    }

    public Cursor queryTeams() {
        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        Cursor cursor = db.query(
                TEAMS,
                new String[] { TEAM_NAME },
                null, null, null, null, null, null);

        return cursor;
    }

    public void insertCall(String home, String away, String dateTime, String place, String callPlace,
                           String callTime, String notes){
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        String year = dateTime.substring(6,10);
        String month = dateTime.substring(3,5);
        String day = dateTime.substring(0,2);
        String time = dateTime.substring(12) + ":00";
        String date = year + "-" + month + "-" + day + " " + time;

        ContentValues cv = new ContentValues();
        cv.put(HOME, home);
        cv.put(AWAY, away);
        cv.put(DATE, date);
        cv.put(PLACE, place);
        cv.put(CALL_PLACE, callPlace);
        cv.put(CALL_TIME, callTime);
        cv.put(NOTES, notes);

        db.insert(CALLS, null, cv);
    }

    public boolean deleteCall(String dateTime) {
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        if (db.delete(CALLS, DATE + "=?", new String[]{dateTime}) > 0)
            return true;
        else return false;
    }

    public Cursor queryCalls() {
        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        Cursor cursor = db.query(
                CALLS,
                new String[] { HOME,AWAY,DATE,PLACE,CALL_PLACE,CALL_TIME,NOTES },
                null, null, null, null, DATE + " DESC");

        return cursor;
    }

    public Cursor queryCall(String date) {
        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        Cursor cursor = db.query(
                CALLS,
                new String[] { HOME,AWAY,DATE,PLACE,CALL_PLACE,CALL_TIME,NOTES },
                DATE + " = '" + date + "'",
                null, null, null, null, null);

        return cursor;
    }

    public void insertPlayersCalled(String player, String dateTime){
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        String year = dateTime.substring(6,10);
        String month = dateTime.substring(3,5);
        String day = dateTime.substring(0,2);
        String time = dateTime.substring(12) + ":00";
        String date = year + "-" + month + "-" + day + " " + time;

        ContentValues cv = new ContentValues();
        cv.put(NAME, player);
        cv.put(DATE, date);

        db.insert(PLAYERS_CALLED, null, cv);
    }

    public Cursor queryPlayersCalled() {
        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        Cursor cursor = db.query(
                PLAYERS_CALLED,
                new String[] { NAME, DATE },
                null, null, null, null, null, null);

        return cursor;
    }

    public Cursor queryPlayersCalled(String date) {
        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        Cursor cursor = db.query(
                PLAYERS_CALLED,
                new String[] { NAME },
                DATE + " = '" + date + "'",
                null, null, null, null, null);

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