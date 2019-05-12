package com.challenges.pierreg.challengesapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by pierreg on 29/03/2019.
 */

public class DatabaseHandler extends SQLiteOpenHelper {

    public static final String CHALLENGE_KEY = "id";
    public static final String CHALLENGE_NAME = "name";
    public static final String CHALLENGE_DURATION = "duration";
    public static final String CHALLENGE_FREQUENCY = "frequency";
    public static final String CHALLENGE_STARTDATEYEAR = "startYear";
    public static final String CHALLENGE_STARTDATEMONTH = "startMonth";
    public static final String CHALLENGE_STARTDATEDAY = "startDay";
    public static final String CHALLENGE_LIST = "listChallenges";


    public static final String CHALLENGE_TABLE_NAME = "Challenge";
    public static final String CHALLENGE_TABLE_CREATE =
            "CREATE TABLE " + CHALLENGE_TABLE_NAME + " (" +
                    CHALLENGE_KEY + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    CHALLENGE_NAME + " TEXT, " +
                    CHALLENGE_DURATION + " REAL, " +
                    CHALLENGE_FREQUENCY + " TEXT, " +
                    CHALLENGE_STARTDATEYEAR + " REAL, " +
                    CHALLENGE_STARTDATEMONTH + " REAL, " +
                    CHALLENGE_STARTDATEDAY + " REAL, " +
                    CHALLENGE_LIST + " TEXT" + ");";

    public static final String CHALLENGE_TABLE_DROP = "DROP TABLE IF EXISTS " + CHALLENGE_TABLE_NAME + ";";

    public DatabaseHandler(Context context, String name, CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CHALLENGE_TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(CHALLENGE_TABLE_DROP);
        onCreate(db);
    }
}

