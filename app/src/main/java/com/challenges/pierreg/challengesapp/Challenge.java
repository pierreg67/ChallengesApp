package com.challenges.pierreg.challengesapp;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Calendar;
import java.util.ArrayList;

/**
 * Created by pierreg on 16/03/2019.
 */

public class Challenge implements Parcelable{
    private int m_id;
    private FrequencyEnum m_frequency;
    private int m_duration;
    private Calendar m_startDate;
    private String m_name;
    private ArrayList<String> m_challengesList;

    public Challenge(int id, String name, int duration, FrequencyEnum frequency, Calendar startDate, ArrayList<String> challengesList){
        m_id = id;
        m_frequency = frequency;
        m_duration = duration;
        m_startDate = startDate;
        m_name = name;
        m_challengesList = challengesList;
    }

    public Challenge(Parcel in) {
        m_id = in.readInt();
        m_frequency = (FrequencyEnum) in.readSerializable();
        m_duration = in.readInt();
        m_startDate = (java.util.Calendar) in.readSerializable();
        m_name = in.readString();
        m_challengesList = in.createStringArrayList();
    }

    public int getId(){
        return m_id;
    }

    public void setId(int id){
        m_id = id;
    }

    public String getName(){
        return m_name;
    }

    public void setName(String name){
        m_name = name;
    }

    public int getDuration(){
        return m_duration;
    }

    public void setDuration(int duration){
        m_duration = duration;
    }

    public Calendar getStartDate() {
        return m_startDate;
    }

    public void setStartDate(Calendar startDate){
        m_startDate = startDate;
    }

    public FrequencyEnum getFrequency(){
        return m_frequency;
    }

    public void setFrequency(FrequencyEnum frequency){
        m_frequency = frequency;
    }

    public ArrayList<String> getChallengesList() {
        return m_challengesList;
    }

    public void setChallengesList(ArrayList<String> challengesList){
        m_challengesList = challengesList;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(m_id);
        dest.writeSerializable(m_frequency);
        dest.writeInt(m_duration);
        dest.writeSerializable(m_startDate);
        dest.writeString(m_name);
        dest.writeStringList(m_challengesList);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Challenge> CREATOR = new Creator<Challenge>() {
        @Override
        public Challenge createFromParcel(Parcel in) {
            return new Challenge(in);
        }

        @Override
        public Challenge[] newArray(int size) {
            return new Challenge[size];
        }
    };
}

