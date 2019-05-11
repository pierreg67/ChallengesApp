package com.challenges.pierreg.challengesapp;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Random;

/**
 * Created by pierreg on 04/04/2019.
 */

public class CreateNewChallenge extends AppCompatActivity {

    private int challengeDuration = 1;
    private Button buttonValidateChallenge;
    private EditText editMainName;
    private int duration = 1;
    private List<EditText> challengesListEditor = new ArrayList<EditText>() {};
    private Resources res;
    private LinearLayout linearLayout;
    private int challengeId = -1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.createchallengepanel);
        res = getResources();

        linearLayout = (LinearLayout) findViewById(R.id.layoutEveryDay);

        editMainName = (EditText) findViewById(R.id.editMainName);



        buttonValidateChallenge = (Button) findViewById(R.id.buttonValidation);
        buttonValidateChallenge.setOnClickListener(validateChallenge);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.actionFab);

        Intent intent = getIntent();
        Challenge existingChallenge = null;
        if (intent != null){
            existingChallenge = intent.getParcelableExtra("modifyChallenge");
        }

        fab.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                duration++;
                addOneAction("Enter action");
            }
        });

        if(existingChallenge == null){
            addAllActions();
        }
        else{
            editMainName.setText(existingChallenge.getName());
            //TODO : adapt frequency : existingChallenge.getFrequency();
            challengeId = existingChallenge.getId();
            duration = existingChallenge.getDuration();
            addAllAction(existingChallenge);
        }
    }

    private View.OnClickListener validateChallenge = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            ArrayList<String> challengesList = new ArrayList<>();
            for (EditText text : challengesListEditor) {
                challengesList.add(text.getText().toString());
            }
//            ArrayList fakeList = new ArrayList(){};
//            fakeList.add("pompes");
//            fakeList.add("abdos");
//            fakeList.add("squat");
//            Challenge newChallenge = new Challenge(-1, "name", 3, FrequencyEnum.DAILY, new GregorianCalendar(2019, 6, 29), fakeList);
            // TODO : Remove + change date
            Challenge newChallenge = new Challenge(-1, editMainName.getText().toString(), duration, FrequencyEnum.DAILY, new GregorianCalendar(2019, 6, 29), challengesList);
            Intent challengeActivity = new Intent(CreateNewChallenge.this, MainActivity.class);

            try {
                ChallengeDAO dao = new ChallengeDAO(CreateNewChallenge.this);
                dao.open();
                if(challengeId == -1){
                    dao.addChallenge(newChallenge);
                }
                else{
                    dao.modifyChallenge(challengeId, newChallenge);
                }
                startActivity(challengeActivity);
                Challenge test = dao.selectOneChallenge(0);
                Toast.makeText(CreateNewChallenge.this, test.getName() + "challenge created!! for " + test.getDuration() + " " + test.getFrequency().name(), Toast.LENGTH_SHORT).show();
                dao.close();
            } catch (Exception e) {
                System.out.println("Message : " + e.getMessage());
                System.out.println("Cause : " + e.getCause()); //TODO : TOAST
            }

        }
    };

    private void addAllActions() {
        for (int i = 0; i < challengeDuration; i++) {
            String action = "??--??";
            addOneAction(action);
        }
    }

    private void setTransparency(View v, String transparency) {
        int color = ((ColorDrawable)v.getBackground()).getColor();
        String hexColor = String.format("#%06X", (0xFFFFFF & color));
        hexColor = "#" + transparency + hexColor.substring(1);
        v.setBackgroundColor(Color.parseColor(hexColor));
    }

    private LinearLayout addOneAction(String action){
        final EditText editText = new EditText(this);
        if(action.equals("??--??")){
            editText.setHint("Enter action");
        }
        else{
            editText.setText(action);
        }
        editText.setInputType(InputType.TYPE_CLASS_TEXT);
        editText.setMaxLines(1);
        editText.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        editText.setPadding(20, 20, 20, 20);
        challengesListEditor.add(editText);

        LinearLayout.LayoutParams layoutParam = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        layoutParam.setMargins(10, 10, 10, 10);

        LinearLayout layoutOneChallenge = new LinearLayout(this);
        layoutOneChallenge.setClickable(false);
        layoutOneChallenge.setOrientation(LinearLayout.VERTICAL);
        layoutOneChallenge.setPadding(10, 10, 10, 10);
        Random random = new Random();
        layoutOneChallenge.setBackgroundColor(Color.argb(255, 64 + random.nextInt(192), 128 + random.nextInt(128), 255));
        layoutOneChallenge.addView(editText, layoutParam);
        linearLayout.addView(layoutOneChallenge, layoutParam);
        return layoutOneChallenge;
    }

    private void addAllAction(Challenge previousChallenges){
        for (int i = 0; i < previousChallenges.getDuration(); i++) {
            String action = previousChallenges.getChallengesList().get(i);
            addOneAction(action);
        }
    }
}