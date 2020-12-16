package com.example.simonsays;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class XylophoneActivity extends AppCompatActivity{

    private TextView turnTv,scoreTv;
    private Button[] buttons;
    private MediaPlayer[] sounds;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xylophone);

        buttons = BoardFactory.getInstance().getButtons();

        scoreTv = findViewById(R.id.score_player_tv);
        turnTv = findViewById(R.id.name);

        buttons[0] = findViewById(R.id.a_key);
        buttons[1] = findViewById(R.id.b_key);
        buttons[2] = findViewById(R.id.c_key);
        buttons[3] = findViewById(R.id.d_key);
        buttons[4] = findViewById(R.id.e_key);
        buttons[5] = findViewById(R.id.f_key);
        buttons[6] = findViewById(R.id.g_key);

        sounds = new MediaPlayer[buttons.length];
        sounds[0] = MediaPlayer.create(XylophoneActivity.this,R.raw.xylo1);
        sounds[1] = MediaPlayer.create(XylophoneActivity.this,R.raw.xylo2);
        sounds[2] = MediaPlayer.create(XylophoneActivity.this,R.raw.xylo3);
        sounds[3] = MediaPlayer.create(XylophoneActivity.this,R.raw.xylo4);
        sounds[4] = MediaPlayer.create(XylophoneActivity.this,R.raw.xylo5);
        sounds[5] = MediaPlayer.create(XylophoneActivity.this,R.raw.xylo6);
        sounds[6] = MediaPlayer.create(XylophoneActivity.this,R.raw.xylo7);

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
                                Intent intent =new Intent(XylophoneActivity.this,MainActivity.class);
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
