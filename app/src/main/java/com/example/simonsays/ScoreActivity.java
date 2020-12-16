package com.example.simonsays;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.mtp.MtpConstants;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class ScoreActivity extends AppCompatActivity {

    private SharedPreferences settings_sp;
    private String boardTypeAndDifficult;
    private String boardType, difficult;
    private Spinner spinnerBoard, spinnerDifficult;
    private Map<String, Integer> playersScore = new HashMap<>();
    private String currPlayerName;
    private Integer currScore;
    private int index;
    private ListView listView;
    private MyAdapter myAdapter;
    private Button backBtn;
    private ValueComparator bvc = new ValueComparator(playersScore);
    private TreeMap<String, Integer> sorted_map = new TreeMap<String, Integer>(bvc);
    private Animation myAnim;
    // Use bounce interpolator with amplitude 0.2 and frequency 20
    private MyBounceInterpolator interpolator;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);


        spinnerBoard = findViewById(R.id.spinnerBoard);
        spinnerDifficult = findViewById(R.id.spinnerDifficult);
        listView = findViewById(R.id.listView_records);
        backBtn=findViewById(R.id.back_btn);

        myAnim= AnimationUtils.loadAnimation(this, R.anim.bounce);
        interpolator = new MyBounceInterpolator(0.2, 20);
        myAnim.setInterpolator(interpolator);

        backBtn.startAnimation(myAnim);

        settings_sp = getSharedPreferences("GAME DATA", Context.MODE_PRIVATE);

        ArrayList<String> difficultArrayList = new ArrayList<>();
        difficultArrayList.add(getResources().getString(R.string.beginner));
        difficultArrayList.add(getResources().getString(R.string.amateur));
        difficultArrayList.add(getResources().getString(R.string.professional));
        ArrayAdapter<String> difficultArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, difficultArrayList);
        difficultArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDifficult.setAdapter(difficultArrayAdapter);
        spinnerDifficult.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                String tempString=spinnerDifficult.getItemAtPosition(position).toString();

                if (tempString.equalsIgnoreCase(getApplicationContext().getString(R.string.beginner)))
                {
                    tempString="beginner";
                }else if (tempString.equalsIgnoreCase(getApplicationContext().getString(R.string.amateur)))
                {
                    tempString="amateur";
                }else if (tempString.equalsIgnoreCase(getApplicationContext().getString(R.string.professional)))
                {
                    tempString="professional";
                }

                difficult = tempString;
                boardTypeAndDifficult = boardType + "_" + difficult;
                getScoreOfAllPlayers();
                myAdapter = new MyAdapter(ScoreActivity.this,sorted_map);
                listView.setAdapter(myAdapter);
                myAdapter.notifyDataSetChanged();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        ArrayList<String> boardsArrayList = new ArrayList<>();
        boardsArrayList.add(getResources().getString(R.string.normal));
        boardsArrayList.add(getResources().getString(R.string.drum));
        boardsArrayList.add(getResources().getString(R.string.piano));
        boardsArrayList.add(getResources().getString(R.string.xylophone));
        ArrayAdapter<String> boardsArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, boardsArrayList);
        boardsArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerBoard.setAdapter(boardsArrayAdapter);
        spinnerBoard.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                String tempString=spinnerBoard.getItemAtPosition(position).toString();

                if (tempString.equalsIgnoreCase(getApplicationContext().getString(R.string.normal)))
                {
                    tempString="normal";
                }else if (tempString.equalsIgnoreCase(getApplicationContext().getString(R.string.drum)))
                {
                    tempString="drum";
                }else if (tempString.equalsIgnoreCase(getApplicationContext().getString(R.string.piano)))
                {
                    tempString="piano";
                }else if (tempString.equalsIgnoreCase(getApplicationContext().getString(R.string.xylophone)))
                {
                    tempString="xylophone";
                }

                boardType = tempString;
                boardTypeAndDifficult = boardType + "_" + difficult;
                getScoreOfAllPlayers();
                myAdapter = new MyAdapter(ScoreActivity.this,sorted_map);
                listView.setAdapter(myAdapter);
                myAdapter.notifyDataSetChanged();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(ScoreActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });

    }


    private void getScoreOfAllPlayers() {
        settings_sp = getSharedPreferences("GAME DATA", Context.MODE_PRIVATE);
        Map<String, ?> highScores = settings_sp.getAll();
        playersScore.clear();

        for (String playerScoreStr : highScores.keySet()) {
            try {
                if (playerScoreStr.contains(boardTypeAndDifficult.toLowerCase())) {
                    currScore = (Integer) highScores.get(playerScoreStr);
                    index = playerScoreStr.indexOf("_");
                    currPlayerName = playerScoreStr.substring(0, index);
                    playersScore.put(currPlayerName, currScore);
                }
            } catch (NumberFormatException e) {
            }
        }

        bvc = new ValueComparator(playersScore);
        sorted_map = new TreeMap<>(bvc);
        sorted_map.putAll(playersScore);
    }

    @Override
    public void onBackPressed()
    {

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle(getText(R.string.menu_back).toString());
        alertDialogBuilder
                .setMessage(getText(R.string.brings_out).toString())
                .setCancelable(false)
                .setPositiveButton(getText(R.string.lo_rotze).toString(),
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Intent intent =new Intent(ScoreActivity.this,MainActivity.class);
                                startActivity(intent);
                                finish();

                            }
                        })

                .setNegativeButton(getText(R.string.rotze).toString(), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        dialog.cancel();
                    }
                });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }
}

