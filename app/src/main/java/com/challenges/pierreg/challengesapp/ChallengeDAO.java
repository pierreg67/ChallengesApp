package com.challenges.pierreg.challengesapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by pierreg on 29/03/2019.
 */

public class ChallengeDAO extends DAOBase {

    private static final String TABLE_NAME = "Challenge";
    private static final String KEY = "id";
    private static final String NAME = "name";
    private static final String STARTDATEYEAR = "startYear";
    private static final String STARTDATEMONTH = "startMonth";
    private static final String STARTDATEDAY = "startDay";
    private static final String DURATION = "duration";
    private static final String FREQUENCY = "frequency";
    private static final String LISTCHALLENGE = "listChallenges";

    public static final String TABLE_CREATE = "CREATE TABLE " + TABLE_NAME + " (" +
            KEY + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            NAME + " TEXT," +
            STARTDATEYEAR + " REAL," +
            STARTDATEMONTH + " REAL," +
            STARTDATEDAY + " REAL," +
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
        value.put(ChallengeDAO.STARTDATEYEAR, challenge.getStartDate().get(Calendar.YEAR));
        value.put(ChallengeDAO.STARTDATEMONTH, challenge.getStartDate().get(Calendar.MONTH));
        value.put(ChallengeDAO.STARTDATEDAY, challenge.getStartDate().get(Calendar.DAY_OF_MONTH));
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
        value.put(ChallengeDAO.STARTDATEYEAR, challenge.getStartDate().get(Calendar.YEAR));
        value.put(ChallengeDAO.STARTDATEMONTH, challenge.getStartDate().get(Calendar.MONTH));
        value.put(ChallengeDAO.STARTDATEDAY, challenge.getStartDate().get(Calendar.DAY_OF_MONTH));
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
            startDate.set(Calendar.YEAR, c.getInt(4));
            startDate.set(Calendar.MONTH, c.getInt(5));
            startDate.set(Calendar.DAY_OF_MONTH, c.getInt(6));
            ArrayList<String> challenges = new ArrayList<>();
            String test =  c.getString(7);
            String[] allChallenges = c.getString(7).substring(1, c.getString(7).length() - 1).split(",");
            for(String challenge : allChallenges){
                challenges.add(challenge.trim());
            }
            challengesList.add(new Challenge(id, name, duration, FrequencyEnum.valueOf(frequency), startDate, challenges));
        }
        return challengesList;
    }

    public Challenge selectLastChallenge() throws Exception {
        Cursor c = mDb.rawQuery("select * from " + TABLE_NAME, null);
        DatabaseUtils.dumpCursorToString(c);
        if(c == null){
            throw new Exception("nothing to select", null);
        }
        c.moveToLast();
        Calendar startDate = Calendar.getInstance();
        int id = c.getInt(0);
        String name = c.getString(1);
        int duration = c.getInt(2);
        String frequency = c.getString(3);
        startDate.set(Calendar.YEAR, c.getInt(4));
        startDate.set(Calendar.MONTH, c.getInt(5));
        startDate.set(Calendar.DAY_OF_MONTH, c.getInt(6));
        ArrayList<String> challenges = new ArrayList<>();
        String[] allChallenges = c.getString(7).substring(1, c.getString(7).length() - 1).split(",");
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
        startDate.set(Calendar.YEAR, c.getInt(4));
        startDate.set(Calendar.MONTH, c.getInt(5));
        startDate.set(Calendar.DAY_OF_MONTH, c.getInt(6));
        ArrayList<String> challenges = new ArrayList<>();
        String[] allChallenges = c.getString(7).substring(1, c.getString(7).length() - 1).split(",");
        for(String challenge : allChallenges){
            challenges.add(challenge.trim());
        }
        return new Challenge(id, name, duration, FrequencyEnum.valueOf(frequency), startDate, challenges);
    }

    public int countChallengeAtDate(Calendar calendar){
        Cursor c = mDb.rawQuery("select * from " + TABLE_NAME +
                " WHERE " + STARTDATEYEAR + " = " + calendar.get(Calendar.YEAR) +
                " AND " + STARTDATEMONTH + " = " + calendar.get(Calendar.MONTH) +
                " AND " + STARTDATEDAY + " = " + calendar.get(Calendar.DAY_OF_MONTH), null);
        DatabaseUtils.dumpCursorToString(c);
        return c.getCount();
    }
}
