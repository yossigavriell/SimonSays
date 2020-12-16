package com.example.simonsays;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class DrumActivity extends AppCompatActivity {


    private TextView turnTv,scoreTv;
    private Button[] buttons;
    private MediaPlayer[] sounds;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drum);

        buttons = BoardFactory.getInstance().getButtons();
//        boardLogic = BoardLogic.getInstance();

        scoreTv =findViewById(R.id.score_player_tv);
        turnTv = findViewById(R.id.name);

        buttons[0] = findViewById(R.id.drum1);
        buttons[1] = findViewById(R.id.drum2);
        buttons[2] = findViewById(R.id.drum3);
        buttons[3] = findViewById(R.id.drum4);
        buttons[4] = findViewById(R.id.drum5);

        sounds = new MediaPlayer[buttons.length];
        sounds[0] = MediaPlayer.create(DrumActivity.this, R.raw.drum_sound1);
        sounds[1] = MediaPlayer.create(DrumActivity.this, R.raw.drum_sound2);
        sounds[2] = MediaPlayer.create(DrumActivity.this, R.raw.drum_sound3);
        sounds[3] = MediaPlayer.create(DrumActivity.this, R.raw.drum_sound4);
        sounds[4] = MediaPlayer.create(DrumActivity.this, R.raw.drum_sound5);

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
                                Intent intent =new Intent(DrumActivity.this,MainActivity.class);
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
