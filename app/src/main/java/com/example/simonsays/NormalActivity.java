package com.example.simonsays;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class NormalActivity extends AppCompatActivity{


    private TextView turnTv,scoreTv;
    private Button[] buttons;
    private MediaPlayer[] sounds;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_normal);

        buttons = BoardFactory.getInstance().getButtons();


        scoreTv =findViewById(R.id.score_player);
        turnTv = findViewById(R.id.normal_player_tv);

        buttons[0] = findViewById(R.id.yellow_button);
        buttons[1] = findViewById(R.id.green_button);
        buttons[2] = findViewById(R.id.blue_button);
        buttons[3] = findViewById(R.id.red_button);

        sounds = new MediaPlayer[buttons.length];
        sounds[0] = MediaPlayer.create(NormalActivity.this,R.raw.sound_green);
        sounds[1] = MediaPlayer.create(NormalActivity.this,R.raw.sound_red);
        sounds[2] = MediaPlayer.create(NormalActivity.this,R.raw.sound_yellow);
        sounds[3] = MediaPlayer.create(NormalActivity.this,R.raw.sound_blue);


        BoardFactory.getInstance().setSounds(sounds);
        BoardFactory.getInstance().setButtons(buttons);
        BoardFactory.getInstance().setContext(this);
        BoardFactory.getInstance().logicInitialize(turnTv,scoreTv);
    }

    @Override
    protected void onStart() {
        super.onStart();
        BoardFactory.getInstance().getGameLogic().simonPlay();


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
                                Intent intent =new Intent(NormalActivity.this,MainActivity.class);
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

