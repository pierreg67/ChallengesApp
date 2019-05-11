package com.challenges.pierreg.challengesapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

/**
 * Created by pierreg on 29/03/2019.
 */

public class ChallengeDAO extends DAOBase {

    private static final String TABLE_NAME = "Challenge";
    private static final String KEY = "id";
    private static final String NAME = "name";
    private static final String STARTDATE = "date";
    private static final String DURATION = "duration";
    private static final String FREQUENCY = "frequency";
    private static final String LISTCHALLENGE = "listChallenges";

    public static final String TABLE_CREATE = "CREATE TABLE " + TABLE_NAME + " (" +
            KEY + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            NAME + " TEXT," +
            STARTDATE + " REAL," +
            FREQUENCY + " TEXT," +
            DURATION + " REAL," +
            LISTCHALLENGE + "TEXT" +
            " )";

    public static final String TABLE_DROP =  "DROP TABLE IF EXISTS " + TABLE_NAME + ";";

    public ChallengeDAO(Context pContext) {
        super(pContext);
    }

    public void addChallenge(Challenge challenge){
        ContentValues value = new ContentValues();
        value.put(ChallengeDAO.NAME, challenge.getName());
        value.put(ChallengeDAO.DURATION, challenge.getDuration());
        value.put(ChallengeDAO.FREQUENCY, challenge.getFrequency().name());
        value.put(ChallengeDAO.STARTDATE, challenge.getStartDate().getTimeInMillis());
        value.put(ChallengeDAO.LISTCHALLENGE, challenge.getChallengesList().toString());
        mDb.insert(ChallengeDAO.TABLE_NAME, null, value);

    }

    public void deleteChallenge(int id){
        mDb.delete(TABLE_NAME, KEY + " = ?", new String[] {String.valueOf(id)});
    }

    public void dropChallenge(Context c){
//        mDb.execSQL("DELETE FROM " + TABLE_NAME + " WHERE " + KEY + " > 2");
        mDb.execSQL(TABLE_DROP);
        mHandler.onUpgrade(mHandler.getWritableDatabase(), 10, 11);
    }

    public void modifyChallenge(int id, Challenge challenge){
        ContentValues value = new ContentValues();
        value.put(ChallengeDAO.NAME, challenge.getName());
        value.put(ChallengeDAO.DURATION, challenge.getDuration());
        value.put(ChallengeDAO.FREQUENCY, challenge.getFrequency().name());
        value.put(ChallengeDAO.STARTDATE, challenge.getStartDate().getTimeInMillis());
        value.put(ChallengeDAO.LISTCHALLENGE, challenge.getChallengesList().toString());
        mDb.update(TABLE_NAME, value, KEY  + " = ?", new String[] {String.valueOf(id)});
    }

    public List<Challenge> selectAllChallenges() throws Exception {
        Cursor c = mDb.rawQuery("select * from " + TABLE_NAME, null);
        DatabaseUtils.dumpCursorToString(c);
        List<Challenge> challengesList = new ArrayList<>();
        if(c == null){
            throw new Exception("cursor empty, no response to the request : " + "select * from " + TABLE_NAME, null);
        }
        while(c.moveToNext()) {
            Calendar startDate = Calendar.getInstance();
            int id = c.getInt(0);
            String name = c.getString(1);
            int duration = c.getInt(2);
            String frequency = c.getString(3);
            startDate.setTimeInMillis(c.getLong(4));
            ArrayList<String> challenges = new ArrayList<>();
            String[] allChallenges = c.getString(5).substring(1, c.getString(5).length() - 1).split(",");
            for(String challenge : allChallenges){
                challenges.add(challenge);
            }
            challengesList.add(new Challenge(id, name, duration, FrequencyEnum.valueOf(frequency), startDate, challenges));
        }
        return challengesList;
    }

    public Challenge selectLastChallenge(int id) throws Exception {
        Cursor c = mDb.rawQuery("select * from " + TABLE_NAME, null);
        DatabaseUtils.dumpCursorToString(c);
        if(c == null){
            throw new Exception("cursor empty, no response to the request : " + "select * from " + TABLE_NAME + "WHERE ID = " + id, null);
        }
        c.moveToLast();
        Calendar startDate = Calendar.getInstance();
        String name = c.getString(1);
        int duration = c.getInt(2);
        String frequency = c.getString(3);
        startDate.setTimeInMillis(c.getLong(4));
        ArrayList<String> challenges = new ArrayList<>();
        String[] allChallenges = c.getString(5).substring(1, c.getString(5).length() - 1).split(",");
        for(String challenge : allChallenges){
            challenges.add(challenge);
        }
        return new Challenge(id, name, duration, FrequencyEnum.valueOf(frequency), startDate, challenges);
    }

    public Challenge selectOneChallenge(int id) throws Exception {
        Cursor c = mDb.rawQuery("select * from " + TABLE_NAME + " WHERE " + KEY + " = " + id, null);
        DatabaseUtils.dumpCursorToString(c);
        if(c == null){
            throw new Exception("cursor empty, no response to the request : " + "select * from " + TABLE_NAME + "WHERE ID = " + id, null);
        }
        c.moveToLast();
        Calendar startDate = Calendar.getInstance();
        String name = c.getString(1);
        int duration = c.getInt(2);
        String frequency = c.getString(3);
        startDate.setTimeInMillis(c.getLong(4));
        ArrayList<String> challenges = new ArrayList<>();
        String[] allChallenges = c.getString(5).substring(1, c.getString(5).length() - 1).split(",");
        for(String challenge : allChallenges){
            challenges.add(challenge);
        }
        return new Challenge(id, name, duration, FrequencyEnum.valueOf(frequency), startDate, challenges);
    }
}
