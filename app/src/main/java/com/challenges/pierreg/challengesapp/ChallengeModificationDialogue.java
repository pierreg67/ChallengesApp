package com.challenges.pierreg.challengesapp;

import android.app.DialogFragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by pierreg on 01/04/2019.
 * Dialog to affect a challenge when click on it
 * Possible actions are :
 *      -start
 *      -modify
 *      -remove
 *      -mark as done
 */

public class ChallengeModificationDialogue extends DialogFragment {

    private LinearLayout clickedLayout;
    private Context context;
    private int challengeId;
    private Button startButton;
    private Button doneButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialogue_challenge_modification, container, false);
        startButton = (Button) view.findViewById(R.id.startChallenge);
        doneButton = (Button) view.findViewById(R.id.validateChallenge);
        TextView dateView = (TextView)clickedLayout.getChildAt(2);
        if(dateView.getText().toString().startsWith("start with")){
            doneButton.setVisibility(View.GONE);
        } else{
            startButton.setVisibility(View.GONE);
        }
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        ((Button) view.findViewById(R.id.deleteChallenge)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                dismiss();
                ChallengeDAO dao = new ChallengeDAO(context);
                try {
                    dao.open();
                    TextView idView = (TextView)clickedLayout.getChildAt(0);
                    dao.deleteChallenge(Integer.parseInt(idView.getText().toString()));
                    Toast.makeText(context, "challenge #" + Integer.parseInt(idView.getText().toString()) + "deleted", Toast.LENGTH_SHORT).show();
                    dao.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                ((ViewGroup) clickedLayout.getParent()).removeView(clickedLayout);
            }
        });
        ((Button) view.findViewById(R.id.startChallenge)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                dismiss();
                ChallengeDAO dao = new ChallengeDAO(context);
                try {
                    dao.open();
                    TextView idView = (TextView)clickedLayout.getChildAt(0);
                    Challenge challenge = dao.selectOneChallenge(Integer.parseInt(idView.getText().toString()));
                    Calendar today = Calendar.getInstance();
                    today.setTime(Calendar.getInstance().getTime());
                    challenge.setStartDate(today);
                    dao.modifyChallenge(Integer.parseInt(idView.getText().toString()), challenge);
                    TextView startDateOfTheChallenge = (TextView) clickedLayout.getChildAt(2);
                    startDateOfTheChallenge.setText(DateFormat.getDateInstance().format(today.getTime()) + " : " + challenge.getChallengesList().get(0));
                    Toast.makeText(context, "challenge #" + Integer.parseInt(idView.getText().toString()) + "started", Toast.LENGTH_SHORT).show();
                    dao.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }

//                Calendar calendar = Calendar.getInstance();
//                calendar.set(Calendar.HOUR_OF_DAY, 21);
//                calendar.set(Calendar.MINUTE, 33);
//                NotificationPlanner.changeNotifDate(calendar);
//                NotificationPlanner.start(context);
                NotificationIntentService ser = new NotificationIntentService();
                ser.sendNotif(context);

            }
        });

        ((Button) view.findViewById(R.id.modifyChallenge)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                dismiss();
                Intent createNewActivity = new Intent(context, CreateNewChallenge.class);
                ChallengeDAO dao = new ChallengeDAO(context);
                try {
                    dao.open();
                    TextView idView = (TextView) clickedLayout.getChildAt(0);
                    Challenge challenge = dao.selectOneChallenge(Integer.parseInt(idView.getText().toString()));
                    createNewActivity.putExtra("modifyChallenge", challenge);
                }catch (Exception e){
                    e.printStackTrace();
                }
                startActivity(createNewActivity);
            }
        });

        ((Button) view.findViewById(R.id.validateChallenge)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                dismiss();
                ChallengeDAO dao = new ChallengeDAO(context);
                try {
                    dao.open();
                    TextView idView = (TextView)clickedLayout.getChildAt(0);
                    Challenge challenge = dao.selectOneChallenge(Integer.parseInt(idView.getText().toString()));
                    Calendar dayDone = challenge.getStartDate();
                    switch(challenge.getFrequency()) {
                        case DAILY:
                            dayDone.add(Calendar.DATE, 1);
                            break;
                        case WEEKLY:
                            dayDone.add(Calendar.WEEK_OF_YEAR, 1);
                            break;
                        case MONTHLY:
                            dayDone.add(Calendar.MONTH, 1);
                            break;
                    }

                    ArrayList<String> oldChallengeList = challenge.getChallengesList();
                    System.out.println(oldChallengeList.get(0));
                    oldChallengeList.remove(0);
                    if(oldChallengeList.size() != 0) {
                        challenge.setChallengesList(oldChallengeList);
                        dao.modifyChallenge(Integer.parseInt(idView.getText().toString()), challenge);
                        TextView startDateOfTheChallenge = (TextView) clickedLayout.getChildAt(2);
                        startDateOfTheChallenge.setText(DateFormat.getDateInstance().format(dayDone.getTime()) + " : " + challenge.getChallengesList().get(0));
                    }
                    else{
                        dao.deleteChallenge(Integer.parseInt(idView.getText().toString()));
                        ((ViewGroup) clickedLayout.getParent()).removeView(clickedLayout);
                    }
                    dao.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        return view;
    }

    public void setClickedLayout(View l){
        clickedLayout = (LinearLayout)l;
        TextView idView = (TextView)clickedLayout.getChildAt(0);
        challengeId = Integer.parseInt(idView.getText().toString());
    }

    public void setContext(Context c){
        context = c;
    }
}
