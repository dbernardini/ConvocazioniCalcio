package com.bernardini.danilo.convocazioniriofreddo;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Convocazioni";

    private static final String TAG = "DBHelper";
    private static final String CALLS = "calls";
    private static final String PLAYERS = "players";
    private static final String PLAYER_ID = "player_id";
    private static final String NAME = "name";
    private static final String HOME = "home";
    private static final String AWAY = "away";
    private static final String DATE = "date";
    private static final String PLACE = "place";
    private static final String CALL_PLACE = "call_place";
    private static final String CALL_TIME = "call_time";
    private static final String NOTES = "notes";
    private static final String PLAYERS_CALLED = "players_called";

    public static final String CREATE_TABLE_PLAYERS =
            "CREATE TABLE IF NOT EXISTS " + PLAYERS + " (" +
                    PLAYER_ID + " INT AUTO_INCREMENT PRIMARY KEY," +
                    NAME + " TEXT )";

    public static final String CREATE_TABLE_CALLS =
            "CREATE TABLE IF NOT EXISTS " + CALLS + " (" +
                    DATE + " DATETIME PRIMARY KEY," +
                    HOME + " TEXT," +
                    AWAY + " TEXT," +
                    PLACE + " TEXT," +
                    CALL_PLACE + " TEXT," +
                    CALL_TIME + " TIME," +
                    NOTES + " TEXT )";

    public static final String CREATE_TABLE_PLAYERS_CALLED =
            "CREATE TABLE IF NOT EXISTS " + PLAYERS_CALLED + " (" +
                    PLAYER_ID + " INT," +
                    DATE + " DATETIME," +
                    "PRIMARY KEY (" + PLAYER_ID + "," + DATE + "), " +
                    "FOREIGN KEY (" + PLAYER_ID + ") REFERENCES " + PLAYERS + "(" + PLAYER_ID + ")," +
                    "FOREIGN KEY (" + DATE + ") REFERENCES " + CALLS + "(" + DATE + ")" +
                    ")";

    public static final String DELETE_TABLE_PLAYERS = "DROP TABLE IF EXISTS " + PLAYERS;
    public static final String DELETE_TABLE_CALLS = "DROP TABLE IF EXISTS " + CALLS;
    public static final String DELETE_TABLE_PLAYERS_CALLED = "DROP TABLE IF EXISTS " + PLAYERS_CALLED;

    public DBHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d(TAG, "onCreate()");
        db.execSQL(CREATE_TABLE_PLAYERS);
        db.execSQL(CREATE_TABLE_CALLS);
        db.execSQL(CREATE_TABLE_PLAYERS_CALLED);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

}
