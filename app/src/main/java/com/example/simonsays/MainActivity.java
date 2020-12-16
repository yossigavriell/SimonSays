package com.example.simonsays;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Button newGameBtn, scoreBtn, exitBtn;
    private Animation myAnim;
    private MediaPlayer mediaPlayer;
    // Use bounce interpolator with amplitude 0.2 and frequency 20
    private MyBounceInterpolator interpolator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.simonsongshort);
        setContentView(R.layout.activity_main);
        newGameBtn = findViewById(R.id.newGame_btn);
        scoreBtn = findViewById(R.id.score_btn);
        exitBtn = findViewById(R.id.exit_btn);
        myAnim=AnimationUtils.loadAnimation(this, R.anim.bounce);
        interpolator = new MyBounceInterpolator(0.2, 20);
        myAnim.setInterpolator(interpolator);

        mediaPlayer.start();
        newGameBtn.startAnimation(myAnim);
        scoreBtn.startAnimation(myAnim);
        exitBtn.startAnimation(myAnim);

        newGameBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(MainActivity.this,GameScreenActivity.class);
                startActivity(intent);
                finish();


            }
        });

        scoreBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this, ScoreActivity.class);
                startActivity(intent);
                finish();

            }
        });

        exitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                onBackPressed();
            }
        });
    }

    @Override
    public void onBackPressed() {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            alertDialogBuilder.setTitle(getText(R.string.exit_app).toString()+"?");
            alertDialogBuilder
                    .setMessage(getText(R.string.click_exit).toString()+"!")
                    .setCancelable(false)
                    .setPositiveButton(getText(R.string.yes).toString(),
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    moveTaskToBack(true);
                                    android.os.Process.killProcess(android.os.Process.myPid());
                                    System.exit(1);
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


    @Override
    protected void onPause() {
        super.onPause();
        mediaPlayer.stop();
        mediaPlayer.release();

    }

    public void didTapButton(View view) {

        view.startAnimation(myAnim);
    }

}


