package com.example.simonsays;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Map;

public class GameOverActivity extends AppCompatActivity {

    private Button tryAgainBtn, NoBtn;
    private Animation myAnim;
    // Use bounce interpolator with amplitude 0.2 and frequency 20
    private MyBounceInterpolator interpolator;
    private SharedPreferences settings_sp;
    private BoardFactory board = BoardFactory.getInstance();
    private SharedPreferences.Editor editor;
    private int bestScore = 0;
    private String bestScorePlayerName = "";
    private String boardTypeAndDifficult = BoardFactory.getInstance().getTypeBoard() + "_" + BoardFactory.getInstance().getDifficult();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over);

        myAnim= AnimationUtils.loadAnimation(this, R.anim.bounce);
        interpolator = new MyBounceInterpolator(0.2, 20);
        myAnim.setInterpolator(interpolator);


        NoBtn =findViewById(R.id.back_btn_game_over);
        tryAgainBtn = findViewById(R.id.try_again_tv);

        NoBtn.startAnimation(myAnim);
        tryAgainBtn.startAnimation(myAnim);

        String name= getIntent().getStringExtra("name");
        int score = getIntent().getIntExtra("score", 0);

        board.setPlayer(name);
        String highScorePrefName = board.getPlayer().getName() + "_" + boardTypeAndDifficult + "_" + "HIGH_SCORE";
        //yossi_normal_easy_HIGH_SCORE
        //yarin_normal_easy_HIGH_SCORE

//        SP looks like this:
//        {yossi_normal_easy_HIGH_SCORE, 5}
//        {yarin_normal_easy_HIGH_SCORE, 6}


        settings_sp = getSharedPreferences("GAME DATA", Context.MODE_PRIVATE);
        int highScore = settings_sp.getInt(highScorePrefName, 0);
        getBestScoreOfAllPlayers();

        //we need to take highScore and to put it in table score

        if (score > highScore) {
            //Save
            editor = settings_sp.edit();
            editor.putInt(highScorePrefName, score);
            editor.commit();
        }


    }


    private void getBestScoreOfAllPlayers() {
        settings_sp = getSharedPreferences("GAME DATA", Context.MODE_PRIVATE);
        Map<String, ?> highScores = settings_sp.getAll();

        for (String playerScoreStr : highScores.keySet()) {
            try {
                if (playerScoreStr.contains(boardTypeAndDifficult)) {
                    int curr = (Integer) highScores.get(playerScoreStr);
                    if (curr > bestScore) {
                        bestScore = curr;
                        int index = playerScoreStr.indexOf("_");
                        bestScorePlayerName = playerScoreStr.substring(0, index);
                    }
                }
            } catch (NumberFormatException e) {
            }
        }
    }


        public void tryAgain(View view)
        {

            BoardFactory.getInstance().boardInitialize();
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
                                Intent intent =new Intent(GameOverActivity.this,MainActivity.class);
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


    public void backButton(View view) {

        Intent intent = new Intent(GameOverActivity.this,MainActivity.class);
        startActivity(intent);
        finish();
    }
}
