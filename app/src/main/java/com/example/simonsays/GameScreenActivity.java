package com.example.simonsays;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ViewFlipper;

import fr.ganfra.materialspinner.MaterialSpinner;

public class GameScreenActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private BoardFactory boardFactory;
    private ImageView imageViewDrum, imageViewPiano, imageViewXylophone, imageViewSimon;
    private String boardName, difficult;
    private String[] nameOfBoards;
    private MaterialSpinner difficultSpinner;
    private Button playBtn, backBtn, nextBtn, prevBtn;
    private ArrayAdapter adapter;
    private int counterBoard = 0;
    private int numOfBoards = 4;
    private Animation myAnim;
    private ViewFlipper viewFlipper;

    // Use bounce interpolator with amplitude 0.2 and frequency 20
    MyBounceInterpolator interpolator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_screen);

        nextBtn =findViewById(R.id.next_btn);
        prevBtn = findViewById(R.id.prev_btn);
        playBtn = findViewById(R.id.play_btn);
        difficultSpinner = findViewById(R.id.spinner);
        backBtn = findViewById(R.id.back_btn);
        viewFlipper =findViewById(R.id.flip_a);

        myAnim=AnimationUtils.loadAnimation(this, R.anim.bounce);
        interpolator = new MyBounceInterpolator(0.2, 20);
        myAnim.setInterpolator(interpolator);


        playBtn.startAnimation(myAnim);
        backBtn.startAnimation(myAnim);
        difficultSpinner.startAnimation(myAnim);

        boardFactory = BoardFactory.getInstance();
        adapter = ArrayAdapter.createFromResource(this, R.array.difficultSpinner, R.layout.color_spinner_layout);
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_layout);
        difficultSpinner.setAdapter(adapter);
        difficultSpinner.setOnItemSelectedListener(GameScreenActivity.this);
        animationFlipper();
        boardName = nameOfBoards[0];



        playBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startGame();

            }
        });


        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle(getText(R.string.back_key_dialog).toString()+"?");
        alertDialogBuilder
                .setMessage(getText(R.string.sure_sentece).toString()+"!")
                .setCancelable(false)
                .setPositiveButton(getText(R.string.yes).toString(),
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Intent intent = new Intent(GameScreenActivity.this, MainActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        })

                .setNegativeButton(getText(R.string.no).toString(), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        dialog.cancel();
                    }
                });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    public void animationFlipper() {

        nameOfBoards = new String[4];
        nameOfBoards[0] = "normal";
        nameOfBoards[1] = "drum";
        nameOfBoards[2] = "piano";
        nameOfBoards[3] = "xylophone";

        imageViewDrum = findViewById(R.id.image_drum);
        imageViewPiano = findViewById(R.id.image_piano);
        imageViewXylophone = findViewById(R.id.image_xylophone);
        imageViewSimon = findViewById(R.id.image_simon);
        nextBtn = findViewById(R.id.next_btn);
        prevBtn = findViewById(R.id.prev_btn);


        final ViewFlipper boardType_VP = findViewById(R.id.flip_a);
        final Animation in = AnimationUtils.loadAnimation(this, android.R.anim.slide_in_left);
        final Animation out = AnimationUtils.loadAnimation(this, android.R.anim.slide_out_right);

        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                boardType_VP.setInAnimation(out);
                boardType_VP.setInAnimation(in);
                boardType_VP.showNext();
                counterBoard++;
                boardName = nameOfBoards[counterBoard % numOfBoards];


            }
        });

        AnimationUtils.loadAnimation(this, android.R.anim.slide_in_left);
        AnimationUtils.loadAnimation(this, android.R.anim.slide_out_right);

        prevBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                boardType_VP.showPrevious();
                if (counterBoard == 0) {
                    counterBoard = (numOfBoards);
                    boardName = nameOfBoards[counterBoard-1];
                }
                    counterBoard--;
                boardName = nameOfBoards[counterBoard % numOfBoards];

            }
        });

    }


    public void startGame() {
        boardFactory.setTypeBoard(boardName);
        boardFactory.setDifficult(difficult);
        boardFactory.setContext(this);
        boardFactory.boardInitialize();

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


        String tempString = parent.getItemAtPosition(position).toString();

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

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {


    }

    public void didTapButton(View view) {

        view.startAnimation(myAnim);
    }





}

