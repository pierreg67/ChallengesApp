package com.challenges.pierreg.challengesapp;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private LinearLayout linearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        linearLayout = (LinearLayout) findViewById(R.id.challengesLayout);
        linearLayout.setPadding(10, 10, 10, 10);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent createNewActivity = new Intent(MainActivity.this, CreateNewChallenge.class);
                startActivity(createNewActivity);
            }
        });

        List<Challenge> allChallenges = null;
        try {
            ChallengeDAO dao = new ChallengeDAO(getBaseContext());
            dao.open();
            allChallenges = dao.selectAllChallenges();
            dao.close();
        } catch (Exception e) {
            System.out.println("unable to create ChallengeActivity because of database failure");
            e.printStackTrace();
        }
        if(allChallenges==null || allChallenges.size() == 0){
            return;
        }

        for(int i=0; i<allChallenges.size(); i++) {
            Challenge challengeToShow = allChallenges.get(i);

            TextView IdOfChallenge = new TextView(this);
            IdOfChallenge.setText(String.valueOf(challengeToShow.getId()));

            TextView nameOfChallenge = new TextView(this);
            nameOfChallenge.setText(challengeToShow.getName());

            TextView challengeOfTheDay = new TextView(this);
            Calendar dateOfTheChallenge = challengeToShow.getStartDate();
            challengeOfTheDay.setText(DateFormat.getDateInstance().format(dateOfTheChallenge.getTime()) + " : " + challengeToShow.getChallengesList().get(0));
            challengeOfTheDay.setPadding(0, 30, 0, 0);
            challengeOfTheDay.setGravity(Gravity.RIGHT);

            LinearLayout.LayoutParams layoutParam = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            layoutParam.setMargins(10, 10, 10, 10);

            LinearLayout.LayoutParams layoutParamInvisible = new LinearLayout.LayoutParams(0,0);


            LinearLayout layoutOneChallenge = new LinearLayout(this);
            layoutOneChallenge.setClickable(true);
            layoutOneChallenge.setOnClickListener(fireModificationChallengeDialogue);
            layoutOneChallenge.setOrientation(LinearLayout.VERTICAL);
            layoutOneChallenge.setPadding(10, 10, 10, 10);
            Random random = new Random();
            layoutOneChallenge.setBackgroundColor(Color.argb(255, 64 + random.nextInt(192), 128 + random.nextInt(128), 255));
            layoutOneChallenge.addView(IdOfChallenge, layoutParam);
            layoutOneChallenge.addView(nameOfChallenge, layoutParam);
            layoutOneChallenge.addView(challengeOfTheDay, layoutParam);
            linearLayout.addView(layoutOneChallenge, layoutParam);
//            GridLayout layout = new ChallengeTab(this, challengeToShow);
//
//            LinearLayout linear = (LinearLayout)findViewById(R.id.challengesLayout);
//            linear.addView(layout);
        }


    }

    private View.OnClickListener fireModificationChallengeDialogue = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            Fragment prev = getFragmentManager().findFragmentByTag("dialog");
            if (prev != null) {
                ft.remove(prev);
            }
            ft.addToBackStack(null);
            ChallengeModificationDialogue dialogFragment = new ChallengeModificationDialogue(); //TODO : pass v as param to personalize dialogue box
            dialogFragment.setClickedLayout(v);
            dialogFragment.setContext(MainActivity.this);
            dialogFragment.show(ft, "dialog");
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
