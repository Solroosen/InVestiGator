package com.example.investigation_deduction;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by Sofie on 2016-10-13.
 */

public class DBHandler extends SQLiteOpenHelper {

    // All static variables
    // Database version
    private static final int DATABASE_VERSION = 1;

    // Database name
    private static final String DATABASE_NAME = "investigator";

    // table name
    private static final String TABLE_PLAYERS = "players";
    private static final String TABLE_SCORES = "scores";

    // players table column names
    private static final String COLUMN_PID = "p_id";
    private static final String COLUMN_PLAYERNAME = "playerName";

    // scores table column names
    private static final String COLUMN_SID = "s_id";
    private static final String COLUMN_SCORE = "score";
    private static final String COLUMN_PLAYERID = "player_id";

    // Table create statements
    // player table statement
    private static final String CREATE_PLAYER_TABLE = "CREATE TABLE " + TABLE_PLAYERS + "("
            + COLUMN_PID + " INTEGER PRIMARY KEY,"
            + COLUMN_PLAYERNAME + " TEXT "
            + ")";

    // score table statement
    private static final String CREATE_SCORE_TABLE = "CREATE TABLE " + TABLE_SCORES + "("
            + COLUMN_SID + " INTEGER PRIMARY KEY, "
            + COLUMN_SCORE + " INTEGER, "
            + COLUMN_PLAYERID + " INTEGER, "
            + "FOREIGN KEY(" + COLUMN_PLAYERID + ") REFERENCES " + TABLE_PLAYERS + "(" + COLUMN_PID + ")"
            + ")";

    public DBHandler(Context context) { super(context, DATABASE_NAME, null, DATABASE_VERSION);}

    // creating tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_PLAYER_TABLE);
        db.execSQL(CREATE_SCORE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if exists
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PLAYERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SCORES);

        // Create tables again
        onCreate(db);
    }

    public void addPlayer(Player player){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_PLAYERNAME, player.get_playerName());
        db.insert(TABLE_PLAYERS, null, values);

        db.close(); // Closing database connection
    }

    public Player existsPlayer(){
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_PLAYERS;
        Cursor cursor = db.rawQuery(query, null);
        if (cursor != null)
            cursor.moveToFirst();

        return new Player(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1));
    }

    public Player findPlayer(String pName) {
        SQLiteDatabase db = this.getReadableDatabase();
        String q = "SELECT * FROM " + TABLE_PLAYERS + " WHERE " + COLUMN_PLAYERNAME + "='" + pName + "'";
        Cursor cursor = db.rawQuery(q, null);
        if (!(cursor.moveToFirst()) || cursor.getCount() == 0){
            return null;
        }
        else {
            return new Player(Integer.parseInt(cursor.getString(0)), cursor.getString(1));
        }
    }


    public void removePlayer(Player player){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "DELETE FROM " + TABLE_PLAYERS + " WHERE " + COLUMN_PID + "=" + player.get_id();
        db.rawQuery(query, null);
        removePlayerScores(player);
        db.close();
    }

    public void addScore(int score, int player_id){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_SCORE, score);
        values.put(COLUMN_PLAYERID, player_id);
        db.insert(TABLE_SCORES, null, values);
        db.close(); // Closing database connection
    }

    public Boolean removePlayerScores(Player player){
        SQLiteDatabase db = this.getReadableDatabase();
        //String q = "DELETE FROM " + TABLE_SCORES + " WHERE " + COLUMN_PLAYERID + "=" + player.get_id();
        //db.rawQuery(q, null);
        return db.delete(TABLE_SCORES, COLUMN_PLAYERID + "=" + player.get_id(), null) > 0;
    }

    public int[] findPlayerScores(int playerId){
        SQLiteDatabase db = this.getReadableDatabase();
        String q = "SELECT " + COLUMN_SCORE + " FROM " + TABLE_SCORES + " WHERE " + COLUMN_PLAYERID + "=" + playerId;
        Cursor cursor = db.rawQuery(q, null);
        int[] scores = new int[0];
        if (cursor.moveToFirst()) {
            do {
                scores = addToArray(scores, Integer.parseInt(cursor.getString(0)));
            } while (cursor.moveToNext());
        }
        db.close();
        return scores;
    }



    // Help functions for database

    private int[] addToArray(int[] array, int a){
        int[] newArray = new int[array.length + 1];
        for(int i = 0; i < array.length; i++) {
            newArray[i] = array[i];
        }
        newArray[newArray.length-1] = a;
        return newArray;
    }


}
