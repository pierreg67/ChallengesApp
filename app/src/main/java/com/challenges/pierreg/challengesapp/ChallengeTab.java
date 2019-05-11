package com.challenges.pierreg.challengesapp;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.GridLayout;
import android.widget.TextView;

import java.text.DateFormat;
import java.util.Random;

/**
 * Created by pierreg on 04/05/2019.
 */

public class ChallengeTab extends GridLayout{

    private LayoutInflater mInflater;
    private TextView nameText;
    private TextView startDateText;
    private TextView nextActionText;

    public ChallengeTab(Context context, Challenge challenge) {
        super(context);
        mInflater = LayoutInflater.from(context);
        View v = mInflater.inflate(R.layout.challengetab, this, true);
        nameText = (TextView) v.findViewById(R.id.challengeName);
        startDateText = (TextView) v.findViewById(R.id.challengeStartDate);
        nextActionText = (TextView) v.findViewById(R.id.challengeNextAction);


        nameText.setText(challenge.getName());
        startDateText.setText(DateFormat.getDateInstance().format(challenge.getStartDate().getTime()) + " : ");
        nextActionText.setText(challenge.getChallengesList().get(0));
        Random random = new Random();
        setBackgroundColor(Color.argb(255, 64 + random.nextInt(192), 128 + random.nextInt(128), 255));
    }
}
